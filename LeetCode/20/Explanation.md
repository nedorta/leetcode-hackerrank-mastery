# Valid Parentheses - Detailed Explanation

## 1. Problem Statement

You have a string s made up only of the six characters:

'(', ')', '{', '}', '[' and ']'

You must determine whether s is valid, meaning:
- Every opening bracket is closed by the same type of bracket.
- Brackets close in the correct order (i.e. properly nested).
- No closing bracket appears without a matching open bracket.

Return true if s is valid, otherwise false.

## 2. Examples

| Input | Output | Why? |
|-------|--------|------|
| "()" | true | Simple matching pair |
| "()[]{}" | true | Three independent matching pairs |
| "(]" | false | ( expects ), but sees ] instead |
| "([])" | true | Nested correctly: (…) around [] |

## 3. Brute-Force Thought (Why We Improve It)

One naive way is to repeatedly scan for any "()", "[]", or "{}" substring and remove it until nothing's left:

```
while s contains "()" or "[]" or "{}":
    remove that pair from s
return s is empty
```

- Time: Each scan and removal can be O(n), and you might do O(n) removals ⇒ O(n²).
- Space: O(n) for the mutable string.

For n up to 10,000, O(n²) can become slow.

## 4. Optimal O(n) Approach: Use a Stack

### 4.1. Key Idea

- As you read left-to-right, every time you see an opening bracket, remember what its matching closing bracket should be.
- When you see a closing bracket, check whether it matches the most recent unmatched opening bracket.
- A stack (LIFO) is the perfect structure for this "most recent" requirement.

### 4.2. Data Structure

- Deque<Character> stack (an ArrayDeque in Java)
  - push( ) adds an expected closer on top.
  - pop( ) removes & returns the top expected closer to compare with what you actually saw.

## 5. Walkthrough of the Code

```java
class Solution {
    public boolean isValid(String s) {
        // A stack to hold the *expected* closing brackets, in order.
        Deque<Character> stack = new ArrayDeque<>(s.length());

        // Process each character in the string one by one:
        for (char c : s.toCharArray()) {

            switch (c) {
                // For an opening bracket, push its matching closer:
                case '(' -> stack.push(')');
                case '{' -> stack.push('}');
                case '[' -> stack.push(']');

                // For anything else, it must be a closing bracket.
                // We verify:
                //  1) stack is not empty (we have an expected closer), and
                //  2) the top of the stack equals this bracket (c).
                default -> {
                    if (stack.isEmpty()    // no opener waiting
                     || stack.pop() != c)  // doesn't match expected closer
                    {
                        return false;      // invalid
                    }
                }
            }
        }

        // At the end, every opener we pushed must have been popped.
        // If any expected closer remains, it means we had an unmatched opener.
        return stack.isEmpty();
    }
}
```

| Step | What's happening |
|------|-----------------|
| 1 | Create a stack sized to s.length() |
| 2 | Loop through each char c in the string |
| 3 | If c is '(', '{' or '[', push its matching closing bracket onto the stack |
| 4 | Else (c is a closer): pop the stack and compare to c. If stack was empty or values differ, return false. |
| 5 | After the loop, return stack.isEmpty(). Only valid if no unmatched openings remain. |

## 6. Complexity Analysis

- Time: O(n)
  - We do one pass through the n characters; each push/pop is O(1).

- Space: O(n) worst-case
  - If the string is all opens—"[[[[["—we push n items before popping.

## 7. Why It's Correct and Efficient

### Correctness:
- Every opening brackets' expected closer is recorded in order.
- Every actual closer is checked immediately against the most recent opener.

### Efficiency:
- No repeated rescanning or substring removals—just one linear pass.
- Minimal extra memory (one stack of at most n elements).

## Key Takeaway

Whenever you need to check nested structure (parentheses, HTML tags, expression evaluation), a stack that tracks what you expect next is almost always the right tool—and it often yields an O(n) solution. 