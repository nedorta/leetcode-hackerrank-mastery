# Koko Eating Bananas - Solution Explanation

## 1. Problem Statement

You have n piles of bananas, `piles[i]` bananas in the i-th pile, and `h` hours to finish. Each hour Koko eats `k` bananas from a single pile (or the remainder if fewer than `k`).

Task: Return the minimum integer `k` so Koko can eat all bananas within `h` hours. No division of piles between hours is allowed beyond choosing a single pile per hour.

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | piles = [3,6,7,11], h = 8 | 4 | At k=4: hours = ⌈3/4⌉+⌈6/4⌉+⌈7/4⌉+⌈11/4⌉ = 1+2+2+3 = 8 |
| 2 | piles = [30,11,23,4,20], h = 5 | 30 | Only k=30 lets you finish in 5 hours (one pile per hour) |
| 3 | piles = [30,11,23,4,20], h = 6 | 23 | k=23 ⇒ hours = 2+1+1+1+1 = 6; any slower speed needs >6 hours |

## 3. Binary Search on Speed

### Key Idea
- The predicate "canFinish(k) = true if hoursNeeded(k) ≤ h" is monotonic in k: larger k always lowers or equalizes required hours
- Binary search over k in [1..max(piles)] to find the smallest feasible speed

## 4. Code Walkthrough

```java
// 1) Find the maximum pile size to bound our search
int maxPile = 0;
for (int p : piles) maxPile = Math.max(maxPile, p);
int left = 1, right = maxPile;

// 2) Binary search loop on k
while (left < right) {
    int mid = left + (right - left) / 2;

    // Compute hours needed at speed = mid
    long hours = 0;
    for (int p : piles) {
        hours += (p + mid - 1) / mid;  // ceil(p/mid)
        if (hours > h) break;         // early exit if too slow
    }

    // 3) Narrow search range
    if (hours <= h) {
        // mid is fast enough → try slower
        right = mid;
    } else {
        // mid too slow → need faster
        left = mid + 1;
    }
}

// 4) left == right is the minimum feasible speed
return left;
```

### Code Section Breakdown

| Section | Purpose |
|---------|---------|
| Compute maxPile | The highest possible k is the largest pile (if k = maxPile, you finish in n hours) |
| `left = 1; right = maxPile;` | Speed must be at least 1 and at most maxPile |
| `mid = (left + right)/2` | Test speed = mid |
| `hours += (p + mid - 1) / mid;` | Calculate hours needed for each pile via ceiling division |
| `if (hours > h) break;` | Early stop if already too many hours |
| `if (hours <= h) right = mid; else left = mid + 1;` | Adjust bounds based on feasibility |
| `return left;` | After search, left is smallest feasible speed |

## 5. Complexity Analysis

### Time: O(n log m)
- where n = piles.length
- and m = max(piles)
- Each binary-search step costs O(n) to sum hours
- There are O(log m) steps

### Space: O(1)
- Only constant extra space needed
- Aside from input and output variables

## 6. Key Takeaway

When you need to find the minimum or maximum value satisfying a monotonic predicate (here, "finishes in ≤ h hours"), binary search on the answer is an efficient O(n log range) strategy—often dramatically faster than linear scanning. 