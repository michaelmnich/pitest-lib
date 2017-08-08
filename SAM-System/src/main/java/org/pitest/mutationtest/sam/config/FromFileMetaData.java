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
    public FromFileMetaData() {
            this(System.getProperty("user.dir"), "metadata.ini");
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
}
