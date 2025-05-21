public class Solution {
    public boolean isPalindrome(String s) {
        // Two pointers starting at the ends of the string
        int left = 0;
        int right = s.length() - 1;

        // Process until the pointers cross
        while (left < right) {
            // Move left forward to the next alphanumeric character
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            // Move right backward to the previous alphanumeric character
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // Compare characters in a case-insensitive way
            char cLeft  = Character.toLowerCase(s.charAt(left));
            char cRight = Character.toLowerCase(s.charAt(right));
            if (cLeft != cRight) {
                // Mismatch ⇒ not a palindrome
                return false;
            }

            // Advance both pointers inward
            left++;
            right--;
        }

        // If we never found a mismatch, it's a palindrome
        return true;
    }
} 