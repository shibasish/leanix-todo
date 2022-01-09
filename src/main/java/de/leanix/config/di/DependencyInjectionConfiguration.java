package de.leanix.config.di;

import java.util.List;

public interface DependencyInjectionConfiguration {
    List<Class<?>> getSingletons();
    List<NamedProperty<? extends Object>> getNamedProperties();
}
