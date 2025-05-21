public class Solution {
    public int maxProfit(int[] prices) {
        // Edge case: if there's only one day, no transaction is possible
        if (prices.length < 2) {
            return 0;
        }

        // minPrice holds the lowest price we've seen so far (best day to buy up to today)
        int minPrice = prices[0];
        // maxProfit holds the best profit we've been able to achieve so far
        int maxProfit = 0;

        // Iterate over each day starting from day 1 (since day 0 is our initial buy)
        for (int i = 1; i < prices.length; i++) {
            int todayPrice = prices[i];

            // 1) Calculate potential profit if we sold today:
            //    profit = todayPrice − minPrice
            //    If this is higher than our current maxProfit, update it.
            int potentialProfit = todayPrice - minPrice;
            if (potentialProfit > maxProfit) {
                maxProfit = potentialProfit;
            }

            // 2) Update the lowest buying price:
            //    If today's price is lower than any we've seen before,
            //    it becomes the new minPrice (best future buy candidate).
            if (todayPrice < minPrice) {
                minPrice = todayPrice;
            }
        }

        // After scanning all days, maxProfit holds the best single-transaction profit.
        // If the prices always went down, maxProfit remains 0 (we never update it).
        return maxProfit;
    }
} 