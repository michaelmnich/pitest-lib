package org.pitest.extensions;

import org.pitest.mutationtest.engine.MutationDetails;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by gosc on 25.08.2016.
 */
public class MutationRandomizerSingleton {
    //ZMIENNE------------------------------------------------------------------------------------------------------
    private static MutationRandomizerSingleton instance = null;
    private Integer config =0;
    private LinkedHashMap<String, ProbabilityEvaluator> mutators = new LinkedHashMap<String, ProbabilityEvaluator>();
    //-------------------------------------------------------------------------------------------------------------

    /**
     *  Konstruktor
     */
    private MutationRandomizerSingleton() {
        // Exists only to defeat instantiation.
        //tutja bedzie jakis confg reader;

        MutationConfig config = new MutationConfig();
        File f = new File("d:\\config.ini");
        try {
            config.readConfig(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //mutators.put( "INVERT_NEGS", new ProbabilityEvaluator(100,50));
        mutators.put( "INVERT_NEGS", new ProbabilityEvaluator(config.GetMutatroScale("INVERT_NEGS"),config.GetMutatorProbabilty("INVERT_NEGS")));


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
    }

    /**
     *
     * @param inputMutationList
     * @return zwraca liste wstępnie przygotowannych mutantów. Lista trojakeigo rodzaju Identycznościowa, Losowa z pliku, Losowa bajes.
     */
    public List<MutationDetails>  Randomize(List<MutationDetails> inputMutationList ){

        if(config.equals(0)){
           return noRandomize(inputMutationList);
        }
        else if(config.equals(1)){
           return fileInputRadndomizer(inputMutationList);
        }else{
            //tutaj ten bajesowski randomizer
            //a narazie return null
            return null;
        }
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
            if(mutators.containsKey(m.getId().getMutator())){
                //mutators bobierz prawdopodobienstwo
                if(mutators.get(m.getId().getMutator()).draw()) outputMutationLIst.add(m);
            }

        }
        return outputMutationLIst;
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
