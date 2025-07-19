package scheduler;

import com.sun.tools.javac.Main;
import datastructures.PriorityNode;
import models.Maintenance;
import models.Vehicle;
import datastructures.MinHeap;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

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
        Vehicle[] vehiclesForService = new Vehicle[n];
        for(int i = 0; i < n; i++){
            if (heap.isEmpty()) {
                System.out.println("No more vehicles due for service.");
                break;
            }
            vehiclesForService[i] = heap.extractMin();
        }
        return vehiclesForService;
    }

    public void markAsServiced(Vehicle[] vehicles, String mechanicShop, float tCost) {
        if(vehicles.length == 0){
            System.out.println("No vehicles scheduled for servicing");
        }
        else {
            float cost_per_v = tCost/ vehicles.length;
            for (Vehicle vehicle : vehicles) {
                markAsServiced(vehicle, cost_per_v, mechanicShop);
            }
        }
    }

    public void markAsServiced(Vehicle vehicle, float cost, String mechanicShop) {
        if(vehicle != null){
            vehicle.setDaysSinceLastService(0);
            Maintenance mInfo = vehicle.getMaintenanceInfo();
            Date dateOfRepairs = new Date();
            if(mInfo == null){
                vehicle.updateMaintenanceInfo("General", 0, dateOfRepairs, cost, mechanicShop);
            }
            else{
                String partRepaired = mInfo.getUrgentPartNeedingRepairs();
                if(partRepaired != null){
                    vehicle.updateMaintenanceInfo(partRepaired, 0, dateOfRepairs, cost, mechanicShop);
                }
                else{
                    vehicle.updateMaintenanceInfo("General", 0, dateOfRepairs, cost, mechanicShop);
                }
            }
        }
    }

    private boolean shouldFlagForService(Vehicle vehicle) {
        if(vehicle.getMaintenanceInfo().getUrgentPartNeedingRepairs() != null){
            return true;
        }
        return vehicle.getMileage() >= 10000 || vehicle.getDaysSinceLastService() >= 90;
    }
}