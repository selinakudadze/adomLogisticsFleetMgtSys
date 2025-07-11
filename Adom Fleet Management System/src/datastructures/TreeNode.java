package datastructures;
// TreeNode holds data and links to left and right child nodes
public class TreeNode<T> {

    public T data;           // The actual data stored in the node
    public TreeNode<T> left; // Left child node (smaller values)
    public TreeNode<T> right;// Right child node (larger values)

    // Constructor to create a node with given data
    public TreeNode(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
