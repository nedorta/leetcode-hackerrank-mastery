# Valid Palindrome - Detailed Explanation

## 1. Problem Statement

A string s is a palindrome if, after:

    Converting all uppercase letters to lowercase.

    Removing all characters that are not letters or digits (i.e., skip punctuation, spaces, symbols).

the resulting sequence of alphanumeric characters reads the same forward and backward.

Return true if s is a palindrome under those rules; otherwise return false.

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | "A man, a plan, a canal: Panama" | true | Normalized → "amanaplanacanalpanama", which reads the same both ways. |
| 2 | "race a car" | false | Normalized → "raceacar", which is not the same backward. |
| 3 | " " | true | Normalized → "" (empty string), trivially a palindrome. |

## 3. Brute-Force Thought (Extra Space)

One could build a new string by scanning s, appending only alphanumerics (in lowercase), then check if that string equals its reverse.

    Time: O(n) to build + O(n) to reverse and compare ⇒ O(n).

    Space: O(n) extra for the filtered string and its reverse.

We can do better on space by comparing in-place.

## 4. Optimal O(n), O(1) Space: Two-Pointer Scan

### Key Idea

Use two pointers, left at the start and right at the end of s, and move them inward:

    Skip any non-alphanumeric characters on each side.

    Convert both characters to lowercase and compare:

        If they differ ⇒ not palindrome ⇒ return false.

        If they match ⇒ move both pointers inward and continue.

    If pointers cross without mismatch ⇒ true.

This uses constant extra space and touches each character at most once ⇒ O(n) time.

## 5. Walkthrough of the Code

```java
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
```

| Code Section | Purpose |
|--------------|---------|
| `int left = 0, right = s.length() - 1` | Initialize pointers at both ends. |
| `while (left < right)` | Continue until pointers meet in middle. |
| `while (...!Character.isLetterOrDigit())` | Skip non-alphanumeric characters. |
| `char cLeft = Character.toLowerCase()` | Convert to lowercase for comparison. |
| `if (cLeft != cRight) return false` | Exit early if mismatch found. |
| `left++; right--` | Move pointers toward center. |
| `return true` | No mismatches found = palindrome. |

## 6. Complexity Analysis

- Time: O(n), where n = s.length().
  - Each character is examined at most twice (once by each pointer).

- Space: O(1) extra.
  - Only integer pointers and a few char variables—no extra strings or arrays.

## 7. Key Takeaway

For palindrome checks where you must ignore certain characters and handle case folding, a two-pointer approach scanning inwards is simple, fast, and space-efficient. It generalizes to many "validity under transformation" problems on strings. 