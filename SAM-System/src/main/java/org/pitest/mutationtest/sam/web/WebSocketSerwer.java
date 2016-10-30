package org.pitest.mutationtest.sam.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Michał Mnich on 30.10.2016.
 */
public class WebSocketSerwer implements  ISerwer, SocketListener  {
    private  ServerSocket _serverSocket;
    private boolean _isrunning;
    private MySocket _immputSocket;
    public WebSocketSerwer(){
        _isrunning = false;
        _immputSocket =null;
    }

    @Override
    public void Start(Integer port)
    {
        try {
            _serverSocket = new ServerSocket(port);
            _isrunning =true;

            while(true){
                Socket tempImmputSocket = _serverSocket.accept();
                if(_immputSocket == null){
                    _immputSocket = new MySocket(tempImmputSocket);
                    _immputSocket.addListener(this);
                    _immputSocket.start();

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void Stop()
    {
        try {
            _serverSocket.close();
            _isrunning = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void InfoReceived(SocketEvent event) {
        System.out.println("Przyloz Event ");
        if(event.mood().equals("dupa testowa")){
            _immputSocket.removeListener(this);
            _immputSocket=null;
        }
    }


    private class MySocket extends  Thread
    {
        private Socket socket;
        private List _listeners = new ArrayList();
        public MySocket(Socket socket)
        {
            this.socket = socket;
        }


        public void run()
        {
            try
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                out.println("You are connected to SAM-SYSTEM ");

                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals("quit")) {
                        break;
                    }
                    out.println(input.toUpperCase());
                }
            } catch (IOException e) {
                System.out.println("Error handling client "+e);
            } finally {
                try {
                    socket.close();

                } catch (IOException e) {
                    System.out.println("Couldn't close a socket, what's going on?");
                }
                _fireEvent();
                System.out.println("Connection with client  closed");

            }
        }
        public synchronized void addListener( SocketListener l ) {
            _listeners.add( l );
        }

        public synchronized void removeListener( SocketListener l ) {
            _listeners.remove( l );
        }
        private synchronized void _fireEvent() {
            SocketEvent mood = new SocketEvent( this, "dupa testowa" );
            Iterator listeners = _listeners.iterator();
            while( listeners.hasNext() ) {
                ((SocketListener)listeners.next()).InfoReceived(mood);
            }
        }
    }

}
