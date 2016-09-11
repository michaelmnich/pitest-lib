import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.pitest.functional.Option;
import org.pitest.maven.AbstractPitMojo;
import org.pitest.maven.MojoToReportOptionsConverter;
import org.pitest.maven.SurefireConfigConverter;
import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.mutationtest.tooling.CombinedStatistics;

/**
 * Created by gosc on 10.09.2016.
 */
@Mojo(name = "dupa", defaultPhase = LifecyclePhase.VERIFY, requiresDependencyResolution = ResolutionScope.TEST)
class MultitreadPitMojo extends AbstractPitMojo {


    protected Option<CombinedStatistics> AnalyseAndMutateProject() throws MojoExecutionException {


        final ReportOptions data = new MojoToReportOptionsConverter(this, new SurefireConfigConverter(), this.filter).convert();


        return Option.some(this.goalStrategy.execute(detectBaseDir(), data, this.plugins, getEnvironmentVariables()));
    }


}
