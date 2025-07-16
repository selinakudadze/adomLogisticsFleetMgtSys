package datastructures;


public class LinkedList<T> {
    public Node<T> head;
    private int size;

    // constructor of the linked list class
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    // method to add a new node at the end of the list
    public void add(T entity) {
        Node<T> newNode = new Node<T>(entity);

        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.nextNode != null) {
                current = current.nextNode;
            }
            current.nextNode = newNode;
        }
        size++;
    }

    // method to remove a node with a specific entity
    public boolean remove(T entity) {
        if (head == null) return false;
        if (head.entity.equals(entity)) {
            head = head.nextNode;
            size--;
            return true;
        }
        Node<T> current = head;
        while (current.nextNode != null) {
            if (current.nextNode.entity.equals(entity)) {
                current.nextNode = current.nextNode.nextNode;
                size--;
                return true;
            }
            current = current.nextNode;
        }
        return false;
    }

    // method to search for a node with specific entity
    public boolean search(T entity) {
        Node<T> current = head;
        while (current != null) {
            if (current.entity.equals(entity)) {
                return true;
            }
            current = current.nextNode;
        }
        return false;
    }

    //method to get the size of the list
    public int size() {
        return size;
    }

    // this method returns an entity at a specific index
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.nextNode;
        }
        return current.entity;
    }

    // Method to convert the linked list to a string array
    public String[] toStringArray() {
        String[] array = new String[size];
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.entity.toString();
            current = current.nextNode;
        }
        return array;
    }

    //This method clears the linked list
    public void clear() {
        head = null;
        size = 0;
    }

    // This method checks if the list contains all elements of another collection
    public boolean containsAll(LinkedList<T> other) {
        if (other == null || other.size() == 0) return true;
        Node<T> currentOther = other.head;
        while (currentOther != null) {
            if (!this.search(currentOther.entity)) {
                return false;
            }
            currentOther = currentOther.nextNode;
        }
        return true;
    }
}


// Constructor (LinkedList) --> Time Complexity: O(1)
// add(T entity) --> Time Complexity: O(n)
// remove(T entity) --> Time Complexity: O(n)
// search(T entity) --> Time Complexity: O(n)
// size() --> Time Complexity: O(1)
// get(int index) --> Time Complexity: O(n)
// toStringArray() --> Time Complexity: O(n)
// clear() --> Time Complexity: O(1)
// containsAll(LinkedList<t> other)</t> --> Time Complexity: $ O(n . m)
// The implementation is space-efficient, using O(n) for the list itself and
// O(1) for most operations, except toStringArray which requires O(n) temporary space.