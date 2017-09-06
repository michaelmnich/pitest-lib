package org.pitest.mutationtest.sam.config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Michał Mnich on 25.10.2016.
 */
public class FromFileMetaData implements IProjectMetaData {
    private List<String> arguments;
    private List<String> _getClaspathAsAList;
    private List<FromFileMetaData> _confList;
    public String Classname="";
    public FromFileMetaData() {
        this(System.getProperty("user.dir"), "metadata.ini");
    }

    public FromFileMetaData(boolean bayes) {
        this(System.getProperty("user.dir"), "metadata.ini", bayes);
    }
    public FromFileMetaData(List<String> Arguments, List<String> GetClaspathAsAList, String classname) {
        arguments =Arguments;
        _getClaspathAsAList =GetClaspathAsAList;
        Classname =classname;
    }

    public FromFileMetaData(String path) {
        this(System.getProperty("user.dir"), "metadata.ini");
    }

    public FromFileMetaData(String dir, String configFile)  {
        try {
            File f =new File(dir,configFile);
            if(f.isFile()){
                BufferedReader br = new BufferedReader(new FileReader(f));

                String line = null;
                arguments = new ArrayList<String>();
                _getClaspathAsAList = new ArrayList<String>();

                int i =0;

                    while ((line = br.readLine()) != null) {
                        arguments.add(line);
                       // System.out.println(arguments.get(i).getClass());
                        if(i == 1){
                            if(!line.equals("")){

                                _getClaspathAsAList.add(line.split(",")[0]);
                                _getClaspathAsAList.add(line.split(",")[1]);
                            }
                        }
                        i++;
                    }

            }else{
                System.out.println("ERROR: There is no ini file: "+ f.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FromFileMetaData(String dir, String configFile, boolean bayes)  {
        try {
            File f =new File(dir,configFile);
            if(f.isFile()){
                BufferedReader br = new BufferedReader(new FileReader(f));

                String line = null;
                arguments = new ArrayList<String>();
                _getClaspathAsAList = new ArrayList<String>();

                int i =0;
                while ((line = br.readLine()) != null) {
                    arguments.add(line);
                    // System.out.println(arguments.get(i).getClass());
                    if(i == 1){
                        if(!line.equals("")){

                            _getClaspathAsAList.add(line.split(",")[0]);
                            _getClaspathAsAList.add(line.split(",")[1]);
                        }
                    }
                    i++;

                }

                if(bayes){
                    String classes ="";
                    String classes2 ="";
                    List<FromFileMetaData> confList = new ArrayList<>();
                    int j=0;
                    int k=0;
                    for (String arg : arguments ) {
                        j++;

                        if(arg.equals("--targetClasses")){
                            classes=  arguments.get(j);
                            break;
                        }
                    }
                        for (String arg : arguments ) {

                            k++;

                        if(arg.equals("--reportDir")){
                            classes2=  arguments.get(k);
                            break;
                        }
                    }

                    for (String cls:classes.split(",")) {
                         List<String> arg= new ArrayList<String>(arguments) ;
                         List<String> clspath=new ArrayList<String>(_getClaspathAsAList);
                        arg.set(j, cls);
                        arg.set(k, arg.get(k)+"\\\\"+cls);
                        confList.add(new FromFileMetaData(arg, clspath, cls));
                    }
                    _confList =confList;
                }

            }else{
                System.out.println("ERROR: There is no ini file: "+ f.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public String[] GetMetaData() {
        return  arguments.toArray(new String[arguments.size()]);
    }

    @Override
    public List<String> GetMetaDataList() {
        return arguments;
    }

    @Override
    public List<String> GetClaspathAsAList(){

        return _getClaspathAsAList;
    }

    @Override
    public List<FromFileMetaData> GetMetaDataAsAList() {
        return _confList;
    }


}
