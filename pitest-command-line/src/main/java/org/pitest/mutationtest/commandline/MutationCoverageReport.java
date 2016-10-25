/*
 * Copyright 2010 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.commandline;

import org.pitest.coverage.CoverageSummary;
import org.pitest.mutationtest.config.PluginServices;
import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.mutationtest.statistics.MutationStatistics;
import org.pitest.mutationtest.tooling.AnalysisResult;
import org.pitest.mutationtest.tooling.CombinedStatistics;
import org.pitest.mutationtest.tooling.EntryPoint;
import org.pitest.util.Unchecked;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Entry point for command line interface
 */
public class MutationCoverageReport {

  public static void main(final String[] args) {


        String args1[] = new String[]{
            "--classPath",
            "D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\test-classes\\,D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\classes\\",
            "--reportDir",
            "D:\\trash\\",
            "--targetClasses",
            "matrixlibrary.IMatrixImpl*",
            "--targetTests",
            "matrixlibrary.*",
            "--sourceDirs",
            "D:\\Doktorat\\PitPlayground\\IOgr602-master\\"};
    String args2[] = new String[]{
            "--classPath",
            "D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\test-classes\\,D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\classes\\",
            "--reportDir",
            "D:\\trash\\",
            "--targetClasses",
            "matrixlibrary.IMatrixMathImpl*",
            "--targetTests",
            "matrixlibrary.*",
            "--sourceDirs",
            "D:\\Doktorat\\PitPlayground\\IOgr602-master\\"};

       List<String> cp1 =  Arrays.asList(
              "D:\\doktorat\\pitest-lib\\pitest\\target\\pitest-1.1.11-SNAPSHOT.jar",
                  "C:\\junit\\hamcrest-core-1.3.jar",
              "C:\\junit\\junit-4.12.jar",
              "D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\test-classes\\",
              "D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\classes\\"
              );
    List<String> cp2 =  Arrays.asList(
            "D:\\doktorat\\pitest-lib\\pitest\\target\\pitest-1.1.11-SNAPSHOT.jar",
            "C:\\junit\\hamcrest-core-1.3.jar",
            "C:\\junit\\junit-4.12.jar",
            "D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\test-classes\\",
            "D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\classes\\"
    );

    DummyWorker dm1 = new DummyWorker();
    DummyWorker dm2 = new DummyWorker();
    dm1.Init(args1,cp1);
    dm2.Init(args2,cp2);

    Thread thread1 = new Thread(dm1);
    Thread thread2 = new Thread(dm2);
    thread1.start();
    thread2.start();

//    final PluginServices plugins = PluginServices.makeForContextLoader();
//    final OptionsParser parser = new OptionsParser(new PluginFilter(plugins));
//
//
////dupa
//    String args2[] = new String[]{
//            "--classPath",
//            "D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\test-classes\\,D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\classes\\",
//            "--reportDir",
//            "D:\\trash\\",
//            "--targetClasses",
//            "matrixlibrary.*",
//            "--targetTests",
//            "matrixlibrary.*",
//            "--sourceDirs",
//            "D:\\Doktorat\\PitPlayground\\IOgr602-master\\"};
//
//    System.out.println("ARGUMENTY------------------------------------------------------------------");
//    for (String s:args) {
//      System.out.println(s);
//
//    }
//    System.out.println("ARGUMENTY------------------------------------------------------------------");
//    final ParseResult pr = parser.parse(args2);
//
//    if (!pr.isOk()) {
//      parser.printHelp();
//      System.out.println(">>>> " + pr.getErrorMessage().value());
//    } else {
//      final ReportOptions data = pr.getOptions();
//
//      List<String> cp =  Arrays.asList(
//              "D:\\doktorat\\pitest-lib\\pitest\\target\\pitest-1.1.11-SNAPSHOT.jar",
//                  "C:\\junit\\hamcrest-core-1.3.jar",
//              "C:\\junit\\junit-4.12.jar",
//              "D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\test-classes\\",
//              "D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\classes\\"
//              );
//      data.setClassPathElements(cp);
//      final CombinedStatistics stats = runReport(data, plugins);
//
//      throwErrorIfScoreBelowCoverageThreshold(stats.getCoverageSummary(),
//          data.getCoverageThreshold());
//      throwErrorIfScoreBelowMutationThreshold(stats.getMutationStatistics(),
//          data.getMutationThreshold());
//      throwErrorIfMoreThanMaxSuvivingMutants(stats.getMutationStatistics(), data.getMaximumAllowedSurvivors());
//    }

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
