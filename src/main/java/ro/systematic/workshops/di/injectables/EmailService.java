package ro.systematic.workshops.di.injectables;

import ro.systematic.workshops.di.framework.Injectable;

public interface EmailService extends Injectable {
    boolean sendEmail(String subject, String destinationAddress, String emailBodyContent);
}
