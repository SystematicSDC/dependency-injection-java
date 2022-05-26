package ro.systematic.workshops.di.injectables.impl;

import ro.systematic.workshops.di.injectables.Database;
import ro.systematic.workshops.di.injectables.EmailService;
import ro.systematic.workshops.di.injectables.FuellingService;
import ro.systematic.workshops.di.model.MilitaryVehicle;

public class FuellingServiceImpl implements FuellingService {

    private Database database;
    private EmailService emailService;

    public FuellingServiceImpl(Database database, EmailService emailService) {
        this.database = database;
        this.emailService = emailService;
    }

    @Override
    public void sendRefuellingNotification() {
        String report = getFuellingReport(20);
        this.emailService.sendEmail("Refuelling report","gas-station@demo-defense.com", report);
    }

    int counter = 0;
    @Override
    public String getFuellingReport(int maximumFuelLevel) {
        StringBuilder sb = new StringBuilder();

        //add report header
        sb.append("\n\n========================== REFUELING REPORT ==========================\n");
        sb.append("No. \t Name \t\t\t\t\t\t\t Fleet name \t Fuel level\n");

        //add rows
        counter = 0;
        database.getByFuelTankLevel(maximumFuelLevel)
                .forEach(vehicle -> appendReportLine(sb, ++counter, vehicle));

        //add footer
        sb.append("---------------------------------------------------------------------");

        return String.valueOf(sb);
    }

    private void appendReportLine(StringBuilder sb, int rowNumber, MilitaryVehicle vehicle) {
        sb.append(String.format("%d \t\t %s \t\t\t %s \t\t %.1f %% %n",
                rowNumber, vehicle.getName(), vehicle.getFleetName(), vehicle.getFuelTankLevel()));
    }
}
