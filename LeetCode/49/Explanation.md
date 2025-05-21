# Group Anagrams - Solution Explanation

## 1. Problem Statement

You're given an array of strings `strs`.
Task: Group the strings that are anagrams of each other (i.e., one can be rearranged to form the other) into lists. Return the collection of these lists in any order.

## 2. Examples

| Example | Input | Output | Explanation |
|---------|--------|--------|-------------|
| 1 | `["eat","tea","tan","ate","nat","bat"]` | `[["bat"],["nat","tan"],["ate","eat","tea"]]` | "eat","tea","ate" share letters; "tan"/"nat"; "bat" alone |
| 2 | `[""]` | `[[""]]` | A single empty string is its own anagram group |
| 3 | `["a"]` | `[["a"]]` | Single-character string forms one group |

## 3. Sorting-Signature Hashing Approach

### Key Idea

- For each string, sort its characters alphabetically to generate a signature
- All anagrams share the same signature
- Use a hash map from signature → list of original strings
- Finally, return all the mapped lists

This runs in O(N · K log K) time (N = number of strings, K = max string length) and uses O(N · K) space.

## 4. Implementation Details

```java
Map<String, List<String>> map = new HashMap<>();
for (String s : strs) {
    char[] arr = s.toCharArray();
    Arrays.sort(arr);                   // sort characters
    String key = new String(arr);       // signature

    map.computeIfAbsent(key, k -> new ArrayList<>())
       .add(s);                         // group by signature
}
return new ArrayList<>(map.values());  // collect grouped anagrams
```

### Code Breakdown

| Code Section | Explanation |
|-------------|-------------|
| `Arrays.sort(arr); String key = ...` | Sort each string's characters to form a canonical key shared by its anagrams |
| `map.computeIfAbsent(key, ...).add(s)` | Append the original string s to its signature's list, creating the list if needed |
| `new ArrayList<>(map.values())` | Gather all value-lists (anagram groups) into the final result |

## 5. Complexity Analysis

### Time Complexity: O(N · K log K)
- N = number of input strings
- K = maximum string length
- Sorting each string takes O(K log K)
- We do this N times

### Space Complexity: O(N · K)
- We store all input strings across:
  - The hash‐map
  - Output lists
- Total space proportional to input size

## 6. Key Takeaway

By converting each string into a sorted signature, you can group all anagrams in linear pass via a hash map. This pattern—canonically normalizing elements to a key—is widely applicable for grouping by equivalence classes in O(N·T) time, where T is the cost of normalization. 