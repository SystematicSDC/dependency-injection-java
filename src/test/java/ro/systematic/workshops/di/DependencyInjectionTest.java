package ro.systematic.workshops.di;

import org.junit.jupiter.api.Test;
import ro.systematic.workshops.di.exceptions.CyclicDependencyException;
import ro.systematic.workshops.di.exceptions.DependencyInjectionException;
import ro.systematic.workshops.di.exceptions.NoInjectableFoundException;
import ro.systematic.workshops.di.exceptions.UnsatisfiedDependencyException;
import ro.systematic.workshops.di.framework.DependencyInjection;
import ro.systematic.workshops.di.injectables.EmailService;
import ro.systematic.workshops.di.injectables.FuellingService;
import ro.systematic.workshops.di.testdata.DummyInjectable;
import ro.systematic.workshops.di.testdata.MockedInjectable;
import ro.systematic.workshops.di.testdata.SecondMockedInjectable;
import ro.systematic.workshops.di.testdata.impl.MockedInjectableImpl;
import ro.systematic.workshops.di.testdata.impl.SecondMockedInjectableImpl;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class DependencyInjectionTest {

    @Test
    void testCanRegisterAndInjectSimpleInjectable() {
        DependencyInjection.getInstance().register(MockedInjectable.class, MockedInjectableImpl::new);
        try {
            MockedInjectable injectable = DependencyInjection.getInstance().get(MockedInjectable.class);
            assertNotNull(injectable);
            assertTrue( injectable instanceof MockedInjectableImpl );
        } catch (DependencyInjectionException e) {
            fail(e.getClass().getSimpleName() + " was thrown ");
        }
    }

    @Test
    void testInjectableNotFound() {
        try {
            DependencyInjection.getInstance().get(DummyInjectable.class);
            fail("No NoInjectableFoundException thrown");
        } catch (NoInjectableFoundException e) {
            //intentionally empty
        } catch (Exception e) {
            fail("Exception was thrown.");
        }
    }

    @Test
    void testInjectableUnsatisfiedDependency() {
        try {
            DependencyInjection.getInstance().register(MockedInjectable.class, MockedInjectableImpl::new, DummyInjectable.class);
            //intentionally do not register the DummyInjectable

            DependencyInjection.getInstance().get(MockedInjectable.class);
            fail("No UnsatisfiedDependencyException thrown");
        } catch (UnsatisfiedDependencyException e) {
            //intentionally empty
        } catch (Exception e) {
            fail("Exception was thrown.");
        }
    }

    @Test
    void testCyclicDependencyHandling() {
        DependencyInjection.getInstance().register(MockedInjectable.class, MockedInjectableImpl::new, SecondMockedInjectable.class);
        DependencyInjection.getInstance().register(SecondMockedInjectable.class, SecondMockedInjectableImpl::new, MockedInjectable.class);

        try {
            DependencyInjection.getInstance().get(MockedInjectable.class);
            fail("It worked and it shouldn't");
        } catch (CyclicDependencyException e) {
            //intentionally empty
        } catch (DependencyInjectionException e) {
            fail(e.getClass().getSimpleName() + " was thrown ");
        }
    }

    @Test
    void testEmailMustBeSent() throws DependencyInjectionException {
        new Application().boostrap();

        //overwrite the EmailService in the dependency injection with a Spy Object
        AtomicBoolean sendEmailCalled = new AtomicBoolean(false);
        DependencyInjection.getInstance().register(EmailService.class, () -> (subject, destinationAddress, emailBodyContent) -> {
            sendEmailCalled.set(true);
            return true;
        });

        DependencyInjection.getInstance().get(FuellingService.class).sendRefuellingNotification();

        //it will fail if the email is not called within sendRefuellingNotification
        assertTrue(sendEmailCalled.get());
    }

}
