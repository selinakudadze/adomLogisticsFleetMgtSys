package Read_Write_To_Databases;

import datastructures.LinkedList;
import datastructures.Node;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static driverassignment.DriverAssignment.getAllDriversID;

public class AddToDatabases {
    static LinkedList<String> allDriversID=getAllDriversID();

    // Reusable method to write a single line to any file
    public static void writeLine(String fileName, String line) {
        try {
            FileWriter fw = new FileWriter(fileName, true); // append = true
            PrintWriter pw = new PrintWriter(fw);
            pw.println(line);
            pw.close();
            System.out.println("\nData saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to remove data from a file based on criteria
    public static void removeData(String fileName, String criterionType, String criterionValue) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println(fileName + " not found.");
                return;
            }

            LinkedList<String> lines = new LinkedList<>();
            for (String line : Files.readAllLines(Paths.get(fileName))) {
                lines.add(line);
            }
            boolean removed = false;

            // Declare current at method level
            Node<String> current;

            // Determine removal logic based on file type
            switch (fileName) {
                case "Adom Fleet Management System/src/dummyTextFiles/Drivers.txt":
                    if (!criterionType.equals("driverID")) {
                        System.out.println("Invalid criterion for Drivers.txt. Use driverID.");
                        return;
                    }
                    LinkedList<String> tempDrivers = new LinkedList<>();
                    current = lines.head;
                    while (current != null) {
                        String[] fields = current.entity.split(";");
                        if (!fields[0].equals(criterionValue)) {
                            tempDrivers.add(current.entity);
                        } else {
                            removed = true;
                        }
                        current = current.nextNode;
                    }
                    lines = tempDrivers;
                    break;

                case "Adom Fleet Management System/src/dummyTextFiles/Vehicles.txt":
                    if (!criterionType.equals("vehicleID")) {
                        System.out.println("Invalid criterion for Vehicles.txt. Use vehicleID.");
                        return;
                    }
                    LinkedList<String> tempVehicles = new LinkedList<>();
                    current = lines.head;
                    while (current != null) {
                        String[] fields = current.entity.split(";");
                        if (!fields[0].equals(criterionValue)) {
                            tempVehicles.add(current.entity);
                        } else {
                            removed = true;
                        }
                        current = current.nextNode;
                    }
                    lines = tempVehicles;
                    break;

//                case "src/dummyTextFiles/Deliveries.txt":
//                    if (!criterionType.equals("vehicleID")) {
//                        System.out.println("Invalid criterion for Deliveries.txt. Use vehicleID.");
//                        return;
//                    }
//                    LinkedList<String> tempDeliveries = new LinkedList<>();
//                    current = lines.head;
//                    while (current != null) {
//                        String[] fields = current.entity.split(";");
//                        if (fields.length == 0 || !fields[0].equals(criterionValue)) {
//                            tempDeliveries.add(current.entity);
//                        } else {
//                            removed = true;
//                        }
//                        current = current.nextNode;
//                    }
//                    lines = tempDeliveries;
//                    break;

//                case "src/dummyTextFiles/Maintenance.txt":
//                    if (!criterionType.equals("vehicleID")) {
//                        System.out.println("Invalid criterion for Maintenance.txt. Use vehicleID.");
//                        return;
//                    }
//                    LinkedList<String> tempMaintenance = new LinkedList<>();
//                    current = lines.head;
//                    while (current != null) {
//                        String[] fields = current.entity.split(";");
//                        if (!fields[0].equals(criterionValue)) {
//                            tempMaintenance.add(current.entity);
//                        } else {
//                            removed = true;
//                        }
//                        current = current.nextNode;
//                    }
//                    lines = tempMaintenance;
//                    break;

//                case "src/dummyTextFiles/locations.txt":
//                    if (!criterionType.equals("locationName")) {
//                        System.out.println("Invalid criterion for locations.txt. Use locationName.");
//                        return;
//                    }
//                    LinkedList<String> tempLocations = new LinkedList<>();
//                    current = lines.head;
//                    while (current != null) {
//                        String[] fields = current.entity.split(";");
//                        if (!fields[0].equals(criterionValue)) {
//                            tempLocations.add(current.entity);
//                        } else {
//                            removed = true;
//                        }
//                        current = current.nextNode;
//                    }
//                    lines = tempLocations;
//                    break;

                default:
                    System.out.println("Unsupported file for removal.");
                    return;
            }

            if (removed) {
                try (PrintWriter pw = new PrintWriter(fileName)) {
                    current = lines.head;
                    while (current != null) {
                        pw.println(current.entity);
                        current = current.nextNode;
                    }
                    System.out.println("Data removed from " + fileName + " based on " + criterionType + " " + criterionValue);
                }
            } else {
                System.out.println("No data found to remove for " + criterionType + " " + criterionValue);
            }
        } catch (IOException e) {
            System.out.println("Error removing data from " + fileName + ": " + e.getMessage());
        }
    }

    // Unified data entry method for all supported files
    public static void runDataEntry() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n===== Data Entry Options =====");
                System.out.println("0. Exit");
                System.out.println("1. Add Driver");
                System.out.println("2. Add Vehicle");
                System.out.println("3. Remove Data");
                System.out.print("Choose option: ");

                // System.out.println("3. Add Order");
                // System.out.println("4. Add Maintenance Record");
                // System.out.println("5. Add Location");

                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("0") || input.equalsIgnoreCase("exit")) {
                    System.out.println("---------- Exiting the Data Entry Section ----------");
                    break;
                }

                int choice = Integer.parseInt(input);

                String[] fields;
                String filePath;
                String label;

                switch (choice) {
                    case 1:
                        label = "Driver";
                       //filePath = "src/dummyTextFiles/Drivers.txt";
                        filePath = "Adom Fleet Management System/src/dummyTextFiles/Drivers.txt";//different filepath depending on your machine
                        fields = new String[]{
                                "Driver ID", "Driver Name", "License Type (Class A/B/C/D)",
                                "Availability (ON_DUTY/OFF_DUTY)", "Current Location", "Experience (comma-separated)"
                        };
                        break;

                    case 2:
                        label = "Vehicle";
                        filePath = "Adom Fleet Management System/src/dummyTextFiles/Vehicles.txt";
                        fields = new String[]{
                                "Vehicle ID", "Registration Number", "Vehicle Type",
                                "Mileage", "Fuel Use", "Current Longitude", "Current Latitude",
                                "Current Driver Name", "Days Since Last Service"
                        };
                        break;

//                    case 3:
//                        label = "Order";
//                        filePath = "src/dummyTextFiles/Deliveries.txt";
//                        fields = new String[]{
//                                "Order ID", "Client Name", "Origin", "Destination",
//                                "Scheduled Date & Time (yyyy-MM-ddTHH:mm)",
//                                "Origin Latitude", "Origin Longitude",
//                                "Destination Latitude", "Destination Longitude"
//                        };
//                        break;
//
//                    case 4:
//                        label = "Maintenance";
//                        filePath = "src/dummyTextFiles/Maintenance.txt";
//                        fields = new String[]{
//                                "Vehicle ID", "Date of Repair (yyyy-MM-dd)", "Cost of Repairs", "Mechanic Shop"
//                        };
//                        break;
//
//                    case 5:
//                        label = "Location";
//                        filePath = "src/dummyTextFiles/locations.txt";
//                        fields = new String[]{
//                                "Location Name", "Latitude", "Longitude"
//                        };
//                        break;

                    case 3:
                        System.out.println("\n=== Remove Data Options ===");
                        System.out.println("1. Remove by Driver ID (Drivers.txt)");
                        System.out.println("2. Remove by Vehicle ID (VH<vehicle number>)");
//                        System.out.println("3. Remove by Location Name (locations.txt)");
                        System.out.print("Choose removal option: ");
                        int removeChoice = Integer.parseInt(scanner.nextLine().trim());

                        String criterionType, filePathRemove;
                        switch (removeChoice) {
                            case 1:
                                criterionType = "driverID";
                                filePathRemove = "Adom Fleet Management System/src/dummyTextFiles/Drivers.txt";
                                System.out.print("Enter Driver ID to remove: ");
                                break;
                            case 2:
                                criterionType = "vehicleID";
                                filePathRemove = null; // Will be determined by context
                                System.out.print("Enter Vehicle ID to remove: ");
                                break;
//                            case 3:
//                                criterionType = "locationName";
//                                filePathRemove = "src/dummyTextFiles/locations.txt";
//                                System.out.print("Enter Location Name to remove: ");
//                                break;
                            default:
                                System.out.println("Invalid removal option.");
                                continue;
                        }

                        String criterionValue = scanner.nextLine().trim();
                        if (removeChoice == 2) {
                            removeData("Adom Fleet Management System/src/dummyTextFiles/Vehicles.txt", criterionType, criterionValue);
//                            removeData("src/dummyTextFiles/Deliveries.txt", criterionType, criterionValue);
//                            removeData("src/dummyTextFiles/Maintenance.txt", criterionType, criterionValue);
                        } else {
                            removeData(filePathRemove, criterionType, criterionValue);
                        }
                        continue;

                    default:
                        System.out.println("Invalid option. Please choose a valid number.");
                        continue;
                }

                // Collect input dynamically
                String[] values = new String[fields.length];
                System.out.println("\n=== Enter " + label + " Details ===");

                for (int i = 0; i < fields.length; i++) {
                    System.out.print(fields[i] + ": ");
                    String nextValue = scanner.nextLine().trim();

                  //check the validity of the driver id for data integrity
                    if(fields[i].equals("Driver ID")) {
                        while (!nextValue.matches("D\\d+")) {
                            System.out.println(nextValue + " is not a valid driver id.\nEnter a valid driver id:");
//                        scanner.nextLine();
                            nextValue = scanner.nextLine().trim();

                            if (allDriversID.search(nextValue)) {
                                System.out.println("Driver ID already exists.\nEnter another Driver ID: ");
                                nextValue = scanner.nextLine().trim();
                            }
                        }


                        //If the format of driverID is correct but the driverID is already being used, go through this next step
                        while (allDriversID.search(nextValue)) {
                            System.out.println("Driver ID already exists.\nEnter another Driver ID: ");
                            nextValue = scanner.nextLine().trim();

                            if (!nextValue.startsWith("DA")) {
                                System.out.println("Driver ID already exists.\nEnter correct Driver ID (D---): ");
                                nextValue = scanner.nextLine().trim();
                            }

                        }
                    }

                    //same test for integrity but for vehicles
                    //check the validity of the driver id for data integrity
                    if(fields[i].equals("Driver ID")) {
                        while (!nextValue.matches("D\\d+")) {
                            System.out.println(nextValue + " is not a valid driver id.\nEnter a valid driver id:");
//                        scanner.nextLine();
                            nextValue = scanner.nextLine().trim();

                            if (allDriversID.search(nextValue)) {
                                System.out.println("Driver ID already exists.\nEnter another Driver ID: ");
                                nextValue = scanner.nextLine().trim();
                            }
                        }


                        //If the format of driverID is correct but the driverID is already being used, go through this next step
                        while (allDriversID.search(nextValue)) {
                            System.out.println("Driver ID already exists.\nEnter another Driver ID: ");
                            nextValue = scanner.nextLine().trim();

                            if (!nextValue.startsWith("DA")) {
                                System.out.println("Driver ID already exists.\nEnter correct Driver ID (D---): ");
                                nextValue = scanner.nextLine().trim();
                            }

                        }
                    }

                    values[i] = nextValue;

                }

                // Join values using ';' separator
                String line = String.join(";", values);

                // Write the line to the file
                writeLine(filePath, line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number input. Please enter a valid option.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    // Optional main method to test directly
    public static void main(String[] args) {
        try {
            runDataEntry();
        } catch (Exception e) {
            System.out.println("Program interrupted or encountered a fatal error: " + e.getMessage());
        } finally {
            System.out.println("Program has terminated.");
        }
    }
}