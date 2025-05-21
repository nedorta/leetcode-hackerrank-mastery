# LRU Cache - Solution Explanation

## 1. Problem Statement

Design a data structure that implements a Least Recently Used (LRU) cache with:

    LRUCache(int capacity): Initialize with positive size capacity.

    int get(int key): If key exists, return its value and mark it most recently used; otherwise return -1.

    void put(int key, int value): Insert or update the key-value pair. If inserting causes size to exceed capacity, evict the least recently used item first.

Both operations must run in O(1) time on average.

## 2. Concrete Example

LRUCache cache = new LRUCache(2);
cache.put(1, 1);      // cache = {1=1}
cache.put(2, 2);      // cache = {1=1, 2=2}
cache.get(1);         // returns 1, cache order becomes {2=2, 1=1}
cache.put(3, 3);      // capacity exceeded, evict LRU key=2 ⇒ cache = {1=1, 3=3}
cache.get(2);         // returns -1 (not found)
cache.put(4, 4);      // evict LRU key=1 ⇒ cache = {3=3, 4=4}
cache.get(1);         // returns -1
cache.get(3);         // returns 3
cache.get(4);         // returns 4

## 3. Brute-Force Thought (Why It Fails)

You might try:

    A list to track usage order, and a hashmap for key→value.

    On each get, scan the list to move the key to "most recent" end (O(n)).

    On put, check size, evict front of list if needed (O(1)), but updates cost O(n).

This yields O(n) operations in the worst case, violating the O(1) requirement.

## 4. Optimal O(1) Approach: HashMap + Doubly-Linked List

### Key Idea

    HashMap (key → Node) gives O(1) access to any cached item.

    A doubly-linked list maintains usage order:

        Most recently used at the front (right after head sentinel).

        Least recently used at the back (right before tail sentinel).

    On get or put (update), move the accessed/updated node to the front in O(1).

    On overflow, remove the node at the back (LRU) in O(1) and delete its map entry.

## 5. Walkthrough of the Code

### Initialization

```java
this.capacity = capacity;
map = new HashMap<>(capacity);
head = new Node(-1, -1);
tail = new Node(-1, -1);
head.next = tail; 
tail.prev = head;
```

    Create an empty map.

    Build an "empty" doubly-linked list with dummy head and tail to simplify edge cases.

### get(key)

```java
Node node = map.get(key);
if (node == null) return -1;
remove(node);
insertAtFront(node);
return node.value;
```

    Lookup the node in O(1).

    If found, remove it from its position and insert at front, marking it most recently used.

    Return its value.

### put(key, value)

```java
Node node = map.get(key);
if (node != null) {
  // Update existing
  node.value = value;
  remove(node);
  insertAtFront(node);
} else {
  // Insert new
  Node newNode = new Node(key, value);
  map.put(key, newNode);
  insertAtFront(newNode);
  if (map.size() > capacity) {
    Node lru = tail.prev;
    remove(lru);
    map.remove(lru.key);
  }
}
```

        If the key exists, update its value and move it to front.

        Otherwise, create a new node, put in map, insert at front.

        If over capacity, evict the least recently used (tail.prev).

### Helper Methods

        remove(node): Splice out in O(1).

        insertAtFront(node): Splice in right after head in O(1).

## 6. Complexity Analysis

    Time:

        get and put each perform a fixed number of HashMap ops (O(1)) and a fixed number of doubly-linked-list pointer updates (O(1)).

        ⇒ O(1) average time per operation.

    Space:

        O(capacity) extra to store the HashMap entries and linked-list nodes.

## 7. Key Takeaway

By combining a HashMap for direct key lookup with a doubly-linked list for tracking LRU order, you can satisfy the O(1) time requirement for both retrieval and eviction operations in an LRU cache. 