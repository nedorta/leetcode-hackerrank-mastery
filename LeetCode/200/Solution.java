class Solution {
    public int numIslands(char[][] grid) {
        int m = grid.length;
        if (m == 0) return 0;
        int n = grid[0].length;
        int islands = 0;

        // 1) Scan every cell in the grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 2) When we find an unvisited land cell...
                if (grid[i][j] == '1') {
                    islands++;                // a new island!
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
        // Boundary and water check
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] != '1') {
            return;
        }
        // Mark current land as water
        grid[i][j] = '0';

        // Explore all four directions
        sinkIsland(grid, i + 1, j, m, n);
        sinkIsland(grid, i - 1, j, m, n);
        sinkIsland(grid, i, j + 1, m, n);
        sinkIsland(grid, i, j - 1, m, n);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case 1
        char[][] grid1 = {
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        System.out.println("Test case 1: " + solution.numIslands(grid1)); // Expected: 1

        // Test case 2
        char[][] grid2 = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        System.out.println("Test case 2: " + solution.numIslands(grid2)); // Expected: 3
    }
} 