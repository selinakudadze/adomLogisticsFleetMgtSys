package models;

import datastructures.LinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
    public Driver(String ID) {
        try {
            File driverFile = new File("Adom Fleet Management System/src/dummyTextFiles/Drivers.txt");
            Scanner driverScanner = new Scanner(driverFile);

            while (driverScanner.hasNextLine()) {
                String[] fields = driverScanner.nextLine().split(";");
                LinkedList<String> experience = new LinkedList<>();


                String[] experienceList = fields[5].split(",");

                if (fields[0].equals(ID)) {
                    int j = 0;

                    while (j < experienceList.length) {
                        experience.add(experienceList[j]);
                        j++;
                    }
                    this.driverID=fields[0];
                    this.driverName=fields[1];
                    this.licenseTpye=fields[2];
                    this.availability=fields[3];
                    this.currentDriverLocation=fields[4];
                    this.experience=experience;

                    this.assignedOrderID = 0;
                    this.assignedVehicleID = 0;
                    this.orderStatus = null;
                    return;
                }
            }
            System.out.println("Driver with ID " + ID + " not found.");
        } catch (FileNotFoundException e) {
            System.out.println("Driver file not found: "+e.getMessage());;
        }
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

    public String getDriverLocation(){
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

    @Override
    public String toString() {
        return "Driver ID: " + driverID +
                "\nFull name: " + driverName +
                "\nLicense Type: " + licenseTpye +
                "\nAvailability Status: " + availability +
                "\nCurrent Location: " + currentDriverLocation +
                "\nAssigned Order ID: " + (assignedOrderID==0 ? "Not assigned yet" : assignedOrderID)+
                "\nOrder status: " + (orderStatus==null ? "Not assigned yet" : orderStatus).toString()+
                "\nAssigned Vehicle ID: " + (assignedVehicleID==0? "No assigned vehicle":assignedVehicleID);
    }

}