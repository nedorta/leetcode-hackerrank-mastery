# Permutations - Solution Explanation

## 1. Problem Statement

You're given an array `nums` of distinct integers.
Task: Return all possible permutations (orderings) of the elements in `nums`. The output can be in any order.

## 2. Examples

| Example | Input | Output |
|---------|--------|--------|
| 1 | `[1,2,3]` | `[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]` |
| 2 | `[0,1]` | `[[0,1],[1,0]]` |
| 3 | `[1]` | `[[1]]` |

## 3. Backtracking Approach

### Key Idea

- Build permutations one position at a time
- Maintain a boolean `used[i]` array to mark which elements are already in the current permutation
- At each step:
  - Loop through all indices
  - If `nums[i]` is not yet used:
    1. Choose it (mark used + add to current)
    2. Recurse
    3. Un-choose it (backtrack)

This explores the full permutation tree of branching factor n, depth n.

## 4. Implementation Details

```java
List<List<Integer>> result = new ArrayList<>();
boolean[] used = new boolean[nums.length];
backtrack(nums, used, new ArrayList<>(), result);
return result;
```
- Initialize the result list and used flags, then start recursion

```java
if (current.size() == nums.length) {
    result.add(new ArrayList<>(current));
    return;
}
```
- Base case: when the current list has length n, it's a full permutation—add a copy to result

```java
for (int i = 0; i < nums.length; i++) {
    if (used[i]) continue;
    used[i] = true;
    current.add(nums[i]);
    backtrack(nums, used, current, result);
    current.remove(current.size() - 1);
    used[i] = false;
}
```
- Loop over each index:
  1. Skip if already used
  2. Choose `nums[i]`: mark `used[i] = true` and append to current
  3. Recurse to fill next position
  4. Backtrack: remove last element and reset `used[i]`

### Code Breakdown

| Code Section | Explanation |
|-------------|-------------|
| `boolean[] used = new boolean[n]` | Tracks which elements are already in the current permutation |
| `if (current.size() == n)` | When we've placed all n elements, record one complete permutation |
| `for (int i=0; i<n; i++)` | Try each element that isn't used yet in the next slot |
| `used[i] = true; current.add(nums[i]);` | Choose `nums[i]` for the current position |
| `backtrack(...)` | Recurse to fill the next position |
| `current.remove(...); used[i] = false;` | Un-choose: undo the choice to restore state for the next iteration |

## 5. Complexity Analysis

### Time Complexity: O(n · n!)
- There are n! permutations, each of length n
- Generating each requires O(n) work to copy into the result

### Space Complexity: O(n)
- Extra space for:
  - Recursion stack
  - `used` array
  - `current` list
- Plus O(n · n!) for the output list (not counting as auxiliary space)

## 6. Key Takeaway

Backtracking systematically explores all arrangements by making a choice at each position, recursing, and then undoing that choice. Using a `used` array ensures each element appears exactly once per permutation. This pattern applies broadly wherever you need to generate all combinations, subsets, or permutations. 