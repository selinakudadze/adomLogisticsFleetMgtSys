package models;

import datastructures.HashMap;
import datastructures.HashNode;
import datastructures.LinkedList;
import datastructures.Node;

import java.util.Date;

public class Maintenance {
    private int vehicleId;
    private HashMap<String, LinkedList<Float>> vehiclePartsRepaired; // Key: Part name, Value: Repair/Replacement status
    private Date dateOfLastRepairs;
    private String lastMechanicShop;
    private int daysSinceLastRepairs;
    private String[] partsNeedingRepairs = new String[3]; // vehicles should have at most three parts needing repairs

    public Maintenance(int vehicleId, int daysSinceLastRepairs, Date dateOfLastRepairs, String lastMechanicShop) {
        this.vehicleId = vehicleId;
        this.vehiclePartsRepaired = new HashMap<>();
        this.dateOfLastRepairs = dateOfLastRepairs;
        this.lastMechanicShop = lastMechanicShop;
        this.daysSinceLastRepairs = daysSinceLastRepairs;
    }

    public Maintenance(int vehicleId, HashMap<String, LinkedList<Float>> vehiclePartsRepaired, int daysSinceLastRepairs, Date dateOfLastRepairs, String lastMechanicShop) {
        this.vehicleId = vehicleId;
        this.vehiclePartsRepaired = vehiclePartsRepaired;
        this.dateOfLastRepairs = dateOfLastRepairs;
        this.lastMechanicShop = lastMechanicShop;
        this.daysSinceLastRepairs = daysSinceLastRepairs;
    }

    public void addPartRepaired(String partName, float cost) {
        if(vehiclePartsRepaired.containsKey(partName)){
            vehiclePartsRepaired.get(partName).add(cost);
        }
        LinkedList<Float> currLinkedList = new LinkedList<>();
        currLinkedList.add(cost);
        vehiclePartsRepaired.put(partName, currLinkedList);
    }

    public boolean isPartRepaired(String partName) {
        return vehiclePartsRepaired.containsKey(partName);
    }

    public float getTotalCostOfRepairs() {
        float totalCost = 0;
        HashNode<String, LinkedList<Float>>[] buckets = vehiclePartsRepaired.getBuckets();
        for(int i = 0; i < buckets.length; i++){
            if(buckets[i] != null) {
                LinkedList<Float> currLinkedList = vehiclePartsRepaired.get(buckets[i].getKey());
                Node<Float> head = currLinkedList.getHead();
                while (head != null) {
                    totalCost += head.entity;
                    head = head.nextNode;
                }
            }
        }
        return totalCost;
    }

    public Date getDateOfLastRepairs() {
        return dateOfLastRepairs;
    }

    // Getters
    public int getVehicleId() { return vehicleId; }
    public HashMap<String, LinkedList<Float>> getVehiclePartsRepaired() {
        return vehiclePartsRepaired;
    }
    public String getLastMechanicShop() { return lastMechanicShop; }
    public int getDaysSinceLastRepairs(){
        return this.daysSinceLastRepairs;
    }
    public void setDaysSinceLastRepairs(int n){
        this.daysSinceLastRepairs = n;
    }
    public void setDateOfLastRepairs(Date dateOfRepairs){
        this.dateOfLastRepairs = dateOfRepairs;
    }
    public void setLastMechanicShop(String mechanicShop){
        this.lastMechanicShop = mechanicShop;
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
