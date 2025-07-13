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

    
    public Order(int orderId, String clientName, String origin, String destination, LocalDateTime scheduledDateTime) {
        this.orderId = orderId;
        this.clientName = clientName;
        this.origin = origin;
        this.destination = destination;
        this.scheduledDateTime = scheduledDateTime;
        this.deliveryStatus = "Scheduled"; 
    }

    
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

    
    @Override
    public String toString() {
        return "Order ID: " + orderId +
               "\nClient: " + clientName +
               "\nDriver: " + (assignedDriver != null ? assignedDriver : "Unassigned") +
               "\nOrigin: " + origin +
               "\nDestination: " + destination +
               "\nStatus: " + deliveryStatus +
               "\nScheduled: " + scheduledDateTime +
               "\nPickup Time: " + (pickupTime != null ? pickupTime : "N/A") +
               "\nETA: " + (eta != null ? eta : "N/A") +
               "\nWait Time: " + getWaitTime().toMinutes() + " minutes";
    }
}
