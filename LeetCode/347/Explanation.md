# Top K Frequent Elements - Solution Explanation

## 1. Problem Statement

Given an integer array `nums` and an integer `k`.
Task: Return the `k` most frequent elements in `nums`. You may return the answer in any order. Your solution must use better than O(n log n) time on average.

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | nums = [1,1,1,2,2,3], k = 2 | [1,2] | 1 occurs 3×, 2 occurs 2×—the top two. |
| 2 | nums = [1], k = 1 | [1] | Only one unique element. |

## 3. Brute-Force Thought (Why It Fails)

A naive approach might be to:
1. Sort the array: O(n log n)
2. Count frequencies while iterating: O(n)
3. Sort unique elements by frequency: O(u log u) where u is unique elements

Problems:
- Overall time complexity O(n log n)
- Fails to meet the requirement of better than O(n log n)
- Wastes time sorting the entire array when we only need top k
- Requires multiple passes over the data

## 4. Optimal O(n log k) Approach: Min-Heap

### 4.1. Key Idea

1. Count frequencies of each unique number using a hash map (num → count).

2. Maintain a min-heap (priority queue) of size ≤ k, ordered by frequency ascending.

3. Iterate through each (num, count) entry:
   - Push it onto the heap
   - If heap size exceeds k, pop the smallest-frequency entry

4. At the end, the heap contains the k most frequent elements.
   Pop them into an array (from back to front to restore any order).

## 5. Walkthrough of the Code

```java
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

        // 4) Extract results; heap has k highest-frequency entries
        int[] res = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            res[i] = heap.poll().getKey();
        }
        return res;
    }
}
```

### Code Section Breakdown

| Code Section | Purpose |
|--------------|---------|
| Map<Integer,Integer> freq = new HashMap<>(); | Create a map to count how many times each number appears. |
| for (int x : nums) { ... } | Populate freq by incrementing the count for each array element. |
| new PriorityQueue<>(Comparator.comparingInt(...)); | Initialize a min-heap that will order map entries by their frequency (value) ascending. |
| for (Map.Entry<Integer,Integer> e : freq.entrySet()) { ... } | For each unique number and its count: add to heap, then if size > k, remove the smallest. |
| heap.poll(); | When heap grows beyond k, evict the entry with the lowest frequency to keep only top k candidates. |
| int[] res = new int[k]; for (int i = k - 1; i >= 0; i--) { ... } | Extract the remaining k entries (most frequent) into the result array, filling from end to front. |
| res[i] = heap.poll().getKey(); | Store each key (the number) in the result. |

## 6. Complexity Analysis

### Time: O(n log k)
- Building the frequency map: O(n)
- Heap operations: for u unique keys, each offer/poll is O(log k), so O(u log k)
  - Since u ≤ n, this is O(n log k)
- Final extraction of k items: O(k log k) ≤ O(n log k)
- Overall O(n + n log k) → O(n log k), which is better than O(n log n) when k ≪ n

### Space: O(u + k)
- HashMap for frequencies: O(u)
- Heap of size ≤ k: O(k)
- Total O(u + k) extra space, where u = number of unique elements

## 7. Key Takeaway

- When you need the top-k frequent items in less than O(n log n) time:
  - Use a size-k min-heap over the frequency map
  - Maintain just the k most frequent candidates
  - Achieves O(n log k) time and O(u + k) space
- The min-heap approach is particularly efficient when k is much smaller than n
- HashMap + Heap combination is a common pattern for frequency-based problems 