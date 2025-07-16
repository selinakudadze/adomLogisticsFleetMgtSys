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

    public Vehicle[] getNextVehiclesForService(int n) {
        if (heap.isEmpty()) {
            System.out.println("No vehicles due for service.");
            return null;
        }
        Vehicle[] nextVehiclesForService = new Vehicle[n];
        for(int i = 0; i < n; i++){
            heap.extractMin();
        }
        return nextVehiclesForService;
    }

    public void markAsServiced(Vehicle[] vehicles) {
        for(Vehicle vehicle: vehicles){
            vehicle.setDaysSinceLastService(0);
        }
    }

    private boolean shouldFlagForService(Vehicle vehicle) {
        return vehicle.mileage() >= 10000 || vehicle.getDaysSinceLastService() >= 90;
    }
}