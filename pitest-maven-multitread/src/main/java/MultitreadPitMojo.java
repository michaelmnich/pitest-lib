import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
/**
 * Created by gosc on 10.09.2016.
 */
@Mojo(name = "dupa")
class MultitreadPitMojo extends AbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("NO zesz dupa dupa dupa");
    }
}
