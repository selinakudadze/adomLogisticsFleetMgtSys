package scheduler;

import models.Vehicle;
import datastructures.MinHeap;

public class MaintenanceScheduler {
    private final MinHeap heap;

    public MaintenanceScheduler() {
        this.heap = new MinHeap();
    }

    public void loadFromVehicleList(Vehicle[] vehicles) {
        for (Vehicle vehicle : vehicles) {
            if (shouldFlagForService(vehicle)) {
                heap.insert(vehicle);
            }
        }
    }

    public Vehicle getNextVehicleForService() {
        if (heap.isEmpty()) {
            System.out.println("No vehicles due for service.");
            return null;
        }
        return heap.extractMin();
    }

    public void markAsServiced(Vehicle vehicle) {
        vehicle.setDaysSinceLastService(0);
    }

    private boolean shouldFlagForService(Vehicle vehicle) {
        return vehicle.getMileage() >= 10000 || vehicle.getDaysSinceLastService() >= 90;
    }
}