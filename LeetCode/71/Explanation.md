# Simplify Path - Solution Explanation

## 1. Problem Statement

Given an absolute Unix-style file system path (a string that always starts with "/"), return its simplified canonical version, observing these rules:

- A single period `.` means "current directory" → skip it
- A double period `..` means "parent directory" → pop one level up (unless already at root)
- Multiple consecutive slashes `//` act as a single slash
- All other names (including names like `...`) are valid directory names

The result must:
- Start with exactly one `/`
- Not end with a trailing slash (unless it's the root: "/")
- Contain no `.` or `..` components

## 2. Concrete Examples

| Input | Output | Explanation |
|-------|--------|-------------|
| "/home/" | "/home" | Trailing slash removed. |
| "/home//foo/" | "/home/foo" | Double slash collapsed. |
| "/home/user/Documents/../Pictures" | "/home/user/Pictures" | `..` pops "Documents". |
| "/../" | "/" | At root, `..` has no effect. |
| "/.../a/../b/c/../d/./" | "/.../b/d" | "..." is a valid name; `.` does nothing; `..` pops the preceding directory each time. |

## 3. Brute-Force Thought (String Manipulation Loop)

You might try repeatedly replacing occurrences of:
- `"/./"` with `"/"`
- `"/dir/../"` with `"/"`
- `//` with `/`
until nothing changes.

- Time: Potentially O(n²) in pathological cases (repeated string scans)
- Why improve: We can do it in a single pass using a stack

## 4. Optimal O(n) Approach: Stack of Path Components

### Key Idea

1. Split the input on "/", yielding directory names, empty strings, ".", or ".."
2. Iterate through each component:
   - Skip empty ("") or "."
   - Pop the stack if component is ".." (go up), but only if the stack isn't empty
   - Push any other valid name
3. After processing all components, rebuild the path by joining the stack contents with "/" separators, prefixed by a leading slash
4. If the stack is empty, the result is simply "/"

This does one linear scan of the components and one more pass to rebuild the string.

## 5. Walkthrough of the Code

```java
String[] components = path.split("/");
```
- Splits on "/"
- E.g. "/a//b/./../c" → ["", "a", "", "b", ".", "..", "c"]

```java
Deque<String> stack = new ArrayDeque<>();
```
- We'll use push and pop to manage our current directory stack

```java
for (String comp : components) {
    if (comp.isEmpty() || comp.equals(".")) {
        continue;              // skip blanks and "current directory"
    }
    if (comp.equals("..")) {
        if (!stack.isEmpty()) {
            stack.pop();       // go up one level if possible
        }
    } else {
        stack.push(comp);      // valid directory name → descend into it
    }
}
```
- Processes each fragment in O(1) time

```java
if (stack.isEmpty()) {
    return "/";               // root directory
}
```
- No directories left → simplified path is the root

```java
StringBuilder result = new StringBuilder();
while (!stack.isEmpty()) {
    result.insert(0, "/" + stack.pop());
}
return result.toString();
```
- We pop from the stack and prepend "/" + name" to build the path from front to back

## 6. Complexity Analysis

### Time: O(n), where n = path.length()
- Splitting is O(n)
- One pass through components is O(n)
- Rebuilding via prepends costs O(d²) in the worst case if done naively, but because the number of components d ≤ n and each insert-at-front is O(1) amortized for StringBuilder shifts on small strings, in practice it remains linear

### Space: O(n) extra
- The stack holds at most one entry per component
- We allocate the split array

## 7. Key Takeaway

- Stacks are ideal for interpreting "go up" (`..`) and "go down" (directory names) operations in nested structures
- Splitting, pushing, popping, and one final rebuild yields a clean, single-pass canonicalization 