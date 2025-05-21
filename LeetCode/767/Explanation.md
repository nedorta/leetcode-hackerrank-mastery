# Reorganize String - Solution Explanation

## 1. Problem Statement

Given a string s of lowercase English letters, rearrange its characters so that no two adjacent characters are the same. If it's impossible to do so, return the empty string "". Any valid rearrangement is acceptable when multiple answers exist.

## 2. Examples

| Example | Input  | Output | Explanation |
|---------|--------|--------|-------------|
| 1 | "aab"  | "aba"  | You can interleave a and b to avoid adjacency. |
| 2 | "aaab" | ""     | Three a's and one b ⇒ at least two a's must be adjacent. |

## 3. Why a Brute-Force Doesn't Work

Trying every permutation of s and checking adjacency is O(n!⋅n) in the worst case—unthinkable for n up to 500.

## 4. Optimal Greedy Approach with a Max-Heap

### Key Idea

1. Count how often each character appears.
2. Always take the two characters with the highest remaining counts and append them in sequence—this avoids placing the same character back-to-back.
3. Decrease their counts and reinsert them into the heap if they still have leftover occurrences.
4. At the end, at most one character (with count 1) may remain; append it if possible, or fail if its count > 1.

Because there are only 26 possible letters, each heap operation is O(log 26) = O(1), making the overall algorithm effectively O(n) time.

## 5. Walkthrough of the Code

```java
// 1) Count character frequencies
int[] freq = new int[26];
for (char c : s.toCharArray()) {
    freq[c - 'a']++;
}

// 2) Build max-heap of (charIndex, count) by descending count
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
for (int i = 0; i < 26; i++) {
    if (freq[i] > 0) {
        pq.offer(new int[]{i, freq[i]});
    }
}

StringBuilder result = new StringBuilder();

// 3) Greedy pairing of top two characters
while (pq.size() > 1) {
    int[] first  = pq.poll();
    int[] second = pq.poll();

    // Place them next to each other
    result.append((char)(first[0] + 'a'));
    result.append((char)(second[0] + 'a'));

    // Decrement counts and reinsert if needed
    if (--first[1] > 0)  pq.offer(first);
    if (--second[1] > 0) pq.offer(second);
}

// 4) Handle a leftover character, if any
if (!pq.isEmpty()) {
    int[] last = pq.poll();
    // If more than one remains, we can't separate them ⇒ fail
    if (last[1] > 1) return "";
    result.append((char)(last[0] + 'a'));
}

return result.toString();
```

Key points:
- Heap entries are arrays [charIndex, remainingCount].
- Polling the heap gives the two most frequent characters.
- Appending them ensures no identical neighbors.
- Reinserting with decremented counts continues the process greedily.
- Leftover check guarantees validity of the final sole character.

## 6. Complexity Analysis

- Time: O(n log k), where k ≤ 26 is the alphabet size. Since log k is constant, this is effectively O(n).
- Space: O(n + k) = O(n), for the result plus the heap and frequency array.

## 7. Key Takeaway

When you must arrange elements so that no two identical items become adjacent, a greedy pairing using a max-heap (or counting sort + pairing for a known small alphabet) often yields a linear-time solution by always "pulling" the most frequent items apart. 