package org.pitest.extensions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created by gosc on 27.08.2016.
 */
public class MutationConfig {


   private LinkedHashMap<String, MutantCofig> mutatorConfig = new LinkedHashMap<String, MutantCofig>();
//    mutatorConfig.put( "INVERT_NEGS", new ProbabilityEvaluator(100,50));
//        "INVERT_NEGS",
//        "RETURN_VALS",
//        "INLINE_CONSTS",
//        "MATH",
//        "VOID_METHOD_CALLS",
//        "NEGATE_CONDITIONALS",
//        "CONDITIONALS_BOUNDARY",
//        "INCREMENTS",
//        "REMOVE_INCREMENTS",
//        "NON_VOID_METHOD_CALLS",
//        "CONSTRUCTOR_CALLS",
//        "REMOVE_CONDITIONALS_EQ_IF",
//        "REMOVE_CONDITIONALS_EQ_ELSE",
//        "REMOVE_CONDITIONALS_ORD_IF",
//        "REMOVE_CONDITIONALS_ORD_ELSE",
//        "REMOVE_CONDITIONALS",
//        "EXPERIMENTAL_MEMBER_VARIABLE",
//        "EXPERIMENTAL_SWITCH",
//        "EXPERIMENTAL_ARGUMENT_PROPAGATION",
//        //Jakies dziwne grupy
//        "REMOVE_SWITCH",
//        "DEFAULTS",
//        "STRONGER",
//        "ALL" //hmmm?

    public void readConfig(File fin) throws IOException {
        // Construct BufferedReader from FileReader
        BufferedReader br = new BufferedReader(new FileReader(fin));

        String line = null;
        while ((line = br.readLine()) != null) {
            //System.out.println(line);
            if(line.contains("INVERT_NEGS")) { mutatorConfig.put("INVERT_NEGS", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("RETURN_VALS")) { mutatorConfig.put("RETURN_VALS", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains( "INLINE_CONSTS")) { mutatorConfig.put("INLINE_CONSTS", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("MATH")) { mutatorConfig.put("MATH", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("VOID_METHOD_CALLS")) { mutatorConfig.put("VOID_METHOD_CALLS", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("NEGATE_CONDITIONALS")) { mutatorConfig.put("NEGATE_CONDITIONALS", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("CONDITIONALS_BOUNDARY")) { mutatorConfig.put("CONDITIONALS_BOUNDARY", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("INCREMENTS")) { mutatorConfig.put("INCREMENTS", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("REMOVE_INCREMENTS")) { mutatorConfig.put("REMOVE_INCREMENTS", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("NON_VOID_METHOD_CALLS")) { mutatorConfig.put("NON_VOID_METHOD_CALLS", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("CONSTRUCTOR_CALLS")) { mutatorConfig.put("CONSTRUCTOR_CALLS", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("REMOVE_CONDITIONALS_EQ_IF")) { mutatorConfig.put("REMOVE_CONDITIONALS_EQ_IF", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("REMOVE_CONDITIONALS_EQ_ELSE")) { mutatorConfig.put("REMOVE_CONDITIONALS_EQ_ELSE", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("REMOVE_CONDITIONALS_ORD_IF")) { mutatorConfig.put("REMOVE_CONDITIONALS_ORD_IF", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("REMOVE_CONDITIONALS_ORD_ELSE")) { mutatorConfig.put("REMOVE_CONDITIONALS_ORD_ELSE", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("REMOVE_CONDITIONALS")) { mutatorConfig.put("REMOVE_CONDITIONALS", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("EXPERIMENTAL_MEMBER_VARIABLE")) { mutatorConfig.put("EXPERIMENTAL_MEMBER_VARIABLE", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("EXPERIMENTAL_SWITCH")) { mutatorConfig.put("EXPERIMENTAL_SWITCH", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
            else if(line.contains("EXPERIMENTAL_ARGUMENT_PROPAGATION")) { mutatorConfig.put("EXPERIMENTAL_ARGUMENT_PROPAGATION", new MutantCofig(Double.parseDouble(line.split(";")[1]), Integer.parseInt(line.split(";")[2])) );}
        }

        br.close();
    }

    public int GetMutatroScale(String key){
        return mutatorConfig.get(key).Scale;
    }

    public int GetMutatorProbabilty(String key){
        return (int) (mutatorConfig.get(key).Scale - (mutatorConfig.get(key).Scale* mutatorConfig.get(key).Probabilty));
    }

    private class MutantCofig{

        public MutantCofig(double probabilty, int scale){
            this.Probabilty = probabilty;
            this.Scale = scale;
        }
        public double Probabilty;
        public int Scale;

    }

}
