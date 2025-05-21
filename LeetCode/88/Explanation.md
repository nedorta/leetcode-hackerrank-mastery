# Merge Sorted Array - Detailed Explanation

## 1. Problem Statement

You are given two integer arrays, nums1 and nums2, both already sorted in non-decreasing order.

- nums1 has length m + n: its first m entries hold real data, and its last n entries are zeros (space reserved for merging).
- nums2 has length n, filled with its own sorted data.

Goal: Merge nums2 into nums1 in-place so that nums1 becomes a single sorted array of length m + n.

You must not return a new array—modify nums1 directly.

## 2. Concrete Examples

| Example | Input | After Merge |
|---------|--------|-------------|
| 1 | nums1 = [1,2,3,0,0,0], m = 3<br>nums2 = [2,5,6], n = 3 | [1,2,2,3,5,6] |
| 2 | nums1 = [1], m = 1<br>nums2 = [], n = 0 | [1] |
| 3 | nums1 = [0], m = 0<br>nums2 = [1], n = 1 | [1] |

## 3. Brute-Force Thought (and Why It Fails)

A naive approach is to:
1. Copy the first m elements of nums1 into a temporary array.
2. Merge that temp array and nums2 using the standard two-pointer merge technique into a brand-new array.
3. Copy the result back into nums1.

- Time: O(m + n) for the merge, but plus O(m + n) for the extra copies ⇒ still O(m + n).
- Space: O(m + n) extra for the temp and the new array.

Why we prefer in-place? The problem explicitly forbids returning a new array, and we want O(1) extra space.

## 4. Optimal O(m + n) In-Place Merge

### Key Idea

By merging from the back of nums1, we avoid overwriting any unprocessed elements:

- p1 points at the last actual element of the original nums1 (m-1).
- p2 points at the last element of nums2 (n-1).
- write points at the very end of nums1's capacity (m + n - 1).

At each step, compare nums1[p1] vs. nums2[p2], copy the larger into nums1[write], and decrement the corresponding pointer. Continue until p2 runs out—any leftovers in nums1 are already in place.

## 5. Walkthrough of the Code

```java
public class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 1) Initialize pointers:
        int p1    = m - 1;         // last real element in nums1
        int p2    = n - 1;         // last element in nums2
        int write = m + n - 1;     // end of nums1's total buffer

        // 2) Merge until nums2 is exhausted:
        while (p2 >= 0) {
            // If nums1 still has items and its current is bigger:
            if (p1 >= 0 && nums1[p1] > nums2[p2]) {
                nums1[write] = nums1[p1];  // copy from nums1
                p1--;                       // move nums1 pointer left
            } else {
                nums1[write] = nums2[p2];  // else copy from nums2
                p2--;                       // move nums2 pointer left
            }
            write--;  // move write pointer left
        }
        // 3) When p2 < 0, all of nums2 has been merged.
        //    Any remaining nums1[0..p1] are already in correct order.
    }
}
```

| Step | What Happens |
|------|--------------|
| Initialize | p1=m-1, p2=n-1, write=m+n-1 |
| Compare | nums1[p1] vs. nums2[p2] |
| Copy larger | Place the larger value at nums1[write] |
| Move pointers | Decrement whichever source pointer (p1 or p2) and always decrement write |
| Finish | Once p2 < 0, all elements from nums2 are merged; leftover nums1 elements (if any) are already in front |

## 6. Complexity Analysis

- Time: O(m + n) — each element from nums1 and nums2 is moved exactly once.
- Space: O(1) extra — only three integer pointers, regardless of input sizes.

## 7. Key Takeaway

- Merging from the back allows in-place merging without overwriting unmerged data.
- This is the same "merge" step used in merge sort, adapted to an array that has extra buffer space at the end.
- The two-pointer technique makes it both time- and space-optimal: O(m + n) time and O(1) extra space. 