package driverassignment;

import datastructures.MyStack;
import datastructures.LinkedList;
import models.Driver;
import models.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DriverAssignment {

    // Stack of available drivers (LIFO)
    private final MyStack<Driver> availableDrivers = new MyStack<>();

    // Stack of already allocated drivers (for tracking)
    private final MyStack<Driver> allocatedDrivers = new MyStack<>();

    // Stack of available vehicles instead of queue
    private final MyStack<String> availableVehicles = new MyStack<>();

    /**
     * Reads driver data from file and adds only ON_DUTY drivers to the available stack.
     */
    public void initializeDrivers() {
        try {
            File file = new File("dummyTextFiles/Drivers.txt");
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                // Read each driver's details split by semicolon
                String[] data = reader.nextLine().split(";");

                // Check availability
                if (data[3].equalsIgnoreCase("ON_DUTY")) {
                    LinkedList<String> experience = new LinkedList<>();

                    // Split and store driver's experience locations
                    for (String location : data[5].split(",")) {
                        experience.add(location.trim());
                    }

                    // Create driver object and push to stack
                    Driver d = new Driver(data[0], data[1], data[2], data[3], data[4], experience);
                    availableDrivers.push(d);
                }
            }

            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("⚠ Unable to locate Drivers.txt");
        }
    }

    /**
     * Loads available vehicles into a stack  (LIFO order).
     */
    public void loadVehicles() {
        availableVehicles.push("V001");
        availableVehicles.push("V002");
        availableVehicles.push("V003");
        availableVehicles.push("V004");
        availableVehicles.push("V005");
    }

    /**
     * Assigns an experienced driver and available vehicle (if any) to an order.
     */
    public void handleOrderAssignment(Order order) {
        MyStack<Driver> tempStack = new MyStack<>();
        boolean foundMatch = false;

        System.out.println("\n🔍 Assigning order #" + order.getOrderId() + " to " + order.getDestination());

        // Check each driver in stack
        while (!availableDrivers.isEmpty()) {
            Driver candidate = availableDrivers.pop();

            // Match experience with destination
            if (candidate.searchExperience(order.getDestination())) {
                // Assign order and update driver status
                candidate.setAssignOrderID(order.getOrderId());
                order.setAssignedDriver(candidate.getDriverID());
                order.updateDeliveryStatus(Order.DeliveryStatus.IN_TRANSIT);
                candidate.updateAvailability(Driver.AvailabilityStatus.OFF_DUTY);
                candidate.updateOrderStatus(Driver.OrderStatus.IN_TRANSIT);

                // Assign vehicle using stack (LIFO)
                if (!availableVehicles.isEmpty()) {
                    String assignedVehicle = availableVehicles.pop();
                    candidate.setAssignedVehicle(assignedVehicle);
                    System.out.println("✅ Driver " + candidate.getDriverID() + " assigned to order " + order.getOrderId() +
                            " with vehicle " + assignedVehicle);
                } else {
                    System.out.println("⚠ No available vehicles for driver " + candidate.getDriverID());
                }

                // Push to allocatedDrivers stack
                allocatedDrivers.push(candidate);
                foundMatch = true;
                break;
            } else {
                // Temporarily store unmatched drivers
                tempStack.push(candidate);
            }
        }

        // Restore unmatched drivers
        while (!tempStack.isEmpty()) {
            availableDrivers.push(tempStack.pop());
        }

        // Notify if no driver was found
        if (!foundMatch) {
            System.out.println("❌ No experienced driver for order " + order.getOrderId());
        }
    }

    /**
     * Program entry point: Loads drivers, vehicles, and processes sample orders.
     */
    public static void main(String[] args) {
        DriverAssignment manager = new DriverAssignment();

        // Load drivers and vehicles
        manager.initializeDrivers();
        manager.loadVehicles();

        // Create sample orders
        Order[] tasks = {
            new Order(2001, "Akosua Mensah", "Airport", "Takoradi", null),
            new Order(2002, "Yaw Owusu", "Kaneshie", "Tema", null),
            new Order(2003, "Linda Asare", "Koforidua", "Madina", null),
            new Order(2004, "Kojo Ababio", "Lapaz", "Tamale", null),
            new Order(2005, "Ama Serwaa", "Amasaman", "Kasoa", null)
        };

        // Assign orders to drivers
        for (Order task : tasks) {
            manager.handleOrderAssignment(task);
        }
    }
}
