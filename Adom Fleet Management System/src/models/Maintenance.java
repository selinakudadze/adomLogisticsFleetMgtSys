package models;

import datastructures.HashMap;
import java.util.Date;

public class Maintenance {
    private int vehicleId;
    private HashMap<String, String> vehiclePartsRepaired; // Key: Part name, Value: Repair/Replacement status
    private Date dateOfRepairs;
    private float costOfRepairs;
    private String mechanicShop;
    private int daysSinceLastRepairs;
    private String[] partsNeedingRepairs = new String[3]; // vehicles should have at most three parts needing repairs

    public Maintenance(int vehicleId, int daysSinceLastRepairs, Date dateOfRepairs, float costOfRepairs, String mechanicShop) {
        this.vehicleId = vehicleId;
        this.vehiclePartsRepaired = new HashMap<>();
        this.dateOfRepairs = dateOfRepairs;
        this.costOfRepairs = costOfRepairs;
        this.mechanicShop = mechanicShop;
        this.daysSinceLastRepairs = daysSinceLastRepairs;
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
    public int getDaysSinceLastRepairs(){
        return this.daysSinceLastRepairs;
    }
    public void setDaysSinceLastRepairs(int n){
        this.daysSinceLastRepairs = n;
    }
    public void setDateOfRepairs(Date dateOfRepairs){
        this.dateOfRepairs = dateOfRepairs;
    }
    public void setCostOfRepairs(float costOfRepairs){
        this.costOfRepairs = costOfRepairs;
    }
    public void setMechanicShop(String mechanicShop){
        this.mechanicShop = mechanicShop;
    }
    public void addPartNeedingRepairs(String partNeedingRepairs, int priority){
        // priority is from 1 to 3
        if (priority < 1 || priority > 3) {
            System.out.println("Priority must be 1 (high), 2 (medium), or 3 (low)");
            return;
        }
        if (partsNeedingRepairs[2] != null) {
            System.out.println("Vehicle already has three parts needing repairs. Fix one first.");
            return;
        }
        switch (priority){
            case 1:
                partsNeedingRepairs[2] = partsNeedingRepairs[1];
                partsNeedingRepairs[1] = partsNeedingRepairs[0];
                partsNeedingRepairs[0] = partNeedingRepairs;
                break;
            case 2:
                if (partsNeedingRepairs[0] != null) {
                    partsNeedingRepairs[2] = partsNeedingRepairs[1];
                    partsNeedingRepairs[1] = partNeedingRepairs;
                } else {
                    partsNeedingRepairs[0] = partNeedingRepairs;
                }
                break;
            case 3:
                if (partsNeedingRepairs[0] == null) {
                    partsNeedingRepairs[0] = partNeedingRepairs;
                } else if (partsNeedingRepairs[1] == null) {
                    partsNeedingRepairs[1] = partNeedingRepairs;
                } else {
                    partsNeedingRepairs[2] = partNeedingRepairs;
                }
                break;
            default:
                break;
        }
    }

    public String getUrgentPartNeedingRepairs(){
        if(this.partsNeedingRepairs[0] == null){
            return "null";
        }
        String urgentPartNeedingRepairs = this.partsNeedingRepairs[0];
        for (int i = 0; i < 2; i++) {
            this.partsNeedingRepairs[i] = this.partsNeedingRepairs[i + 1];
        }
        partsNeedingRepairs[2] = null;
        return urgentPartNeedingRepairs;
    }
}
