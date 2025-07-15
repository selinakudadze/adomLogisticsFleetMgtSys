package driverassignment;

import datastructures.BinarySearchTree;
import datastructures.Queue;
import datastructures.Node;
import models.Driver;
import models.Vehicle;
import models.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DriverToVehicleAssignment {
    private BinarySearchTree<Vehicle> vehicleTreeByType; // Organized by vehicleType
    private BinarySearchTree<Vehicle> vehicleTreeByMileage; // Organized by mileage
    private Queue<Driver> assignedDriversQueue;

    public DriverToVehicleAssignment() {
        this.vehicleTreeByType = new BinarySearchTree<>();
        this.vehicleTreeByMileage = new BinarySearchTree<>();
        this.assignedDriversQueue = new Queue<>();
    }

    public void LoadAvailableVehicles() {
        try {
            File vehicleFile = new File("src/dummyTextFiles/Vehicles.txt");
            Scanner vehicleScanner = new Scanner(vehicleFile);

            // Skip the header line
            if (vehicleScanner.hasNextLine()) {
                vehicleScanner.nextLine();
            }

            while (vehicleScanner.hasNextLine()) {
                String[] fields = vehicleScanner.nextLine().split(";");
                if (fields.length >= 6) {
                    int vehicleId = Integer.parseInt(fields[0].substring(4));
                    String registrationNumber = fields[1];
                    String vehicleType = fields[2];
                    int mileage = Integer.parseInt(fields[3]);
                    float fuelUse = Float.parseFloat(fields[4]);
                    int lastServiceDate = Integer.parseInt(fields[5]);

                    Vehicle vehicle = new Vehicle(vehicleId, registrationNumber, vehicleType, mileage, fuelUse, 0.0, 0.0, null, lastServiceDate);
                    vehicleTreeByType.insert(vehicle); // Insert by vehicleType (using toString for comparison)
                    vehicleTreeByMileage.insert(vehicle); // Insert by mileage
                }
            }
            vehicleScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Vehicle.txt File Not Found");
        } catch (Exception e) {
            System.out.println("Error loading vehicles: " + e.getMessage());
        }
    }

    public void assignVehicleToDriver(Driver driver) {
        if (driver.getAvailability().equals(Driver.AvailabilityStatus.OFF_DUTY.toString()) && driver.getAssignedOrderID() != 0) {
            System.out.println("--> Assigning vehicle to driver " + driver.getDriverID() + " with order " + driver.getAssignedOrderID());

            // Get all vehicles sorted by vehicleType
            Vehicle[] vehiclesByType = vehicleTreeByType.getAllElements(new Vehicle[vehicleTreeByType.getSize()]);
            for (Vehicle vehicle : vehiclesByType) {
                if (vehicle.getAssignedDriverId() == null && vehicle.getVehicleType().equals(driver.getLicenseType())) {
                    // Match vehicle type with driver's license type
                    vehicle.setAssignedDriverId(driver.getDriverID());
                    driver.setAssignVehicleID(vehicle.getVehicleId());
                    System.out.println("Vehicle " + vehicle.getRegistrationNumber() + " assigned to driver with ID " + driver.getDriverID());
                    return;
                }
            }

            // If no type match, try by mileage (lower mileage preferred)
            Vehicle[] vehiclesByMileage = vehicleTreeByMileage.getAllElements(new Vehicle[vehicleTreeByMileage.getSize()]);
            for (Vehicle vehicle : vehiclesByMileage) {
                if (vehicle.getAssignedDriverId() == null && vehicle.getMileage() < 20000) {
                    // Arbitrary mileage threshold
                    // Simple proximity check using current location (assumes driver location is set)
                    double driverLat = 0.0, driverLong = 0.0; // Placeholder, replace with actual driver location
                    double distance = Math.sqrt(Math.pow(vehicle.getCurrentLat() - driverLat, 2) +
                            Math.pow(vehicle.getCurrentLong() - driverLong, 2));
                    if (distance < 5.0) { // Arbitrary distance threshold (e.g., 5 units)
                        vehicle.setAssignedDriverId(driver.getDriverID());
                        driver.setAssignVehicleID(vehicle.getVehicleId());
                        System.out.println("Vehicle " + vehicle.getRegistrationNumber() + " assigned to Driver with ID " + driver.getDriverID() + " based on mileage");
                        return;
                    };
                }
            }



            System.out.println("No suitable vehicle found for driver " + driver.getDriverID());
        } else {
            System.out.println("Driver " + driver.getDriverID() + " is not available or has no order assigned");
        }
    }

    public void processAssignedDrivers() {
        Queue<Driver> tempQueue = new Queue<>();
        while (!assignedDriversQueue.isEmpty()) {
            Driver driver = assignedDriversQueue.dequeue().entity;
            if (driver.getAssignedOrderID() != 0) { // Only process drivers with assigned orders
                assignVehicleToDriver(driver);
            } else {
                System.out.println("Driver " + driver.getDriverID() + " has no assigned order, skipping vehicle assignment");
            }
            tempQueue.enqueue(driver);
        }
        assignedDriversQueue = tempQueue;
    }

    public static void main(String[] args) {
        DriverToVehicleAssignment assignment = new DriverToVehicleAssignment();
        assignment.LoadAvailableVehicles();

        // Simulate loading assigned drivers from DriverAssignment
        DriverAssignment driverAssignment = new DriverAssignment();
        driverAssignment.LoadAvailableDrivers();
        Order order = new Order(1121, "SEAK", "Dome", "Kpedze", null, 5.6037, -0.1869, 6.6666, -1.6163);
        driverAssignment.assignDriverToOrder(order);
        Order order1 = new Order(1122, "SEAK", "Dome", "Nsawam", null, 6.6833, -1.6163, 9.4032, 2.7775);
        driverAssignment.assignDriverToOrder(order1);
        assignment.assignedDriversQueue = driverAssignment.assignedDriversQueue; // Transfer assigned drivers

        assignment.processAssignedDrivers();
    }
}
