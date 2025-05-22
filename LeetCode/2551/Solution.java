public class Solution {
    public long putMarbles(int[] weights, int k) {
        int n = weights.length;
        if (k == 1) return 0L;

        // Build the gap-array S of length n−1
        int[] S = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            S[i] = weights[i] + weights[i + 1];
        }

        // maxHeap keeps the k−1 smallest S[i]
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        // minHeap keeps the k−1 largest  S[i]
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int x : S) {
            // maintain k−1 smallest in maxHeap
            maxHeap.offer(x);
            if (maxHeap.size() > k - 1) {
                maxHeap.poll();
            }
            // maintain k−1 largest in minHeap
            minHeap.offer(x);
            if (minHeap.size() > k - 1) {
                minHeap.poll();
            }
        }

        long minSum = 0, maxSum = 0;
        // sum all in maxHeap → k−1 smallest
        for (int x : maxHeap) minSum += x;
        // sum all in minHeap → k−1 largest
        for (int x : minHeap) maxSum += x;

        return maxSum - minSum;
    }
} 