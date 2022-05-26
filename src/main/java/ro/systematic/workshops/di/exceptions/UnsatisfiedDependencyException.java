package ro.systematic.workshops.di.exceptions;

public class UnsatisfiedDependencyException extends DependencyInjectionException {
    public UnsatisfiedDependencyException(String message){
        super(message);
    }
}
