package datastructures;

import datastructures.PriorityNode;

public class PriorityQueue<T> {
    private PriorityNode<T> head;
    private int size;

    public PriorityQueue() {
        this.head = null;
        int size = 0;
    }

    // Add a node to the priority queue
    public void enqueue(T value, int priority) {
        PriorityNode<T> newNode = new PriorityNode<>(value, priority);
        if(this.head == null){
            this.head = newNode;
        }
        else{
            PriorityNode<T> curr = this.head;
            PriorityNode<T> prev = null;
            while(curr != null){
                if(curr.getPriority() >= newNode.getPriority()){
                    prev.setNext(newNode);
                    newNode.setNext(curr);
                    break;
                }
                curr = curr.next();
            }
        }
        size++;
    }

    // Remove and return the highest priority vehicle
    public PriorityNode<T> dequeue() {
        PriorityNode<T> curr = this.head.next();
        PriorityNode<T> front = this.head;
        this.head = curr;
        while(curr != null){
            curr.setPriority(curr.getPriority() - 1);
            curr = curr.next();
        }
        size--;
        return front;
    }

    public PriorityNode<T> peek(){
        return this.head;
    }
    // Check if queue is empty
    public boolean isEmpty() {
        return size == 0;
    }
}