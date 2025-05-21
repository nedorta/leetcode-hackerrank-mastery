class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        // 1) Establish search range [1 .. maxPile]
        int maxPile = 0;
        for (int p : piles) {
            maxPile = Math.max(maxPile, p);
        }
        int left = 1, right = maxPile;

        // 2) Binary search on k
        while (left < right) {
            int mid = left + (right - left) / 2;
            // Compute hours needed at speed = mid
            long hours = 0;
            for (int p : piles) {
                hours += (p + mid - 1) / mid; // ceiling division
                if (hours > h) break;        // can stop early
            }
            // 3) Adjust search based on feasibility
            if (hours <= h) {
                // mid is feasible: try slower (smaller) speed
                right = mid;
            } else {
                // mid is too slow: need faster speed
                left = mid + 1;
            }
        }
        // 4) left == right is the smallest feasible k
        return left;
    }
} 