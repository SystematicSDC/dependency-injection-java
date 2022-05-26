package ro.systematic.workshops.di.injectables.impl;

import ro.systematic.workshops.di.injectables.VehicleDataSource;
import ro.systematic.workshops.di.model.MilitaryVehicle;

import java.util.Arrays;
import java.util.List;

public class VesselDataSource implements VehicleDataSource {
    private static final String US_NAVY_NAME = "US Navy";
    private static final String FR_NAVY_NAME = "FR Navy";

    private static final MilitaryVehicle[] OBJECTS = {
            new MilitaryVehicle("Aircraft carrier 1126", 24.5f, 5820000, US_NAVY_NAME),
            new MilitaryVehicle("Aircraft carrier 6872", 5.9f, 4300000, FR_NAVY_NAME),
            new MilitaryVehicle("Cargo 1567-Charlie", 16.4f, 1300000, US_NAVY_NAME),
            new MilitaryVehicle("Navy stealth IX-529", 11f, 4800000, US_NAVY_NAME),
            new MilitaryVehicle("Cargo 43497-Foxtrot", 99.0f, 3900000, US_NAVY_NAME),
            new MilitaryVehicle("Civilian cargo 54612", 4.3f, 3700000, FR_NAVY_NAME),
            new MilitaryVehicle("Zumwalt destroyer IX9", 11f, 2100000, US_NAVY_NAME)
    };

    @Override
    public List<MilitaryVehicle> getData() {
        return Arrays.asList(OBJECTS);
    }

}
