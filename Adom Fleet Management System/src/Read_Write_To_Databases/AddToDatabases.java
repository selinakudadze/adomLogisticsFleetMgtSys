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
    //Update Data Method
    public static void updateData(String fileName, String idValue, String[] newValues) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println(fileName + " not found.");
                return;
            }

            LinkedList<String> lines = new LinkedList<>();
            boolean updated = false;


            for (String line : Files.readAllLines(Paths.get(fileName))) {
                String[] fields;

                if (fileName.contains("Deliveries.txt")) {
                    fields = line.split(",");
                } else {
                    fields = line.split(";");
                }

                if (fields.length > 0 && fields[0].equals(idValue)) {
                    // Preserve ID and uneditable fields
                    if (fileName.contains("Drivers.txt")) {
                        fields[1] = newValues[0]; // Name
                        fields[2] = newValues[1]; // License
                        fields[3] = newValues[2]; // Availability
                        fields[5] = newValues[3]; // Experience
                    } else if (fileName.contains("Vehicles.txt")) {
                        // Skip index 0 (ID), 1 (Registration), 2 (Type)
                        fields[3] = newValues[0]; // Mileage
                        fields[4] = newValues[1]; // Fuel Use
                        fields[5] = newValues[2]; // Longitude
                        fields[6] = newValues[3]; // Latitude
                        fields[7] = newValues[4]; // Driver Name
                        fields[8] = newValues[5]; // Days since last service
                    } else if (fileName.contains("Deliveries.txt")) {
                        fields[1] = newValues[0];
                        fields[2] = newValues[1];
                        fields[3] = newValues[2];
                        fields[4] = newValues[3];
                        fields[5] = newValues[4];
                        fields[6] = newValues[5];
                        fields[7] = newValues[6];
                        fields[8] = newValues[7];

                    }

                    String updatedLine = (fileName.contains("Deliveries.txt")
                            ?String.join(",", fields)
                            :String.join(";", fields));
                    lines.add(updatedLine);
                    updated = true;
                } else {
                    lines.add(line);
                }
            }

            if (updated) {
                try (PrintWriter pw = new PrintWriter(fileName)) {
                    for (Node<String> current = lines.head; current != null; current = current.nextNode) {
                        pw.println(current.entity);
                    }
                }
                System.out.println("Data updated successfully for ID: " + idValue);
            } else {
                System.out.println("No matching ID found for update.");
            }

        } catch (IOException e) {
            System.out.println("Error updating data in file: " + e.getMessage());
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
                case "./src/dummyTextFiles/Drivers.txt"://different filepath depending on your machine
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

                case "./src/dummyTextFiles/Vehicles.txt":
                    if (!criterionType.equals("vehicleID")) {
                        System.out.println("Invalid criterion for Vehicles. Use vehicle ID.");
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

                case "./src/dummyTextFiles/Deliveries.txt":
                    if (!criterionType.equals("orderID")) {
                        System.out.println("Invalid criterion for Deliveries. Use order ID.");
                        return;
                    }
                    LinkedList<String> tempDeliveries = new LinkedList<>();
                    current = lines.head;
                    while (current != null) {
                        String[] fields = current.entity.split(";");
                        if (fields.length == 0 || !fields[0].equals(criterionValue)) {
                            tempDeliveries.add(current.entity);
                        } else {
                            removed = true;
                        }
                        current = current.nextNode;
                    }
                    lines = tempDeliveries;
                    break;

//                case "./src/dummyTextFiles/Maintenance.txt":
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

//                case "./src/dummyTextFiles/locations.txt":
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
                    System.out.println("Data removed from " + fileName + " based on " + criterionType);
                }
            } else {
                System.out.println("No data found to remove for " + criterionType + " " + criterionValue);
            }
        } catch (IOException e) {
            System.out.println("Error removing data from " + fileName + ": " + e.getMessage());
        }
    }

    // Search data about Order, Driver, and Vehicle by ID
    public static void searchById(String fileName, String idValue) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println(fileName + " not found.");
                return;
            }

            boolean found = false;
            for (String line: Files.readAllLines(Paths.get(fileName))) {
                if (line.startsWith(idValue + ";")) {
                    System.out.println("Records found: " + line);
                    found = true;
                    break;
                } else if (line.startsWith(idValue + ",")){
                    System.out.println("Order found: " + line + "\n");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("No record found for ID: " + idValue);
            }
        } catch (IOException e) {
            System.out.println(("Error searching file: " + e.getMessage()));
        }
    }

    // Unified data entry method for all supported files
    public static void runDataEntry() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("===== Data Entry Options ===== \n Enter only the corresponding digits of the instructions below.");
                System.out.println("0. Exit");
                System.out.println("1. Add Driver");
                System.out.println("2. Add Vehicle");
                System.out.println("3. Add Order");
                System.out.println("4. Remove Data");
                System.out.println("5. Update Data");
                System.out.println("6. Search for data by ID");
                System.out.print("Choose option: ");


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
                        filePath = "./src/dummyTextFiles/Drivers.txt";//different filepath depending on your machine
                        fields = new String[]{
                                "Driver ID", "Driver Name", "License Type (Class A/B/C/D)",
                                "Availability (ON_DUTY/OFF_DUTY)", "Current Location", "Experience (comma-separated)"
                        };
                        break;

                    case 2:
                        label = "Vehicle";
                        filePath = "./src/dummyTextFiles/Vehicles.txt";
                        fields = new String[]{
                                "Vehicle ID", "Registration Number", "Vehicle Type",
                                "Mileage", "Fuel Use", "Current Longitude", "Current Latitude",
                                "Current Driver Name", "Days Since Last Service"
                        };
                        break;

                    case 3:
                        label = "Order";
                        filePath = "./src/dummyTextFiles/Deliveries.txt";
                        fields = new String[]{
                                "Order ID", "Client Name", "Origin", "Destination",
                                "Scheduled Date & Time (yyyy-MM-ddTHH:mm)",
                                "Origin Latitude", "Origin Longitude",
                                "Destination Latitude", "Destination Longitude"
                        };
                        break;
//
//                    case 4:
//                        label = "Maintenance";
//                        filePath = "./src/dummyTextFiles/Maintenance.txt";
//                        fields = new String[]{
//                                "Vehicle ID", "Date of Repair (yyyy-MM-dd)", "Cost of Repairs", "Mechanic Shop"
//                        };
//                        break;
//
//                    case 5:
//                        label = "Location";
//                        filePath = "./src/dummyTextFiles/locations.txt";
//                        fields = new String[]{
//                                "Location Name", "Latitude", "Longitude"
//                        };
//                        break;

                    case 4:
                        System.out.println("\n=== Remove Data Options ===");
                        System.out.println("1. Remove by Driver ID (DXXX)");
                        System.out.println("2. Remove by Vehicle ID");
                        System.out.println("3. Remove by Order ID");
                        System.out.print("Choose removal option: ");
                        int removeChoice = Integer.parseInt(scanner.nextLine().trim());

                        String criterionType, filePathRemove;
                        switch (removeChoice) {
                            case 1:
                                criterionType = "driverID";
                                filePathRemove = "./src/dummyTextFiles/Drivers.txt";
                                System.out.print("Enter Driver ID to remove: ");
                                break;
                            case 2:
                                criterionType = "vehicleID";
                                filePathRemove = "./src/dummyTextFiles/Vehicles.txt"; // Will be determined by context
                                System.out.print("Enter Vehicle ID to remove: ");
                                break;
                            case 3:
                                criterionType = "orderID";
                                filePathRemove = "./src/dummyTextFiles/Deliveries.txt";
                                System.out.print("Enter the ID of the order you want to remove: ");
                                break;
                            default:
                                System.out.println("Invalid removal option.");
                                continue;
                        }

                        String criterionValue = scanner.nextLine().trim();
                        removeData(filePathRemove, criterionType, criterionValue);
                        continue;



                        //  Case for Update Data
                    case 5:
                        System.out.println("Update Data || 1. Driver Info | 2. Vehicle Info | 3. Order Details");
                        System.out.print("Choose option: ");
                        int updateChoice = Integer.parseInt(scanner.nextLine().trim());

                        switch (updateChoice) {
                            case 1:
                                filePath = "./src/dummyTextFiles/Drivers.txt";
                                fields = new String[]{
                                        "New Driver Name",
                                        "New License Type (B/C/D)",
                                        "New Availability (ON_DUTY/OFF_DUTY)",
                                        "New Experience (comma-separated)"
                                };
                                System.out.print("Enter Driver ID to update(eg.D001): ");
                                break;

                            case 2:
                                filePath = "./src/dummyTextFiles/Vehicles.txt";
                                fields = new String[]{
                                        "New Mileage",
                                        "New Fuel Use",
                                        "New Longitude",
                                        "New Latitude",
                                        "New Current Driver Name",
                                        "New Days Since Last Service"
                                };
                                System.out.print("Enter Vehicle ID to update: ");
                                break;

                            case 3:
                                filePath = "./src/dummyTextFiles/Deliveries.txt";
                                fields = new String[]{
                                        "New client name",
                                        "New origin",
                                        "New destination",
                                        "New scheduled Date & Time (YYYY-MM-DDThh:mm)",
                                        "New origin latitude",
                                        "New origin longitude",
                                        "New destination latitude",
                                        "New destination Longitude"};
                                System.out.print("Enter order ID to update: ");
                                break;

                            default:
                                System.out.println("Invalid update option.");
                                continue;
                        }


                        String idToUpdate = scanner.nextLine().trim();
                        String[] newValues = new String[fields.length];

                        for (int i = 0; i < fields.length; i++) {
                            System.out.print(fields[i] + ": ");
                            newValues[i] = scanner.nextLine().trim();
                        }

                        updateData(filePath, idToUpdate, newValues);
                        continue;

                    case 6:
                        System.out.println("---- Search Driver, Vehicle, and Order Details ----");
                        System.out.println("1. Driver \n2. Vehicle \n3. Order");
                        System.out.print("Enter the your choice: ");
                        int searchChoice = Integer.parseInt(scanner.nextLine());
                        String searchFile = "";
                        if (searchChoice == 1) {
                            searchFile = "./src/dummyTextFiles/Drivers.txt";
                        } else if(searchChoice == 2) {
                            searchFile = "./src/dummyTextFiles/Vehicles.txt";
                        } else if(searchChoice == 3){
                            searchFile = "./src/dummyTextFiles/Deliveries.txt";
                        } else {
                            System.out.println("Please enter a valid number.");
                            continue;
                        }
                        System.out.print("Enter ID to search: ");
                        String searchId = scanner.nextLine().trim();
                        searchById(searchFile, searchId);
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