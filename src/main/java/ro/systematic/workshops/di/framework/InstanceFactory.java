package ro.systematic.workshops.di.framework;

import ro.systematic.workshops.di.exceptions.DependencyInjectionException;

public interface InstanceFactory<T> {
    T newInstance() throws DependencyInjectionException;
}
