package delivery_tracking;

import datastructures.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import models.Order;
import utils.OrderReader;

public class OrderTracker {
    private HashMap<Integer, Order> orderMap;
    OrderReader orderReader;

    public OrderTracker(OrderReader orderReader) {
        this.orderMap = new HashMap<>();
        this.orderReader = orderReader;
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

    public void loadOrdersAndHashMap(){
        orderMap=orderReader.getOrderHashMap();
    }

//    public void loadOrdersFromFile(String fileName) {
//    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//        String line;
//        reader.readLine(); // Skip header
//
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//
//        while ((line = reader.readLine()) != null) {
//            if (line.trim().isEmpty()) continue; // ✅ Skip empty lines
//
//            String[] fields = line.split(",");
//
//            if (fields.length < 9) {
//                System.err.println("Skipping invalid line: " + line);
//                continue;
//            }
//
//            try {
//                int orderId = Integer.parseInt(fields[0].trim());
//                String clientName = fields[1].trim();
//                String origin = fields[2].trim();
//                String destination = fields[3].trim();
//                LocalDateTime scheduledDateTime = LocalDateTime.parse(fields[4].trim(), formatter);
//                double originLat = Double.parseDouble(fields[5].trim());
//                double originLon = Double.parseDouble(fields[6].trim());
//                double destLat = Double.parseDouble(fields[7].trim());
//                double destLon = Double.parseDouble(fields[8].trim());
//
//                Order order = new Order(orderId, clientName, origin, destination, scheduledDateTime,
//                        originLat, originLon, destLat, destLon);
//
//                addOrder(order);
//            } catch (NumberFormatException | DateTimeParseException e) {
//                System.err.println("Skipping malformed line: " + line);
//            }
//        }
//
//        System.out.println("Orders loaded successfully.");
//    } catch (IOException e) {
//        System.err.println("Error reading file: " + e.getMessage());
//    }
//
//    }

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