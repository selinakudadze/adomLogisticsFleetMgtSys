package utils;

import datastructures.BinarySearchTree;
import datastructures.LinkedList;
import delivery_rerouting.DeliveryReroute;
import delivery_tracking.OrderTracker;
import driverassignment.DriverAssignment;
import driverassignment.DriverToVehicleAssignment;
import models.Driver;
import models.Order;
import models.Vehicle;

import java.util.Scanner;

import static sort_and_search.BinarySearch.searchByRegistration;

public class MainClassReader {

    //
    static boolean vehiclesloaded = false;

    //vehicle reader
    static VehicleReader vehicleReader = new VehicleReader("Adom Fleet Management System/src/dummyTextFiles/Vehicles.txt");
    static Vehicle[] vehicles = vehicleReader.readVehiclesFromFile();


    //using the Binary search tree to display vehicles by type or mileage
    static DriverToVehicleAssignment vehicleAssignemnt = new DriverToVehicleAssignment();


    //using orderTracking to track an order and display all current orders
    static OrderTracker orderTracker = new OrderTracker();
    static boolean ordersLoaded = false;

    static DriverAssignment driverAssignment = new DriverAssignment();
    static boolean driversLoaded = false;

    static OrderReader orderReader = new OrderReader("Adom Fleet Management System/src/dummyTextFiles/Deliveries.txt");
    static Order[] orders = orderReader.readOrdersFromFile();

    static LinkedList<Order> ordersList = orderReader.getOrdersList();//returns a LinkedList of orders

    static DeliveryReroute deliveryReroute = new DeliveryReroute();


    public static void getDriverInfo(Scanner scanner) {
        System.out.println("Enter driver id: ");
        String driverId = scanner.nextLine();
        Driver driver = new Driver(driverId);
        if (driver.getDriverID() == null) {
            System.out.println("\n");
        } else {
            System.out.println("Driver details: " + driver);
        }
    }

    public static void searchVehicleByRegistration(Scanner scanner) {
        System.out.println("Enter vehicle registration number: ");
        String vehicleId = scanner.nextLine();
        int index = searchByRegistration(vehicles, vehicleId);

        if (index == -1) {
            System.out.println("\nNo vehicle with registration number " + vehicleId);
        } else {
            System.out.println(vehicles[index]);
        }

    }

    public static void loadVehicles() {
        if (!vehiclesloaded) {
            vehicleAssignemnt.LoadAvailableVehicles();
            vehiclesloaded = true;
        }
    }

    public static void organizeVehiclesByMileage() {
        loadVehicles();
        //sorted vehicles by mileage
        BinarySearchTree<Vehicle> vehicleTreeByMileage = vehicleAssignemnt.getVehicleTreeByMileage();
        Vehicle[] sortedVehiclesMileage = new Vehicle[vehicleTreeByMileage.getSize()];


        vehicleTreeByMileage.getAllElements(sortedVehiclesMileage);
        System.out.println("Organized vehicles by Mileage:");
        for (Vehicle vehicle : sortedVehiclesMileage) {
            System.out.println(vehicle.toString());
        }

    }

    public static void organizeVehiclesByType() {
        loadVehicles();
        //sorted vehicles by type

        BinarySearchTree<Vehicle> vehicleTreeByType = vehicleAssignemnt.getVehicleTreeByType();
        Vehicle[] sortedVehiclesType = new Vehicle[vehicleTreeByType.getSize()];

        vehicleTreeByType.getAllElements(sortedVehiclesType);
        System.out.println("Organized vehicles by type:");
        for (Vehicle vehicle : sortedVehiclesType) {
            System.out.println(vehicle.toString());
        }
    }


    public static void loadOrders() {
        if (!ordersLoaded) {
            orderTracker.loadOrdersFromFile("Adom Fleet Management System/src/dummyTextFiles/Deliveries.txt");
            ordersLoaded = true;
        }

    }

    public static void displayAllOrders() {
        loadOrders();
        orderTracker.printAllOrders();
    }

    public static void trackOrderById(Scanner scanner) {
        loadOrders();
        try {
            System.out.println("Enter order id: ");
            int orderid = Integer.parseInt(scanner.nextLine());

            System.out.println(orderTracker.getOrder(orderid));
        } catch (NumberFormatException e) {
            System.out.println("Invalid order ID. Please enter a number.");
        }
    }

    public static void loadDrivers() {
        if (!driversLoaded) {
            driverAssignment.LoadAvailableDrivers();
            driversLoaded = true;
        }
    }

    public static void assignOrders(Scanner scanner) {
        loadDrivers();
        System.out.println("Size of orders list: " + ordersList.size());
        try {
            System.out.println("Assign orders");
            System.out.print("Enter number of orders to be assigned: ");
            int numOrders = Integer.parseInt(scanner.nextLine());

            for (int j = 0; j < numOrders; j++) {
                if (numOrders == 0 || numOrders > ordersList.size()) break;
                Order order = ordersList.get(j);


                if (order.getDeliveryStatus().equalsIgnoreCase("IN_TRANSIT")) {
                    System.out.println("Order " + order.getOrderId() + " is already in transit. Next order....");
                    continue;
                }

                driverAssignment.assignDriverToOrder(order);
                orders[j] = order;
                //System.out.println("Order " + order.getOrderId() + " assigned successfully.");

            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please enter a valid integer.");
        }
    }

    public static void reRoutes(Scanner scanner) {
        try {
            System.out.println("Check for order reroutes");
//                        for (Order o : orders) {
//                            o.setDeliveryStatus("Stuck");
//                        }
            deliveryReroute.rerouteDeliveries(orders, vehicles);
            for (Order o : orders) {
                System.out.println(o.getDeliveryStatus());
            }
        } catch (Exception e) {
            System.out.println("Error rerouting deliveries: " + e.getMessage());
        }

    }

    public static void displayOutliers(Scanner scanner) {
        try {
            System.out.println("Show outliers");
            System.out.println("\nEnter number of standard deviations: ");
            int n=Integer.parseInt(scanner.nextLine());

            int numOutlierVehicles=0;

            float[] vehicleFuelUsage = new float[vehicles.length];
            float totalFuel = 0;

            for (int i = 0; i < vehicles.length; i++) {
                vehicleFuelUsage[i] = vehicles[i].calculateAverageFuelConsumption();
                totalFuel += vehicleFuelUsage[i];
            }
            //calculating the aveareg fuel consumption
            float averageFuel = totalFuel / vehicles.length;
            float variance=0;

            //for variance
            for (float fuel : vehicleFuelUsage) {
                float varFuel=fuel-averageFuel;
                variance+=varFuel*varFuel;
            }
            float standardDeviation = (float) Math.sqrt(variance/vehicles.length);
            System.out.println("This is the standard deviation: "+standardDeviation);


            for (int i = 0; i < vehicleFuelUsage.length; i++) {
                if (Math.abs(vehicleFuelUsage[i]-averageFuel)>n*standardDeviation) {
                    System.out.println(vehicles[i].getRegistrationNumber() + " - " + vehicleFuelUsage[i]);
                    numOutlierVehicles++;
                }
            }
            if (numOutlierVehicles==0) {
                System.out.println("No vehicles outliers above "+n+" standard deviations from "+ averageFuel+"\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please try again.");
        }
        catch (Exception e) {
            System.out.println("Error calculating outliers: " + e.getMessage());
        }
    }
}
