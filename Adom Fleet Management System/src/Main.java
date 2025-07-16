import models.Driver;
import models.Order;
import models.Vehicle;
import sort_and_search.BinarySearch;
import utils.VehicleReader;
import utils.OrderReader;
import scheduler.MaintenanceScheduler;
import delivery_rerouting.DeliveryReroute;
import driverassignment.DriverAssignment;

import java.util.Scanner;

import static sort_and_search.BinarySearch.searchByRegistration;

public class Main {

    public static void main(String[] args) {
        //OrderTracker orderTracker = new OrderTracker();
//        while(true) {
        OrderReader orderReader = new OrderReader("Adom Fleet Management System/src/dummyTextFiles/Deliveries.txt");
        Order[] orders = orderReader.readOrdersFromFile();
        VehicleReader vehicleReader = new VehicleReader("Adom Fleet Management System/src/dummyTextFiles/Vehicles.txt");
        Vehicle[] vehicles = vehicleReader.readVehiclesFromFile();
        MaintenanceScheduler maintenanceScheduler = new MaintenanceScheduler();
        DeliveryReroute deliveryReroute = new DeliveryReroute();

        DriverAssignment driverAssignment = new DriverAssignment();
        driverAssignment.LoadAvailableDrivers();

        Scanner scanner = new Scanner(System.in);
        String continueChoice;

        do {
            System.out.println("\nADOM LOGISTICS FLEET MANAGEMENT SYSTEM DISPATCHER");
            System.out.println("-----------------------------");
            System.out.println("1. Get driver info (D00-)");
            System.out.println("2. Get vehicle info");
            System.out.println("3. Track order");
            System.out.println("4. Assign order");
            System.out.println("5. Check reroute");
            System.out.println("6. Maintain vehicles");
            System.out.println("7. Show outliers");
            System.out.println("8. Exit");

            String choice = scanner.nextLine();
            //scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Enter driver id: ");
                    String driverId = scanner.nextLine();
                    Driver driver = new Driver(driverId);
                    System.out.println("Driver details " + driver);

                    break;
                case "2":
                    System.out.println("Enter vehicle registration number: ");
                    String vehicleId = scanner.nextLine();
                    int index=searchByRegistration(vehicles,vehicleId);
                    System.out.println(vehicles[index]);
                    break;

                case "3":
                    System.out.println("Enter order id");
                    int orderid = scanner.nextInt();

                    for(Order order : orders) {
                        if(order.getOrderId() == orderid) {
                            System.out.println("Order id " + order.getOrderId()+" is "+order.getDeliveryStatus());
                        }
                    }
                    break;
                case "4":
                    System.out.println("Assign orders");
                    System.out.println("Enter number of orders to be assigned");
                    int numOrders = scanner.nextInt();
                    scanner.nextLine();

                    for(Order order : orders){
                        if(numOrders == 0){
                            break;
                        }
                        driverAssignment.assignDriverToOrder(order);
                        numOrders--;
                    }
                    break;
                case "5":
                    System.out.println("Check for order reroutes");
                    deliveryReroute.rerouteDeliveries(orders, vehicles);
                    for(Order o: orders){
                        o.setDeliveryStatus("Stuck");
                        System.out.println(o.getDeliveryStatus());
                    }
                    deliveryReroute.rerouteDeliveries(orders, vehicles);
                    for(Order o: orders){
                        System.out.println(o.getDeliveryStatus());
                    }

                    break;
                case "6":
                    maintenanceScheduler.loadFromVehicleList(vehicles);
                    for(Vehicle v: vehicles){
                        System.out.println(v.toString());
                    }
                    System.out.println("Maintain vehicles");
                    System.out.println("Enter number of vehicles");
                    int n = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter mechanic to fix vehicles at");
                    String mech = scanner.nextLine();

                    System.out.println("Enter cost to fix all vehicles");
                    float cost = scanner.nextFloat();
                    scanner.nextLine();

                    Vehicle[] toMaintain = maintenanceScheduler.getNextVehiclesForService(n);


                    maintenanceScheduler.markAsServiced(toMaintain, mech, cost);

                    for(Vehicle v: vehicles){
                        System.out.println(v.toString());
                    }

                    break;
                case "7":
                    System.out.println("Show outliers");
                    float[] vehicleFuelUsage= new float[vehicles.length];
                    for(int i =0;i<vehicles.length;i++){
                        vehicleFuelUsage[i]=vehicles[i].calculateAverageFuelConsumption();
                    }
                    float totalFuelConsumption = 0;
                    float averageFuelConsumption=0;


                    for(float v: vehicleFuelUsage){
                        totalFuelConsumption++;
                    }
                    averageFuelConsumption=totalFuelConsumption/vehicleFuelUsage.length;

                    System.out.printf("These vehicles have fuel usage above "+averageFuelConsumption+"\n");
                    for(int i=0;i<vehicleFuelUsage.length;i++){
                        if(vehicleFuelUsage[i]>averageFuelConsumption){

                            System.out.println(vehicles[i].getRegistrationNumber()+"-"+vehicleFuelUsage[i]);
                        }
                    }

                    break;
                case "8":

                    break;
                default:
                    System.out.println("Invalid choice");

            }
            if(choice.equals("8")) {
                System.out.println("\nWould you like to exit? (y/n)");
                continueChoice = scanner.nextLine();
            }else {
                System.out.print("\nWould you like to perform another operation? (y/n): ");

                continueChoice = scanner.nextLine();
            }

        }while(continueChoice.equalsIgnoreCase("y"));

        System.out.println("\nSEE YOU SOON...");
        scanner.close();
    }
}

