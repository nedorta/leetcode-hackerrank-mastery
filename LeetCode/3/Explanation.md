# Longest Substring Without Repeating Characters - Solution Explanation

## 1. Problem Statement

You're given a string `s`.
Task: Find the length of the longest substring of `s` that contains no duplicate characters.

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | "abcabcbb" | 3 | The longest is "abc". |
| 2 | "bbbbb" | 1 | Every character is "b", so max substring without repeat is "b". |
| 3 | "pwwkew" | 3 | The longest is "wke". Note "pwke" is not a substring. |

## 3. Brute-Force Thought (Why It Fails)

A naive approach might be to:
1. Generate all possible substrings: O(n²)
2. Check each for duplicates: O(n)
3. Track the longest valid one

Problems:
- O(n³) time complexity
- Generates unnecessary substrings
- Redundant character checks
- Impractical for large strings

## 4. Optimal Approach: Sliding-Window

### 4.1. Key Idea

Maintain a window `[left..i]` that has no duplicates:
1. As you extend the right end `i`
2. If `s[i]` already exists in the window
3. Slide the left pointer just past that previous occurrence
4. Track the maximum window size seen

This works because:
- Each character is processed exactly once
- Window always contains a valid substring
- Left pointer only moves forward

## 5. Walkthrough of the Code

```java
int[] last = new int[256];
// last[c] stores (index + 1) of the last time we saw c in the window
int left = 0, maxLen = 0;
for (int i = 0; i < s.length(); i++) {
    char c = s.charAt(i);
    // 1) Shrink left if c is duplicated in [left..i)
    left = Math.max(left, last[c]);

    // 2) Update max length
    maxLen = Math.max(maxLen, i - left + 1);

    // 3) Record this position of c (use i+1 to distinguish from default zero)
    last[c] = i + 1;
}
return maxLen;
```

### Code Section Breakdown

| Code Section | Purpose |
|--------------|---------|
| int[] last = new int[256]; | Array to remember last seen position+1 for each ASCII character. |
| left = Math.max(left, last[c]); | If c last appeared inside the current window, move left just past that index. |
| maxLen = Math.max(maxLen, i - left + 1); | Compute current window length and update global maximum. |
| last[c] = i + 1; | Record the new last-seen position of c (store i+1 to avoid confusion with default zero). |

## 6. Complexity Analysis

### Time: O(n)
- Where n = s.length()
- Each character is processed exactly once
- All operations within the loop are O(1)

### Space: O(1)
- The `last` array is fixed-size 256
- Independent of input size n
- Only a few primitive variables needed

## 7. Key Takeaway

The sliding-window with an index map (`last[c]`) lets you maintain a current no-duplicate substring in linear time:

1. Expand the window at the right
2. When a duplicate appears, jump the left boundary to just past its previous occurrence
3. Continuously track the maximum window size

This pattern of using a sliding window with an auxiliary data structure (here, the `last` array) is common for substring problems. 