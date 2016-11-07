package org.pitest.mutationtest.sam.web;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by gosc on 07.11.2016.
 */
public class SocketClient {

    public SocketClient(){

    }

    /**
     * Private coonection metod used in public Coonnectors
     */
    private void connect(String serverAddress){
        try {

            Socket s = null;

            String sentence="none";
            String modifiedSentence;
            BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
            Socket clientSocket = new Socket(serverAddress, 8081);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

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
                    while(true){
                            modifiedSentence = inFromServer.readLine();
                            System.out.println("FROM SERVER: " + modifiedSentence);
                        if(!inFromServer.ready()){
                            break;
                        }
                    }
                }



                System.out.println("dskajdhahdao");
            }


            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Manuall connect
     */
    public void Connsect(){
        String serverAddress = JOptionPane.showInputDialog(
                "Enter IP Address of a machine that is\n" +
                        "running the date service on port 9090:");
        this.connect(serverAddress);
    }

    /**
     * From Connfig Connect
     * @param connectionData Connection Data
     */
    public void Connsect(String connectionData){

    }
}
