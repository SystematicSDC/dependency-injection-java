package ro.systematic.workshops.di.injectables.impl;

import ro.systematic.workshops.di.injectables.VehicleDataSource;
import ro.systematic.workshops.di.model.MilitaryVehicle;

import java.util.Arrays;
import java.util.List;

public class TanksDataSource implements VehicleDataSource {

    private static final MilitaryVehicle[] OBJECTS = {
            new MilitaryVehicle("Black Panther 44235", 14.5f, 1350, "DIV3 TANK"),
            new MilitaryVehicle("M1A2 Stealth XI-789", 2.9f, 900, "DIV1 TANK"),
            new MilitaryVehicle("Leopard Heavy 2A7Z34", 13.4f, 1900, "DIV5 TANK"),
            new MilitaryVehicle("Challenger 2-91-Delta", 12f, 1450, "DIV4 TANK"),
            new MilitaryVehicle("Panzer VII Lowe Heavy", 71.0f, 2100, "DIV4 TANK"),
            new MilitaryVehicle("Landkreuzer P. 1000", 4.3f, 1300, "DIV7 TANK"),
            new MilitaryVehicle("T-26 Lightweight-9", 18f, 350, "DIV1 TANK")
    };

    @Override
    public List<MilitaryVehicle> getData() {
        return Arrays.asList(OBJECTS);
    }

}
