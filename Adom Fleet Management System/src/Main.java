import models.Driver;
import models.Vehicle;

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        //OrderTracker orderTracker = new OrderTracker();
//        while(true) {
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
                    System.out.println("Enter vehicle id: ");
                    String vehicleId = scanner.nextLine();
                    Vehicle vehicle = new Vehicle(Integer.parseInt(vehicleId));
                    System.out.println("Vehicle details " + vehicle);
                    break;

                case "3":
                    System.out.println("Enter order id");
                    int orderid = scanner.nextInt();
                    //                Order order=orderTracker.getOrder(orderid);
                    //                System.out.println(order.toString());
                    break;
                case "4":
                    System.out.println("Assign orders");
                    break;
                case "5":
                    System.out.println("Check for order reroutes");
                    break;
                case "6":
                    System.out.println("Maintain vehicles");
                    break;
                case "7":
                    System.out.println("Show outliers");
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

