# Put Marbles in Bags - Detailed Explanation

## 1. Problem Statement
You have k bags and an array weights[0…n-1] of non‐negative integers. You must split the marbles into k non‐empty contiguous bags. If a bag covers indices [i…j], its cost is `weights[i] + weights[j]`.

The score is the sum of all k bag‐costs. Return (maximum possible score) − (minimum possible score).

## 2. Examples

| Example | weights | k | Output | Explanation |
|---------|---------|---|--------|-------------|
| 1 | [1,3,5,1] | 2 | 4 | The only cut‐candidates are the gaps → compute S = [1+3, 3+5, 5+1] = [4,8,6].<br/>Pick one gap for each distribution:<br/>– Min when picking S[0]=4 → total difference = …→6<br/>– Max when picking S[1]=8 → total …→10<br/>Difference = 10–6 = 4. |
| 2 | [1,3] | 2 | 0 | Only one gap S = [4], so max = min = 4 → difference = 0. |

## 3. Solution Approaches

### Brute‐Force Approach (Exponential)
- **Idea**: Try every choice of the k−1 cut positions among the n−1 gaps, compute the bag‐cost sum, track min and max.
- **Cost**: (n−1 choose k−1) possibilities ⇒ exponential in n.
- **Why it fails**: Even moderate n (say n=30) makes this impossible in reasonable time.

### Sorting‐Based O(n log n) Solution
1. Build an array S[i] = weights[i] + weights[i+1], i=0…n−2 of length n−1.
2. Sort S in ascending order.
3. The minimum score uses the k−1 smallest elements of S; the maximum uses the k−1 largest.
4. Return sum(k−1 largest) - sum(k−1 smallest)

```java
int[] S = new int[n-1];
for (int i = 0; i < n-1; i++) S[i] = weights[i] + weights[i+1];
Arrays.sort(S);
long minSum=0, maxSum=0;
for(int i=0;i<k-1;i++){
    minSum += S[i];
    maxSum += S[S.length-1-i];
}
return maxSum - minSum;
```

- **Time**: O(n log n) to sort
- **Space**: O(n) for array S

### Heap‐Based O(n log k) Alternative
If k≪n, you can avoid sorting all n−1 gaps by keeping only the top/bottom k−1 in two heaps:
1. A max-heap of size ≤ k−1 to track the k−1 smallest gaps (for minSum)
2. A min-heap of size ≤ k−1 to track the k−1 largest gaps (for maxSum)

```java
PriorityQueue<Integer> small = new PriorityQueue<>(Comparator.reverseOrder());
PriorityQueue<Integer> large = new PriorityQueue<>();
for (int i = 0; i < n-1; i++) {
    int x = weights[i] + weights[i+1];
    small.offer(x);
    if (small.size()>k-1) small.poll();
    large.offer(x);
    if (large.size()>k-1) large.poll();
}
long minSum=0, maxSum=0;
for(int v: small) minSum += v;
for(int v: large) maxSum += v;
return maxSum - minSum;
```

- **Time**: O(n log k) — each of the n−1 pushes/polls costs O(log k)
- **Space**: O(k) for the two heaps + O(1) extra

## 4. Implementation Details
Our implementation uses the heap-based approach for better efficiency when k is much smaller than n:

```java
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
        // minHeap keeps the k−1 largest S[i]
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int x : S) {
            maxHeap.offer(x);
            if (maxHeap.size() > k - 1) maxHeap.poll();
            minHeap.offer(x);
            if (minHeap.size() > k - 1) minHeap.poll();
        }

        long minSum = 0, maxSum = 0;
        for (int x : maxHeap) minSum += x;
        for (int x : minHeap) maxSum += x;

        return maxSum - minSum;
    }
}
```

## 5. Complexity Analysis

| Approach | Time | Extra Space |
|----------|------|-------------|
| Brute‐Force (all cuts) | Exponential | O(1) |
| Sort‐Based (sort S) | O(n log n) | O(n) |
| Heap‐Based | O(n log k) | O(k) |

When k is small relative to n, the heap-based method outperforms full sorting.

## 6. Key Takeaway
By reducing the problem to selecting k−1 gap-values (weights[i]+weights[i+1]), you transform an exponential partition problem into either:
1. A sort + two partial sums in O(n log n), or
2. A pair of fixed-size heaps in O(n log k) if you only need the top/bottom k−1

Both run in linearithmic time, with the heap approach saving time and memory when k≪n. 