# Number of Islands - Solution Explanation

## 1. Problem Statement

Given a 2D grid of characters `grid[m][n]` where:
- '1' represents land
- '0' represents water

An island is a group of adjacent land cells ('1's) connected horizontally or vertically, surrounded by water ('0's). You may assume all four edges of the grid are surrounded by water.

Task: Return the number of distinct islands in the grid.

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | [["1","1","1","1","0"],<br>["1","1","0","1","0"],<br>["1","1","0","0","0"],<br>["0","0","0","0","0"]] | 1 | One large island spanning multiple cells |
| 2 | [["1","1","0","0","0"],<br>["1","1","0","0","0"],<br>["0","0","1","0","0"],<br>["0","0","0","1","1"]] | 3 | Three islands: at (0,0), (2,2), and (3,3)/(3,4) |

## 3. Brute-Force Thought (Why It Fails)

A naive approach might be to:
1. For each land cell, check all 8 surrounding cells
2. Keep track of visited cells in a separate boolean array
3. Use multiple passes to group connected land cells

Problems:
- Extra space for visited array: O(m×n)
- Multiple passes over the grid
- Complex bookkeeping for island identification

## 4. Optimal O(m×n) Approach: DFS with In-Place Modification

### 4.1. Key Idea

1. Scan the grid cell by cell
2. When you find a '1' (land):
   - You've found a new island → increment count
   - Use DFS to "sink" the entire island (change all connected '1's to '0's)
   - This marks the island as visited without extra space
3. Continue until all cells are processed

This runs in O(m×n) time and uses O(m×n) space only for recursion stack.

## 5. Walkthrough of the Code

```java
public class Solution {
    /**
     * Returns the number of islands in the given 2D grid.
     * Uses DFS-based flood-fill to "sink" each island once it's counted.
     */
    public int numIslands(char[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        int islands = 0;

        // 1) Scan every cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {      // found unvisited land
                    islands++;                // new island!
                    sinkIsland(grid, i, j, m, n);
                }
            }
        }
        return islands;
    }

    /**
     * Recursively "sinks" all connected '1's starting at (i,j)
     * by marking them as '0', so we don't recount them.
     */
    private void sinkIsland(char[][] grid, int i, int j, int m, int n) {
        // boundary or water check
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] != '1') {
            return;
        }
        // mark current land as water
        grid[i][j] = '0';

        // recurse in four directions
        sinkIsland(grid, i + 1, j, m, n);
        sinkIsland(grid, i - 1, j, m, n);
        sinkIsland(grid, i, j + 1, m, n);
        sinkIsland(grid, i, j - 1, m, n);
    }
}
```

### Code Section Breakdown

| Code Section | Purpose |
|--------------|---------|
| Main loop over grid | Find unvisited land cells ('1's) |
| sinkIsland call | DFS to mark entire island as visited |
| Boundary checks | Ensure we stay within grid and only process land |
| Four directional recursion | Visit all connected land cells |

## 6. Complexity Analysis

### Time: O(m×n)
- Each cell is visited at most twice:
  - Once during the main grid scan
  - Once during a DFS if it's part of an island
- Total operations ≈ 2×m×n in worst case

### Space: O(m×n)
- No extra space for visited array (modified in-place)
- Recursion stack can go m×n deep in worst case
- For thin islands, stack depth is O(max(m,n))

## 7. Key Takeaway

- DFS/flood-fill is perfect for finding connected components in grids
- In-place modification eliminates need for visited array
- Linear time O(m×n) is optimal as we must check each cell
- Recursion provides elegant solution but watch stack space 