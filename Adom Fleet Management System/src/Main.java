import datastructures.*;
import delivery_tracking.OrderTracker;
import driverassignment.DriverToVehicleAssignment;
import models.Driver;
import models.Order;
import models.Vehicle;
import models.Maintenance;

import utils.VehicleReader;
import utils.OrderReader;
import utils.MaintenanceReader;
import scheduler.MaintenanceScheduler;
import delivery_rerouting.DeliveryReroute;
import driverassignment.DriverAssignment;

import java.util.Scanner;

import static Read_Write_To_Databases.AddToDatabases.runDataEntry;
import static sort_and_search.BinarySearch.searchByRegistration;
import static utils.MainClassReader.*;

public class Main {

    public static void main(String[] args) {
       //using orderTracking to track an order and display all current orders


        //using the Binary search tree to display vehicles by type or mileage
        DriverToVehicleAssignment vehicleAssignemnt = new DriverToVehicleAssignment();
        vehicleAssignemnt.LoadAvailableVehicles();

        //sorted vehicles by mileage
        BinarySearchTree<Vehicle> vehicleTreeByMileage = vehicleAssignemnt.getVehicleTreeByMileage();
        Vehicle[] sortedVehiclesMileage = new Vehicle[vehicleTreeByMileage.getSize()];
        System.out.println("Tree by mileage size is: "+vehicleTreeByMileage.getSize());
        vehicleTreeByMileage.getAllElements(sortedVehiclesMileage);
        System.out.println(sortedVehiclesMileage.length);

        //sorted vehicles by type
        BinarySearchTree<Vehicle> vehicleTreeByType = vehicleAssignemnt.getVehicleTreeByType();
        Vehicle[] sortedVehiclesType= new Vehicle[vehicleTreeByType.getSize()];
        vehicleTreeByType.getAllElements(sortedVehiclesType);



        OrderReader orderReader = new OrderReader("Adom Fleet Management System/src/dummyTextFiles/Deliveries.txt");
        Order[] orders = orderReader.readOrdersFromFile(
        );

        OrderTracker orderTracker = new OrderTracker(orderReader);
        orderTracker.loadOrdersAndHashMap();

        LinkedList<Order> ordersList=orderReader.getOrdersList();//returns a LinkedList of orders

        VehicleReader vehicleReader = new VehicleReader("Adom Fleet Management System/src/dummyTextFiles/Vehicles.txt");
        Vehicle[] vehicles = vehicleReader.readVehiclesFromFile();
        System.out.println("reg num of last vehicle:"+ vehicles[4].getRegistrationNumber());
        MaintenanceReader maintenanceReader = new MaintenanceReader("Adom Fleet Management System/src/dummyTextFiles/Maintenance.txt");
        HashMap<Integer, Maintenance> maintenances = maintenanceReader.readMaintenancesFromFile();
        // add each vehicle's maintenance info
        for(Vehicle vehicle: vehicles) {
            vehicle.setMaintenanceInfo(maintenances.get(vehicle.getVehicleId()));
        }

        MaintenanceScheduler maintenanceScheduler = new MaintenanceScheduler();
        DeliveryReroute deliveryReroute = new DeliveryReroute();

        DriverAssignment driverAssignment = new DriverAssignment();
        driverAssignment.LoadAvailableDrivers();

        Scanner scanner = new Scanner(System.in);
        String continueChoice;

        do {
            System.out.println("\nADOM LOGISTICS FLEET MANAGEMENT SYSTEM");
            System.out.println("-----------------------------");
            System.out.println("1. Get driver info (D00-)");
            System.out.println("2. Get vehicle info");
            System.out.println("3. Display vehicles by mileage");
            System.out.println("4. Display vehicles by type");
            System.out.println("5. Display orders");
            System.out.println("6. Track order by Id");
            System.out.println("7. Assign orders");
            System.out.println("8. Check reroutes");
            System.out.println("9. Maintain vehicles");
            System.out.println("10. Show outliers");
            System.out.println("11. Data Entry");
            System.out.println("12. Exit");

            String choice = scanner.nextLine();
            //scanner.nextLine();

            switch (choice) {
                case "1":
                    getDriverInfo(scanner);
                    break;

                case "2":
                    searchVehicleByRegistration(scanner);
                    break;

                case "3":
                    organizeVehiclesByMileage();
                    break;
                case "4":
                    organizeVehiclesByType();
                    break;
                case "5":
                    displayAllOrders();
                    break;
                case "6":
                    trackOrderById(scanner);
                    break;
                case "7":
                    assignOrders(scanner);
                    break;
                case "8":
                   reRoutes(scanner);
                    break;
                case "9":
                    try {
                        maintenanceScheduler.loadFromVehicleList(vehicles);
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

                    break;
                case "10":
                    displayOutliers(scanner);
                    break;
                case "11":
                    runDataEntry();
                case "12":
                    System.out.println("\nExiting program...\nSEE YOU SOON");
                    System.out.println("----------------------------------------");
                    System.out.println("ADOM LOGISTICS FLEET MANAGEMENT SYSTEM");

                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }

            while (true) {
                System.out.print("Would you like to perform another operation? (y/n): ");
                continueChoice = scanner.nextLine().trim().toLowerCase();

                if (continueChoice.equals("y")) {
                    break;
                } else if (continueChoice.equals("n")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                } else {
                    System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
                }
            }

        } while (continueChoice.equalsIgnoreCase("y"));

        System.out.println("\nSEE YOU SOON...");
        System.out.println("----------------------------------------");
        System.out.println("ADOM LOGISTICS FLEET MANAGEMENT SYSTEM");
        scanner.close();


    }
}