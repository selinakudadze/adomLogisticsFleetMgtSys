package delivery_tracking;

import datastructures.HashMap;
import models.Order;

public class OrderTracker {
    private HashMap<Integer, Order> orderMap;

    public OrderTracker() {
        orderMap = new HashMap<>();
    }

    public void addOrder(Order order) {
        orderMap.put(order.getOrderId(), order);
    }

    public Order getOrder(int orderId) {
        return orderMap.get(orderId);
    }

    public void updateLocation(int orderId, double lat, double lon) {
        Order order = orderMap.get(orderId);
        if (order != null) {
            order.updateCurrentLocation(lat, lon);
        } else {
            System.out.println("Order not found.");
        }
    }

    public void updateStatus(int orderId, String status) {
        Order order = orderMap.get(orderId);
        if (order != null) {
            order.setDeliveryStatus(status);
        } else {
            System.out.println("Order not found.");
        }
    }

    public void assignDriver(int orderId, String driverName) {
        Order order = orderMap.get(orderId);
        if (order != null) {
            order.assignToDriver(driverName);
        } else {
            System.out.println("Order not found.");
        }
    }

    public void removeOrder(int orderId) {
        boolean removed = orderMap.remove(orderId);
        if (!removed) {
            System.out.println("Order not found or already removed.");
        }
    }

    public void printAllOrders() {
        orderMap.printAll();
    }
}

// This class manages the tracking of orders, allowing for adding, updating, and removing orders.
// It uses a HashMap to store orders by their ID, providing methods to update their status, location, and assigned driver.
// The printAllOrders method allows for displaying all current orders in the system.
//Time Complexity (per operation on average): O(1) for add, get, update, and remove operations due to the use of a HashMap
//Time Complexity (per operation in the worst case): O(n) if there are many hash collisions, but this is rare with a good hash function.
// Space Complexity: O(n) where n is the number of orders stored in the system.

/** CODE IMPLEMENTATION IN MAIN.JAVA
 * OrderTracker tracker = new OrderTracker();

 Order o1 = new Order(101, "Alice", "Tema", "Kumasi", LocalDateTime.now(), 5.6, -0.1, 6.7, -1.3);
 tracker.addOrder(o1);
 tracker.assignDriver(101, "Kwame");
 tracker.updateStatus(101, "In Transit");

 tracker.printAllOrders();
 */