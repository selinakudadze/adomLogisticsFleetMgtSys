package driverassignment;

import datastructures.HashMap;
import java.io.File;
import java.util.Scanner;

public class LocationService {
    // Use your custom HashMap to store location name → Coordinate
    private static HashMap<String, Coordinate> locationMap = new HashMap<>();

    // Load locations from the text file
    public static void loadLocations(String filepath) {
        try (Scanner scanner = new Scanner(new File(filepath))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    double lat = Double.parseDouble(parts[1].trim());
                    double lon = Double.parseDouble(parts[2].trim());
                    locationMap.put(name, new Coordinate(lat, lon));
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to load locations: " + e.getMessage());
        }
    }

    // Get the coordinate for a given location name
    public static Coordinate getCoordinate(String locationName) {
        return locationMap.get(locationName);
    }
}
