package org.pitest.extensions;

import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.mutationtest.report.block.BlockReportListner;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Michał Mnich on 25.08.2016.
 */
public class MutationRandomizerSingleton {
    //ZMIENNE------------------------------------------------------------------------------------------------------
    private static MutationRandomizerSingleton instance = null;

    private LinkedHashMap<String, ProbabilityEvaluator> mutators = new LinkedHashMap<String, ProbabilityEvaluator>();
            MutationConfig configData;
    private List<String> mutansNames = new ArrayList<String>(Arrays.asList(
            "INVERT_NEGS",
            "RETURN_VALS",
            "INLINE_CONSTS",
            "MATH",
            "VOID_METHOD_CALLS",
            "NEGATE_CONDITIONALS",
            "CONDITIONALS_BOUNDARY",
            "INCREMENTS",
            "REMOVE_INCREMENTS",
            "NON_VOID_METHOD_CALLS",
            "CONSTRUCTOR_CALLS",
            "REMOVE_CONDITIONALS_EQ_IF",
            "REMOVE_CONDITIONALS_EQ_ELSE",
            "REMOVE_CONDITIONALS_ORD_IF",
            "REMOVE_CONDITIONALS_ORD_ELSE",
            "REMOVE_CONDITIONALS",
            "EXPERIMENTAL_MEMBER_VARIABLE",
            "EXPERIMENTAL_SWITCH",
            "EXPERIMENTAL_ARGUMENT_PROPAGATION",
            //Jakies dziwne grupy
            "REMOVE_SWITCH",
            "DEFAULTS",
            "STRONGER",
            "ALL" //hmmm?
    ));
    private BlockReportListner blockListner;
    //-------------------------------------------------------------------------------------------------------------

    /**
     *  Konstruktor
     */
    private MutationRandomizerSingleton() {
        // Exists only to defeat instantiation.



        blockListner = new BlockReportListner();

        //tutja bedzie jakis confg reader;
        //TODO wypysac konfig oraz dorobic inne konfiguracje poza mutantami
        configData = new MutationConfig();
        String dir = "d:\\config.ini";
        File f = new File(dir); //to jakos inaczej podac trzeba
        try {
            configData.readConfig(f);
        } catch (IOException e) {
            System.out.println("==================================================================");
            System.out.println("CONFIG FILE ERROR! Cheack file dir: "+dir);
            System.out.println("==================================================================");
            e.printStackTrace();
        }


    }

    /**
     *  Przygotowuje liste mutantów wraz z prawdopodobienstwami an podstawie danych z configData
     */
    private void setUpDataFromConfig(){
        //mutators.put( "INVERT_NEGS", new ProbabilityEvaluator(100,50));
        //po koleji jesli takig mutotora w konfigu nie bedzdie niec dha prawdopodobienstwo pewne
        System.out.println("================================================================================");
        Date d =new Date(System.currentTimeMillis());
        System.out.println("EXTENDED PIT >> " + d.toString() +" >> Imput Mutation Configuration");
        System.out.println("================================================================================");
        for (String mutantName:mutansNames) {
            int scale =0;
            int probality =0;
            if(configData.IsMutantKeyExist(mutantName)) {
                scale =configData.GetMutatroScale(mutantName);
                probality =configData.GetMutatorProbabilty(mutantName);
                mutators.put(mutantName, new ProbabilityEvaluator(scale,probality ));
                System.out.format("%-15s%-15s%-15s\n",mutantName,  "scale: "+ scale, "probabilty: " + probality);
            }else{
                mutators.put( mutantName, new ProbabilityEvaluator(1,0));
                System.out.format("%-15s%-15s%-15s\n",mutantName, "scale: "+ scale, "probabilty: 1 <- Mutant in config was unset.");
            }

        }
        System.out.println("================================================================================");
    }

    /**
     *
     * @param inputMutationList
     * @return Zwraca lista identycznościową dla metody Randomize.
     */
    private List<MutationDetails> noRandomize(List<MutationDetails> inputMutationList ){
        return inputMutationList;
    }

    /**
     *
     * @param inputMutationList
     * @return Zwraca liste losowych mutantó zgodnych z plikiem konfiguracyjnym.
     */
    private List<MutationDetails> fileInputRadndomizer(List<MutationDetails> inputMutationList ){
        List<MutationDetails> outputMutationLIst = new ArrayList<MutationDetails>();
        Integer i = 0;
        for (MutationDetails m: inputMutationList) {
            //jesli dany mutant wystepuje w kolekcji mutatorów
            //pobierz jego prawdopodobienstwo
            if(mutators.containsKey(m.getId().getMutatorEnumName())){//jeli dany mutant jest typu znajdujacego sie w liscie mutatorow
                //mutators bobierz prawdopodobienstwo
                if(mutators.get(m.getId().getMutatorEnumName()).draw()) outputMutationLIst.add(m);//wylosuj czy dodjaemy mutnta czy nie
            }

        }
        return outputMutationLIst;
    }

    /**
     *
     * @param inputMutationList
     * @return zwraca liste wstępnie przygotowannych mutantów. Lista trojakeigo rodzaju Identycznościowa, Losowa z pliku, Losowa bajes.
     */
    public List<MutationDetails>  Randomize(List<MutationDetails> inputMutationList ){

        if( configData.MutationMode()==0){
           return noRandomize(inputMutationList);
        }
        else if(configData.MutationMode()==1){
            setUpDataFromConfig();
           return fileInputRadndomizer(inputMutationList);
        }else{
            //tutaj ten bajesowski randomizer
            //a narazie return null
            return null;
        }
    }

    public BlockReportListner GetBlockReportListner(){
        return this.blockListner;
    }


    /**
     *
     * @return zwraca instancje singletonu
     */
    public static MutationRandomizerSingleton getInstance() {
        if(instance == null) {
            instance = new MutationRandomizerSingleton();
        }
        return instance;
    }
}
