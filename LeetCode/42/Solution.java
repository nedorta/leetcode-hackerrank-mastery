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