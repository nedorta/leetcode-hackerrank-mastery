# Add Two Numbers - Solution Explanation

## 1. Problem Statement

You're given two non-empty singly‐linked lists, `l1` and `l2`, each representing a non-negative integer in reverse digit order. Each node holds a single digit [0–9].

Task: Compute their sum and return it as a new linked list in the same reverse‐order format. No input list has leading zeros except the number zero itself.

## 2. Concrete Examples

| Example | l1 | l2 | Output | Explanation |
|---------|----|----|--------|-------------|
| 1 | [2→4→3] | [5→6→4] | [7→0→8] | 342 + 465 = 807 |
| 2 | [0] | [0] | [0] | 0 + 0 = 0 |
| 3 | [9→9→9→9→9→9→9] | [9→9→9→9] | [8→9→9→9→0→0→0→1] | 9,999,999 + 9,999 = 10,009,998 |

## 3. Brute-Force Thought (Why It Fails)

A naive approach might be to:
1. Convert each linked list to an integer
2. Add the integers
3. Convert sum back to linked list

Problems:
- Integer overflow for large numbers
- Multiple passes over the lists
- Extra space for number conversion
- Doesn't utilize the natural digit-by-digit structure

## 4. Optimal Approach: Grade-School Addition with Carry

### 4.1. Key Idea

1. Initialize a dummy head (`ListNode dummy = new ListNode(0)`) and a `carry = 0`.

2. Traverse the two lists simultaneously:
   - Let `x = (l1 != null) ? l1.val : 0`, `y = (l2 != null) ? l2.val : 0`
   - Compute `sum = x + y + carry`
   - New `digit = sum % 10`; update `carry = sum / 10`
   - Append a new node with the new digit to the result list
   - Advance `l1` and `l2` if non-null

3. After the loop, if `carry > 0`, append a final node with that carry.

4. Return `dummy.next` as the head of the summed list.

## 5. Walkthrough of the Code

```java
ListNode dummy = new ListNode(0);
ListNode curr  = dummy;
int carry      = 0;

while (l1 != null || l2 != null || carry != 0) {
    int x = (l1 != null) ? l1.val : 0;
    int y = (l2 != null) ? l2.val : 0;
    int sum = x + y + carry;
    carry = sum / 10;              // new carry for next digit
    int digit = sum % 10;          // current digit

    curr.next = new ListNode(digit); // append digit
    curr = curr.next;                // move tail

    if (l1 != null) l1 = l1.next;   // advance inputs
    if (l2 != null) l2 = l2.next;
}

return dummy.next;  // head of the summed list
```

### Code Section Breakdown

| Code Section | Purpose |
|--------------|---------|
| ListNode dummy = new ListNode(0); | Dummy head to simplify list construction. |
| while (...) | Continue while at least one list has digits or a carry remains. |
| sum = x + y + carry | Add digits from both lists plus any previous carry. |
| carry = sum / 10; digit = sum % 10 | Compute new carry (0 or 1) and the digit to store (0–9). |
| curr.next = new ListNode(digit) | Append the resulting digit to the output list. |
| if (l1 != null) l1 = l1.next; | Advance each input list only if it hasn't ended. |
| return dummy.next; | The true head of the result list follows the dummy node. |

## 6. Complexity Analysis

### Time: O(max(m, n))
- Where m and n are the lengths of l1 and l2
- We traverse each node at most once
- Each operation (addition, modulo, division) is O(1)

### Space: O(max(m, n))
- For the new result list
- Plus O(1) extra variables (carry, curr, etc.)
- No recursion, so no call stack space

## 7. Key Takeaway

- Simulating elementary addition with a carry yields an elegant solution
- Dummy head technique simplifies linked list construction
- One-pass solution achieves optimal linear time and space
- The approach generalizes to other digit-by-digit number operations
- Handle edge cases (different lengths, carry) naturally in the main loop 