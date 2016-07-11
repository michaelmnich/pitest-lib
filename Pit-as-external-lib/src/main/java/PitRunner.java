import org.pitest.coverage.CoverageSummary;
import org.pitest.functional.predicate.Predicate;
import org.pitest.mutationtest.commandline.OptionsParser;
import org.pitest.mutationtest.commandline.ParseResult;
import org.pitest.mutationtest.commandline.PluginFilter;
import org.pitest.mutationtest.config.PluginServices;
import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.mutationtest.execute.MutationAnalysisExecutor;
import org.pitest.mutationtest.statistics.MutationStatistics;
import org.pitest.mutationtest.tooling.AnalysisResult;
import org.pitest.mutationtest.tooling.CombinedStatistics;
import org.pitest.mutationtest.tooling.EntryPoint;
import org.pitest.testapi.Configuration;
import org.pitest.util.Unchecked;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by gosc on 21.06.2016.
 */
public class PitRunner {
    private MutationAnalysisExecutor mae;
    private Configuration config;

    public PitRunner(String args2){ //moze string args moze tablica argumento to sie okaze

        System.out.println("Pit runs with args: "+ args2);

    //------------------------------------------------------------------------------------------------------------------


        String args[] = new String[]{
//                "--reportDir D:\\trash\\",
//                "--targetClasses matrixlibrary.*," ,
//                "--targetTests matrixlibrary.*,",
//                "--sourceDirs D:\\Doktorat\\IOgr602-master (1)\\IOgr602-master\\",


                "",};




        final PluginServices plugins = PluginServices.makeForContextLoader();
        final OptionsParser parser = new OptionsParser(new PluginFilter(plugins));
        final ParseResult pr = parser.parse(args); //Ta linijke mozna zakomentowac

//        if (!pr.isOk()) {
//            parser.printHelp();
//        System.out.println(">>>> " + pr.getErrorMessage().value());
//    } else

        {
            final ReportOptions data  = new ReportOptions();//= pr.getOptions();
            data.setReportDir("D:\\trash");

            final File f= new File("D:\\Doktorat\\IOgr602-master\\IOgr602-master\\");
            Collection<File> sourceDirs = new ArrayList<File>();
            sourceDirs.add(f);
            data.setSourceDirs(sourceDirs);

            //WTF SET1-----------------------------------------------------
            final Collection<Predicate<String>> targetClasses = new ArrayList<Predicate<String>>();
            Predicate<String> pred = new Predicate<String>() {
                @Override
                public Boolean apply(String s) {
                    return null;
                }
            };
            pred.apply("matrixlibrary.*");
            targetClasses.add(pred);
            data.setTargetClasses(targetClasses);
            //WTF SET1-----------------------------------------------------


            //WTF SET2-----------------------------------------------------
            final Collection<Predicate<String>> targettest = new ArrayList<Predicate<String>>();
            Predicate<String> pred2 = new Predicate<String>() {
                @Override
                public Boolean apply(String s) {
                    return null;
                }
            };
            pred2.apply("matrixlibrary.*");
            targettest.add(pred);
            //WTF SET2-----------------------------------------------------
            data.setTargetTests(targettest);
            final Collection<String> classPathElements = new ArrayList<String>();
            classPathElements.add("C:\\Program Files\\Java\\junit\\junit-4.12.jar");
            data.setClassPathElements( classPathElements);
            final CombinedStatistics stats = runReport(data, plugins);

            throwErrorIfScoreBelowCoverageThreshold(stats.getCoverageSummary(),
                    data.getCoverageThreshold());
            throwErrorIfScoreBelowMutationThreshold(stats.getMutationStatistics(),
                    data.getMutationThreshold());
            throwErrorIfMoreThanMaxSuvivingMutants(stats.getMutationStatistics(), data.getMaximumAllowedSurvivors());
        }



    }


    private static void throwErrorIfScoreBelowCoverageThreshold(
            CoverageSummary stats, int threshold) {
        if ((threshold != 0) && (stats.getCoverage() < threshold)) {
            throw new RuntimeException("Line coverage of " + stats.getCoverage()
                    + " is below threshold of " + threshold);
        }
    }

    private static void throwErrorIfScoreBelowMutationThreshold(
            final MutationStatistics stats, final int threshold) {
        if ((threshold != 0) && (stats.getPercentageDetected() < threshold)) {
            throw new RuntimeException("Mutation score of "
                    + stats.getPercentageDetected() + " is below threshold of "
                    + threshold);
        }
    }

    private static void throwErrorIfMoreThanMaxSuvivingMutants(
            final MutationStatistics stats, final long threshold) {
        if ((threshold > 0)
                && (stats.getTotalSurvivingMutations() > threshold)) {
            throw new RuntimeException("Had "
                    + stats.getTotalSurvivingMutations() + " surviving mutants, but only "
                    + threshold + " survivors allowed");
        }
    }

    private static CombinedStatistics runReport(ReportOptions data,
                                                PluginServices plugins) {

        EntryPoint e = new EntryPoint();
        AnalysisResult result = e.execute(null, data, plugins,
                new HashMap<String, String>());
        if (result.getError().hasSome()) {
            throw Unchecked.translateCheckedException(result.getError().value());
        }
        return result.getStatistics().value();

    }












public void testrun(){

}







    public static class alamakota {

        public int testReturnOne() {
            return 1;
        }


        public int testReturnTwo() {
            int a= (int)Math.floor(Math.PI *Math.E);
            return a;
        }

    }

    public static class InfiniteLoopTest {
        @Test
        public void pass() {
            alamakota a= new alamakota();
            assertEquals(a.testReturnOne(),1);
        }
    }


}
