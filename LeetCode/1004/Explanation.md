# Max Consecutive Ones III - Detailed Explanation

## 1. Problem Statement

You are given:
- A binary array `nums` (elements are only 0 or 1)
- An integer `k`, the maximum number of zeros you may flip to ones

Goal: After flipping at most k zeros, what is the maximum length of a subarray consisting entirely of 1s?

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2 | 6 | Flip two zeros in the middle ⇒ subarray [1,1,1,0,0,1,1,1,1,1,1] has six consecutive 1's. |
| 2 | nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3 | 10 | By flipping three zeros, you obtain ten consecutive 1's in the merged window. |

## 3. Brute-Force Thought (O(n²) Time)

One naive approach is to try every possible subarray:

```java
maxLen = 0
for i from 0 to n−1:
  zeros = 0
  for j from i to n−1:
    if nums[j] == 0: zeros++
    if zeros > k: break
    maxLen = max(maxLen, j − i + 1)
return maxLen
```

- Time: O(n²) in worst case.
- Why it fails: With n up to 10⁵, O(n²) is far too slow.

## 4. Optimal O(n) Approach: Sliding Window

### Key Insight

- Maintain a window [left..right] that contains at most k zeros
- As you expand right, count zeros
- If you exceed k, move left forward until you're back to ≤ k zeros
- At each step, the window size right − left + 1 is a candidate answer

## 5. Walkthrough of the Code

```java
public class Solution {
    public int longestOnes(int[] nums, int k) {
        int left = 0;          // start of the window
        int zerosCount = 0;    // number of zeros in [left..right]
        int maxLen = 0;        // best window size seen

        // Expand window to the right one element at a time
        for (int right = 0; right < nums.length; right++) {
            // Include the new element in zerosCount if it's 0
            if (nums[right] == 0) {
                zerosCount++;
            }

            // If we've exceeded k zeros, shrink from the left
            while (zerosCount > k) {
                if (nums[left] == 0) {
                    zerosCount--;    // dropping a zero from the window
                }
                left++;              // move left boundary right
            }

            // Now [left..right] has ≤ k zeros ⇒ can be all 1's by flipping
            int windowSize = right - left + 1;
            if (windowSize > maxLen) {
                maxLen = windowSize;
            }
        }

        return maxLen;
    }
}
```

| Code Section | Purpose |
|--------------|---------|
| `int left = 0, zerosCount = 0;` | Initialize left boundary and zero counter. |
| `for (right = 0; _; right++)` | Expand window's right end through the array. |
| `if (nums[right] == 0) zerosCount++` | Count zeros inside the window. |
| `while (zerosCount > k)` | If too many zeros, move `left` right until zerosCount ≤ k. |
| `int windowSize = right-left+1` | Current window length (all-1's subarray after flips). |
| `maxLen = max(maxLen, windowSize)` | Track the maximum window seen. |
| `return maxLen;` | Final answer: largest possible consecutive 1's after ≤ k flips. |

## 6. Complexity Analysis

- Time: O(n)
  - Each element enters the window once (right pointer).
  - Each element leaves the window at most once (left pointer).

- Space: O(1) extra
  - Only three integer variables (left, zerosCount, maxLen).

## 7. Key Takeaway

This problem is a classic variable-size sliding window:
- Expand the window until you violate the constraint (more than k zeros).
- Contract from the left until you satisfy the constraint again.
- Track the maximum window size throughout.

Whenever you need the "longest subarray satisfying X," sliding window is often the way to get O(n) time and O(1) extra space. 