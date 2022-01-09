package de.leanix;

import de.leanix.config.ToDoConfiguration;
import de.leanix.config.di.DependencyInjectionBundle;
import de.leanix.persistance.entity.TaskEntity;
import de.leanix.persistance.repository.TaskRepository;
import de.leanix.web.resource.TaskResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ToDoApplication extends Application<ToDoConfiguration> {

    public static void main(String[] args) throws Exception {
        new ToDoApplication().run(args);
    }

    private final HibernateBundle<ToDoConfiguration> hibernate = new HibernateBundle<ToDoConfiguration>(TaskEntity.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ToDoConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void run(ToDoConfiguration toDoConfiguration, Environment environment) throws Exception {

        final TaskRepository taskRepository = new TaskRepository(hibernate.getSessionFactory());
        environment.jersey().register(new TaskResource(taskRepository));

        final DependencyInjectionBundle dependencyInjectionBundle = new DependencyInjectionBundle();
        dependencyInjectionBundle.run(toDoConfiguration, environment);
    }

    @Override
    public void initialize(Bootstrap<ToDoConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }
}
