# Search Insert Position - Solution Explanation

## 1. Problem Statement

You have:
- A sorted array of distinct integers `nums`
- A target value

Task:
- If target exists in nums, return its index
- Otherwise, return the index where target would be inserted to keep nums sorted

You must achieve O(log n) runtime, which suggests binary search.

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|--------|--------|-------------|
| 1 | nums = [1,3,5,6], target = 5 | 2 | 5 is found at index 2. |
| 2 | nums = [1,3,5,6], target = 2 | 1 | 2 isn't present; it would fit between 1 and 3 ⇒ index 1. |
| 3 | nums = [1,3,5,6], target = 7 | 4 | 7 is larger than all elements; would be inserted at end ⇒ 4. |
| 4 | nums = [1,3,5,6], target = 0 | 0 | 0 is smaller than all elements; would go at start ⇒ 0. |

## 3. Brute-Force Thought (O(n) Time)

You could scan left-to-right until you find target or a larger element:

```java
for (int i = 0; i < nums.length; i++) {
    if (nums[i] >= target) {
        return i;
    }
}
return nums.length;
```

- Time Complexity: O(n)
- Why we improve: The problem requires O(log n), so we need binary search

## 4. Optimal O(log n) Approach: Binary Search

### Key Idea

Maintain two pointers, left and right, that bracket the search space:

1. Compute `mid = left + (right − left) / 2`
2. Compare nums[mid] with target:
   - If equal ⇒ return mid
   - If nums[mid] < target ⇒ move left to mid + 1
   - If nums[mid] > target ⇒ move right to mid − 1
3. Repeat while left ≤ right

If you exit the loop without finding target, left is the correct insertion index.

## 5. Why left Is the Insertion Point

When the loop ends, we have:
- All indices < left contain values < target
- All indices ≥ left contain values > target (or the array end)

Thus, inserting at left maintains sorted order.

## 6. Complexity Analysis

- Time: O(log n) — each step halves the search range
- Space: O(1) extra — only pointers and a mid-variable

## 7. Key Takeaway

- Binary search is the go-to for any "search/insert in sorted array" problem requiring O(log n) time
- Remember the loop invariant: upon exit, left is the smallest index where nums[left] ≥ target, making it the ideal insertion spot if an exact match isn't found 