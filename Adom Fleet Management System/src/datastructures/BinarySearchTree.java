package datastructures;
// BinarySearchTree stores and organizes elements in sorted order
public class BinarySearchTree<T extends Comparable<T>> {

    private TreeNode<T> root; // The top node of the tree
    private int size = 0;     // Number of elements in the tree

    // Adds a new item to the tree
    public void insert(T item) {
        TreeNode<T> newNode = new TreeNode<>(item);

        if (root == null) {
            root = newNode; // If tree is empty, make this the root
        } else {
            TreeNode<T> current = root;
            TreeNode<T> parent;

            // Find the correct place to insert the new node
            while (true) {
                parent = current;

                // If item is smaller, go left
                if (item.compareTo(current.data) < 0) {
                    current = current.left;

                    if (current == null) {
                        parent.left = newNode;
                        break;
                    }
                } else {
                    // If item is larger or equal, go right
                    current = current.right;

                    if (current == null) {
                        parent.right = newNode;
                        break;
                    }
                }
            }
        }

        size++; // Increase count after adding new node
    }

    // Returns the number of items in the tree
    public int getSize() {
        return size;
    }

    // Returns all elements in sorted order (in-order)
    public T[] getAllElements(T[] array) {
        fillArray(root, array, new Counter());
        return array;
    }

    // Helper class to track index while filling the array
    private class Counter {
        int index = 0;
    }

    // In-order traversal to add nodes to array
    private void fillArray(TreeNode<T> node, T[] array, Counter counter) {
        if (node != null) {
            fillArray(node.left, array, counter);       // Visit left
            array[counter.index++] = node.data;         // Add current
            fillArray(node.right, array, counter);      // Visit right
        }
    }
}

// Constructor (BinarySearchTree) --> Time Complexity: O(1)
// insert(T item) --> Time Complexity: O(h), where h is the height of the tree
// getSize() --> Time Complexity: O(1)
// getAllElements(T[] array) --> Time Complexity: O(n)
// fillArray(TreeNode<t> node, T[] array, Counter counter)</t> (Helper Method)
// --> Time Complexity: O(n) for the entire tree

// Space Complexity --> Overall Structure: O(n)

// Efficient Operations: getSize() is O(1)
// getAllElements and fillArray use O(n)
// time but benefit from sorted output due to in-order traversal
// The implementation is space-efficient at O(n) for storage, with
// temporary space scaling with tree height (O(log n) balanced, O(n) unbalanced).