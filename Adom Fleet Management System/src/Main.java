import delivery_tracking.OrderTracker;
import models.Order;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        OrderTracker tracker = new OrderTracker();
        tracker.loadOrdersFromFile("src/dummyTextFiles/Deliveries.txt");

        // Print all loaded orders
        // tracker.printAllOrders();

        // Print a specific order by ID
        System.out.println("Printing order with ID 1002:");
        Order specificOrder = tracker.getOrder(1002);
        if (specificOrder != null) {
            System.out.println(specificOrder);
        } else {
            System.out.println("Order not found.");
        }

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
    }
}