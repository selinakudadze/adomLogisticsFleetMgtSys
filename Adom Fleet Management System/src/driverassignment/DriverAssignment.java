package driverassignment;


import datastructures.Queue;
import datastructures.Node;
import models.Driver;
import models.Order;
import datastructures.LinkedList;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DriverAssignment {
    Queue<Driver> driverQueue=new Queue<>();
    Queue<Driver> assignedDriversQueue=new Queue<>();

    public void LoadAvailableDrivers(){
        try{
            File driverFile=new File("Adom Fleet Management System/src/dummyTextFiles/Drivers.txt");
            Scanner driverScanner =new Scanner(driverFile);


            while(driverScanner.hasNextLine()){
                String[] fields=driverScanner.nextLine().split(";");
                LinkedList<String> experience=new LinkedList<>();

                if (fields[3].equals("ON_DUTY")) {
                    String[] experienceList=fields[5].split(",");
                    int j=0;

                    while(j<experienceList.length){
                        experience.add(experienceList[j]);
                        j++;
                    }
                    Driver driver = new Driver(fields[0],fields[1],fields[2],fields[3],fields[4],experience);
                    driverQueue.enqueue(driver);
                }
            }
            driverScanner.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Drivers.txt File Not Found");
        }
        }

    public void assignDriverToOrder(Order order){

        if (driverQueue.isEmpty()) {
        System.out.println("No drivers available to assign to order " + order.getOrderId());
        return;
                    }
        //Assignment based on Experience
        //Try proximity later
        Driver originalHead=driverQueue.front();
        Queue<Driver> placeHolderDriverQueue=new Queue<>();
        Driver currentDriver=driverQueue.dequeue().entity;
        boolean assignedByExperience = false;

        System.out.println("Order "+order.getOrderId()+" is going to "+order.getDestination());
        while(currentDriver != null){
            if(currentDriver.searchExperience(order.getDestination())){
                //if the driver has been to that location
                currentDriver.setAssignOrderID(order.getOrderId());
                System.out.println("The order "+ order.getOrderId()+" has been assigned to "+currentDriver.getDriverID());
                assignedDriversQueue.enqueue(currentDriver);
                order.setAssignedDriver(currentDriver.getDriverID());
                order.updateDeliveryStatus(Order.DeliveryStatus.IN_TRANSIT);
                currentDriver.updateAvailability(Driver.AvailabilityStatus.OFF_DUTY);//changes the status of the driver when assigned to an order
                currentDriver.updateOrderStatus(Driver.OrderStatus.IN_TRANSIT);//update the order status of the Driver

                assignedByExperience = true;

                if(originalHead.getDriverID().equals(currentDriver.getDriverID())){
                    return;
                }else{
                    while(!driverQueue.isEmpty()){
                        Driver dequeuedDriver=driverQueue.dequeue().entity;
                        System.out.println("The "+ dequeuedDriver.getDriverID()+" driver was added to the other queue");
                        placeHolderDriverQueue.enqueue(dequeuedDriver);
                    }break;
                }

            }else{
                placeHolderDriverQueue.enqueue(currentDriver);
                System.out.println("The "+ currentDriver.getDriverID()+" driver was added to the other queue");
                if(driverQueue.isEmpty()){
                    System.out.println("There are no available drivers to be assigned order "+order.getOrderId()+" based on EXPERIENCE. Try proximity");
                    break;
                }
                Driver nextDriver=driverQueue.dequeue().entity;
                currentDriver=nextDriver;
                System.out.println("The next driver to check is "+currentDriver.getDriverID());

                
            }
            //what if none of the drivers have been to the order destination?
            //Use proximity?
            

        }
             // If no driver matched by experience, try proximity
        if (!assignedByExperience) {
            assignByProximity(order, placeHolderDriverQueue);
        }

        driverQueue=placeHolderDriverQueue;

    }

     // METHOD TO ASSIGN DRIVER BASED ON LOCATION PROXIMITY 
        public void assignByProximity(Order order, Queue<Driver> availableDrivers) {
          if (availableDrivers.isEmpty()) {
            // exit if there are no drivers to check
        System.out.println("No available drivers for order " + order.getOrderId());
        return; 
          }
        System.out.println("Attempting proximity-based assignment...");

            // Gets coordinate for origin city
        Coordinate originCoord = LocationService.getCoordinate(order.getOrigin());

        // exist if there are no coordinates found
        if (originCoord == null) {
            System.out.println("Origin location not found in location list.");
            return;
        }
            
        Driver closestDriver = null;   //store the closest driver found
        double minDistance = Double.MAX_VALUE; 
        Queue<Driver> tempQueue = new Queue<>(); // temporary queue to preserbe drivers

        while (!availableDrivers.isEmpty()) {
            Driver driver = availableDrivers.dequeue().entity;  // get each driver one by one


            Coordinate driverCoord = LocationService.getCoordinate(driver.getDriverLocation());// get driver's location coordinate

            if (driverCoord != null) {
                double distance = haversine(driverCoord.latitude, driverCoord.longtitude, originCoord.latitude, originCoord.longtitude);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestDriver = driver;
                }
            }
            tempQueue.enqueue(driver); // Keep the queue intact by requeuing all drivers
        }

        driverQueue = tempQueue; 

        if (closestDriver != null) {
            closestDriver.setAssignOrderID(order.getOrderId());
            closestDriver.updateAvailability(Driver.AvailabilityStatus.OFF_DUTY);
            closestDriver.updateOrderStatus(Driver.OrderStatus.IN_TRANSIT);
            order.setAssignedDriver(closestDriver.getDriverID());
            order.updateDeliveryStatus(Order.DeliveryStatus.IN_TRANSIT);
            assignedDriversQueue.enqueue(closestDriver);

            System.out.println("Order " + order.getOrderId() + " assigned to " + closestDriver.getDriverID()
                    + " based on proximity (" + String.format("%.2f", minDistance) + " km).");
        } else {
            System.out.println("No suitable driver found based on proximity.");
        }
    }

    // NEW METHOD: Haversine formula for calculating distance between two locations
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // Radius of Earth in kilometers

        double dLat = Math.toRadians(lat2 - lat1); //change in latitude
    
        double dLon = Math.toRadians(lon2 - lon1); // change in longtitude
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dLon / 2), 2); // Haversine formula

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // return distance 
    }

    // Everyone can run this to check proximty
    public static void main(String[] args){
        DriverAssignment driverAssignment=new DriverAssignment();
         LocationService.loadLocations("Adom Fleet Management System/src/dummyTextFiles/locations.txt");
        driverAssignment.LoadAvailableDrivers();

        Order order=new Order(1121,"SEAK","Dome","Kpedze",null,0,0,0,0);
        driverAssignment.assignDriverToOrder(order);

        Order order_1=new Order(1122,"SEAK","Dome","Nsawam",null,0,0,0,0);
        driverAssignment.assignDriverToOrder(order_1);

        Order order_2=new Order(1123,"SEAK_2","Ho","New Junction",null,0,0,0,0);
        driverAssignment.assignDriverToOrder(order_2);

        Order order_3=new Order(1124,"SEAK_3","Wa","Kpedze",null,0,0,0,0);
        driverAssignment.assignDriverToOrder(order_3);

        Order order_4=new Order(1125,"SEAK_3","Lapaz","Asamankesi",null,0,0,0,0);
        driverAssignment.assignDriverToOrder(order_4);
        }
        //System.out.println("Available Drivers Loaded");
    }




