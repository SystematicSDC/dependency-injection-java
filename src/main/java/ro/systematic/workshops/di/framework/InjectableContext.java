package ro.systematic.workshops.di.framework;

import java.util.List;

public class InjectableContext<T> {
    T instance;
    Class<? extends Injectable> injectableClass;
    List<Class<? extends Injectable>> dependencies;
    InstanceFactory<T> factory;
}
