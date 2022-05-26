package ro.systematic.workshops.di.injectables;

import ro.systematic.workshops.di.framework.Injectable;

public interface FuellingService extends Injectable {
    String getFuellingReport(int maximumFuelLevel);

    void sendRefuellingNotification();
}
