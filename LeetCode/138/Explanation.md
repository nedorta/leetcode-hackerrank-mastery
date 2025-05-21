# Copy List with Random Pointer - Solution Explanation

## 1. Problem Statement

You have a singly‐linked list where each node has two pointers:
- `next`: points to the next node in sequence, or null
- `random`: points to any node in the list (or null)

Task: Return a deep copy of this list: create brand-new nodes so that both `next` and `random` pointers in the copy replicate the original structure, but no pointer in the copy refers back to an original node.

## 2. Examples

| Example | Input | Output |
|---------|--------|--------|
| 1 | `[[7,null],[13,0],[11,4],[10,2],[1,0]]` | `[[7,null],[13,0],[11,4],[10,2],[1,0]]` |
| 2 | `[[1,1],[2,1]]` | `[[1,1],[2,1]]` |
| 3 | `[[3,null],[3,0],[3,null]]` | `[[3,null],[3,0],[3,null]]` |

Here each pair `[val, random_index]` shows the node's value and the index its random pointer refers to (or null).

## 3. Three‐Pass Weaving Approach

1. **Weave clones**: For each original node X, create a clone X' and insert it immediately after X
2. **Set randoms**: Traverse the interleaved list; for each original X, set X'.random = X.random.next (if X.random exists)
3. **Unweave**: Separate the interleaved list into the original list and the cloned list by fixing next pointers

This uses O(n) time and O(1) extra space (aside from the output).

## 4. Implementation Details

### Pass 1: Weave cloned nodes
```java
curr = head;
while (curr != null) {
    clone = new Node(curr.val);
    clone.next = curr.next;
    curr.next = clone;
    curr = clone.next;
}
```
- Insert each new node X' right after its original X so we can easily reference neighbors

### Pass 2: Set random pointers
```java
curr = head;
while (curr != null) {
    if (curr.random != null) {
        curr.next.random = curr.random.next;
    }
    curr = curr.next.next;
}
```
- For original X, its clone is X.next
- Similarly, X.random.next is the clone of X.random

### Pass 3: Unweave lists
```java
curr = head;
newHead = head.next;
copy = newHead;
while (curr != null) {
    curr.next = curr.next.next;
    if (copy.next != null) {
        copy.next = copy.next.next;
    }
    curr = curr.next;
    copy = copy.next;
}
```
- Restore the original next pointers
- Extract the cloned list by skipping every other node

### Visual Example
Original list: A → B → C (with random pointers)
1. After weaving: A → A' → B → B' → C → C'
2. After setting randoms: A ⇢ C, A' ⇢ C', B ⇢ A, B' ⇢ A', etc.
3. After unweaving:
   - Original: A → B → C
   - Clone: A' → B' → C'

## 5. Complexity Analysis

### Time Complexity: O(n)
- Three linear scans of the list:
  1. Weaving clones
  2. Setting random pointers
  3. Unweaving lists

### Space Complexity: O(1)
- Extra space (aside from the new nodes themselves):
  - Only a few pointer variables
  - No additional data structures needed

## 6. Key Takeaway

By interleaving cloned nodes with the originals, you can set up and copy both next and arbitrary random pointers in constant extra space, then unweave to split into the deep‐copied list and restore the original. This is a classic O(n) time, O(1) extra‐space solution. 