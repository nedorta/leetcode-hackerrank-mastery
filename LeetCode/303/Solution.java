class NumArray {
    // prefix[i] will hold the sum of nums[0..i-1]
    private int[] prefix;

    // Constructor: O(n) preprocessing
    public NumArray(int[] nums) {
        int n = nums.length;
        // allocate one extra slot so prefix[0]=0 and prefix[i+1]=sum up to nums[i]
        prefix = new int[n + 1];
        prefix[0] = 0;
        // build prefix sums
        for (int i = 0; i < n; i++) {
            // sum up to index i is sum up to i-1 plus nums[i]
            prefix[i + 1] = prefix[i] + nums[i];
        }
    }
    
    // sumRange: O(1) per query
    public int sumRange(int left, int right) {
        // sum nums[left..right] = prefix[right+1] - prefix[left]
        return prefix[right + 1] - prefix[left];
    }
} 