# Solution Explanation

## 1. Problem Statement

You have an array `nums[0…n–1]` of nonnegative demands.

You have `m` interval queries `queries[j] = [l,r]`, each can be used at most once to decrement by 1 any chosen indices in `[l..r]`.

The goal is to remove as many queries as possible while still ensuring every index `i` is covered by at least `nums[i]` of the remaining queries.

Return the maximum removable queries, or –1 if even using all queries cannot meet the demands.

## 2. Concrete Example

Given:
```
nums    = [0,0,1,1,0]
queries = [[3,4],[0,2],[2,3]]
```

Sort by start → `[[0,2],[2,3],[3,4]]`

Sweep `i = 0…4`, maintaining:
- A diff array so `diff[k]` tracks the net coverage change at k
- A max-heap `available` of end times for queries that have started

| i | demand | available | diff before → prefix → coverage | Actions | used |
|---|---------|-----------|--------------------------------|---------|------|
| 0 | 0 | {2} | 0 | none | 0 |
| 1 | 0 | {2} | 0 | none | 0 |
| 2 | 1 | {2,3} | 0 | pop 3, apply diff[2]++, diff[4]-- → cov=1 | 1 |
| 3 | 1 | {2} | diff prefix gives cov=1 | none | 1 |
| 4 | 0 | … | … | none | 1 |

Result: used = 1 ⇒ removable = 3−1 = 2

## 3. Advantages of This Approach

1. Single heap instead of two
2. Difference array handles expiring coverage automatically—no need to pop "assigned" queries when they end
3. Maintains exact coverage count in O(1) per step via prefix sums and diff updates
4. Overall: O(m log m + n) time, O(n + m) space. Very efficient

## 4. Complexity Analysis

### Time Complexity
- Sorting queries: O(m log m)
- Sweeping through n indices, each query is pushed once and popped at most once ⇒ O((n + m) log m)

### Space Complexity
- O(n) for the diff array
- O(m) for the heap

This refined approach is both simpler in code and optimal in performance for the given constraints. 