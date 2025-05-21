# Generate Parentheses - Solution Explanation

## 1. Problem Statement

Given n pairs of parentheses, generate all combinations of well-formed (balanced) parentheses strings of length 2·n.

## 2. Examples

| Example | Input | Output |
|---------|--------|--------|
| 1 | n = 3 | `["((()))","(()())","(())()","()(())","()()()"]` |
| 2 | n = 1 | `["()"]` |

## 3. Backtracking Approach

### Key Idea

- Build the string one character at a time, choosing either '(' or ')' when valid
- Track two counters:
  - `open`: number of '(' used so far
  - `close`: number of ')' used so far
- Rules:
  - You may add '(' if `open < n`
  - You may add ')' if `close < open` (to keep balance)
  - Once the length reaches 2·n, record the built string as one valid answer

This explores the space of all balanced parentheses in a depth-first manner, pruning invalid prefixes early.

## 4. Implementation Details

```java
List<String> ans = new ArrayList<>();
backtrack(ans, new StringBuilder(), 0, 0, n);
return ans;
```
- Initialize answer list and start recursion with zero opens/closes

```java
if (sb.length() == 2 * max) {
    ans.add(sb.toString());
    return;
}
```
- Base case: when the built string has length 2·n, it's complete and valid—add it

```java
if (open < max) {
    sb.append('(');
    backtrack(ans, sb, open + 1, close, max);
    sb.deleteCharAt(sb.length() - 1);
}
```
- Add '(' if we haven't used all n opens. Recurse, then backtrack (remove it)

```java
if (close < open) {
    sb.append(')');
    backtrack(ans, sb, open, close + 1, max);
    sb.deleteCharAt(sb.length() - 1);
}
```
- Add ')' only if it won't unbalance (i.e. we have more opens than closes). Recurse and backtrack

## 5. Complexity Analysis

Let Cₙ be the n-th Catalan number (count of valid strings):
- Cₙ = (2n)! / ((n+1)!·n!) ≈ 4ⁿ / (n^(3/2)√π)

### Time Complexity: O(Cₙ · n)
- We generate Cₙ strings, each of length 2n
- Backtracking overhead is proportional to the generation process

### Space Complexity: O(n)
- Extra space for the recursion stack and StringBuilder
- Not counting the output list which stores the results

## 6. Key Takeaway

When generating all combinatorial objects subject to local validity constraints (here, balanced parentheses), backtracking with counters for the constraints lets you prune invalid partial solutions early and explore the solution space in an orderly, depth-first way. 