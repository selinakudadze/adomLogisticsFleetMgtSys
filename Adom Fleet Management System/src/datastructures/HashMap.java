package datastructures;

public class HashMap<K, V> {
    private int capacity = 16;
    private int size = 0;
    private HashNode<K, V>[] buckets;

    public HashMap() {
        buckets = new HashNode[capacity];
    }

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value) {
        int index = getBucketIndex(key);
        HashNode<K, V> head = buckets[index];

        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value; // Update value
                return;
            }
            head = head.next;
        }

        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
    }

    public V get(K key) {
        int index = getBucketIndex(key);
        HashNode<K, V> head = buckets[index];

        while (head != null) {
            if (head.key.equals(key)) return head.value;
            head = head.next;
        }

        return null;
    }

    public boolean remove(K key) {
        int index = getBucketIndex(key);
        HashNode<K, V> head = buckets[index];
        HashNode<K, V> prev = null;

        while (head != null) {
            if (head.key.equals(key)) {
                if (prev != null) {
                    prev.next = head.next;
                } else {
                    buckets[index] = head.next;
                }
                size--;
                return true;
            }
            prev = head;
            head = head.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }
    public HashNode<K, V>[] getBuckets(){
        return this.buckets;
    }

    public void printAll() {
        for (int i = 0; i < capacity; i++) {
            HashNode<K, V> current = buckets[i];
            while (current != null) {
                System.out.println(current.value);  // toString() in Order will be used
                System.out.println();
                current = current.next;
            }
        }
    }
}
