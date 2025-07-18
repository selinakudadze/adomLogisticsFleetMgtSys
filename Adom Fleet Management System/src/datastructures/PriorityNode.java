package datastructures;

public class PriorityNode<T> {
    private T value;
    private int priority; // Lower value = higher priority
    private PriorityNode<T> next;

    public PriorityNode(T value, int priority, PriorityNode next) {
        this.value = value;
        this.priority = priority;
        this.next = next;
    }
    public PriorityNode(T value, int priority) {
        this.value = value;
        this.priority = priority;
        this.next = null;
    }

    public T getValue(){
        return this.value;
    }

    public void setValue(T value){
        this.value = value;
    }
    public  int getPriority(){
        return this.priority;
    }
    public void setPriority(int priority){
        this.priority = priority;
    }
    public PriorityNode<T> next(){
        return this.next;
    }
    public void setNext(PriorityNode<T> newNext){
        this.next = newNext;
    }
}
