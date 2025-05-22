# Trapping Rain Water - Detailed Explanation

## 1. Problem Statement
You're given an array `height` of n non-negative integers, where each value represents the elevation of a bar of width 1.
Task: Compute how much water is trapped after raining—that is, the total units of water that can sit on top of the bars.

## 2. Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | [0,1,0,2,1,0,1,3,2,1,2,1] | 6 | Between the bars, 6 units of water accumulate (visualize valleys between tall bars). |
| 2 | [4,2,0,3,2,5] | 9 | Water pools in the dips: 2 above index 1, 4 above index 2, 1 above index 4, and 2 above index 5 = 9 total. |

## 3. Solution Approaches

### Brute-Force Approach (O(n²) Time)
```java
int trapped = 0;
for (int i = 0; i < n; i++) {
    // 1) Find the highest bar to the left of i
    int leftMax = 0;
    for (int j = 0; j <= i; j++) leftMax = Math.max(leftMax, height[j]);
    // 2) Find the highest bar to the right of i
    int rightMax = 0;
    for (int j = i; j < n; j++) rightMax = Math.max(rightMax, height[j]);
    // 3) Water at i = min(leftMax, rightMax) - height[i]
    trapped += Math.min(leftMax, rightMax) - height[i];
}
return trapped;
```

**Analysis:**
- Time: O(n²) (two nested loops)
- Space: O(1)
- Why bad? For n up to 2×10⁴, n² (≈4×10⁸) is too slow.

### Two-Pointer Optimal Approach (O(n) Time, O(1) Extra Space)

#### Key Idea
Maintain two pointers, `left` and `right`, starting at the ends of the array, plus two running maxima, `leftMax` and `rightMax`.

- If `height[left] < height[right]`, then water trapped at left depends only on `leftMax` (because `rightMax ≥ height[right] > height[left]`), so accumulate `leftMax − height[left]` and move left right.
- Otherwise accumulate `rightMax − height[right]` and move right left.
- Update `leftMax`/`rightMax` as you go.

## 4. Implementation Details

```java
public class Solution {
    /**
     * Computes trapped rain water in O(n) time and O(1) extra space.
     */
    public int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0, trapped = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                // Work on left side
                if (height[left] >= leftMax) {
                    leftMax = height[left];      // new max on left
                } else {
                    trapped += leftMax - height[left];
                }
                left++;
            } else {
                // Work on right side
                if (height[right] >= rightMax) {
                    rightMax = height[right];    // new max on right
                } else {
                    trapped += rightMax - height[right];
                }
                right--;
            }
        }
        return trapped;
    }
}
```

### Code Walkthrough

| Code Section | Explanation |
|--------------|-------------|
| `while (left < right)` | Continue until pointers meet in the middle. |
| `if (height[left] < height[right])` | The left side is "the bottleneck" ⇒ process left. |
| `if (height[left] >= leftMax) leftMax = height[left]` | New left boundary height, no water trapped here. |
| `else trapped += leftMax - height[left];` | Water trapped equals the difference to the left boundary max. |
| `left++;` | Move inward from the left. |
| Symmetrically for the right pointer. | When `height[right] ≤ height[left]`, the right side is bottleneck ⇒ process right. |

## 5. Complexity Analysis
- Time: O(n) — exactly one pass through the array.
- Space: O(1) extra — only pointers and counters, no auxiliary arrays.

## 6. Key Takeaway
By using two pointers and maintaining only the current maximum boundaries from each side, you can calculate trapped water in one linear sweep without extra memory, vastly outperforming any quadratic or extra-space methods. 