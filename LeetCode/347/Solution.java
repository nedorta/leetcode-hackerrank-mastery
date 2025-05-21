import java.util.*;

public class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // 1) Build frequency map: num → count
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) {
            freq.put(x, freq.getOrDefault(x, 0) + 1);
        }

        // 2) Min-heap ordered by frequency (smallest count at the top)
        PriorityQueue<Map.Entry<Integer,Integer>> heap =
            new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        // 3) Maintain heap of size k
        for (Map.Entry<Integer,Integer> e : freq.entrySet()) {
            heap.offer(e);
            if (heap.size() > k) {
                heap.poll();  // remove the entry with smallest frequency
            }
        }

        // 4) Extract results; heap has k highest-frequency entries, but in
        //    ascending order, so we fill result from back to front.
        int[] res = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            res[i] = heap.poll().getKey();
        }
        return res;
    }
} 