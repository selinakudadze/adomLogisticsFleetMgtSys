package models;

import datastructures.HashMap;
import datastructures.LinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

public class Vehicle implements Comparable<Vehicle>{
    private int vehicleId;
    private String registrationNumber;
    private String vehicleType;
    private int mileage;
    private float fuelUse;
    private String assignedDriverId;
    private HashMap<Integer, Maintenance> maintenanceHistory; // Key: mileageAtService, Value: Maintenance object
    private double currentLong; // longitude
    private double currentLat; // latitude
    private String currentDriver;
    private int daysSinceLastService; // days since last service
    private Maintenance maintenanceInfo;

    public Vehicle(int vehicleId, String registrationNumber,
                   String vehicleType, int mileage, float fuelUse,
                   double currentLong, double currentLat,
                   String currentDriver, int daysSinceLastService) {
        this.vehicleId = vehicleId;
        this.registrationNumber = registrationNumber;
        this.vehicleType = vehicleType;
        this.mileage = mileage;
        this.fuelUse = fuelUse;
        this.assignedDriverId = null; // No driver assigned by default
        this.maintenanceHistory = new HashMap<>();
        this.currentLong = currentLong;
        this.currentLat = currentLat;
        this.currentDriver = currentDriver;
        this.daysSinceLastService = daysSinceLastService;
        this.maintenanceInfo = new Maintenance(vehicleId);
    }

    public Vehicle(int ID) {
        try {
            File driverFile = new File("Adom Fleet Management System/src/dummyTextFiles/Vehicles.txt");
            Scanner driverScanner = new Scanner(driverFile);

            while (driverScanner.hasNextLine()) {
                String[] fields = driverScanner.nextLine().split(";");
                LinkedList<String> experience = new LinkedList<>();


                String[] experienceList = fields[5].split(",");
                System.out.println(fields[0]);
                if (fields[0].equals(ID)) {
                    int j = 0;
                    System.out.println(fields[0]);

                    while (j < experienceList.length) {
                        experience.add(experienceList[j]);
                        j++;
                    }
                    this.vehicleId=Integer.parseInt(fields[0]);
                    this.registrationNumber=fields[1];
                    this.vehicleType=fields[2];
                    this.mileage=Integer.parseInt(fields[3]);
                    this.fuelUse=Float.parseFloat(fields[4]);
                    this.assignedDriverId=null;

                    this.daysSinceLastService=Integer.parseInt(fields[5]);
                    this.maintenanceHistory=null;
                   this.currentLong=0;
                   this.currentLat=0;
                    this.maintenanceInfo = new Maintenance(vehicleId);
                    return;
                }
            }
            System.out.println("Driver with ID " + ID + " not found.");
        } catch (FileNotFoundException e) {
            System.out.println("Driver file not found: "+e.getMessage());;
        }
    }

    public boolean canBeAssignedToDriver() {
        return assignedDriverId == null && maintenanceHistory.containsKey(mileage);
    }

    public void checkOffForMaintenance(Maintenance maintenance) {
        maintenanceHistory.put(maintenance.getVehicleId(), maintenance);
    }

    public float calculateAverageFuelConsumption() {
        if (maintenanceHistory.size() == 0) return fuelUse;
        int serviceCount = maintenanceHistory.size();
        float totalFuel = fuelUse * serviceCount;
        return totalFuel / serviceCount;
    }

    public boolean flagOutliers(float threshold) {
        float avgFuel = calculateAverageFuelConsumption();
        return Math.abs(fuelUse - avgFuel) > threshold;
    }

    public static void sortVehicleByFuelPerformance(Vehicle[] vehicles) {
        // Simple bubble sort for demonstration (can be replaced with merge/quick sort)
        for (int i = 0; i < vehicles.length - 1; i++) {
            for (int j = 0; j < vehicles.length - i - 1; j++) {
                if (vehicles[j].fuelUse > vehicles[j + 1].fuelUse) {
                    Vehicle temp = vehicles[j];
                    vehicles[j] = vehicles[j + 1];
                    vehicles[j + 1] = temp;
                }
            }
        }
    }

    public static Vehicle[] filterVehiclesByFuelPerformance(Vehicle[] vehicles, float minFuel, float maxFuel) {
        HashMap<Integer, Vehicle> filteredMap = new HashMap<>();
        int index = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.fuelUse >= minFuel && vehicle.fuelUse <= maxFuel) {
                filteredMap.put(index++, vehicle);
            }
        }
        Vehicle[] result = new Vehicle[filteredMap.size()];
        for (int i = 0; i < filteredMap.size(); i++) {
            result[i] = filteredMap.get(i);
        }
        return result;
    }

    @Override
    public int compareTo(Vehicle other) {
        // Prioritize by mileage (higher mileage first), then by
        // lastServiceDate (higher days since service)
        if (this.mileage != other.mileage) {
            return Integer.compare(other.mileage, this.mileage);
            // Higher mileage = higher priority
        }
        return Integer.compare(other.daysSinceLastService, this.daysSinceLastService);
    }

    @Override
    public String toString() {
        String vehicleInfo = registrationNumber + "| Mileage: " + " | LastService: " + daysSinceLastService + " | FuelUse: " + fuelUse + " | Location: (" + currentLong + ", " + currentLat + ")";
        if(this.maintenanceInfo != null){
            vehicleInfo += "| Urgent part needing repairs: " + this.maintenanceInfo.getUrgentPartNeedingRepairs();
        }
        return vehicleInfo;
    }

    // Getters
    public int getVehicleId() { return vehicleId;}
    public String getRegistrationNumber() { return registrationNumber; }
    public String getVehicleType() { return vehicleType;}
    public int getMileage() { return mileage; }
    public float getFuelUse() { return fuelUse; }
    public String getAssignedDriverId() { return assignedDriverId; }
    public HashMap<Integer, Maintenance> getMaintenanceHistory() { return maintenanceHistory; }
    public double getCurrentLat() { return currentLat; }
    public double getCurrentLong() { return currentLong; }
    public String getCurrentDriver()  { return currentDriver; }
    public int getDaysSinceLastService() { return daysSinceLastService;}


    // Setters
    public void setAssignedDriverId(String assignedDriverId) { this.assignedDriverId = assignedDriverId; }
    public void setMileage(int mileage) { this.mileage = mileage; }
    public void setFuelUse(float fuelUse) { this.fuelUse = fuelUse; }
    public void setCurrentLat(double currentLat) {this.currentLat = currentLat;}
    public void setCurrentLong(double currentLong) {this.currentLong = currentLong;}
    public void setCurrentDriver(String currentDriver) { this.currentDriver = currentDriver;}
    public void setDaysSinceLastService(int daysSinceLastService) { this.daysSinceLastService = daysSinceLastService;}
    public Maintenance getMaintenanceInfo(){
        return this.maintenanceInfo;
    }
    public void updateMaintenanceInfo(String partRepaired, int daysSinceLastRepairs, Date dateOfLastRepairs, float costOfRepairs, String lastMechanicShop){
        if(this.getMaintenanceInfo() == null){
            this.maintenanceInfo = new Maintenance(this.vehicleId, daysSinceLastRepairs, dateOfLastRepairs, lastMechanicShop);
        }
        this.maintenanceInfo.setDaysSinceLastRepairs(daysSinceLastRepairs);
        this.maintenanceInfo.setDateOfLastRepairs(dateOfLastRepairs);
        this.maintenanceInfo.setLastMechanicShop(lastMechanicShop);
        this.maintenanceInfo.addPartRepaired(partRepaired, costOfRepairs);
    }
    public void setMaintenanceInfo(Maintenance maintenance){
        this.maintenanceInfo = maintenance;
    }

    public void addRepair(String partNeedingRepairs, int priority){
        if(this.getMaintenanceInfo() == null){
            this.maintenanceInfo = new Maintenance(this.vehicleId, 0, null, "");
        }
        this.maintenanceInfo.addPartNeedingRepairs(partNeedingRepairs, priority);
    }
//
//    @Override
//    public String toString() {
//        return "Vehicle ID: " + vehicleId +
//                "\nRegistration Number " + registrationNumber +
//                "\nVehicle Type: " + vehicleType +
//                "\nMileage " + mileage +
//                "\nFuel Usage " + fuelUse +
//                "\nCurrentDriver: " + currentDriver +
//                "\nDays Since last service " + daysSinceLastService;
//
//    }
}