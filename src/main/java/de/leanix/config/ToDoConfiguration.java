package de.leanix.config;

import de.leanix.config.di.DependencyInjectionConfiguration;
import de.leanix.config.di.NamedProperty;
import de.leanix.web.resource.TaskResource;
import io.dropwizard.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ToDoConfiguration extends Configuration implements DependencyInjectionConfiguration {

    @Override
    public List<Class<?>> getSingletons() {
        final List<Class<?>> result = new ArrayList();
        result.add(TaskResource.class);
        return result;
    }

    @Override
    public List<NamedProperty<? extends Object>> getNamedProperties() {
        return null;
    }
}
