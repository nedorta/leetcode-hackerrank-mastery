class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), maxLen = 0;
        // last[c] = index+1 of the last occurrence of character c in the window
        int[] last = new int[256];

        // left boundary of current window
        int left = 0;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            // if we've seen c inside current window, move left just past its last occurrence
            left = Math.max(left, last[c]);

            // window is [left..i], length = i - left + 1
            maxLen = Math.max(maxLen, i - left + 1);

            // record the position of c as i+1
            last[c] = i + 1;
        }
        return maxLen;
    }
} 