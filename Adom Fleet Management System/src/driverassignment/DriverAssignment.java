package driverassignment;


import datastructures.Queue;
import datastructures.Node;
import models.Driver;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DriverAssignment {
    Queue<Driver> driverQueue=new Queue<>();

    public void LoadAvailableDrivers(){
        try{
            File driverFile=new File("Adom Fleet Management System/src/dummyTextFiles/Drivers.txt");
            Scanner driverScanner =new Scanner(driverFile);

            while(driverScanner.hasNextLine()){
                String[] fields=driverScanner.nextLine().split(";");
                Driver driver = new Driver(fields[0],fields[1],fields[2],fields[3],fields[4]);
                System.out.println(fields[4]);


                if (driver.getAvailability().equals("ON_DUTY")) {
                    Node<Driver> driverNode=new Node<>(driver);
                    //System.out.println(driverNode.entity.driverID);
                    driverQueue.enqueue(driverNode.entity);
                }
            }
            driverScanner.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Drivers.txt File Not Found");
        }



        }
    public static void main(String[] args){
        DriverAssignment driverAssignment=new DriverAssignment();
        driverAssignment.LoadAvailableDrivers();
    }
        //System.out.println("Available Drivers Loaded");
    }




