package utils;

import datastructures.BinarySearchTree;
import datastructures.HashMap;
import datastructures.LinkedList;
import delivery_rerouting.DeliveryReroute;
import delivery_tracking.OrderTracker;
import driverassignment.DriverAssignment;
import driverassignment.DriverToVehicleAssignment;
import models.Driver;
import models.Maintenance;
import models.Order;
import models.Vehicle;
import scheduler.MaintenanceScheduler;

import java.util.Scanner;

import static driverassignment.DriverAssignment.getAllDrivers;
import static driverassignment.DriverAssignment.getDriverHashMap;
import static sort_and_search.BinarySearch.searchByRegistration;

public class MainClassReader {

    //orderReader class is instantiated and all orders are placed into an array of orders
    static OrderReader orderReader = new OrderReader("./src/dummyTextFiles/Deliveries.txt");
    static Order[] orders = orderReader.readOrdersFromFile();

    //used to check if vehicles have already been loaded into the hashMap
    static boolean vehiclesloaded = false;

    //vehicle reader
    //VehicleReader class is instantiated and all vehicles are placed into an array of vehicles
    static VehicleReader vehicleReader = new VehicleReader("./src/dummyTextFiles/Vehicles.txt");
    static Vehicle[] vehicles = vehicleReader.readVehiclesFromFile();


    //using the Binary search tree to display vehicles by type and mileage. This is used in the functions organizeVehiclesByType and
    //organizeVehiclesByMileage
    static DriverToVehicleAssignment vehicleAssignment = new DriverToVehicleAssignment();


    //using orderTracking to track an order and display all current orders
    static OrderTracker orderTracker = new OrderTracker(orderReader);
    static boolean ordersLoaded = false;

    //DriverAssignment class instantiated and used to assign orders to drivers
    static DriverAssignment driverAssignment = new DriverAssignment();
    static boolean driversLoaded = false;
    //this same class has methods that return a linkedlist of drivers to display all drivers
    static LinkedList<Driver> driverLinkedList =getAllDrivers();
    //It also has a method that returns a HashMap used to display individual drivers based on their Id
    static HashMap<String, Driver> driversHashMap=getDriverHashMap();

    //this linkedlist of Orders is used to store orders and is used for assigning orders to drivers as well
    //Derived from the OrderReader class
    static LinkedList<Order> ordersList = orderReader.getOrdersList();//returns a LinkedList of orders

    //used to simulate delivery rerouting
    static DeliveryReroute deliveryReroute = new DeliveryReroute();


    //resources for maintaining vehicles
    static MaintenanceReader maintenanceReader = new MaintenanceReader("./src/dummyTextFiles/Maintenance.txt");
    static HashMap<Integer, Maintenance> maintenances = maintenanceReader.readMaintenancesFromFile();

    static MaintenanceScheduler maintenanceScheduler = new MaintenanceScheduler();


    //this method is used in the first case. It displays all drivers
    public static void getAllDriverInfo() {
        loadDrivers();
        System.out.println("Available Drivers");
        for(int j=0;j<driverLinkedList.size();j++){
            System.out.println(driverLinkedList.get(j));
            System.out.println("\n");
        }
    }


    //used in the second case, it displays a particular driver from the HashMap using the driverID
    public static void getDriverInfo(Scanner scanner) {
        System.out.println("Enter driver id: ");
        String driverId = scanner.nextLine();
        Driver driver=driversHashMap.get(driverId);
        if (driver.getDriverID() == null) {
            System.out.println("Driver not found for ID: " + driverId);
        } else {
            System.out.println("Driver details: " + driver);
        }
    }


    //This method searches for a particular vehicle using its registration number
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


    //this method loads all vehicles using the DriverToVehicleAssignment object instantiated above
    public static void loadVehicles() {
        if (!vehiclesloaded) {
            vehicleAssignment.LoadAvailableVehicles();
            vehiclesloaded = true;
        }
    }

    //this method displays all vehicles organized by mileage
    public static void organizeVehiclesByMileage() {
        loadVehicles();
        //sorted vehicles by mileage
        BinarySearchTree<Vehicle> vehicleTreeByMileage = vehicleAssignment.getVehicleTreeByMileage();
        Vehicle[] sortedVehiclesMileage = new Vehicle[vehicleTreeByMileage.getSize()];


        vehicleTreeByMileage.getAllElements(sortedVehiclesMileage);
        System.out.println("Organized vehicles by Mileage:");
        for (Vehicle vehicle : sortedVehiclesMileage) {
            System.out.println(vehicle.toString());
        }

    }

    //This method displays all vehicles organized by type
    public static void organizeVehiclesByType() {
        loadVehicles();
        //sorted vehicles by type

        BinarySearchTree<Vehicle> vehicleTreeByType = vehicleAssignment.getVehicleTreeByType();
        Vehicle[] sortedVehiclesType = new Vehicle[vehicleTreeByType.getSize()];

        vehicleTreeByType.getAllElements(sortedVehiclesType);
        System.out.println("Organized vehicles by type:");
        for (Vehicle vehicle : sortedVehiclesType) {
            System.out.println(vehicle.toString());
        }
    }

    //This method loads all orders using the OrderTracker and OrderReader classes
    public static void loadOrders() {
        if (!ordersLoaded) {
            orderTracker.loadOrdersAndHashMap();
            ordersLoaded = true;
        }

    }


    //this method displays all orders using the Ordertracker
    public static void displayAllOrders() {
        loadOrders();
        orderTracker.printAllOrders();
    }

    //this method displays an order
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

    //this method loads all available drivers
    public static void loadDrivers() {
        if (!driversLoaded) {
            driverAssignment.LoadAvailableDrivers();
            driversLoaded = true;
        }
    }


    //this method assigns orders to drivers
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
//                orders[j] = order;
                //System.out.println("Order " + order.getOrderId() + " assigned successfully.");

            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please enter a valid integer.");
        }
    }


    //this method reroutes orders
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


    //this method displays outliers
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
            //calculating the average fuel consumption
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
                System.out.println("No vehicle outliers outside "+n+" standard deviations from "+ averageFuel+"\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please try again.");
        }
        catch (Exception e) {
            System.out.println("Error calculating outliers: " + e.getMessage());
        }
    }

    //this method sets maintenance details to be used by maintenance scheduler
    public static void setMaintenanceDetails(Vehicle[] vehicles){
        // add each vehicle's maintenance info
        for(Vehicle vehicle: vehicles) {
            vehicle.setMaintenanceInfo(maintenances.get(vehicle.getVehicleId()));
        }
    }


    //this method maintains the vehicles
    public static void maintainVehicles(Scanner scanner) {
        setMaintenanceDetails(vehicles);
        try {
            System.out.println("Before error!!");
            maintenanceScheduler.loadFromVehicleList(vehicles);
            System.out.println("After error!!");
            for (Vehicle v : vehicles) {
                System.out.println(v);
            }

            System.out.println("Maintain vehicles");
            System.out.print("Enter number of vehicles: ");
            int n = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter mechanic to fix vehicles at: ");
            String mech = scanner.nextLine();

            System.out.print("Enter cost to fix all vehicles: ");
            float cost = Float.parseFloat(scanner.nextLine());

            Vehicle[] toMaintain = maintenanceScheduler.getNextVehiclesForService(n);
            maintenanceScheduler.markAsServiced(toMaintain, mech, cost);

            for (Vehicle v : vehicles) {
                System.out.println(v);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number or cost. Please try again.");
        } catch (Exception e) {
            System.out.println("An error occurred during maintenance: " + e.getMessage());
        }

    }
}
