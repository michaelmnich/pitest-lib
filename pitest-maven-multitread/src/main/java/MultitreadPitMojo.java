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
    EncapsulatedPit root;

    protected Option<CombinedStatistics> AnalyseAndMutateProject() throws MojoExecutionException {


        root = new EncapsulatedPit();

        //metody get i set targetClasses daja nam mozliwość operowania na danych i mozemy manipulowac lista klass
        final ReportOptions data = new MojoToReportOptionsConverter(this, new SurefireConfigConverter(), this.filter).convert();

        //tutaj bedziemy wstryzkiwac singletownowi konfig;
        //w wersji bayesowskiej jeden konfig na jedna klasse i bedzie sie uczył ??? moze

        CombinedStatistics ex =root.Goal.execute(detectBaseDir(), data, this.plugins, getEnvironmentVariables());
        //to wyzej to troszke przerobic niech root dostate dane nie jego goal bedzie mugl sobie podzielic prace

        //po wykonaniu wszytkich instancji pita zbiorczo zebrac dane z naszzehj klasy albo zbiorczo je tam zapisywac upenic sie ze jest unikatowa
        //synchronizacja () nastpnie po zebraniu danych poprawic konfig zgodnie za bayesem


        return Option.some(ex); //zwrotka dla obsługi błedów przeniesc jednak coalosc tu

    }


}
