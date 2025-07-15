package datastructures;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PriorityQueue {
    private static class Node {
        Vehicle vehicle;
        long priority; // Lower value = higher priority (e.g., days since last service or mileage excess)

        Node(Vehicle vehicle, long priority) {
            this.vehicle = vehicle;
            this.priority = priority;
        }
    }

    private Node[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public PriorityQueue() {
        this.heap = new Node[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Add a vehicle to the priority queue
    public void enqueue(Vehicle vehicle) {
        if (size == heap.length) resize();
        heap[size] = new Node(vehicle, calculatePriority(vehicle));
        siftUp(size);
        size++;
    }

    // Remove and return the highest priority vehicle
    public Vehicle dequeue() {
        if (size == 0) return null;
        Vehicle vehicle = heap[0].vehicle;
        heap[0] = heap[size - 1];
        size--;
        siftDown(0);
        return vehicle;
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Calculate priority based on mileage and service date
    private long calculatePriority(Vehicle vehicle) {
        long mileagePriority = vehicle.getMileage() > 10000 ? vehicle.getMileage() - 10000 : 0; // STANDARD MILEAGE = 10,000
        LocalDate lastService = vehicle.getLastServiceDate();
        long daysSinceService = lastService != null ? ChronoUnit.DAYS.between(lastService, LocalDate.now()) : 180;
        long servicePriority = daysSinceService > 180 ? daysSinceService - 180 : 0; // 6 months = 180 days
        return Math.max(mileagePriority, servicePriority);
    }

    // Sift up to maintain heap property
    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[parent].priority <= heap[index].priority) break;
            swap(parent, index);
            index = parent;
        }
    }

    // Sift down to maintain heap property
    private void siftDown(int index) {
        while (true) {
            int smallest = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;

            if (left < size && heap[left].priority < heap[smallest].priority) {
                smallest = left;
            }
            if (right < size && heap[right].priority < heap[smallest].priority) {
                smallest = right;
            }

            if (smallest == index) break;
            swap(index, smallest);
            index = smallest;
        }
    }

    // Resize heap when full
    private void resize() {
        Node[] newHeap = new Node[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
    }

    // Swap nodes in heap
    private void swap(int i, int j) {
        Node temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}

// Placeholder Vehicles.txt class (to be implemented fully)
class Vehicle {
    private String registrationNumber;
    private int mileage;
    private LocalDate lastServiceDate;

    public Vehicle(String registrationNumber, int mileage, LocalDate lastServiceDate) {
        this.registrationNumber = registrationNumber;
        this.mileage = mileage;
        this.lastServiceDate = lastServiceDate;
    }

    public int getMileage() { return mileage; }
    public LocalDate getLastServiceDate() { return lastServiceDate; }
}
