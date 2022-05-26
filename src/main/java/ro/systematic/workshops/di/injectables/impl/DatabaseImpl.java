package ro.systematic.workshops.di.injectables.impl;

import ro.systematic.workshops.di.injectables.Database;
import ro.systematic.workshops.di.injectables.VehicleDataSource;
import ro.systematic.workshops.di.model.MilitaryVehicle;

import java.util.List;
import java.util.stream.Collectors;

public class DatabaseImpl implements Database {
    VehicleDataSource dataSource;

    @Override
    public List<MilitaryVehicle> getByFuelTankLevel(float maximumLevel) {
        return dataSource.getData()
                .stream()
                .filter(v -> v.getFuelTankLevel() <= maximumLevel)
                .collect(Collectors.toList());
    }

    @Override
    public List<MilitaryVehicle> getAll() {
        return dataSource.getData();
    }


    public void setDataSource(VehicleDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
