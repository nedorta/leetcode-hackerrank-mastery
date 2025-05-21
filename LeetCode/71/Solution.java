class Solution {
    public String simplifyPath(String path) {
        // Use a deque as a stack to hold valid directory names
        Deque<String> stack = new ArrayDeque<>();
        // Split on "/" to extract components; consecutive slashes produce empty strings
        String[] components = path.split("/");

        for (String comp : components) {
            // Skip empty components or "." which mean "current directory"
            if (comp.isEmpty() || comp.equals(".")) {
                continue;
            }
            // ".." means "go up to parent" → pop if possible
            if (comp.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                // Any other name is a valid directory → push onto stack
                stack.push(comp);
            }
        }

        // Build the canonical path by joining the stack contents in reverse
        StringBuilder result = new StringBuilder();
        if (stack.isEmpty()) {
            // If nothing remains, we're at root
            return "/";
        }
        // Since we pushed onto the stack, we need to reverse order
        while (!stack.isEmpty()) {
            result.insert(0, "/" + stack.pop());
        }
        return result.toString();
    }
} 