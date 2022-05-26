package ro.systematic.workshops.di.model;

public class MilitaryVehicle {
    //name of vessel
    String name;

    //level of the fuel tank as percentage. e.g.: 50.0 (meaning is half full)
    Float fuelTankLevel;

    //tank capacity liters
    Integer fuelTankCapacity;

    //vehicle fleet name
    String fleetName;

    public MilitaryVehicle(String name, Float fuelTankLevel, Integer fuelTankCapacity, String fleetName){
        setName(name);
        setFuelTankLevel(fuelTankLevel);
        setFuelTankCapacity(fuelTankCapacity);
        setFleetName(fleetName);
    }


    public String getFleetName() {
        return fleetName;
    }

    public void setFleetName(String fleetName) {
        this.fleetName = fleetName;
    }
    public Integer getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(Integer fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getFuelTankLevel() {
        return fuelTankLevel;
    }

    public void setFuelTankLevel(Float fuelTankLevel) {
        this.fuelTankLevel = fuelTankLevel;
    }

    @Override
    public String toString() {
        return "Vessel{" +
                "name='" + name + '\'' +
                ", fuelTankLevel=" + fuelTankLevel +
                '}';
    }
}
