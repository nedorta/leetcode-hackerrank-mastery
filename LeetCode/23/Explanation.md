# Merge k Sorted Lists - Solution Explanation

## 1. Problem Statement

You're given an array `lists` of k singly-linked lists. Each list is sorted in ascending order.
Task: Merge all k lists into one sorted linked list and return its head.

## 2. Examples

| Example | Input | Output | Explanation |
|---------|--------|--------|-------------|
| 1 | `[[1→4→5], [1→3→4], [2→6]]` | `1→1→2→3→4→4→5→6` | Merging the three sorted chains yields the combined sorted sequence |
| 2 | `[]` | `[]` | No lists to merge—result is empty |
| 3 | `[[]]` | `[]` | One empty list—still empty after merge |

## 3. Min-Heap (Priority Queue) Approach

### Key Idea

- Use a min-heap (priority queue) of size up to k to always extract the smallest current head among the k lists
- Initialize the heap with each non-null list head
- Repeat:
  1. Pop the smallest node n from the heap
  2. Append n to the merged list
  3. If n.next exists, push n.next into the heap
- Stop when the heap is empty

This ensures at each step you add the globally smallest available node, preserving sorted order.

## 4. Implementation Details

### Step 1: Initialize Min-Heap
```java
// Build a min-heap of all k initial heads
PriorityQueue<ListNode> heap = new PriorityQueue<>(Comparator.comparingInt(n -> n.val));
for (ListNode head : lists) {
    if (head != null) heap.offer(head);
}

// Prepare a dummy head to build the result
ListNode dummy = new ListNode(0), tail = dummy;
```

### Step 2: Build Merged List
```java
// While there are nodes in the heap:
while (!heap.isEmpty()) {
    ListNode node = heap.poll(); // smallest current node
    tail.next = node;            // append it
    tail = tail.next;
    if (node.next != null) {
        heap.offer(node.next);   // push the next from the same list
    }
}

// Return the merged list, skipping dummy
return dummy.next;
```

### Code Breakdown

| Code Section | Explanation |
|-------------|-------------|
| `heap.offer(head)` | Add each list's starting node to the min-heap (if non-null) |
| `node = heap.poll()` | Remove the node with the smallest val across all k heads |
| `tail.next = node; tail = tail.next;` | Append that node to our merged result |
| `if (node.next != null) heap.offer(...)` | After consuming one node from a list, advance in that list by pushing its next node into heap |

## 5. Alternative Approach: Divide & Conquer

```java
public class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        return mergeRange(lists, 0, lists.length - 1);
    }

    // Recursively merge lists[l..r]
    private ListNode mergeRange(ListNode[] lists, int l, int r) {
        if (l == r) return lists[l];
        int mid = l + (r - l) / 2;
        ListNode left  = mergeRange(lists, l,     mid);
        ListNode right = mergeRange(lists, mid+1, r);
        return mergeTwo(left, right);
    }

    // Standard in‐place merge of two sorted lists, O(m+n) time, O(1) extra space
    private ListNode mergeTwo(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0), tail = dummy;
        while (a != null && b != null) {
            if (a.val < b.val) {
                tail.next = a;  a = a.next;
            } else {
                tail.next = b;  b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }
}
```

## 6. Complexity Analysis

### Comparison of Approaches

| Approach | Time | Extra Space |
|----------|------|-------------|
| Min-heap (priority queue) | O(N log k) | O(k) |
| Divide & conquer | O(N log k) | O(1)* |

*Plus O(log k) recursion stack

#### Time Complexity
- Both approaches are optimal at O(N log k)
- N = total number of nodes across all lists
- k = number of lists

#### Space Complexity
- Heap approach: O(k) for the priority queue
- Divide & conquer: O(1) list pointers + O(log k) stack frames

## 7. Key Takeaway

Merging k sorted sequences can be done efficiently in two ways:
1. Using a min-heap to always select the next smallest element in O(log k) time per element
2. Using divide & conquer to recursively merge pairs of lists

Both achieve optimal O(N log k) time, with divide & conquer using less extra space but being more complex to implement. 