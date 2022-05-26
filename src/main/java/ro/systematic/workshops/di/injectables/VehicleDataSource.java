package ro.systematic.workshops.di.injectables;

import ro.systematic.workshops.di.framework.Injectable;
import ro.systematic.workshops.di.model.MilitaryVehicle;

import java.util.List;

public interface VehicleDataSource extends Injectable {
    List<MilitaryVehicle> getData();
}
