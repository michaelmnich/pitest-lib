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
package org.pitest.mutationtest.sam;

import org.pitest.coverage.CoverageSummary;
import org.pitest.mutationtest.config.PluginServices;
import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.mutationtest.sam.config.FromFileMetaData;
import org.pitest.mutationtest.sam.config.IProjectMetaData;
import org.pitest.mutationtest.sam.config.ProjectConfig;
import org.pitest.mutationtest.sam.web.WebSocketSerwer;
import org.pitest.mutationtest.statistics.MutationStatistics;
import org.pitest.mutationtest.tooling.AnalysisResult;
import org.pitest.mutationtest.tooling.CombinedStatistics;
import org.pitest.mutationtest.tooling.EntryPoint;
import org.pitest.util.Unchecked;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Michał Mnich on 25.10.2016.
 */
public class MainWorker {

  public static void main(final String[] args) {

    WebSocketSerwer workerSerwer = new WebSocketSerwer();
    String client = JOptionPane.showInputDialog("Coonet to other serwer" );
    if(client.equals("yes")){
      System.out.println("Coonecting to serwer");
      workerSerwer.ConnectClient("dupa",12);
    }

    String port = JOptionPane.showInputDialog("Enter Port" );
    workerSerwer.Start(Integer.valueOf(port));

  }



    private void RunnMutation(){
    //KONFIGURACJE-------------------------------------------------------------
    final PluginServices plugins = PluginServices.makeForContextLoader();
    final OptionsParser parser = new OptionsParser(new PluginFilter(plugins));
    IProjectMetaData pmd = new FromFileMetaData();
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
