# Two Sum - Detailed Explanation

## 1. What the Problem is Asking

You have an array (list) of numbers, e.g. [2, 7, 11, 15], and a target number, e.g. 9.
Find two different positions in the array whose values add up exactly to the target, and return their indices (positions).

Indices are just the positions: in [2,7,11,15] the number 2 is at index 0, 7 at index 1, etc.

You may assume exactly one valid pair exists.

You cannot use the same element twice (so you can't pick index 1 and again index 1).

## 2. A Concrete Example

nums = [3, 2, 4]
target = 6

Check pairs:
- 3 + 2 = 5 (no)
- 3 + 4 = 7 (no)
- 2 + 4 = 6 (yes!)

So we return the indices [1, 2].

## 3. A First (Brute-Force) Idea

The most obvious way—especially if you've never seen this before—is to try every pair:
```
for i in 0 ... n-2:
  for j in i+1 ... n-1:
    if nums[i] + nums[j] == target:
      return [i, j]
```

- Time Complexity: O(n²), because for each of the n elements you might check up to another n elements.
- Space Complexity: O(1), since you only use a couple of extra variables.

Why it can be slow:
If n = 10,000, then n² = 100 million checks—this starts to take noticeable time.

## 4. The Fast (O(n)) Approach: Using a Map

### 4.1. Key Insight

Instead of looking forward for a match, keep track of what you'd need:
- When you see a number x, you'd like some earlier number y such that
  x + y = target ⇒ y = target – x.

So if you can instantly tell me whether you've seen y before, you're done.

A hash map (in Java: HashMap<Integer,Integer>) gives average O(1) lookup and insert.

### 4.2. How it Works

1. Create an empty map `seen` from number → index.
2. Loop through the array with index i and value x = nums[i]:
   a. Compute need = target – x.
   b. Ask the map: "Have we seen need already?"
   - Yes → we know seen.get(need) is the earlier index, so return [seen.get(need), i].
   - No → store x in the map with seen.put(x, i) and move on.
3. Since the problem guarantees one solution, you'll always return inside the loop.

## 5. Walkthrough of the Code

```java
public class Solution {
    public int[] twoSum(int[] nums, int target) {
        // 1) Prepare an empty map from value → index
        Map<Integer, Integer> seen = new HashMap<>(nums.length);

        // 2) Scan once through nums
        for (int i = 0; i < nums.length; i++) {
            int x    = nums[i];           // current number
            int need = target - x;        // what we'd like to have seen

            // 3) If 'need' is already in our map, we found our pair!
            if (seen.containsKey(need)) {
                return new int[]{ seen.get(need), i };
            }

            // 4) Otherwise, record x for future matches
            seen.put(x, i);
        }

        // Here we return the original array as a fallback.
        return nums;
    }
}

```

| Line(s) | What it does |
|---------|-------------|
| new HashMap<>(...) | Creates the map; sizing it up front avoids extra resizing. |
| for(...) | Single pass, i = 0 → n−1 |
| need = target - x | "What other number would make the pair sum to target?" |
| seen.containsKey(need) | "Have I encountered that number before?" |
| seen.put(x,i) | "Remember that x occurred at position i for future checks." |
| return […, …] | As soon as you find a valid pair, you return their indices. |

## 6. Complexity Summary

| Approach | Time | Extra Space |
|----------|------|-------------|
| Brute force | O(n²) | O(1) |
| Hash-map one-pass | O(n) | O(n) |

- O(n) time is optimal here—each element is processed once.
- O(n) extra space comes from storing up to n elements in the map.

## 7. Key Takeaways

- Brute force (double loop) always works but is slow for large n.
- Hash map transforms the problem into "can I look up a needed value instantly?"
- In Java, HashMap gives average O(1) insert/lookup.
- Because the problem guarantees one solution, you don't need to worry about "not found" at the end. 