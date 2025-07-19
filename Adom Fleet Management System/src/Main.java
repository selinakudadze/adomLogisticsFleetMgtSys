import delivery_tracking.OrderTracker;
import models.Driver;
import models.Order;
import models.Vehicle;
import sort_and_search.BinarySearch;
import utils.VehicleReader;
import utils.OrderReader;
import scheduler.MaintenanceScheduler;
import delivery_rerouting.DeliveryReroute;
import driverassignment.DriverAssignment;

import java.util.HashMap;
import java.util.Scanner;

import static sort_and_search.BinarySearch.searchByRegistration;

public class Main {

    public static void main(String[] args) {
        OrderTracker orderTracker = new OrderTracker();
        while(true) {

         orderReader = new OrderReader("Adom Fleet Management System/src/dummyTextFiles/Deliveries.txt");
        Order[] orders= orderReader.readOrdersFromFile();
        VehicleReader vehicleReader = new VehicleReader("Adom Fleet Management System/src/dummyTextFiles/Vehicles.txt");
        Vehicle[] vehicles = vehicleReader.readVehiclesFromFile();
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
            System.out.println("3. Track orders");
            System.out.println("4. Assign orders");
            System.out.println("5. Check reroutes");
            System.out.println("6. Maintain vehicles");
            System.out.println("7. Show outliers");
            System.out.println("8. Exit");

    //         String choice = scanner.nextLine();
    //         //scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Enter driver id: ");
                    String driverId = scanner.nextLine();
                    Driver driver = new Driver(driverId);
                    if (driver.getDriverID() == null) {
                        System.out.println("\n");
                    } else {
                        System.out.println("Driver details: " + driver);
                    }
                    break;

                case "2":
                    System.out.println("Enter vehicle registration number: ");
                    String vehicleId = scanner.nextLine();
                    int index = searchByRegistration(vehicles, vehicleId);

                    if (index == -1) {
                        System.out.println("\nNo vehicle with registration number " + vehicleId);
                    } else {
                        System.out.println(vehicles[index]);
                    }
                    break;

                case "3":
                    try {
                        System.out.println("Enter order id: ");
                        int orderid = Integer.parseInt(scanner.nextLine());

                        boolean found = false;
                        for (Order order : orders) {
                            if (order.getOrderId() == orderid) {
                                System.out.println("Order id " + order.getOrderId() + " is " + order.getDeliveryStatus());
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("Order ID not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid order ID. Please enter a number.");
                    }
                    break;
                case "4":
                    try {
                        System.out.println("Assign orders");
                        System.out.print("Enter number of orders to be assigned: ");
                        int numOrders = Integer.parseInt(scanner.nextLine());

                        for (Order order : orders) {
                            if (numOrders == 0) break;

                            if (order.getDeliveryStatus().equalsIgnoreCase("IN_TRANSIT")) {
                                System.out.println("Order " + order.getOrderId() + " is already in transit. Next order....");
                                continue;
                            }

                            driverAssignment.assignDriverToOrder(order);
                            //System.out.println("Order " + order.getOrderId() + " assigned successfully.");
                            numOrders--;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number. Please enter a valid integer.");
                    }
                    break;
                case "5":
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
                    break;
                case "6":
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

                case "7":
                    try {
                        System.out.println("Show outliers");
                        float[] vehicleFuelUsage = new float[vehicles.length];
                        for (int i = 0; i < vehicles.length; i++) {
                            System.out.println(vehicles[i].getFuelUse());
                            vehicleFuelUsage[i] = vehicles[i].calculateAverageFuelConsumption();
                        }

                        float totalFuel = 0;
                        for (float fuel : vehicleFuelUsage) {
                            totalFuel += fuel;
                        }

                        float averageFuel = totalFuel / vehicles.length;
                        System.out.printf("These vehicles have fuel usage above %.2f:\n", averageFuel);

                        for (int i = 0; i < vehicleFuelUsage.length; i++) {
                            if (vehicleFuelUsage[i] > averageFuel) {
                                System.out.println(vehicles[i].getRegistrationNumber() + " - " + vehicleFuelUsage[i]);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error calculating outliers: " + e.getMessage());
                    }
                    break;

                case "8":
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
                    System.out.println("\nSEE YOU SOON...");
                    System.out.println("----------------------------------------");
                    System.out.println("ADOM LOGISTICS FLEET MANAGEMENT SYSTEM");
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
//        OrderTracker tracker = new OrderTracker();
//        Order orders = tracker.loadOrdersFromFile("Adom Fleet Management System/src/dummyTextFiles/Deliveries.txt");
//        System.out.println(orders_1[5].toString());
    }
}

