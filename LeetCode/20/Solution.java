class Solution {
    public boolean isValid(String s) {
        // We use a Deque<Character> as a stack to store the *expected* closing brackets.
        // Initial capacity is s.length() just to avoid resizing.
        Deque<Character> stack = new ArrayDeque<>(s.length());

        // Iterate through each character in the string
        for (char c : s.toCharArray()) {
            switch (c) {
                // If it's an opening bracket, push the corresponding closing bracket
                // onto the stack. That way, when we see a closing bracket later, we
                // can simply compare it to stack.pop().
                case '(' -> stack.push(')');
                case '{' -> stack.push('}');
                case '[' -> stack.push(']');

                // If it's *not* one of the three openings, it must be a closing bracket.
                // We check two things:
                // 1) The stack must not be empty (there must be an expected closer waiting).
                // 2) The top of the stack (the expected closer) must match the current char.
                // If either check fails, the string is invalid.
                default -> {
                    if (stack.isEmpty() || stack.pop() != c) {
                        return false;
                    }
                }
            }
        }

        // After processing all characters, the stack must be empty:
        // every pushed closer had to be matched by a corresponding pop.
        return stack.isEmpty();
    }
} 