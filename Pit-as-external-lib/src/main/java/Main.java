import org.pitest.functional.FCollection;
import org.pitest.mutationtest.config.PluginServices;
import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.mutationtest.tooling.AnalysisResult;
import org.pitest.mutationtest.tooling.EntryPoint;
import org.pitest.util.Glob;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gosc on 21.06.2016.
 */
public class Main {


    public static void main(String [] args)
    {

        System.out.println("Hello Pit Type some comands");
        EntryPoint e = new EntryPoint();
        File baseDir = new File("PLiczek");
        ReportOptions data = new ReportOptions();
        //tutaj posutawiamy opvje raportu
        data.setReportDir("D:\\trash");

        final File f= new File("D:\\Doktorat\\IOgr602-master\\IOgr602-master\\src\\main\\");
        final File f2= new File("D:\\Doktorat\\IOgr602-master\\IOgr602-master\\src\\test\\");


       ;;;;
        Collection<File> sourceDirs = new ArrayList<File>();
        sourceDirs.add(f);
        sourceDirs.add(f2);

        Collection<String> classPathElements = new ArrayList<String>();
        classPathElements.add("D:\\Doktorat\\IOgr602-master\\IOgr602-master\\target\\test-classes\\");

        classPathElements.add("D:\\Doktorat\\IOgr602-master\\IOgr602-master\\target\\classes\\");
        classPathElements.add("C:\\pit\\pitest-1.1.11-SNAPSHOT.jar");
        classPathElements.add("C:\\pit\\junit-4.12.jar");
        classPathElements.add("C:\\pit\\pitest-command-line-1.1.11-SNAPSHOT.jar");
        classPathElements.add("C:\\Program Files\\Java\\jdk1.7.0_79\\bin");
     //   classPathElements.add("D:\\Doktorat\\IOgr602-master\\IOgr602-master\\target\\classes\\");


        data.setSourceDirs(sourceDirs);
data.setClassPathElements(classPathElements);
        //WTF SET1-----------------------------------------------------

       // pred.apply("matrixlibrary.*");

        List<String> l1 = new ArrayList<String>();
        l1.add("matrixlibrary.*");
        data.setTargetClasses(FCollection.map(l1, Glob.toGlobPredicate()));
        data.setTargetTests(FCollection.map(l1,Glob.toGlobPredicate()));
                PluginServices plugins = PluginServices.makeForContextLoader();

        AnalysisResult result = e.execute(null, data, plugins, new HashMap<String, String>());


//        System.out.println("Hello Pit Type some comands");
//
//           if(args.length==2) ConsoleComandsContorler.ProgramImmput(args[0],args[1]); //tutaj zrobimy przerubke czytania argumentow z mauvena.
//        //np czytanie z jakiegos pliku textowego czy cos i szalejemy
//
//
//
//        //jesli nie program nie zostal odpalony z odpowiednimi argumentami mozna to zrobic z konsoli
//        Scanner scaner = new Scanner(System.in);
//        String consoleInput = "";
//        while(!consoleInput.equals("exit") && !consoleInput.equals("Exit") ){
//            consoleInput = scaner.nextLine();
//            ConsoleComandsContorler.ImmputComand(consoleInput);
//        }
    }
}
