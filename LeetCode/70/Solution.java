public class Solution {
    public int climbStairs(int n) {
        // Base cases:
        // If there's 0 or 1 step, there's exactly 1 way to "climb" it.
        if (n <= 1) {
            return 1;
        }

        // twoStepsBefore  = number of ways to reach step i–2 (initially f(0) = 1)
        // oneStepBefore   = number of ways to reach step i–1 (initially f(1) = 1)
        int twoStepsBefore = 1;
        int oneStepBefore = 1;
        int currentWays = 0;  // will hold f(i) in each iteration

        // Build from step 2 up to step n
        for (int i = 2; i <= n; i++) {
            // The recurrence: to get to step i, you either
            //   • take one step from i–1 (oneStepBefore)
            //   • take two steps from i–2 (twoStepsBefore)
            currentWays = oneStepBefore + twoStepsBefore;

            // Shift our window forward:
            //   • what was "one step before" becomes "two steps before" next round
            //   • and the newly computed f(i) becomes the "one step before"
            twoStepsBefore = oneStepBefore;
            oneStepBefore = currentWays;
        }

        // After the loop, currentWays == f(n)
        return currentWays;
    }
} 