package models;

import datastructures.HashMap;
import java.util.Date;

public class Maintenance {
    private int vehicleId;
    private HashMap<String, String> vehiclePartsRepaired; // Key: Part name, Value: Repair/Replacement status
    private Date dateOfRepairs;
    private float costOfRepairs;
    private String mechanicShop;

    public Maintenance(int vehicleId, Date dateOfRepairs, float costOfRepairs, String mechanicShop) {
        this.vehicleId = vehicleId;
        this.vehiclePartsRepaired = new HashMap<>();
        this.dateOfRepairs = dateOfRepairs;
        this.costOfRepairs = costOfRepairs;
        this.mechanicShop = mechanicShop;
    }

    public void addPartRepaired(String partName, String status) {
        vehiclePartsRepaired.put(partName, status);
    }

    public boolean isPartRepaired(String partName) {
        return vehiclePartsRepaired.containsKey(partName);
    }

    public float getTotalCost() {
        return costOfRepairs;
    }

    public Date getDateOfRepairs() {
        return dateOfRepairs;
    }

    // Getters
    public int getVehicleId() { return vehicleId; }
    public HashMap<String, String> getVehiclePartsRepaired() {
        return vehiclePartsRepaired;
    }
    public float getCostOfRepairs() { return costOfRepairs; }
    public String getMechanicShop() { return mechanicShop; }
}
