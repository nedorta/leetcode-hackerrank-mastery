# Climbing Stairs - Detailed Explanation

## 1. Problem Statement

You stand at the bottom of a staircase with n steps.
Each move you make, you can climb either 1 step or 2 steps.

Question: In how many distinct ways can you reach the top (step n)?

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|--------|---------|------------|
| 1 | n = 2 | 2 | Ways: (1+1), (2) |
| 2 | n = 3 | 3 | Ways: (1+1+1), (1+2), (2+1) |
| 3 | n = 4 | 5 | Ways: (1+1+1+1), (1+1+2), (1+2+1), (2+1+1), (2+2) |

## 3. Brute-Force Thought (Exponential time)

You could imagine at each step you branch into "take 1" or "take 2," and explore every possible sequence:

```java
countWays(pos):
  if pos == n: return 1       // exactly reached top
  if pos  > n: return 0       // overshot
  return countWays(pos+1) + countWays(pos+2)
```

Time: ~O(2ⁿ) (explosive growth).

Why bad: n up to 45 ⇒ 2⁴⁵ calls is impossibly large.

## 4. Optimal O(n) Approach: Dynamic Programming (Fibonacci)

### 4.1. Key Insight

Let f(i) = "number of ways to reach step i."
To land on step i:

- Your last move was a 1-step from i−1 ⇒ f(i−1) ways
- OR a 2-step from i−2 ⇒ f(i−2) ways

So the recurrence is:

f(i) = f(i−1) + f(i−2)

This is exactly the Fibonacci sequence, with base cases:
- f(0) = 1 (one way to "stay" at the bottom)
- f(1) = 1 (only one 1-step move to reach step 1)

### 4.2. Memory-Efficient Iteration

You only ever need the last two values to compute the next one. So you can use three variables:
- twoStepsBefore = f(i−2)
- oneStepBefore = f(i−1)
- currentWays = f(i)

and slide them forward in a loop from i = 2 up to n.

## 5. Walkthrough of the Code

```java
public class Solution {
    public int climbStairs(int n) {
        // If n is 0 or 1, there's exactly one way (either do nothing, or one 1-step).
        if (n <= 1) {
            return 1;
        }

        // Initialize the "window" for i = 0 and i = 1:
        int twoStepsBefore = 1;  // f(0)
        int oneStepBefore  = 1;  // f(1)
        int currentWays    = 0;  // will hold f(i) each iteration

        // Build f(2) through f(n)
        for (int i = 2; i <= n; i++) {
            // Compute f(i) = f(i−1) + f(i−2)
            currentWays = oneStepBefore + twoStepsBefore;

            // Slide the window forward:
            //    what was f(i−1) becomes f(i−2) next time
            //    what was f(i)   becomes f(i−1) next time
            twoStepsBefore = oneStepBefore;
            oneStepBefore  = currentWays;
        }

        // After the loop, currentWays == f(n)
        return currentWays;
    }
}
```

| Line(s) | Explanation |
|---------|-------------|
| if (n <= 1) return 1; | Base cases: 0 or 1 step ⇒ exactly one way |
| twoStepsBefore = 1 | Number of ways to reach step 0 (standing still) |
| oneStepBefore = 1 | Number of ways to reach step 1 (one 1-step) |
| for (i = 2; i <= n; i++) | Compute each next f(i) up to f(n) |
| currentWays = one + two | Recurrence: f(i) = f(i−1) + f(i−2) |
| Slide window | Shift f(i−1) → f(i−2), f(i) → f(i−1) for next iteration |
| return currentWays; | At the end, holds the total number of ways to reach step n |

## 6. Complexity Analysis

- Time: O(n) — exactly one loop from 2 to n.
- Space: O(1) — only three integer variables, regardless of n.

## 7. Key Takeaway

- Whenever you see "you can do 1 step or 2 steps" (or any small fixed set of choices), look for a linear recurrence (dynamic programming).
- You often only need the last few values to compute the next one—so you can collapse O(n) memory down to O(1).

This pattern applies to many "counting paths" or "ways to make change" problems—recognize the recurrence, initialize your base cases, and iterate! 