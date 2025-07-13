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
                //System.out.println(fields[0]);
                if (fields[3].equals("ON_DUTY")) {
                    String[] experienceList=fields[5].split(",");
                    int j=0;

                    while(j<experienceList.length){
                        experience.add(experienceList[j]);
                        //System.out.println(experienceList[j]);
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
        //Assignment based on Experience
        //Try proximity later
        Driver currentDriver=driverQueue.front();
        while(currentDriver != null){
            System.out.println("Order is going to "+order.getDestination());
            if(currentDriver.searchExperience(order.getDestination())){
                System.out.println(currentDriver.driverID);
                //if the driver has been to that location
                currentDriver.assignOrder(order.getOrderId());
                System.out.println("This order "+ order.getOrderId()+" has been assigned to "+currentDriver.driverID);
                assignedDriversQueue.enqueue(currentDriver);
                return;
            }else{
                currentDriver=driverQueue.next();
            }
            //what if none of the drivers have been to the order destination?
            //Use approximation?

        }



    }
    public static void main(String[] args){
        DriverAssignment driverAssignment=new DriverAssignment();
        driverAssignment.LoadAvailableDrivers();

        Order order=new Order(1122,"SEAK","Dome","Nsawam",null);
        driverAssignment.assignDriverToOrder(order);
    }
        //System.out.println("Available Drivers Loaded");
    }




