package datastructures;

import datastructures.PriorityNode;

public class PriorityQueue<T> {
    private PriorityNode<T> head;
    private int size;

    public PriorityQueue() {
        this.head = null;
        this.size = 0;
    }

    // Add a node to the priority queue
    public void enqueue(T value, int priority) {
        PriorityNode<T> newNode = new PriorityNode<>(value, priority);
        if(this.head == null){
            this.head = newNode;
        }
        else if(this.head.getPriority() >= newNode.getPriority()){
            newNode.setNext(this.head);
            this.head = newNode;
        }
        else{
            PriorityNode<T> curr = this.head;
            PriorityNode<T> prev = null;
            while(curr != null && curr.getPriority() <= newNode.getPriority()){
                prev = curr;
                curr = curr.next();
            }
            prev.setNext(newNode);
            newNode.setNext(curr);
        }
        size++;
    }

    // Remove and return the highest priority item
    public PriorityNode<T> dequeue() {
        if(this.head == null){
            return null;
        }
        PriorityNode<T> curr = this.head.next();
        PriorityNode<T> front = this.head;
        this.head = curr;
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