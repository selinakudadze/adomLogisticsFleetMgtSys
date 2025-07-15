package datastructures;

public class Node<T>{

    /** This class has attributes,
     * entity( which takes in objects of the various classes-Vehicles.txt,Order and Drivers)
     * and nextNode
     */
    public T entity;
    public Node<T> nextNode;

    public Node(T entity) {
        this.entity = entity;
        this.nextNode = null;
    }



    public static void main(String[] args) {
        Node<String> node= new Node("Hilfeeee");
        System.out.println(node.entity);
    }


}
