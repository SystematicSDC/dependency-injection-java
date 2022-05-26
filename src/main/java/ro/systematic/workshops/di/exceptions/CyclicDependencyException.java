package ro.systematic.workshops.di.exceptions;

import ro.systematic.workshops.di.framework.Injectable;

import java.util.List;
import java.util.stream.Collectors;

public class CyclicDependencyException extends DependencyInjectionException{
    public CyclicDependencyException(List<Class<? extends Injectable>> callstack){
        super(callstack.stream().map(Class::getSimpleName).collect(Collectors.joining(" -> ")));
    }
}
