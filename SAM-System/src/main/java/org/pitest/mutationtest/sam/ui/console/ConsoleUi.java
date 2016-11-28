package org.pitest.mutationtest.sam.ui.console;

import org.pitest.mutationtest.sam.config.FromFileMetaData;
import org.pitest.mutationtest.sam.config.IProjectMetaData;
import org.pitest.mutationtest.sam.ui.Iui;
import org.pitest.mutationtest.sam.web.WebSocketWorkerNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by gosc on 19.11.2016.
 */
public class ConsoleUi implements Iui{

    private WebSocketWorkerNode _workerSerwer;
    private  boolean  _isOn;
    private static Object _lock;
    private BufferedReader _cnsl;

    //Comands Strings-------------------------------

    String CCS_stoped = "SAM-System Console stoped";
    String CCS_start ="================================================================"+System.lineSeparator()+
                      "SAM-SYSTEM v 1.0"+System.lineSeparator()+
                      "================================================================";
    //Comands Strings-------------------------------


    public ConsoleUi(WebSocketWorkerNode workerSerwer) {
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
                            this.connectTo(adress,port);
                            break;
                        case "start":
                            System.out.println("Serwer working port Port: ");
                            int port1 = Integer.parseInt(_cnsl.readLine());
                            this.startSerwer(Integer.valueOf(port1));
                            break;
                        case "run mutation":
                            IProjectMetaData tempData =new FromFileMetaData();//i to trzeba jakos ogarnac tutaj zabawa analityczna
                            //Przed wszystkim klasy trzeba wyciac osobno i do testów ilosc klas przez ilosc nodó i wywylayac jakos
                            _workerSerwer.SendToAllConnectedNodes("PitRun", tempData);
                            break;
                        case "Send":
                            System.out.println("Message: ");
                            String msg = _cnsl.readLine();
                            _workerSerwer.SendToAllConnectedNodes(msg, null);
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
        _workerSerwer.Start(port);
    }

    @Override
    public void connectTo(String adress, int port) {
        _workerSerwer.ConnectSlaveNode(adress,port);
    }

    @Override
    public void runnPit(IProjectMetaData data) {

    }
}
