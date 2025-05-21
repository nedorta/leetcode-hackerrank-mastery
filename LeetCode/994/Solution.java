import java.util.*;

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