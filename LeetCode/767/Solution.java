import java.util.PriorityQueue;

class Solution {
    public String reorganizeString(String s) {
        int n = s.length();
        // 1) Count frequencies of each character
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // 2) Build a max-heap (priority queue) ordered by remaining count
        //    Each entry is an array [charIndex, count]
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> b[1] - a[1]  // highest count first
        );
        //    Only add characters that actually appear
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                pq.offer(new int[]{i, freq[i]});
            }
        }

        StringBuilder result = new StringBuilder();

        // 3) Greedily always take the two most frequent remaining characters
        while (pq.size() > 1) {
            int[] first  = pq.poll();  // most frequent
            int[] second = pq.poll();  // second most frequent

            // Append one instance of each to avoid adjacency
            result.append((char) (first[0] + 'a'));
            result.append((char) (second[0] + 'a'));

            // Decrease their counts and re-add if still positive
            if (--first[1] > 0) {
                pq.offer(first);
            }
            if (--second[1] > 0) {
                pq.offer(second);
            }
        }

        // 4) If one character remains, it can only be placed if count == 1
        if (!pq.isEmpty()) {
            int[] last = pq.poll();
            if (last[1] > 1) {
                // More than one of the same character cannot be separated
                return "";
            }
            result.append((char) (last[0] + 'a'));
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test cases
        System.out.println(solution.reorganizeString("aab"));   // Expected: "aba"
        System.out.println(solution.reorganizeString("aaab"));  // Expected: ""
    }
} 