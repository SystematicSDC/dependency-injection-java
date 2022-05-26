package ro.systematic.workshops.di.injectables.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.systematic.workshops.di.injectables.EmailService;

public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public boolean sendEmail(String subject, String destinationAddress, String emailBodyContent) {
        logger.info("SENDING EMAIL...");
        logger.info("Subject: {}", subject);
        logger.info("Destination: {}", destinationAddress);
        logger.info("Body: {}", emailBodyContent);

        //this is a mockup, obviously we are not actually sending any email...
        //if you are interested on how this really could be done, you can take a look for a Java SMTP example.

        logger.info("EMAIL SENT SUCCESSFULLY!");
        return true;
    }
}
