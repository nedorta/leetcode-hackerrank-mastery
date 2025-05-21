class Solution {
    public int rob(int[] nums) {
        // Edge case: no houses
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // prevNoRob: max money if we do NOT rob the previous house
        // prevRob:   max money if we DO rob the previous house
        int prevNoRob = 0;
        int prevRob = 0;

        // Iterate through each house
        for (int amount : nums) {
            // If we rob this house, we cannot have robbed the previous one:
            //   newRob = prevNoRob + amount
            int newRob = prevNoRob + amount;

            // If we skip this house, we take the best of the previous decisions:
            //   newNoRob = max(prevNoRob, prevRob)
            int newNoRob = Math.max(prevNoRob, prevRob);

            // Shift for next iteration:
            //   prevNoRob becomes the best if we skip this house
            //   prevRob   becomes the best if we rob this house
            prevNoRob = newNoRob;
            prevRob = newRob;
        }

        // The answer is the best of robbing or skipping the last house
        return Math.max(prevNoRob, prevRob);
    }
} 