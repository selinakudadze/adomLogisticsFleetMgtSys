import models.Driver;
import models.Vehicle;

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
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


//import delivery_tracking.OrderTracker;
//import models.Order;
//
//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Hello and welcome!");
//
//        OrderTracker tracker = new OrderTracker();
//        tracker.loadOrdersFromFile("src/dummyTextFiles/Deliveries.txt");
//
//        // Print all loaded orders
//        // tracker.printAllOrders();
//
//        // Print a specific order by ID
//        System.out.println("Printing order with ID 1002:");
//        Order specificOrder = tracker.getOrder(1002);
//        if (specificOrder != null) {
//            System.out.println(specificOrder);
//        } else {
//            System.out.println("Order not found.");
//        }

        // Add a new order manually
    //     Order newOrder = new Order(
    //             2025, "John Doe", "Accra", "Cape Coast",
    //             LocalDateTime.of(2025, 7, 21, 10, 30),
    //             5.6037, -0.1870, 5.1053, -1.2466
    //     );
    //     tracker.addOrder(newOrder);
    //     System.out.println("\nNew order added successfully.\n");

    //     System.out.println("Printing newly added order with ID 2025:");
    //     System.out.println(tracker.getOrder(2025));

    //     System.out.println("\nAttempting to remove order with ID 1001:");
    //     tracker.removeOrder(1001);

    //     Order checkRemoved = tracker.getOrder(1001);
    //     if (checkRemoved == null) {
    //         System.out.println("Order 1001 successfully removed.");
    //     } else {
    //         System.out.println("Order 1001 still exists.");
    //     }

    //     System.out.println("\nAll remaining orders:");
    //     tracker.printAllOrders();
//    }
//}