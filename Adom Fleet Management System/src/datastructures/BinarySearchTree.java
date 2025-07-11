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
