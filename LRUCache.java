
// Design a cache system with LRU eviction policy using OOP.
//1st february
import java.util.HashMap;

// Node class for the doubly linked list
class Node {
    int key;
    int value;
    Node prev;
    Node next;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

// LRU Cache class
public class LRUCache {
    private final int capacity;
    private final HashMap<Integer, Node> cache;
    private Node head; // Most recently used
    private Node tail; // Least recently used

    // Constructor to initialize the cache
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node(-1, -1); // Dummy head
        this.tail = new Node(-1, -1); // Dummy tail
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    // Get the value for a given key
    public int get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            // Move the accessed node to the front (most recently used)
            removeNode(node);
            addToFront(node);
            return node.value;
        }
        return -1; // Key not found
    }

    // Put a key-value pair into the cache
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            // Update the value and move the node to the front
            Node node = cache.get(key);
            node.value = value;
            removeNode(node);
            addToFront(node);
        } else {
            if (cache.size() >= capacity) {
                // Evict the least recently used node (tail.prev)
                Node lruNode = tail.prev;
                removeNode(lruNode);
                cache.remove(lruNode.key);
            }
            // Add the new node to the cache and the front of the list
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToFront(newNode);
        }
    }

    // Helper method to remove a node from the linked list
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Helper method to add a node to the front of the linked list
    private void addToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    // Main method to test the LRU Cache
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 1); // Cache is {1=1}
        cache.put(2, 2); // Cache is {1=1, 2=2}
        System.out.println(cache.get(1)); // Returns 1 (most recently used)
        cache.put(3, 3); // Evicts key 2, cache is {1=1, 3=3}
        System.out.println(cache.get(2)); // Returns -1 (not found)
        cache.put(4, 4); // Evicts key 1, cache is {4=4, 3=3}
        System.out.println(cache.get(1)); // Returns -1 (not found)
        System.out.println(cache.get(3)); // Returns 3
        System.out.println(cache.get(4)); // Returns 4
    }
}