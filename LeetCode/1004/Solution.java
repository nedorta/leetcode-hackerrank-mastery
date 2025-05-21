public class Solution {
    public int longestOnes(int[] nums, int k) {
        // left: start index of our sliding window
        int left = 0;
        // zerosCount: how many zeros are in the current window [left..right]
        int zerosCount = 0;
        // maxLen: the best (maximum) window size seen so far
        int maxLen = 0;

        // Expand the window by moving 'right' from 0 to the end of the array
        for (int right = 0; right < nums.length; right++) {
            // If the new element is 0, include it in zerosCount
            if (nums[right] == 0) {
                zerosCount++;
            }

            // If we have exceeded our budget of k flips (zerosCount > k),
            // we must shrink the window from the left until zerosCount ≤ k again.
            while (zerosCount > k) {
                // If the leftmost element is a zero, removing it reduces zerosCount
                if (nums[left] == 0) {
                    zerosCount--;
                }
                // Move the left boundary right by one (shrinking the window)
                left++;
            }

            // At this point, the window [left..right] contains at most k zeros,
            // so it can be turned into all 1's by flipping those zeros.
            // Update maxLen if this window is larger than any we've seen.
            int windowSize = right - left + 1;
            if (windowSize > maxLen) {
                maxLen = windowSize;
            }
        }

        // After processing all right endpoints, maxLen holds the answer
        return maxLen;
    }
} 