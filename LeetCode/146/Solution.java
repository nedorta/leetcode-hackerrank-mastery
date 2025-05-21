public class LRUCache {

  // Doubly-linked list node to hold key-value pairs
  private static class Node {
      int key, value;
      Node prev, next;
      Node(int k, int v) { key = k; value = v; }
  }

  private final int capacity;            // Maximum number of items in the cache
  private final Map<Integer, Node> map;  // Map from key to its node in the list
  private final Node head, tail;         // Sentinels for the doubly-linked list

  /**
   * Initialize the LRU cache with positive size capacity.
   */
  public LRUCache(int capacity) {
      this.capacity = capacity;
      this.map = new HashMap<>(capacity);

      // Create dummy head & tail nodes to avoid null checks
      head = new Node(-1, -1);
      tail = new Node(-1, -1);
      head.next = tail;
      tail.prev = head;
  }

  /**
   * Return the value of the key if it exists, otherwise return -1.
   * Also marks the key as most recently used.
   */
  public int get(int key) {
      Node node = map.get(key);
      if (node == null) {
          return -1;              // Key not in cache
      }
      // Move this node to the front (most recently used)
      remove(node);
      insertAtFront(node);
      return node.value;
  }

  /**
   * Update the value of the key if it exists.
   * Otherwise, add the key-value pair to the cache.
   * If capacity is exceeded, evict the least recently used key.
   */
  public void put(int key, int value) {
      Node node = map.get(key);
      if (node != null) {
          // Key exists: update value and move to front
          node.value = value;
          remove(node);
          insertAtFront(node);
      } else {
          // Key not present: create new node
          Node newNode = new Node(key, value);
          map.put(key, newNode);
          insertAtFront(newNode);

          // If over capacity, remove the LRU item (node before tail)
          if (map.size() > capacity) {
              Node lru = tail.prev;   // least recently used
              remove(lru);
              map.remove(lru.key);
          }
      }
  }

  // Remove a node from its current position in the list
  private void remove(Node node) {
      node.prev.next = node.next;
      node.next.prev = node.prev;
  }

  // Insert a node right after head (front = most recently used)
  private void insertAtFront(Node node) {
      node.next = head.next;
      node.prev = head;
      head.next.prev = node;
      head.next = node;
  }
}