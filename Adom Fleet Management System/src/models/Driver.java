package models;

import datastructures.LinkedList;

public class Driver{




    public enum OrderStatus{
        IN_TRANSIT,
        DELIVERED,
        REROUTED

    }//must have different options

    public enum AvailabilityStatus{
        //after a package has been delivered, this updates the availability of the driver
        ON_DUTY,
        OFF_DUTY,
    }

    private String driverID;
    private String driverName;
    private String licenseTpye;
    private int assignedVehicleID;
    private int assignedOrderID;
    private String availability;
    private OrderStatus orderStatus;
    private String currentDriverLocation;
    private LinkedList<String> experience;
    //proximity??




    //when we instantiate a driver, we will read from the text file(data from the text file will be used to instantiate drivers
    public Driver(String driverID,String driverName,String licenseTpye,String availability,String location,LinkedList<String> experience){
        this.driverID=driverID;
        this.driverName=driverName;
        this.licenseTpye=licenseTpye;
        this.assignedVehicleID=0;
        this.assignedOrderID=0;
        this.orderStatus=null;
        this.availability=availability;
        this.currentDriverLocation=location;
        this.experience=experience;
        //this.proximity???

    }


    //getters and setters
    public String getDriverID(){
        return driverID;
    }

    public String getDriverName(){
        return driverName;
    }

    public String getLicenseType(){
        return licenseTpye;
    }


    public void setAssignVehicleID(int vehicleID){
        this.assignedVehicleID=vehicleID;
    }
    public int getAssignedVehicleID(){
        return assignedVehicleID;
    }


    public void setAssignOrderID(int orderID){
        this.assignedOrderID=orderID;
    }
    public int getAssignedOrderID(){
        return assignedOrderID;
    }

    public void updateOrderStatus(OrderStatus status){
        orderStatus=status;
    }
    public String getOrderStatus(){
        return orderStatus.toString();
    }

    public String getCurrentDriverLocation(){
        return currentDriverLocation;
    }





    public void updateAvailability(AvailabilityStatus status){
        //change availability status in text file as well
        this.availability=status.toString();
    }
    public String getAvailability(){
        return this.availability;
    }

    public void addExperience(String location){
        experience.add(location);
        //add method used in the proximity implementation of assigning orders to drivers
        //write method to add location to experience in text file needed
    }

    public boolean searchExperience(String orderOrigin){
        return experience.search(orderOrigin);
    }


}
