class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] output = new int[n];

        // 1) Build output as the prefix products:
        //    output[i] = nums[0] * nums[1] * ... * nums[i-1]
        int prefix = 1;
        for (int i = 0; i < n; i++) {
            output[i] = prefix;
            prefix *= nums[i];
        }

        // 2) Traverse from the end, maintaining a suffix product:
        //    suffix = nums[i+1] * nums[i+2] * ... * nums[n-1]
        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            output[i] *= suffix;
            suffix *= nums[i];
        }

        return output;
    }
} 