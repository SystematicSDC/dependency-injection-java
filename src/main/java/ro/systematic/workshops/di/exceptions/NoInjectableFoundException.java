package ro.systematic.workshops.di.exceptions;

public class NoInjectableFoundException extends DependencyInjectionException {
    public NoInjectableFoundException(String message) {
        super(message);
    }
}
