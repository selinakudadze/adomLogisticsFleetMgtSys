package models;

import datastructures.HashMap;

public class Vehicle {
    private int vehicleId;
    private String registrationNumber;
    private String vehicleType;
    private int mileage;
    private float fuelUse;
    private String assignedDriverId;
    private HashMap<Integer, Maintenance> maintenanceHistory; // Key: mileageAtService, Value: Maintenance object
    private double[] currentLocation = new double[2]; // [longitude , latitude]
    private String currentDriver;

    public Vehicle(int vehicleId, String registrationNumber, String vehicleType, int mileage, float fuelUse, double currentLocLongitude, double currentLocLatitude, String currentDriver) {
        this.vehicleId = vehicleId;
        this.registrationNumber = registrationNumber;
        this.vehicleType = registrationNumber;
        this.mileage = mileage;
        this.fuelUse = fuelUse;
        this.assignedDriverId = null; // No driver assigned by default
        this.maintenanceHistory = new HashMap<>();
        this.currentLocation[0] = currentLocLongitude; // longitude
        this.currentLocation[1] = currentLocLatitude; //latitude
        this.currentDriver = currentDriver;
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

    // Getters
    public int getVehicleId() { return vehicleId;}
    public String getRegistrationNumber() { return registrationNumber; }
    public String getVehicleType() { return vehicleType;}
    public int mileage() { return mileage; }
    public float getFuelUse() { return fuelUse; }
    public String getAssignedDriverId() { return assignedDriverId; }
    public HashMap<Integer, Maintenance> getMaintenanceHistory() { return maintenanceHistory; }
    public double[] getLocation() { return currentLocation; }
    public String getCurrentDriver()  { return currentDriver; }

    // Setters
    public void setAssignedDriverId(String assignedDriverId) { this.assignedDriverId = assignedDriverId; }
    public void setMileage(int mileage) { this.mileage = mileage; }
    public void setFuelUse(float fuelUse) { this.fuelUse = fuelUse; }
    public void setLocation(double currentLocLongitude, double currentLocLatitude) {
        this.currentLocation[0] = currentLocLongitude;
        this.currentLocation[1] = currentLocLatitude;
    }
    public void setCurrentDriver(String currentDriver) { this.currentDriver = currentDriver;}
}