package sort_and_search;
import models.Vehicle;

public class InsertionSortByMileage {

 public static void sortByMileage(Vehicle[] vehicles) {
        for (int i = 1; i < vehicles.length; i++) {
            Vehicle key = vehicles[i];
            int j = i - 1;

            while (j >= 0 && vehicles[j].getMileage() > key.getMileage()) {
                vehicles[j + 1] = vehicles[j];
                j--;
            }

            vehicles[j + 1] = key;
        }
    }
}

