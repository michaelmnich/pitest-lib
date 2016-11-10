package org.pitest.mutationtest.sam.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by gosc on 07.11.2016.
 */
public class SocketClient extends Thread {

    private BufferedReader inFromServer;
    private DataOutputStream outToServer;
    private Socket clientSocket;
    public SocketClient(){

    }

    /**
     * Private coonection metod used in public Coonnectors
     */
    private void connect(String serverAddress, Integer port){
        try {

            Socket s = null;

            String sentence="none";

            BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
            clientSocket = new Socket(serverAddress, port);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.start();

            while(true){
                if(sentence.equals("close")){
                    System.out.println("Connection will be closed");
                    break;
                }
                System.out.println("-----Send COmand to serwer");
                sentence = inFromUser.readLine();
                System.out.println("Comand is: "+sentence);
                outToServer.writeBytes(sentence + '\n');

                if(!sentence.equals("")){

                }



                System.out.println("dskajdhahdao");
            }


            clientSocket.close();
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
        this.connect(ip,port);
    }
}
