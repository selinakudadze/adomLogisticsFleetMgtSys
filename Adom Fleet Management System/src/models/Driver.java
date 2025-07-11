package models;

import datastructures.LinkedList;

public class Driver{

    public enum OrderStatus{
        INTRANSIT,
        DELIVERED,
        REROUTED

    }//must have different options

    public enum AvailabilityStatus{
        //after a package has been delivered, this updates the availability of the driver
        ON_DUTY,
        OFF_DUTY,
    }

    private int driverID;
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
    public Driver(int driverID,String driverName,String licenseTpye,String avaliability,String location){
        this.driverID=driverID;
        this.driverName=driverName;
        this.licenseTpye=licenseTpye;
        this.assignedVehicleID=0;
        this.assignedOrderID=0;
        this.orderStatus=null;
        this.availability=avaliability;
        this.currentDriverLocation=location;
        //this.proximity???

    }

    public void assignVehicle(int vehicleID){
        this.assignedVehicleID=vehicleID;
    }

    public void assignOrder(int orderID){
        this.assignedOrderID=orderID;
    }

    public void updateAvailability(AvailabilityStatus status){
        this.availability=status.toString();

    }

    public void addExperience(String location){
        experience.add(location);
        //write method to add location to experience in text file needed
    }


}
