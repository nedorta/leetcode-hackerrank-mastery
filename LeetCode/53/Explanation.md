# Maximum Subarray - Solution Explanation

## 1. Problem Statement

You're given an integer array `nums`.
Task: Find the contiguous subarray (containing at least one number) which has the largest sum, and return its sum.

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | [-2,1,-3,4,-1,2,1,-5,4] | 6 | The subarray [4,-1,2,1] has sum 6. |
| 2 | [1] | 1 | Only one element. |
| 3 | [5,4,-1,7,8] | 23 | The whole array sums to 23. |

## 3. Kadane's Algorithm (O(n) Time)

### Key Idea
1. Maintain two variables as you scan the array once:
   - `maxEndingHere`: maximum sum of a subarray ending at the current position
   - `maxSoFar`: maximum sum seen so far over all subarrays

2. At each element x, you either:
   - Extend the previous subarray (`maxEndingHere + x`)
   - Start fresh at x (`x`)

3. Update formulas:
   ```
   maxEndingHere = max(x, maxEndingHere + x)
   maxSoFar      = max(maxSoFar, maxEndingHere)
   ```

## 4. Code Walkthrough

```java
public class Solution {
    /**
     * Returns the largest sum of any contiguous subarray.
     *
     * @param nums an array of integers
     * @return     maximum subarray sum
     */
    public int maxSubArray(int[] nums) {
        // Initialize both to the first element.
        int maxEndingHere = nums[0];
        int maxSoFar      = nums[0];

        // Process the rest of the array
        for (int i = 1; i < nums.length; i++) {
            int x = nums[i];

            // 1) Best subarray ending at i is either x alone, or extend the previous
            maxEndingHere = Math.max(x, maxEndingHere + x);

            // 2) Update overall maximum
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }
}
```

### Code Section Breakdown

| Section | Purpose |
|---------|---------|
| Initial values | Start both variables at the first element as our initial subarray |
| Main loop | Iterate through each remaining element exactly once |
| maxEndingHere update | Decide whether to start a new subarray at x or extend the previous one |
| maxSoFar update | Track the best subarray sum found so far across all positions |

## 5. Complexity Analysis

### Time: O(n)
- Single pass through the array
- Each element processed exactly once
- All operations within the loop are O(1)

### Space: O(1)
- Only two extra integer variables needed
- Space usage independent of input size

## 6. Key Takeaway

Kadane's algorithm efficiently finds the maximum-sum contiguous subarray by keeping a running best "ending here" and updating a global maximum, all in linear time and constant space. The key insight is that at each position, we only need to decide between starting fresh or extending the previous best subarray. 