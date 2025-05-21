class Solution {
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