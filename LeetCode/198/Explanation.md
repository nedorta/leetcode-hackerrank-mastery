# House Robber - Solution Explanation

## 1. Problem Statement

You have a row of houses, each with some money inside. You want to maximize the total money you rob, but you cannot rob two adjacent houses (alarm systems are linked). Given an array `nums` where `nums[i]` is the amount in the i<sup>th</sup> house, return the maximum you can rob.

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | [1,2,3,1] | 4 | Rob houses 0 and 2 ⇒ 1 + 3 = 4. |
| 2 | [2,7,9,3,1] | 12 | Rob houses 1, 2, and 4 ⇒ 2 + 9 + 1 = 12 or 0,2,4: same. |

## 3. Brute-Force Thought (Exponential)

Try every subset of non-adjacent houses:

```java
maxLoot(i):
  if i >= n: return 0
  // Option 1: skip house i
  skip = maxLoot(i + 1)
  // Option 2: rob house i, then skip i+1
  rob  = nums[i] + maxLoot(i + 2)
  return max(skip, rob)
```

- Time: O(2ⁿ) — branches at each house
- Why bad: nums.length can be up to 100 ⇒ 2¹⁰⁰ is infeasible

## 4. Optimal O(n) Approach: Iterative DP with Two Variables

### 4.1. Key Insight

At each house i, you have two states:
- `prevNoRob` = maximum loot up to the previous house if you did not rob it
- `prevRob` = maximum loot up to the previous house if you did rob it

When you arrive at house i with nums[i]:
- Rob i ⇒ you cannot have robbed i-1, so your loot = prevNoRob + nums[i]
- Skip i ⇒ you take the best you had at i-1, which is max(prevNoRob, prevRob)

Update those two variables in one pass.

## 5. Walkthrough of the Code

```java
int prevNoRob = 0;  // best loot if we skip the previous house
int prevRob   = 0;  // best loot if we robbed the previous house

for (int amount : nums) {
    // If we rob this house:
    int newRob = prevNoRob + amount;

    // If we skip this house:
    int newNoRob = Math.max(prevNoRob, prevRob);

    // Shift forward for next house:
    prevNoRob = newNoRob;
    prevRob   = newRob;
}

// Final answer: best of robbing or skipping the last house
return Math.max(prevNoRob, prevRob);
```

### Step-by-Step Breakdown

| Step | What's happening |
|------|-----------------|
| `newRob = prevNoRob + amt` | Rob current house ⇒ add its money to loot when last was skipped. |
| `newNoRob = max(prevNoRob, prevRob)` | Skip current ⇒ carry forward best previous loot. |
| Shift variables | Prepare for the next iteration (house). |
| Final `max(...)` | Choose the better of robbing or skipping the last house. |

## 6. Complexity Analysis

### Time: O(n)
- One pass through the array of length n

### Space: O(1)
- Only two (constant) pairs of integer variables

## 7. Key Takeaway

By keeping just two running totals—the best loot if you robbed the last house versus if you skipped it—you can decide in O(1) time per house whether to rob or skip, achieving an overall O(n) solution with constant extra space. 