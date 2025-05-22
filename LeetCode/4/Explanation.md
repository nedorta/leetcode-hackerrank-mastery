# Median of Two Sorted Arrays - Solution Explanation

## 1. Problem Statement

You are given two sorted arrays, `nums1` of length m and `nums2` of length n.
Task: Find the median of the two arrays combined in O(log(m+n)) time.

## 2. Examples

| Example | nums1 | nums2 | Output | Explanation |
|---------|-------|-------|--------|-------------|
| 1 | `[1,3]` | `[2]` | `2.0` | Merged → `[1,2,3]`, median = 2 |
| 2 | `[1,2]` | `[3,4]` | `2.5` | Merged → `[1,2,3,4]`, median = (2+3)/2 |
| 3 | `[]` | `[1]` | `1.0` | Single element |

## 3. Solution Approaches

### 3.1 Brute-Force Merge & Sort

```java
public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] merged = new int[m + n];
        System.arraycopy(nums1, 0, merged, 0, m);
        System.arraycopy(nums2, 0, merged, m, n);
        Arrays.sort(merged);  // O((m+n)·log(m+n))

        int L = merged.length;
        if (L % 2 == 0) {
            return (merged[L/2 - 1] + merged[L/2]) / 2.0;
        } else {
            return merged[L/2];
        }
    }
}
```

#### Analysis
- Time: O((m+n)·log(m+n))
- Space: O(m+n) for the merged array
- **Why it's bad**: Sorting the entire combined array is more work than necessary when you only need the middle element(s)

### 3.2 Two-Pointer Partial Merge

```java
public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int i = 0, j = 0;
        int prev = 0, curr = 0;
        int target = (m + n) / 2;

        for (int k = 0; k <= target; k++) {
            prev = curr;
            if (i < m && (j >= n || nums1[i] <= nums2[j])) {
                curr = nums1[i++];
            } else {
                curr = nums2[j++];
            }
        }
        if ((m + n) % 2 == 1) {
            return curr;
        } else {
            return (prev + curr) / 2.0;
        }
    }
}
```

#### Analysis
- Time: O(m+n) — may traverse nearly the whole array in worst case
- Space: O(1) extra
- **Why it's sub-optimal**: Although it avoids sorting and extra arrays, it still runs in linear time, not the required logarithmic time

### 3.3 Optimal Binary-Search Partition

```java
public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length)
            return findMedianSortedArrays(nums2, nums1);

        int m = nums1.length, n = nums2.length;
        int total = m + n;
        int half  = (total + 1) / 2;

        int lo = 0, hi = m;
        while (lo <= hi) {
            int i = (lo + hi) / 2;    // # taken from nums1
            int j = half - i;         // # taken from nums2

            int Aleft  = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int Aright = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int Bleft  = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int Bright = (j == n) ? Integer.MAX_VALUE : nums2[j];

            // If partition is valid...
            if (Aleft <= Bright && Bleft <= Aright) {
                // Odd total → max of left halves
                if ((total & 1) == 1) {
                    return Math.max(Aleft, Bleft);
                }
                // Even total → average of the two center values
                return (Math.max(Aleft, Bleft) + Math.min(Aright, Bright)) / 2.0;
            }
            // Too many from nums1 on left → move left
            else if (Aleft > Bright) {
                hi = i - 1;
            }
            // Too few from nums1 on left → move right
            else {
                lo = i + 1;
            }
        }
        return -1.0;  // Should never reach here for valid input
    }
}
```

#### Analysis
- Time: O(log min(m,n)) — binary search on the smaller array
- Space: O(1) extra

## 4. Complexity Comparison

| Approach | Time Complexity | Space Complexity |
|----------|----------------|------------------|
| Brute-Force sort & merge | O((m+n)·log(m+n)) | O(m+n) |
| Two-Pointer partial merge | O(m+n) | O(1) |
| Binary-Search Partition | O(log min(m,n)) | O(1) |

## 5. Key Takeaway

When you need only the median (not the full merge), partitioning the two arrays via binary search achieves optimal logarithmic time, far outperforming linear or sort-based methods in large datasets. 