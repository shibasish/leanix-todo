package de.leanix.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.leanix.config.di.DependencyInjectionConfiguration;
import de.leanix.config.di.NamedProperty;
import de.leanix.persistance.repository.TaskRepository;
import de.leanix.web.resource.TaskResource;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ToDoConfiguration extends Configuration implements DependencyInjectionConfiguration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    protected Class<?> getTaskRepository() {
        return TaskRepository.class;
    }

    @Override
    public List<Class<?>> getSingletons() {
        final List<Class<?>> result = new ArrayList();
        result.add(TaskResource.class);
        result.add(getTaskRepository());
        return result;
    }

    @Override
    public List<NamedProperty<? extends Object>> getNamedProperties() {
        final List<NamedProperty<? extends Object>> result = new ArrayList<>();
        result.add(new NamedProperty<>("dbUser", "dummy_db_user", String.class));

        return result;
    }
}
