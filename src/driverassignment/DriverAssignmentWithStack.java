package driverassignment;

import datastructures.MyStack;
import datastructures.LinkedList;
import models.Driver;
import models.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DriverAssignment {

    // Stack to hold all available drivers (ON_DUTY)
    private final MyStack<Driver> availableDrivers = new MyStack<>();

    // Stack to hold all drivers who have been assigned orders
    private final MyStack<Driver> allocatedDrivers = new MyStack<>();

    // Stack to hold all available vehicles
    private final MyStack<String> availableVehicles = new MyStack<>();

    // List to hold all orders that need assignment
    private final LinkedList<Order> pendingOrders = new LinkedList<>();

    /**
     * Loads ON_DUTY drivers from the file into the availableDrivers stack.
     * Each driver has a list of locations they're experienced in.
     */
    public void initializeDrivers() {
        try {
            Scanner reader = new Scanner(new File("dummyTextFiles/Drivers.txt"));
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(";");
                // Check data validity and filter only ON_DUTY drivers
                if (data.length >= 6 && data[3].equalsIgnoreCase("ON_DUTY")) {
                    LinkedList<String> experience = new LinkedList<>();
                    for (String location : data[5].split(",")) {
                        experience.add(location.trim());
                    }
                    // Create and add driver to the stack
                    Driver driver = new Driver(data[0], data[1], data[2], data[3], data[4], experience);
                    availableDrivers.push(driver);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("⚠ Unable to locate Drivers.txt");
        }
    }

    /**
     * Loads vehicle IDs from the file into the availableVehicles stack.
     */
    public void loadVehicles() {
        try {
            Scanner reader = new Scanner(new File("dummyTextFiles/Vehicles.txt"));
            while (reader.hasNextLine()) {
                String vehicle = reader.nextLine().trim();
                if (!vehicle.isEmpty()) {
                    availableVehicles.push(vehicle);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("⚠ Unable to locate Vehicles.txt");
        }
    }

    /**
     * Loads orders from the file into the pendingOrders list.
     */
    public void loadOrders() {
        try {
            Scanner reader = new Scanner(new File("dummyTextFiles/Orders.txt"));
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(";");
                if (data.length >= 4) {
                    int orderId = Integer.parseInt(data[0]);
                    String customer = data[1];
                    String origin = data[2];
                    String destination = data[3];
                    pendingOrders.add(new Order(orderId, customer, origin, destination, null));
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("⚠ Unable to locate Orders.txt");
        }
    }

    /**
     * Assigns the best available driver for the order based on:
     * - Experience in the destination (worth 2 points)
     * - Proximity to the origin (worth 1 point)
     */
    public void handleOrderAssignment(Order order) {
        // Edge case: no available drivers
        if (availableDrivers.isEmpty()) {
            System.out.println("❌ No available drivers for order #" + order.getOrderId());
            return;
        }

        // Temporary stack to store drivers during selection
        MyStack<Driver> tempStack = new MyStack<>();
        Driver bestDriver = null;
        int bestScore = -1;

        System.out.println("\n🔍 Processing Order #" + order.getOrderId() +
                           " - Destination: " + order.getDestination());

        // Evaluate each available driver
        while (!availableDrivers.isEmpty()) {
            Driver candidate = availableDrivers.pop();

            int score = 0;
            if (candidate.searchExperience(order.getDestination())) score += 2;
            if (candidate.searchExperience(order.getOrigin())) score += 1;

            // Select the highest scoring driver
            if (score > bestScore) {
                if (bestDriver != null) {
                    tempStack.push(bestDriver);
                }
                bestDriver = candidate;
                bestScore = score;
            } else {
                tempStack.push(candidate);
            }
        }

        // If a suitable driver was found
        if (bestDriver != null) {
            bestDriver.setAssignOrderID(order.getOrderId());
            order.setAssignedDriver(bestDriver.getDriverID());
            order.updateDeliveryStatus(Order.DeliveryStatus.IN_TRANSIT);
            bestDriver.updateAvailability(Driver.AvailabilityStatus.OFF_DUTY);
            bestDriver.updateOrderStatus(Driver.OrderStatus.IN_TRANSIT);

            // Assign a vehicle if available
            if (!availableVehicles.isEmpty()) {
                String vehicle = availableVehicles.pop();
                bestDriver.setAssignedVehicle(vehicle);
                System.out.println("✅ Assigned Driver: " + bestDriver.getDriverID() + " with Vehicle: " + vehicle);
            } else {
                System.out.println("⚠ No available vehicles for Driver: " + bestDriver.getDriverID());
            }

            allocatedDrivers.push(bestDriver);
        } else {
            // No matching driver found
            System.out.println("❌ No suitable driver found for order #" + order.getOrderId());
        }

        // Return all unassigned drivers back to available stack
        while (!tempStack.isEmpty()) {
            availableDrivers.push(tempStack.pop());
        }
    }

    /**
     * Main method to load data and assign all pending orders.
     */
    public static void main(String[] args) {
        DriverAssignment manager = new DriverAssignment();

        // Load resources
        manager.initializeDrivers();
        manager.loadVehicles();
        manager.loadOrders();

        // Check if there are orders to process
        if (manager.pendingOrders.size() == 0) {
            System.out.println("⚠ No orders to process.");
            return;
        }

        // Assign drivers to orders
        for (int i = 0; i < manager.pendingOrders.size(); i++) {
            Order order = manager.pendingOrders.get(i);
            manager.handleOrderAssignment(order);
        }

        System.out.println("\n📦 All orders processed.");
    }
}
