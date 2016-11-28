package org.pitest.mutationtest.sam.ui.console;

import org.pitest.mutationtest.sam.ui.Iui;
import org.pitest.mutationtest.sam.web.WebSocketSerwer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by gosc on 19.11.2016.
 */
public class ConsoleUi implements Iui{

    private WebSocketSerwer _workerSerwer;
    private  boolean  _isOn;
    private static Object _lock;
    private BufferedReader _cnsl;

    //Comands Strings-------------------------------

    String CCS_stoped = "SAM-System Console stoped";
    String CCS_start ="================================================================"+System.lineSeparator()+
                      "SAM-SYSTEM v 1.0"+System.lineSeparator()+
                      "================================================================";
    //Comands Strings-------------------------------


    public ConsoleUi(WebSocketSerwer workerSerwer) {
        _workerSerwer = workerSerwer;
        _isOn = true;
        _lock = new Object();
        _cnsl = new BufferedReader(new InputStreamReader(System.in));
        Runnable consoleImput = () -> {consoleImmputTask();};
        consoleImput.run();
    }

    private void consoleImmputTask(){
        try {
            synchronized (_lock){
                System.out.println(CCS_start);
                while (_isOn){
                        String comand = _cnsl.readLine();
                    switch (comand) {
                        case "test":
                           System.out.println("Test wykonany");
                            break;
                        case "connect":
                            System.out.println("Serwer adress: ");
                            String adress = _cnsl.readLine();
                            System.out.println("Serwer Port: ");
                            int port = Integer.parseInt(_cnsl.readLine());
                            _workerSerwer.ConnectClient(adress,port);
                            break;
                        case "start":
                            System.out.println("Serwer working port Port: ");
                            int port1 = Integer.parseInt(_cnsl.readLine());
                            _workerSerwer.Start(Integer.valueOf(port1));
                            break;
                        case "Send":
                            System.out.println("Message: ");
                            String msg = _cnsl.readLine();
                            _workerSerwer.SendToAllConnectedNodes(msg);
                            break;

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            _isOn = false;
            System.out.println(CCS_stoped);
        }

    }


    public void killConsole(){
        synchronized (_lock) {
            _isOn = false;
        }
    }


    @Override
    public void startSerwer(int port) {

    }

    @Override
    public void connectTo(String adress, int port) {

    }
}
