package org.pitest.maven;

/**
 * Created by gosc on 23.09.2016.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "test")
public class DemoMojo  extends AbstractMojo{

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("Test dziala");
    }
}
