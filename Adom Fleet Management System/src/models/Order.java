package models;

import java.time.LocalDateTime;
import java.time.Duration;

public class Order {
    private int orderId;
    private String clientName;
    private String assignedDriver; 
    private String origin;
    private String destination;
    private String deliveryStatus;
    private LocalDateTime scheduledDateTime;
    private LocalDateTime pickupTime;
    private LocalDateTime eta;

    private double originLatitude;
    private double originLongitude;
    private double destinationLatitude;
    private double destinationLongitude;

    private double currentLatitude;
    private double currentLongitude;

    public Order(int orderId, String clientName, String origin, String destination, LocalDateTime scheduledDateTime) {
        this.orderId = orderId;
        this.clientName = clientName;
        this.origin = origin;
        this.destination = destination;
        this.scheduledDateTime = scheduledDateTime;
        this.deliveryStatus = "Scheduled"; 
    }

    // --- Getters and Setters ---
    public int getOrderId() {
        return orderId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(String assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public LocalDateTime getScheduledDateTime() {
        return scheduledDateTime;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalDateTime getEta() {
        return eta;
    }

    public void setEta(LocalDateTime eta) {
        this.eta = eta;
    }

    public Duration getWaitTime() {
        if (pickupTime == null) {
            return Duration.ZERO;
        }
        return Duration.between(scheduledDateTime, pickupTime);
    }

    public void assignToDriver(String driverName) {
        this.assignedDriver = driverName;
    }

    public void updateDeliveryStatus(String newStatus) {
        this.deliveryStatus = newStatus;
    }

    // --- Location Methods ---

    public double getOriginLatitude() {
        return originLatitude;
    }

    public void setOriginLatitude(double originLatitude) {
        this.originLatitude = originLatitude;
    }

    public double getOriginLongitude() {
        return originLongitude;
    }

    public void setOriginLongitude(double originLongitude) {
        this.originLongitude = originLongitude;
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId +
               "\nClient: " + clientName +
               "\nDriver: " + (assignedDriver != null ? assignedDriver : "Unassigned") +
               "\nOrigin: " + origin + " (" + originLatitude + ", " + originLongitude + ")" +
               "\nDestination: " + destination + " (" + destinationLatitude + ", " + destinationLongitude + ")" +
               "\nCurrent Location: (" + currentLatitude + ", " + currentLongitude + ")" +
               "\nStatus: " + deliveryStatus +
               "\nScheduled: " + scheduledDateTime +
               "\nPickup Time: " + (pickupTime != null ? pickupTime : "N/A") +
               "\nETA: " + (eta != null ? eta : "N/A") +
               "\nWait Time: " + getWaitTime().toMinutes() + " minutes";
    }
}
