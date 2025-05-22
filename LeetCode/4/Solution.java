public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Alias shorter array to A, longer to B (we binary‐search on A)
        int[] A = nums1, B = nums2;
        int total = A.length + B.length;
        int half  = (total + 1) / 2;   // # of elements in left half

        // If A is longer than B, swap to ensure A.length <= B.length
        if (A.length > B.length) {
            int[] tmp = A;
            A = B;
            B = tmp;
        }

        // Now A.length <= B.length
        int m = A.length, n = B.length;
        int lo = 0, hi = m;

        // Binary search over how many from A go into the left partition
        while (lo <= hi) {
            int i = (lo + hi) / 2;    // take i elems from A
            int j = half - i;         // take the rest from B

            // Compute left/right border elements or use ±∞ if out of bounds
            int Aleft  = (i > 0) ? A[i - 1] : Integer.MIN_VALUE;
            int Aright = (i < m) ? A[i]     : Integer.MAX_VALUE;
            int Bleft  = (j > 0) ? B[j - 1] : Integer.MIN_VALUE;
            int Bright = (j < n) ? B[j]     : Integer.MAX_VALUE;

            // Check if partition is valid:
            // max(left sides) ≤ min(right sides)
            if (Aleft <= Bright && Bleft <= Aright) {
                // Found correct split
                if ((total & 1) == 1) {
                    // Odd total length → median is max of left‐half borders
                    return Math.max(Aleft, Bleft);
                } else {
                    // Even total → median is average of two center values
                    int leftMax  = Math.max(Aleft, Bleft);
                    int rightMin = Math.min(Aright, Bright);
                    return (leftMax + rightMin) / 2.0;
                }
            }
            // If Aleft > Bright, we took too many from A → move hi left
            else if (Aleft > Bright) {
                hi = i - 1;
            }
            // Otherwise we took too few from A → move lo right
            else {
                lo = i + 1;
            }
        }

        // If inputs are valid sorted arrays, we should never reach here
        return -1;
    }
} 