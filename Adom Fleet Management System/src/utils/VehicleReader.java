package utils;
import models.Vehicle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class VehicleReader {
    String fileName;
    public VehicleReader(String fileName){
        this.fileName = fileName;
    }

    public Vehicle[] readVehiclesFromFile() {
        int i = 0;
        int numLines = 0;
        try (Scanner counterScanner = new Scanner(new File(this.fileName))) {
            while (counterScanner.hasNextLine()) {
                if (!counterScanner.nextLine().trim().isEmpty()) {
                    numLines++;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Vehicle file not found: " + fileName);
            return new Vehicle[0];
        }
        Vehicle[] vehicles = new Vehicle[numLines + 1];
        try (Scanner vehicleScanner = new Scanner(new File(this.fileName))) {
            while (vehicleScanner.hasNextLine()) {
                String line = vehicleScanner.nextLine().trim();
                if(line.isEmpty()){
                    continue;
                }
                try {
                    String[] fields = line.split(";");
                    int vehicleId = Integer.parseInt(fields[0]);
                    String registrationNumber = fields[1];
                    String vehicleType = fields[2];
                    int mileage = Integer.parseInt(fields[3]);
                    float fuelUsage = Float.parseFloat(fields[4]);
                    double currentLong = Double.parseDouble(fields[5]);
                    double currentLat = Double.parseDouble(fields[6]);
                    String currentDriver = fields[7];
                    int daysSinceLastService = Integer.parseInt(fields[8]);
                    vehicles[i] = new Vehicle(
                            vehicleId,
                            registrationNumber,
                            vehicleType,
                            mileage,
                            fuelUsage,
                            currentLong,
                            currentLat,
                            currentDriver,
                            daysSinceLastService);
                    i++;
                } catch (Exception e) {
                    System.err.println("Skipping invalid vehicle: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Vehicle file not found: " + fileName);
        }
        return Arrays.copyOf(vehicles, i);
    }
}