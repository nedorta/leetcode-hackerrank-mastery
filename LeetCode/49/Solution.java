class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // Map from sorted-character signature → list of anagrams
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            // 1) Compute signature by sorting characters
            char[] arr = s.toCharArray();
            Arrays.sort(arr);
            String key = new String(arr);

            // 2) Group by signature
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        // 3) Collect all groups
        return new ArrayList<>(map.values());
    }
} 