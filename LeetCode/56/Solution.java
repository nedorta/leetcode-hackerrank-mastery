import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Solution {
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

        // Convert list back to array
        return merged.toArray(new int[merged.size()][]);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test case 1
        int[][] intervals1 = {{1,3}, {2,6}, {8,10}, {15,18}};
        int[][] result1 = solution.merge(intervals1);
        System.out.println("Test case 1: " + Arrays.deepToString(result1));  // Expected: [[1,6],[8,10],[15,18]]
        
        // Test case 2
        int[][] intervals2 = {{1,4}, {4,5}};
        int[][] result2 = solution.merge(intervals2);
        System.out.println("Test case 2: " + Arrays.deepToString(result2));  // Expected: [[1,5]]
    }
} 