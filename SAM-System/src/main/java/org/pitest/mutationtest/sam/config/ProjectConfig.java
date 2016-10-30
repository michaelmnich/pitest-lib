package org.pitest.mutationtest.sam.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michał Mnich on 25.10.2016.
 */
public class ProjectConfig {
public List<String> cp;
    public ProjectConfig() {
      cp =  new ArrayList<String>();
       File fin = new File( System.getProperty("user.dir"), "mainconfig.cfg");
        try {
            readConfig(fin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//
//    "D:\\Doktorat\\pitcmd\\pitest-1.1.11-SNAPSHOT.jar",
//            "D:\\Doktorat\\pitcmd\\pitest-command-line-1.1.11-SNAPSHOT.jar",
//            "C:\\junit\\hamcrest-core-1.3.jar",
//            "C:\\junit\\junit-4.12.jar",
//            "D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\test-classes\\",
//            "D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\classes\\"
//

    public void readConfig(File fin) throws IOException {
        // Construct BufferedReader from FileReader
        BufferedReader br = new BufferedReader(new FileReader(fin));

        String line = null;
        boolean isConfigBegined = false;
        while ((line = br.readLine()) != null) {
            if(line.equals("<HeareConfigStarts>")) isConfigBegined = true;
            if( !line.equals("") && (isConfigBegined && !(line.substring(0,2).equals("//"))) ){ //substring ma złożoność  O(n)
                if(line.contains("PIT_JAR_DIR")) {this.cp.add(line.split(";")[1]);}
                else if(line.contains("PIT_CMD_JAR_DIR")) {this.cp.add(line.split(";")[1]);}
                else if(line.contains("HAMCREST_DIR")) {this.cp.add(line.split(";")[1]);}
                else if(line.contains("JUNIT_DIR")) {this.cp.add(line.split(";")[1]);}
                else if(line.contains("PROJECT_CLASS_DIR")) {this.cp.add(line.split(";")[1]);}
                else if(line.contains("PROJECT_TEST_CLASS_DIR")) {this.cp.add(line.split(";")[1]);}

            }

        }

        br.close();
    }
}
