package zest;

public class BestTimeToBuyAndSellStock {

    /**
     * Returns the maximum profit that can be achieved by
     * buying on one day and selling on a later day.
     *
     * @param prices array of stock prices
     * @return maximum possible profit, or 0 if no profit is possible
     * @throws IllegalArgumentException if prices is null or empty
     */
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty");
        }

        if (prices.length > Math.pow(10, 5)) {
            throw new IllegalArgumentException("Input array may not possess a length exceeding 10^5");
        }

        if (prices[0] < 0 || prices[0] > Math.pow(10, 4)) {
            throw new IllegalArgumentException("Input array may not include prices lower than 0 or greater than 10^4");
        }

        int minPrice = prices[0];

        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < 0 || prices[i] > Math.pow(10, 4)) {
                throw new IllegalArgumentException("Input array may not include prices lower than 0 or greater than 10^4");
            }

            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                int profit = prices[i] - minPrice;
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }

        return maxProfit;
    }

//    public static void main(String[] args) {
//        System.out.println(maxProfit(new int[]{5, 10, 15}));
//        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
//
//    }

}