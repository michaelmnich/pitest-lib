package org.pitest.mutationtest.sam;

import org.pitest.coverage.CoverageSummary;
import org.pitest.mutationtest.config.PluginServices;
import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.mutationtest.statistics.MutationStatistics;
import org.pitest.mutationtest.tooling.AnalysisResult;
import org.pitest.mutationtest.tooling.CombinedStatistics;
import org.pitest.mutationtest.tooling.EntryPoint;
import org.pitest.util.Unchecked;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wawrzyniak on 25.10.2016.
 */
public class DummyWorker implements Runnable {

    String[] input;
    List<String> classpath;

    public void Init(String[] input, List<String> classpath) {
        this.input = input;
        this.classpath = classpath;
    }

    @Override
    public void run() {
        System.out.println("Starting worker");

        final PluginServices plugins = PluginServices.makeForContextLoader();
        final OptionsParser parser = new OptionsParser(new PluginFilter(plugins));


        final ParseResult pr = parser.parse(input);

        if (!pr.isOk()) {
            parser.printHelp();
            System.out.println(">>>> " + pr.getErrorMessage().value());
        } else {
            final ReportOptions data = pr.getOptions();

            data.setClassPathElements(classpath);
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
        AnalysisResult result = e.execute(null, data, plugins, new HashMap<String, String>());
        if (result.getError().hasSome()) {
            throw Unchecked.translateCheckedException(result.getError().value());
        }
        return result.getStatistics().value();

    }
}
