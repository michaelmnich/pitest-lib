package org.pitest.extensions;

import org.pitest.mutationtest.DetectionStatus;
import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.mutationtest.report.block.BlockReportListner;
import org.pitest.mutationtest.statistics.MutationStatistics;
import org.pitest.mutationtest.statistics.MutationStatisticsListener;
import org.pitest.mutationtest.statistics.Score;
import org.pitest.mutationtest.statistics.StatusCount;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Michał Mnich on 25.08.2016.
 */
public class MutationRandomizerSingleton {
    //ZMIENNE------------------------------------------------------------------------------------------------------
    private static MutationRandomizerSingleton instance = null;

    private LinkedHashMap<String, ProbabilityEvaluator> mutators = new LinkedHashMap<String, ProbabilityEvaluator>();
    private MutationConfig configData;
    private List<MutatorsNames> mutansNames;

    private BlockReportListner blockListner;

    //Bayses------
    double _alpha=Double.NaN;
    double _betha=Double.NaN;
    public static boolean SetBayes = false;
    //Bayses------

    //-------------------------------------------------------------------------------------------------------------

    /**
     *  Konstruktor
     */
    private MutationRandomizerSingleton() {
        // Exists only to defeat instantiation.

        mutansNames = new ArrayList<MutatorsNames>(Arrays.asList(
                new MutatorsNames ("INVERT_NEGS", "org.pitest.mutationtest.engine.gregor.mutators.InvertNegsMutator"),
                new MutatorsNames ("RETURN_VALS", "org.pitest.mutationtest.engine.gregor.mutators.ReturnValsMutator"),
                new MutatorsNames ("INLINE_CONSTS", "org.pitest.mutationtest.engine.gregor.mutators.InlineConstantMutator"),
                new MutatorsNames ("MATH", "org.pitest.mutationtest.engine.gregor.mutators.MathMutator"),
                new MutatorsNames ("VOID_METHOD_CALLS", "org.pitest.mutationtest.engine.gregor.mutators.VoidMethodCallMutator"),
                new MutatorsNames ("NEGATE_CONDITIONALS", "org.pitest.mutationtest.engine.gregor.mutators.NegateConditionalsMutator"),
                new MutatorsNames ("CONDITIONALS_BOUNDARY", "org.pitest.mutationtest.engine.gregor.mutators.ConditionalsBoundaryMutator"),
                new MutatorsNames ("INCREMENTS", "org.pitest.mutationtest.engine.gregor.mutators.IncrementsMutator"),
                new MutatorsNames ("REMOVE_INCREMENTS", "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveIncrementsMutator"),
                new MutatorsNames ("NON_VOID_METHOD_CALLS", "org.pitest.mutationtest.engine.gregor.mutators.NonVoidMethodCallMutator"),
                new MutatorsNames ("CONSTRUCTOR_CALLS", "org.pitest.mutationtest.engine.gregor.mutators.ConstructorCallMutator"),
                new MutatorsNames ("REMOVE_CONDITIONALS_EQ_IF", "org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator"),
                new MutatorsNames ("REMOVE_CONDITIONALS_EQ_ELSE", "org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator"),
                new MutatorsNames ("REMOVE_CONDITIONALS_ORD_IF", "org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator"),
                new MutatorsNames ("REMOVE_CONDITIONALS_ORD_ELSE", "org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator"),
                new MutatorsNames ("REMOVE_CONDITIONALS", "org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator"),
                new MutatorsNames ("EXPERIMENTAL_MEMBER_VARIABLE", "org.pitest.mutationtest.engine.gregor.mutators.experimental.MemberVariableMutator"),
                new MutatorsNames ("EXPERIMENTAL_SWITCH", "org.pitest.mutationtest.engine.gregor.mutators.experimental.SwitchMutator"),
                new MutatorsNames ("EXPERIMENTAL_ARGUMENT_PROPAGATION", " "),
//Jakies dziwne grupy
                new MutatorsNames ("REMOVE_SWITCH", "org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator"),
                new MutatorsNames ("DEFAULTS", " "),
                new MutatorsNames ("STRONGER", " "),
                new MutatorsNames ("ALL" , " ")
                        //hmmm?
                ));

        //mutansNames2 = mutansNames;
        blockListner = new BlockReportListner();

        //tutja bedzie jakis confg reader;
        //TODO wypysac konfig oraz dorobic inne konfiguracje poza mutantami
        configData = new MutationConfig();
        String dir = "";
        File f;
        Path path = Paths.get(System.getProperty("user.dir"), "Bayes");
        File f2 = new File( path.toString(), "mutatorConfigBayes.ini");
        if(SetBayes && f2.exists()){
            dir = f2.toString();
            f = f2;
        }
        else{
             dir = "MutationProbabilityConfig.ini";
             f = new File( System.getProperty("user.dir"), dir); //to jakos inaczej podac trzeba
        }

        _alpha = 12;
        _betha = 12;
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
        for (MutatorsNames mutantName:mutansNames) {
            int scale =0;
            int probality =0;
            if(configData.IsMutantKeyExist(mutantName.Id)) {
                scale =configData.GetMutatroScale(mutantName.Id);
                probality =configData.GetMutatorProbabilty(mutantName.Id);
                mutators.put(mutantName.Id, new ProbabilityEvaluator(scale,probality ));
                System.out.format("%-15s%-15s%-15s\n",mutantName.Id,  "scale: "+ scale, "probabilty: " + probality);
            }else{
                mutators.put( mutantName.Id, new ProbabilityEvaluator(1,0));
                System.out.format("%-15s%-15s%-15s\n",mutantName.Id, "scale: "+ scale, "probabilty: 1 <- Mutant in config was unset.");
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

    public static void PushStats(MutationStatisticsListener stats) {
        if(!SetBayes) return;
        //1. Objasnienie matematyczne Dla Kazdego i wyznaczamy alpha oraz beta  (stałe bayesa)  i to jest indeks operataro mutacyjnego
        //Poczatkowo ustawiamy stale o wartosni np 12 kazda wartośc poczatkow ajest dobra gdy nic nie wiemy. PATRZ konstruktor
        // obliczamy BETA ( Teta | Alpha, Beta)
        // Obliczamy E_THETA
        String toLog="";
        System.out.println("================================================================================");
        System.out.println("Prawdopodobieństwa Popraione Bayesem Nowe wartosci: ");
        System.out.println("================================================================================");
        MutationStatistics mutStat = stats.getStatistics();
        toLog ="Operator;P_old(mut);p_new(mut);skala;KILLED;SURVIVED;NO_COVERAGE;TIMED_OUT;MEMORY_ERROR;RUN_ERROR;NON_VIABLE;NOT_STARTED;STARTED"+System.getProperty("line.separator");
        for (Score sorce : mutStat.getScores())
        {

            long M_s=0;
            long total_M=0;

            Iterable<StatusCount> counts =  sorce.GetCounts();
            total_M = sorce.getTotalMutations();
            for (StatusCount status:counts) {
                if(status.getStatus().equals(DetectionStatus.SURVIVED)){

                   M_s = status.getCount(); //liczba zywych mutantow
                }
                if(status.getStatus().equals(DetectionStatus.NO_COVERAGE)){
                    M_s += status.getCount(); // liczba nie pokrytych mutantów ergo zywych
                }
            }

            instance.BETA_OF_Tetha(M_s,total_M);
            M_s=0;
            total_M=0;
            MutatorsNames mm =   getMutatorsNamesObj(sorce.getMutatorName()); //pobieram nazwe operatora mutacyjnego ktremu nalezy poprawic prawdopodobienstow
            double newProbablity = instance.E_THETA();
            double OldProb =1;
            int scale =100;
            if(instance.configData.IsMutantKeyExist(mm.Id)) {
            scale =instance.configData.GetMutatroScale(mm.Id) ;
            OldProb  = instance.configData.GetMutatorProbabiltyVal(mm.Id);
            }
            instance.configData.SetMutatorProbabilty(mm.Id, newProbablity,  scale);//poprawka konfiga mutacji
            System.out.println("Operator: "+mm.Id+ " stare= "+ OldProb +" nowe= "+ newProbablity+" Skala= "+ scale);

            //dla loga----------------------------------------------------------------------------------------------
            Map<String,String> csvLine = new HashMap<String,String>();
            csvLine.put("Operator",mm.Id+"");
            csvLine.put("stare",OldProb+"");
            csvLine.put("nowe",newProbablity+"");
            csvLine.put("Skala",scale+"");
            for (StatusCount status:counts) {
                if(status.getStatus().equals(DetectionStatus.KILLED)){
                    csvLine.put(DetectionStatus.KILLED.toString(),status.getCount()+"");
                }
                if(status.getStatus().equals(DetectionStatus.SURVIVED)){
                    csvLine.put(DetectionStatus.SURVIVED.toString(),status.getCount()+"");
                }
                if(status.getStatus().equals(DetectionStatus.NO_COVERAGE)){
                    csvLine.put(DetectionStatus.NO_COVERAGE.toString(),status.getCount()+"");
                }
                if(status.getStatus().equals(DetectionStatus.TIMED_OUT)){
                    csvLine.put(DetectionStatus.TIMED_OUT.toString(),status.getCount()+"");
                }
                if(status.getStatus().equals(DetectionStatus.MEMORY_ERROR)){
                    csvLine.put(DetectionStatus.MEMORY_ERROR.toString(),status.getCount()+"");
                }
                if(status.getStatus().equals(DetectionStatus.RUN_ERROR)){
                    csvLine.put(DetectionStatus.RUN_ERROR.toString(),status.getCount()+"");
                }
                if(status.getStatus().equals(DetectionStatus.NON_VIABLE)){
                    csvLine.put(DetectionStatus.NON_VIABLE.toString(),status.getCount()+"");
                }
                if(status.getStatus().equals(DetectionStatus.NOT_STARTED)){
                    csvLine.put(DetectionStatus.NOT_STARTED.toString(),status.getCount()+"");
                }
                if(status.getStatus().equals(DetectionStatus.STARTED)){
                    csvLine.put(DetectionStatus.STARTED.toString(),status.getCount()+"");
                }
            }
            //TODO wypisac cala linijke i dodać nazwe klasy

            //dla loga----------------------------------------------------------------------------------------------

            // toLog +="SURVIVED"+
        }
        //todo na koniec natpisac konfiga
        System.out.println("================================================================================");

        instance.configData.UpdateConfig();
        SetBayes = false;
    }

    private void BETA_OF_Tetha( long M_survived, long AllMutants){
        _alpha = _alpha + M_survived;
        _betha = AllMutants -  M_survived + _betha;

    }

    private double  E_THETA(){
        return _alpha/(_alpha+_betha);
    }


    private static MutatorsNames getMutatorsNamesObj(String key){
        for (MutatorsNames mm  : instance.mutansNames)
        {
            if(mm.Id.equals(key) || mm.ClassName.equals(key)){
                return mm;
            }
        }
        return null;
    }

    public class MutatorsNames {
        public MutatorsNames(String id, String className){
            Id= id;
            ClassName =className;
        }
        public String Id;
        public String ClassName;
    }
}
