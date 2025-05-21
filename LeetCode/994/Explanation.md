# Rotting Oranges - Solution Explanation

## 1. Problem Statement

You're given an `m × n` grid where each cell is one of:
- `0` = empty
- `1` = fresh orange
- `2` = rotten orange

Every minute, any fresh orange 4-directionally adjacent to a rotten orange becomes rotten.

Task: Return the minimum number of minutes until no fresh oranges remain. If some fresh orange can never rot, return -1.

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | [[2,1,1],[1,1,0],[0,1,1]] | 4 | Minute by minute all fresh oranges adjacent to rot spread until none left. |
| 2 | [[2,1,1],[0,1,1],[1,0,1]] | -1 | The bottom-left fresh orange can never be reached by rot (isolated). |
| 3 | [[0,2]] | 0 | No fresh oranges at all at start ⇒ zero minutes needed. |

## 3. Multi-Source BFS Approach

### Key Idea
1. Collect all initially rotten oranges into a queue (these are our BFS sources)
2. Count total fresh oranges
3. BFS by layers: each layer = one minute. For each rotten in the queue, rot its 4 neighbors if they are fresh (turn 1 → 2, decrement fresh-count, enqueue them)
4. After each layer, increment minutes
5. Stop when queue empties. If any fresh remain at end, return -1; otherwise return minutes

## 4. Code Walkthrough

```java
public class Solution {
    public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> q = new ArrayDeque<>();
        int fresh = 0;

        // 1) Initialize queue with all rotten oranges and count fresh
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    q.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    fresh++;
                }
            }
        }
        // If no fresh oranges, answer is 0
        if (fresh == 0) return 0;

        int minutes = 0;
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        // 2) BFS level-by-level
        while (!q.isEmpty()) {
            int size = q.size();
            boolean rottedThisMinute = false;

            for (int k = 0; k < size; k++) {
                int[] cell = q.poll();
                int x = cell[0], y = cell[1];

                // 3) Try to rot all fresh neighbors
                for (int[] d : dirs) {
                    int nx = x + d[0], ny = y + d[1];
                    if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
                    if (grid[nx][ny] == 1) {
                        grid[nx][ny] = 2;     // mark as rotten
                        fresh--;             // one fewer fresh
                        q.offer(new int[]{nx, ny});
                        rottedThisMinute = true;
                    }
                }
            }

            // 4) Only increment minutes if we actually rotted something
            if (rottedThisMinute) {
                minutes++;
            }
        }

        // 5) Check if any fresh left
        return (fresh == 0 ? minutes : -1);
    }
}
```

### Code Section Breakdown

| Section | Purpose |
|---------|---------|
| Queue initialization & fresh count | We seed the BFS queue with all rotten oranges and tally fresh ones. |
| `if (fresh == 0) return 0;` | If there are no fresh oranges at the start, no time is needed. |
| `while (!q.isEmpty()) { ... }` | Process the BFS in layers; each layer represents one minute passing. |
| Inner loop over `size = q.size()` | Ensures we only process the oranges that were rotten at the start of this minute. |
| Neighbor check & rot | For each direction, if neighbor is fresh, turn it rotten, decrement fresh, enqueue it, and mark spread. |
| `if (rottedThisMinute) minutes++` | We only increment the minute count if at least one fresh orange was turned this round. |
| Final check | After BFS, if any fresh remain (`fresh > 0`), return -1; otherwise return the total minutes elapsed. |

## 5. Complexity Analysis

### Time: O(m·n)
- We visit each cell at most once when it becomes rotten, plus the initial scan

### Space: O(m·n)
- In the worst case (all rotten), the queue may hold O(m·n) cells

## 6. Key Takeaway

Using a multi-source BFS from all initially rotten oranges, with one level per minute, efficiently simulates the simultaneous rotting process and yields the minimum time to rot all reachable fresh oranges (or detects impossibility). 