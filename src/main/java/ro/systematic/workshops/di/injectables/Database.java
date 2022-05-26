package ro.systematic.workshops.di.injectables;

import ro.systematic.workshops.di.framework.Injectable;
import ro.systematic.workshops.di.model.MilitaryVehicle;

import java.util.List;

public interface Database extends Injectable {
    List<MilitaryVehicle> getAll();
    List<MilitaryVehicle> getByFuelTankLevel(float maximumLevel);
}
