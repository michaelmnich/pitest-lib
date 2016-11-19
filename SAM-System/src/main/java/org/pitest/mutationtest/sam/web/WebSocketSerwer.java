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
    private MySerwerSocket _immputSocket; //seket wejsciowy oczekuje zleceń;
    private MyClient _outputSocket; //sokety wyjsciowe nimi laczymy sie z innymi sam systemami i wysylamy im zlecenia
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
            System.out.println("Serwer wating for reqest on port:" +port);
            while(true){
                Socket tempImmputSocket = _serverSocket.accept();
                if(_immputSocket == null){
                    _immputSocket = new MySerwerSocket(tempImmputSocket);
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
    public void ConnectClient(String adress, Integer port) {
        _outputSocket = new MyClient(adress,port);
        _outputSocket.start();
    }

    @Override
    public void InfoReceived(SocketEvent event) {
        System.out.println("Przyloz Event ");
        if(event.info().equals("dupa testowa")){
           // _immputSocket.removeListener(this); //to niech robi dziecko
            _immputSocket=null;
        }
    }
    private class MyClient extends  Thread
    {
        SocketClient socketClient;
        String ip;
        Integer port;

        public MyClient(String adress, Integer port){
            this.ip =adress;
            this.port =port;
        }
        public void run()
        {
            System.out.println("dupa debug");
            socketClient = new SocketClient();
            socketClient.Connsect(this.ip,this.port);
        }

    }

    private class MySerwerSocket extends  Thread
    {
        private Socket socket;
        private List _listeners = new ArrayList();
        public MySerwerSocket(Socket socket)
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
                        System.out.println("SMA: INFO: Client send comand: "+input);
                        break; //wyskakuje z while i konczy nasluchiwanie komend
                    }
                    out.println(input.toUpperCase());
                }
            } catch (IOException e) {
                System.out.println("SMA: ERROR: Error handling client "+e);
            } finally {
                try {
                    socket.close();

                } catch (IOException e) {
                    System.out.println("SMA: ERROR: Couldn't close a socket, what's going on?");
                }
                _fireEvent();
                _listeners.clear();//ten soket jest zamykany i bedzie usuniety w serwerze moze sastapic go kolejny
                //Wobec tego nalezy odlaczyc dla pewności klase matke od listy listnerów i wszytko inne tez bo obiekt do usunieca
                //jest to martwy soket
                System.out.println("SMA: INFO: Connection with client closed Socket Closed");

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
