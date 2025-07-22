package datastructures;

public class Queue<T> {
    /**
     * Our Queue uses the Node class
     */

    public Node<T> head;
    public Node<T> tail;
    int size;


    public Queue() {
        this.head = null;
        this.tail = null;
        this.size=0;
    }

    public void enqueue(T entity) {
        Node<T> currentNode = new Node<T>(entity);

        //We use the tail to track the current node

        if (head == null) {
            this.head = currentNode;
            this.tail = currentNode;
        } else {
            this.tail.nextNode = currentNode;
            this.tail = currentNode;
        }
        size++;
    }

    public Node<T> dequeue() {
        Node<T> currentNode = this.head;

        if(currentNode == null){
            this.tail=null;
        }
        else{
            this.head = this.head.nextNode;
            currentNode.nextNode = null;
        }
        size--;
        return currentNode;
    }

    public T front() {
        // Returns the first element in the Queue, or null if empty
        if (this.head == null) {
            return this.head.entity;
        }
        return this.head.entity;
    }

    public T back() {
        //Returns the last element in the queue
        return this.tail.entity;
    }

    public boolean isEmpty(){
        if(this.head == null) return true;
        else return false;
    }

    public int size(){
        // This method returns the size of the queue(That is, the number of elements in the queue)
        return size;
    }

    public T next(){
        return head.nextNode.entity;
    }

//    public static void main(String[] args){
//        Queue<Integer> queue_test=new Queue<Integer>();
//        queue_test.enqueue(0);
//        queue_test.enqueue(2);
//        queue_test.enqueue(4);
//        queue_test.enqueue(6);
//        queue_test.enqueue(8);
//        queue_test.enqueue(10);
//
//        for(int i=0;i<4;i++){
//            System.out.println(queue_test.dequeue().entity+" has been removed");
//        }
//
//        System.out.println("This is the first element "+queue_test.front());
//        System.out.println("This is the last element "+queue_test.back());
//        queue_test.enqueue(-2);
//        System.out.println("Size of the queue is "+queue_test.size());
//        System.out.println(queue_test.isEmpty());
//    }
}
