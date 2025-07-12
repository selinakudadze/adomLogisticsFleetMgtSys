package datastructures;

public class Stack<T>{


        // Top node of the stack
        private Node<T> top;

        // Number of elements in the stack
        private int size;

        // Initializes an empty stack
        public Stack() {
            top = null;
            size = 0;
        }

        // Pushes an item onto the top of the stack
        public void push(T item) {
            Node<T> node = new Node<>(item);   // Create a new node
            node.nextNode = top;               // Link the new node to the current top
            top = node;                        // Set the new node as the top
            size++;                            // Increment size
        }

        // Removes and returns the item at the top of the stack
        public T pop() {
            if (isEmpty())
                throw new RuntimeException("Stack is empty");

            T item = top.entity;     // Get the data from the top node
            top = top.nextNode;      // Move the top to the next node
            size--;                  // Decrement size
            return item;             // Return popped item
        }

        // Returns the item at the top of the stack without removing it
        public T peek() {
            if (isEmpty())
                throw new RuntimeException("Stack is empty");

            return top.entity;
        }

        // Checks whether the stack is empty
        public boolean isEmpty() {
            return top == null;
        }

        // Returns the number of elements in the stack
        public int size() {
            return size;
        }
        
    }


