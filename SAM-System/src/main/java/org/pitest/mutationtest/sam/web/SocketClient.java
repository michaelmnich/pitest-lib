package org.pitest.mutationtest.sam.web;

import org.pitest.mutationtest.sam.config.IProjectMetaData;

import java.io.*;
import java.net.Socket;

/**
 * Created by gosc on 07.11.2016.
 */
public class SocketClient extends Thread {

    private BufferedReader inFromServer;
    private ObjectOutputStream outToServer;
    private Socket clientSocket;
    public SocketClient(){

    }

    /**
     * Private coonection metod used in public Coonnectors
     */
    private void connect(String serverAddress, Integer port){
        try {

            clientSocket = new Socket(serverAddress, port);
            outToServer = new ObjectOutputStream (clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.start();




        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public void SendMessageToConnectedNOde(String sentence, IProjectMetaData metaData){
        try {

            if(sentence.equals("close")){
                System.out.println("Connection will be closed");
            }
            System.out.println("-----Send COmand to serwer");
            System.out.println("Comand is: "+sentence);
            //Test------------------------------
            IWebCommunicationProtocolData d = new WebCommunicationProtocol();
            d.SetInfo(sentence);
            d.SetPitRunMetadata(metaData);
            //Test------------------------------
            outToServer.writeObject(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void run(){
        try
        {

            String modifiedSentence;
            while(true){

                if(inFromServer.ready()){
                    modifiedSentence = inFromServer.readLine();
                    System.out.println("FROM SERVER: " + modifiedSentence);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    /**
     * From Connfig Connect
     * @param ip
     * @param port
     */
    public void Connsect(String ip, Integer port){
        try{
            this.connect(ip,port);
        }catch (Exception e){
            System.out.println("Connection error Try again");
          //  e.printStackTrace();
        }

    }
}
