package ro.systematic.workshops.di.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.systematic.workshops.di.exceptions.CyclicDependencyException;
import ro.systematic.workshops.di.exceptions.DependencyInjectionException;
import ro.systematic.workshops.di.exceptions.NoInjectableFoundException;
import ro.systematic.workshops.di.exceptions.UnsatisfiedDependencyException;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class DependencyInjection {
    private static final Logger logger = LoggerFactory.getLogger(DependencyInjection.class);

    private DependencyInjection() {
    }

    private static DependencyInjection obj = null;

    public static DependencyInjection getInstance() {
        if (obj == null) {
            obj = new DependencyInjection();
        }
        return obj;
    }

    Map<Class<? extends Injectable>, InjectableContext<? extends Injectable>> diMap = new HashMap<>();

    /**
     * Register the injectable in the injector registry
     *
     * @param injectableClass class based on which the injector will provide an instance
     * @param factory         callback that is responsible with creating new instances
     */
    @SafeVarargs
    public final <T extends Injectable> void register(Class<T> injectableClass, InstanceFactory<T> factory, Class<? extends Injectable> ... dependencies) {
        InjectableContext<T> injectable = new InjectableContext<>();
        injectable.factory = factory;
        injectable.injectableClass = injectableClass;
        injectable.dependencies = Arrays.asList(dependencies);
        diMap.put(injectableClass, injectable);
    }

    /**
     * Resolves and provides instance of provided class based on the registered injectables
     *
     * @param injectableClazz class used to search for instance in the registry
     */
    public <T extends Injectable> T get(Class<T> injectableClazz) throws DependencyInjectionException {
        InjectableContext<T> injectable = (InjectableContext<T>) diMap.get(injectableClazz);

        if (injectable != null) {
            logger.debug("Getting: {}", injectableClazz.getSimpleName());

            if (injectable.instance == null) {
                createInstance(injectable);
            }
            return injectable.instance;
        } else {
            throw new NoInjectableFoundException("No injectable found with name: "+injectableClazz.getSimpleName());
        }
    }

    /**
     * Creates instance and resolves dependencies for this injectable
     */
    private <T extends Injectable> void createInstance(InjectableContext<T> injectable) throws DependencyInjectionException {
        checkCyclicDependency(injectable, new LinkedList<>());

        List<Class<? extends Injectable>> unsatisfiedDependencies = injectable.dependencies.stream()
                .filter(dependency -> {
                    try{
                        return getInstance().get(dependency) == null;
                    }catch (Exception e){
                        return true;
                    }
                })
                .collect(Collectors.toList());

        if (!unsatisfiedDependencies.isEmpty()) {
            throw new UnsatisfiedDependencyException("Could not find instance for dependency: "+unsatisfiedDependencies.get(0).getSimpleName());
        }

        injectable.instance = injectable.factory.newInstance();
    }

    private <T extends Injectable> void checkCyclicDependency(InjectableContext<T> injectable, List<Class<? extends Injectable>> callStack ) throws CyclicDependencyException {
        if(callStack.contains(injectable.injectableClass)){
            callStack.add(injectable.injectableClass);
            throw new CyclicDependencyException(callStack);
        }else{
            callStack.add(injectable.injectableClass);
            for(Class<? extends Injectable> dependency : injectable.dependencies){
                InjectableContext<? extends Injectable> dependencyContext = diMap.get(dependency);
                if(dependencyContext!=null){
                    checkCyclicDependency(dependencyContext, callStack);
                }
            }
        }
    }

}
