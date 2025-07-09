package datastructures;

import models.Vehicle;

public class MinHeap {
    private Vehicle[] heap;
    private int size;
    private int capacity;

    public MinHeap() {
        this.capacity = 100;
        this.size = 0;
        this.heap = new Vehicle[capacity];
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int left(int i) { return 2 * i + 1; }
    private int right(int i) { return 2 * i + 2; }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(Vehicle v) {
        if (size == capacity) {
            resize();
        }
        heap[size] = v;
        heapifyUp(size);
        size++;
    }

    public Vehicle extractMin() {
        if (isEmpty()) return null;
        Vehicle min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return min;
    }

    private void heapifyUp(int i) {
        while (i > 0 && heap[i].compareTo(heap[parent(i)]) < 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void heapifyDown(int i) {
        int smallest = i;
        int l = left(i);

        int r = right(i);

        if (l < size && heap[l].compareTo(heap[smallest]) < 0) {
            smallest = l;
        }
        if (r < size && heap[r].compareTo(heap[smallest]) < 0) {
            smallest = r;
        }
        if (smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest);
        }
    }

    private void swap(int i, int j) {
        Vehicle temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void resize() {
        capacity *= 2;
        Vehicle[] newHeap = new Vehicle[capacity];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
    }
}