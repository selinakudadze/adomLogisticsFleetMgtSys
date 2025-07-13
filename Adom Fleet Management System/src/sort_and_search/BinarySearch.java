package sort_and_search;

import models.Vehicle;

public class BinarySearch {
    // Binary search to find a vehicle by registration number
    public static int searchByRegistration(Vehicle[] vehicles, String targetRegistration) {
        int left = 0;
        int right = vehicles.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = vehicles[mid].getRegistrationNumber().compareTo(targetRegistration);

            if (comparison == 0) {
                return mid; // Found the vehicle
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1; // Vehicle not found
    }

    // Placeholder method to get registration number (assumed in Vehicle class)
    private static String getRegistrationNumber(Vehicle vehicle) {
        return vehicle.getRegistrationNumber(); // Assumes Vehicle has this getter
    }
}

// Placeholder Vehicle class with necessary method (to be integrated with models.Vehicle)
