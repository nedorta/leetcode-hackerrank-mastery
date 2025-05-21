# Best Time to Buy and Sell Stock - Detailed Explanation

## 1. Problem Statement

You have an array prices where prices[i] is the price of a stock on day i.
You may complete at most one transaction: choose a day to buy and a later day to sell.

Goal: Return the maximum profit you can achieve. If no profit is possible, return 0.

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|--------|---------|-------------|
| 1 | [7,1,5,3,6,4] | 5 | Buy at 1 (day 1), sell at 6 (day 4): profit = 6−1 = 5. |
| 2 | [7,6,4,3,1] | 0 | Prices only go down ⇒ best is no transaction ⇒ profit = 0. |

## 3. Brute-Force Thought (O(n²) time)

You could try every pair of days (i, j) with j > i:

```java
maxProfit = 0;
for (i = 0; i < n; i++) {
  for (j = i+1; j < n; j++) {
    maxProfit = max(maxProfit, prices[j] - prices[i]);
  }
}
return maxProfit;
```

- Time: O(n²) — too slow for n up to 10⁵ (10¹⁰ operations!).
- Space: O(1).

## 4. Optimal O(n) Approach: Single Pass Tracking Min Price

### Key Insight

1. Keep track of the cheapest buy price seen so far (minPrice).
2. At each day i, imagine selling on day i: profit = prices[i] - minPrice.
3. Update your maxProfit if this profit is larger.
4. Also update minPrice whenever you see a new lower price.

This way you only scan once (O(n)) and use O(1) extra space.

## 5. Walkthrough of the Code

```java
public class Solution {
    public int maxProfit(int[] prices) {
        // Edge case: if there's only one day, no transaction is possible
        if (prices.length < 2) {
            return 0;
        }

        // 1) minPrice = lowest price seen so far (best day to buy up to today)
        int minPrice = prices[0];
        // 2) maxProfit = best profit found so far
        int maxProfit = 0;

        // 3) Iterate from day 1 to the end
        for (int i = 1; i < prices.length; i++) {
            int todayPrice = prices[i];

            // a) Compute profit if we sold today at todayPrice
            int potentialProfit = todayPrice - minPrice;
            if (potentialProfit > maxProfit) {
                // Update maxProfit when a better profit is found
                maxProfit = potentialProfit;
            }

            // b) Update minPrice if today's price is lower
            if (todayPrice < minPrice) {
                minPrice = todayPrice;
            }
        }

        // 4) Return the best profit (0 if never profitable)
        return maxProfit;
    }
}
```

| Line(s) | Explanation |
|---------|-------------|
| if (prices.length < 2) | If only one day (or none), you can't buy and sell ⇒ profit = 0. |
| minPrice = prices[0] | Initialize the cheapest buy price to the first day's price. |
| maxProfit = 0 | We haven't made any profit yet. |
| Loop from i = 1 to n-1 | For each new day, consider selling today or update the best buy price. |
| potentialProfit = today − minPrice | Profit if you had bought at the cheapest earlier day and sold today. |
| Update maxProfit | Keep the highest profit seen. |
| Update minPrice | If today's price is lower than any before, use it as a new buy candidate. |
| return maxProfit | After the loop, holds the maximum achievable profit (or 0). |

## 6. Complexity Analysis

- Time: O(n) — one pass through the array.
- Space: O(1) — only two extra integer variables.

## 7. Key Takeaway

- When you must find a max difference subject to order (buy before sell), track the minimum element seen so far and compute potential profit on the fly.
- This pattern transforms an O(n²) brute force into a linear-time O(n) solution with constant space. 