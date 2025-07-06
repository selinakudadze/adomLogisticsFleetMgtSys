package Stacks_and_Queues;

public class Queue<T> {
    /**
     * Our Queue uses the Node class
     */

    public Node<T> head;
    public Node<T> tail;


    public Queue() {
        this.head = null;
        this.tail = null;
    }

    private void enqueue(T entity) {
        Node<T> currentNode = new Node<T>(entity);

        //We use the tail to track the current node

        if (head == null) {
            this.head = currentNode;
            this.tail = currentNode;
        } else {
            this.tail.nextNode = currentNode;
            this.tail = currentNode;
        }
    }

    private Node<T> dequeue() {
        Node<T> currentNode = this.head;

        if(currentNode == null){
            this.tail=null;
        }
        else{
            this.head = this.head.nextNode;
            currentNode.nextNode = null;
        }
        return currentNode;
    }

    private T front() {
        //Returns the first element in the Queue
        return this.head.entity;
    }

    private T back() {
        //Returns the last element in the queue
        return this.tail.entity;
    }

    private boolean isEmpty(){
        if(this.head == null) return true;
        else return false;
    }

    private int size(){
        // This method returns the size of the queue(That is, the number of elements in the queue)

        int size_counter=0;
        Node<T> currentNode = this.head;

        if(this.head== null) return 0;
        else{
            while(true){
                if(currentNode != null) {
                    size_counter += 1;
                    currentNode = currentNode.nextNode;
                }else break;
            }
        }
        return size_counter;

    }

    public static void main(String[] args){
        Queue<Integer> queue_test=new Queue<Integer>();
        queue_test.enqueue(0);
        queue_test.enqueue(2);
        queue_test.enqueue(4);
        queue_test.enqueue(6);
        queue_test.enqueue(8);
        queue_test.enqueue(10);

        for(int i=0;i<4;i++){
            System.out.println(queue_test.dequeue().entity+" has been removed");
        }

        System.out.println("This is the first element "+queue_test.front());
        System.out.println("This is the last element "+queue_test.back());
        queue_test.enqueue(-2);
        System.out.println("Size of the queue is "+queue_test.size());
    }
}
