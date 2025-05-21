class Solution {
    public int searchInsert(int[] nums, int target) {
        // Define the search interval [left..right]
        int left = 0;
        int right = nums.length - 1;

        // Standard binary search loop
        while (left <= right) {
            // Avoid overflow: mid = left + (right - left) / 2
            int mid = left + (right - left) / 2;

            // If we found target at mid, return immediately
            if (nums[mid] == target) {
                return mid;
            }

            // If target is larger, discard left half by moving left up
            if (nums[mid] < target) {
                left = mid + 1;
            } 
            // Otherwise discard right half by moving right down
            else {
                right = mid - 1;
            }
        }

        // At this point, left is the smallest index > all elements < target,
        // i.e., the correct insertion position if target was not found.
        return left;
    }
} 