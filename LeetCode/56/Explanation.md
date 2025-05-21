# Merge Intervals - Solution Explanation

## 1. Problem Statement

Given an array of intervals where `intervals[i] = [starti, endi]`, merge all overlapping intervals into non-overlapping intervals that cover all the input intervals.

Two intervals overlap if:
- One's start time falls within another's range
- They share an endpoint (e.g., [1,4] and [4,5] overlap at 4)

Task: Return an array of merged, non-overlapping intervals in any order.

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | [[1,3],[2,6],[8,10],[15,18]] | [[1,6],[8,10],[15,18]] | [1,3] and [2,6] overlap → merge into [1,6]; others don't overlap |
| 2 | [[1,4],[4,5]] | [[1,5]] | Intervals share endpoint 4, so they merge |
| 3 | [[1,4],[2,3]] | [[1,4]] | Second interval completely contained in first |

## 3. Brute-Force Thought (Why It Fails)

A naive approach might be to:
```java
for each interval i:
    for each interval j != i:
        if i and j overlap:
            merge them
```

Problems:
- O(n²) time complexity
- Need complex logic to handle chain reactions
- Hard to track which intervals are already merged
- With up to 10⁴ intervals, quadratic time is too slow

## 4. Optimal O(n log n) Approach: Sort + Linear Scan

### 4.1. Key Idea

1. Sort intervals by start time
2. Then we only need to compare each interval with the previous merged interval:
   - If they overlap, extend the previous interval
   - If not, add the previous interval to result and move on

This works because:
- After sorting, if interval i doesn't overlap with merged interval, no future interval can overlap with merged interval
- We only need one pass through the sorted intervals

## 5. Walkthrough of the Code

```java
public class Solution {
    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;  // nothing to merge
        }

        // 1) Sort intervals by ascending start time
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> merged = new ArrayList<>();
        // Start with the first interval as our "current"
        int[] current = intervals[0];

        // 2) Iterate through the rest
        for (int i = 1; i < intervals.length; i++) {
            int[] next = intervals[i];
            if (next[0] <= current[1]) {
                // 3a) Overlap → extend the current interval's end if needed
                current[1] = Math.max(current[1], next[1]);
            } else {
                // 3b) No overlap → save current and move to next
                merged.add(current);
                current = next;
            }
        }

        // 4) Don't forget to add the last interval
        merged.add(current);

        return merged.toArray(new int[merged.size()][]);
    }
}
```

### Code Section Breakdown

| Code Section | Purpose |
|--------------|---------|
| Base case check | Handle empty or single interval cases |
| Sort by start time | Ensures we can process intervals in one pass |
| Track current interval | Holds the interval being potentially extended |
| Overlap check (next[0] <= current[1]) | Determines if intervals should merge |
| Math.max for end time | Handles nested intervals correctly |
| Add to result list | Only when we're sure no more merging needed |
| Final interval addition | Ensures last merged interval is included |

## 6. Complexity Analysis

### Time: O(n log n)
- Sorting intervals: O(n log n)
- Single pass through sorted intervals: O(n)
- ArrayList to array conversion: O(n)
- Overall dominated by sort: O(n log n)

### Space: O(n)
- Output array: O(n) in worst case
- Sorting space: O(log n) to O(n) depending on sort algorithm
- ArrayList for intermediate storage: O(n)

## 7. Key Takeaway

- Sorting first can simplify interval processing from O(n²) to O(n log n)
- When merging intervals, always consider:
  - Proper handling of shared endpoints
  - Nested intervals
  - The last interval in process
- Using a "current" interval as buffer simplifies merging logic 