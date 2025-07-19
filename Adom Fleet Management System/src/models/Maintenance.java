package models;

import datastructures.*;

import java.util.Date;

public class Maintenance {
    private int vehicleId;
    private HashMap<String, LinkedList<Float>> vehiclePartsRepaired; // Key: Part name, Value: Repair/Replacement status
    private Date dateOfLastRepairs;
    private String lastMechanicShop;
    private int daysSinceLastRepairs;
    private PriorityQueue<String> partsNeedingRepairs = new PriorityQueue<>(); // vehicles should have at most three parts needing repairs

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
        if(!partsNeedingRepairs.isEmpty()){
            partsNeedingRepairs.dequeue();
        }
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
        this.partsNeedingRepairs.enqueue(partNeedingRepairs, priority);
    }

    public String getUrgentPartNeedingRepairs(){
        return this.partsNeedingRepairs.peek().getValue();
    }
}