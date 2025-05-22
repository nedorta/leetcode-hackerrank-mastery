class Solution {
    public int maxRemoval(int[] nums, int[][] queries) {
        int n = nums.length, m = queries.length;

        // 1) Sort queries by their start index ascending
        Arrays.sort(queries, Comparator.comparingInt(a -> a[0]));

        // 2) Difference array to track current coverage at each index
        int[] diff = new int[n + 1];

        // 3) Max-heap of end-indices for queries that have started but not yet used up
        PriorityQueue<Integer> available = new PriorityQueue<>(Comparator.reverseOrder());

        int used = 0;    // number of queries we actually apply
        int qi   = 0;    // pointer into sorted queries[]

        // 4) Sweep index i from 0 to n–1
        for (int i = 0; i < n; i++) {
            // Apply any previous diff updates to get current coverage
            diff[i] += diff[i - 1 < 0 ? 0 : i - 1];  // prefix sum
            int coverage = diff[i];

            // Add all queries whose start == i into available
            while (qi < m && queries[qi][0] == i) {
                available.offer(queries[qi][1]);
                qi++;
            }

            // While coverage < demand, pick the interval that ends latest
            while (coverage < nums[i]) {
                if (available.isEmpty() || available.peek() < i) {
                    // No interval can cover i ⇒ impossible
                    return -1;
                }
                int end = available.poll();
                // "Use" this interval: increment coverage on [i..end]
                coverage++;
                used++;
                diff[i]++;         // start counting coverage from i
                diff[end + 1]--;   // stop counting past end
            }
            // Store back updated coverage for next iteration
            diff[i] = coverage;
        }

        // We applied 'used' queries ⇒ can remove the other m – used
        return m - used;
    }
} 