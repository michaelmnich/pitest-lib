package org.pitest.mutationtest.sam;

import org.pitest.coverage.CoverageSummary;
import org.pitest.mutationtest.config.PluginServices;
import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.mutationtest.sam.config.IProjectMetaData;
import org.pitest.mutationtest.sam.config.ProjectConfig;
import org.pitest.mutationtest.statistics.MutationStatistics;
import org.pitest.mutationtest.tooling.AnalysisResult;
import org.pitest.mutationtest.tooling.CombinedStatistics;
import org.pitest.mutationtest.tooling.EntryPoint;
import org.pitest.util.Unchecked;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gosc on 28.11.2016.
 */
public class PitRunner {

    //TODO
    // 1.KONFIGURACJE WYSYLANE DO TEGO NODA
    // 2.Zwracanie raportów


        public PitRunner(){

        }





    public void RunnMutation(IProjectMetaData argsMetadata){
        //KONFIGURACJE-------------------------------------------------------------
        final PluginServices plugins = PluginServices.makeForContextLoader();
        final OptionsParser parser = new OptionsParser(new PluginFilter(plugins));
        IProjectMetaData pmd = argsMetadata; //new FromFileMetaData();
        String args2[] = pmd.GetMetaData();
        ProjectConfig pc= new ProjectConfig();
        List<String> cp = pc.cp;
        //KONFIGURACJE-------------------------------------------------------------

        final ParseResult pr = parser.parse(args2);
        if (!pr.isOk())
        {
            parser.printHelp();
            System.out.println(">>>> " + pr.getErrorMessage().value());
        }
        else
        {
            //PIT I JEGO MUTACJE-----------------------------------------------------
            final ReportOptions data = pr.getOptions();
            data.setClassPathElements(cp);
            final CombinedStatistics stats = runReport(data, plugins);
            throwErrorIfScoreBelowCoverageThreshold(stats.getCoverageSummary(), data.getCoverageThreshold());
            throwErrorIfScoreBelowMutationThreshold(stats.getMutationStatistics(), data.getMutationThreshold());
            throwErrorIfMoreThanMaxSuvivingMutants(stats.getMutationStatistics(), data.getMaximumAllowedSurvivors());
            //PIT I JEGO MUTACJE-----------------------------------------------------
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

}
