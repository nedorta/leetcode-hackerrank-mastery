public class Solution {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // Pointer to the last valid element in the original nums1
        int p1 = m - 1;
        // Pointer to the last element in nums2
        int p2 = n - 1;
        // Pointer to the end of nums1's total capacity (m + n - 1)
        int write = m + n - 1;

        // While there are still elements in nums2 to place...
        while (p2 >= 0) {
            // If nums1 still has elements and its current is greater than nums2's current...
            if (p1 >= 0 && nums1[p1] > nums2[p2]) {
                // Copy nums1[p1] into the write position
                nums1[write] = nums1[p1];
                p1--;           // move nums1 pointer left
            } else {
                // Otherwise copy nums2[p2] into write
                nums1[write] = nums2[p2];
                p2--;           // move nums2 pointer left
            }
            write--;            // move write pointer left
        }
        // When p2 < 0, every nums2 element has been placed.
        // Any remaining nums1[0..p1] are already in correct position.
    }
} 