package ro.systematic.workshops.di;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.systematic.workshops.di.exceptions.DependencyInjectionException;
import ro.systematic.workshops.di.framework.DependencyInjection;
import ro.systematic.workshops.di.framework.InstanceFactory;
import ro.systematic.workshops.di.injectables.Database;
import ro.systematic.workshops.di.injectables.EmailService;
import ro.systematic.workshops.di.injectables.FuellingService;
import ro.systematic.workshops.di.injectables.VehicleDataSource;
import ro.systematic.workshops.di.injectables.impl.*;

@SuppressWarnings("java:S1604")//suppress warnings for using lambdas
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.debug("Starting app...");

        Application app = new Application();
        app.boostrap();

        //once we have setup up the core services start the application logic
        app.start();
    }

    DependencyInjection injector;

    public Application() {
        injector = DependencyInjection.getInstance();
    }

    /**
     * Method that is called at application startup and is responsible to
     * setup the core elements and services of the application.
     * */
    public void boostrap(){
        //register a new injectable and instruct to create instance by using the default constructor
        injector.register(VehicleDataSource.class, new InstanceFactory<VehicleDataSource>() {
            @Override
            public VehicleDataSource newInstance() {
                return new VesselDataSource();//FOR TASK 4
            }
        });
        injector.register(VehicleDataSource.class, TanksDataSource::new, "tanks");
        //register new injectable with dependency via a setter
        injector.register(Database.class, () -> {
            DatabaseImpl database = new DatabaseImpl();
            database.setDataSource(injector.get(VehicleDataSource.class, "tanks"));
            return database;
        }, VehicleDataSource.class);

        //register new injectable with dependency via a constructor
        injector.register(FuellingService.class,
                () -> new FuellingServiceImpl(injector.get(Database.class), injector.get(EmailService.class)),
                Database.class, EmailService.class);

        injector.register(EmailService.class, new InstanceFactory<EmailService>() {
            @Override
            public EmailService newInstance() throws DependencyInjectionException {
                return new EmailServiceImpl();
            }
        });
    }

    public void start() {
        try {
            FuellingService fuellingService = injector.get(FuellingService.class);
            fuellingService.sendRefuellingNotification();

            /* TODO: TASK1 - how to register and inject dependency
             Inspect the method from above "boostrap" on how to register a dependency and how to inject it.
            */

            /* TODO: TASK2 - different use cases and situations that can appear
             Run and inspect the unit tests from DependencyInjectionTest to get an idea of different situations might appear.
             Some of the units tests will fail, we will have all them passed and green in the next task.
            */

            /* TODO: TASK3 - register and inject EmailService in FuellingService
             Make the FuellingService implementation for method sendRefuellingNotification use the EmailService to simulate sending the report via email.
             Modify implementation to call the method "sendEmail" from EmailService instead of the logging service.
             You will have to:
             - register the EmailService in the dependency injection
             - declare EmailService as a dependency to the FuellingService and decouple code from the EmailServiceImpl
             - inject EmailService in FuellingService via setter or constructor
             - run the application or the unit tests
            */

            /* TODO: TASK4 - For the data source, register VesselDataSource instead of TankDataSource.
             Because we are using dependency injection and the service implementation is decoupled from the actual dependencies implementation
             the application is written in a modular way and it can be reused or converted easily.

             By changing the data source is registered from TanksDataSource to VesselDataSource, observe that we can create now reports for Vessels & Warships
            */

            /* TODO: Challenge1 - it is possible to have multiple implementations for a Injectable?
             Can we support both VesselDataSource and TankDataSource ?
             When injecting VehicleDataSource, how we could differentiate between VesselDataSource or TankDataSource?
            */

            /* TODO: Challenge2 - how an implementation without providing callbacks for creating the instance could look like?
             Can Reflection be used? Maybe Annotations?
             Have a brainstorming session in your group and exchange ideas.
            */
        } catch (DependencyInjectionException e) {
            logger.error("Error occurred", e);
        }
    }

}
