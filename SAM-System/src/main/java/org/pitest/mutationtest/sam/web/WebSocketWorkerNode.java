package org.pitest.mutationtest.sam.web;

import org.pitest.mutationtest.sam.PitRunner;
import org.pitest.mutationtest.sam.config.IProjectMetaData;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Michał Mnich on 30.10.2016.
 *
 * Klasa odpowiadajaca za bycie workerem główna klasa noda
 */
public class WebSocketWorkerNode implements  ISerwer, SocketListener  {
    private  ServerSocket _serverSocket;
    private boolean _isrunning;
    private MyWorker _immputSocket; //seket wejsciowy oczekuje zleceń;
    private ArrayList<MySlaveWorkerNode> _outputSocket = new ArrayList<MySlaveWorkerNode>(); //sokety wyjsciowe nimi laczymy sie z innymi sam systemami i wysylamy im zlecenia
    private PitRunner _pitRunner;

    public WebSocketWorkerNode(){
        _isrunning = false;
        _immputSocket =null;
        _pitRunner = new PitRunner();
    }

    public void RunnPitStandAlone(IProjectMetaData metaData){
        System.out.println("SAM: INFO: Stand Alone Mutation request ");
        System.out.println("Start Mutation coverage-----------------------");
        System.out.println("INFO: Starting mutation coverage");
        _pitRunner.RunnMutation(metaData);
    }



    @Override
    //Metoda startowania serwera przyjmujacego reqesty do mutacji
    public void Start(Integer port)
    {
        try {
            _serverSocket = new ServerSocket(port);
            _isrunning =true;
            System.out.println("Serwer waiting for request on port:" +port);
            while(true){
                Socket tempImmputSocket = _serverSocket.accept();//Przychodzi nowe polaczenie
                if(_immputSocket == null){
                    _immputSocket = new MyWorker(tempImmputSocket);//tworze nowy worker mutacji dla przychodzcego polaczenia
                    _immputSocket.addListener(this);//przesylam temu workerowi samego siebie i teraz mozemy nasluchiwac eventu od tej klasy
                    _immputSocket.start();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    //Metoda Stopowania serwera przyjmujacego reqesty do mutacji
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
    public void ConnectSlaveNode(String adress, Integer port) { // to jest z SocketListener to jest event jak wraca cos od innych nodow do ktorych sie polaczylem

            MySlaveWorkerNode temp = new MySlaveWorkerNode(adress,port);
            _outputSocket.add(temp);
            temp.start();


    }

    @Override
    public void InfoReceived(SocketEvent event) {
        System.out.println("Przyloz Event ");
        if(event.info().equals("dupa testowa")){
           // _immputSocket.removeListener(this); //to niech robi dziecko
            _immputSocket=null;
        }
    }

    public void SendToAllConnectedNodes(String msg, IProjectMetaData metaData){
        for (MySlaveWorkerNode node: _outputSocket) {
            node.SendMessageToNode(msg,metaData);
        }
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //Ponizej gdy tworze(łaczę sie) z robolem !!!
    //KLASA Socketa Pod workera-----------------------------------------------------------------------------------------
    private class MySlaveWorkerNode extends  Thread
    {
        SocketClient socketDataSender;
        String ip;
        Integer port;

        public MySlaveWorkerNode(String adress, Integer port){
            this.ip =adress;
            this.port =port;
        }
        public void run()
        {
            try {
            //System.out.println("dupa debug");
            socketDataSender = new SocketClient();
            socketDataSender.Connsect(this.ip,this.port);
            }catch (Exception e){
                System.out.println("Connection error Try again");
               // e.printStackTrace();

            }
        }

        public void SendMessageToNode(String msg, IProjectMetaData metaData){
            socketDataSender.SendMessageToConnectedNOde(msg, metaData);
        }

    }
    //KLASA Socketa Pod workera-----------------------------------------------------------------------------------------

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //Ponizej gdy ja jestem robolem !!!
    //KLASA Workera lokalnego jesli utowrozny może przyjmować zadania---------------------------------------------------
    private class MyWorker extends  Thread
    {
        private Socket socket;
        private List _listeners = new ArrayList();
        public double Bandwich;//Numeryczna wartość obciązenia jaka może być wykożystana
        public MyWorker(Socket socket)
        {
            this.socket = socket;
        }


        public void run()
        {
            try
            {
               // BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));



                InputStream is = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                out.println("You are connected to SAM-SYSTEM Node"); //Tutaj moze odpowiedzec numerem noda i zasobami swoimi

                while (true) {
                    WebCommunicationProtocol to = (WebCommunicationProtocol) ois.readObject();
                    //String input = in.readLine();
                    String input = to.GetInfo();

                    if (input == null || input.equals("quit")) {
                        System.out.println("SMA: INFO: Master node send comand: "+input);
                        break; //wyskakuje z while i konczy nasluchiwanie komend
                    }

                    //Tutaj analiza komend przychadzacych od noda nadrzednego------------------------------------------
                    if(input.equals("PitRun")){
                       //Tutaj na przyklad odpalimy pita dla danego data
                        System.out.println("SAM: INFO: Master node send comand: "+input);
                        System.out.println("Start Mutation coverage-----------------------");
                        out.println("INFO: NOde Starting mutation coverage");
                        _pitRunner.RunnMutation(to.GetPitRunMetaData());
                    }else{
                        out.println(input.toUpperCase());
                    }

                    //Tutaj analiza komend przychadzacych od noda nadrzednego------------------------------------------


                }
            } catch (Exception e) {
                System.out.println("SAM: ERROR: Error handling Master node "+e);
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
            _listeners.add( l ); //tu dodajemy sobie obiekty, ktore beda sluchac naszego eventu
        }

        public synchronized void removeListener( SocketListener l ) {
            _listeners.remove( l );
        }
        private synchronized void _fireEvent() { // strzelamy eventem
            SocketEvent mood = new SocketEvent( this, "dupa testowa" );
            Iterator listeners = _listeners.iterator();
            while( listeners.hasNext() ) {
                ((SocketListener)listeners.next()).InfoReceived(mood);
            }
        }
    }
    //KLASA Workera lokalnego jesli utowrozny może przyjmować zadania---------------------------------------------------

}
