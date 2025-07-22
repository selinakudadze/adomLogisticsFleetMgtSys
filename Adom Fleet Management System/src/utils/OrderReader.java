package utils;
import datastructures.HashMap;
import datastructures.LinkedList;
import delivery_tracking.OrderTracker;
import models.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class OrderReader {
    String fileName;
    //adding linkedList to store orders
    LinkedList<Order> ordersList = new LinkedList<>();
    HashMap<Integer, Order> orderHashMap = new HashMap<>();


    public OrderReader(String fileName){
        this.fileName = fileName;
    }

    public Order[] readOrdersFromFile() {
        int i = 0;
        int numLines = 0;
        try (Scanner counterScanner = new Scanner(new File(this.fileName))) {
            while (counterScanner.hasNextLine()) {
                if (!counterScanner.nextLine().trim().isEmpty()) {
                    numLines++;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Order file not found: " + fileName);
            return new Order[0];
        }
        Order[] orders = new Order[numLines + 1];
        try (Scanner orderScanner = new Scanner(new File(this.fileName))) {
            while (orderScanner.hasNextLine()) {
                String line = orderScanner.nextLine().trim();
                if(line.isEmpty()){
                    continue;
                }
                try {
                    String[] fields = line.split(",");
                    int orderId = Integer.parseInt(fields[0]);
                    String clientName = fields[1];
                    String origin = fields[2];
                    String destination = fields[3];
                    LocalDateTime scheduledDateTime = LocalDateTime.parse(fields[4]);
                    double originLatitude = Double.parseDouble(fields[5]);
                    double originLongitude = Double.parseDouble(fields[6]);
                    double destinationLatitude = Double.parseDouble(fields[7]);
                    double destinationLongitude = Double.parseDouble(fields[8]);
                    orders[i] = new Order(
                            orderId,
                            clientName,
                            origin,
                            destination,
                            scheduledDateTime,
                            originLatitude,
                            originLongitude,
                            destinationLatitude,
                            destinationLongitude);

                    ordersList.add(orders[i]);//adding order to the linkedList
                    orderHashMap.put(orders[i].getOrderId(), orders[i]);//adds order to hashMap
                    i++;


                } catch (Exception e) {
                    System.err.println("Skipping invalid order: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Order file not found: " + fileName);
        }
        return Arrays.copyOf(orders, i);
    }

    public LinkedList<Order> getOrdersList() {
        //returns list of orders
        return ordersList;
    }
    public HashMap<Integer, Order> getOrderHashMap() {
        //returns hashMap of orders
        return orderHashMap;
    }
}