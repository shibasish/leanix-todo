package de.leanix;

import de.leanix.config.ToDoConfiguration;
import de.leanix.config.di.DependencyInjectionBundle;
import de.leanix.web.resource.TaskResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ToDoApplication extends Application<ToDoConfiguration> {

    public static void main(String[] args) throws Exception {
        new ToDoApplication().run(args);
    }

    @Override
    public void run(ToDoConfiguration toDoConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new TaskResource());
        final DependencyInjectionBundle dependencyInjectionBundle = new DependencyInjectionBundle();
        dependencyInjectionBundle.run(toDoConfiguration, environment);
    }

    @Override
    public void initialize(Bootstrap<ToDoConfiguration> bootstrap) {
        // nothing to do yet
    }
}
