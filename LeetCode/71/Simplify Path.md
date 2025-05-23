# Simplify Path

Given an absolute path for a Unix-style file system, which always begins with a slash '/', transform this absolute path into its simplified canonical path.

## Rules for Unix-style File System

1. A single period '.' represents the current directory.
2. A double period '..' represents the previous/parent directory.
3. Multiple consecutive slashes such as '//' and '///' are treated as a single slash '/'.
4. Any sequence of periods that does not match the rules above should be treated as a valid directory or file name (e.g., '...' and '....' are valid names).

## Rules for Simplified Canonical Path

1. The path must start with a single slash '/'.
2. Directories within the path must be separated by exactly one slash '/'.
3. The path must not end with a slash '/', unless it is the root directory.
4. The path must not have any single or double periods ('.' and '..') used to denote current or parent directories.

## Examples

### Example 1:
```
Input: path = "/home/"
Output: "/home"
Explanation: The trailing slash should be removed.
```

### Example 2:
```
Input: path = "/home//foo/"
Output: "/home/foo"
Explanation: Multiple consecutive slashes are replaced by a single one.
```

### Example 3:
```
Input: path = "/home/user/Documents/../Pictures"
Output: "/home/user/Pictures"
Explanation: A double period ".." refers to the directory up a level (the parent directory).
```

### Example 4:
```
Input: path = "/../"
Output: "/"
Explanation: Going one level up from the root directory is not possible.
```

### Example 5:
```
Input: path = "/.../a/../b/c/../d/./"
Output: "/.../b/d"
Explanation: "..." is a valid name for a directory in this problem.
```

## Constraints:
- 1 <= path.length <= 3000
- path consists of English letters, digits, period '.', slash '/' or '_'
- path is a valid absolute Unix path 