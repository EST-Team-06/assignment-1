# Specification-based Testing
**Step 1: Understand requirements**
* Based on the requirements, we have a method that should provide us with the max profit we can earn, given a list of prices on different days.
* Input: List of prices (integers)
* Output: Max profit (integer)
* It should be calculated by checking all buy-sell possibilities, where buy prices must be strictly before sell prices.
* If there is no profit, the method should return 0.
* The method should also handle edge cases, namely:
  * single-day price array (array of length 1)
  * strictly increasing prices
  * strictly decreasing prices
  * multiple potential profit opportunities (since it is max profit, I assume this means equal max profits on different days)

**Step 2: Explore program if is not well-known**
* In the book, the author did simple unit tests to understand the problem but this is not required.
* I created a main method and ran some example cases
    ```
    System.out.println(maxProfit(new int[]{5, 10, 15})); 
    System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4})); 
    ```
* I got output:
    ``` 
    15
    5
    ```
* Since this was wrong, I checked the code and found a bug:
    ``` 
    int minPrice = 0;
    ```
* Since the loop starts at `i=1`, `minPrice` should be the first price and not 0. I fixed the bug and the output was correct:
    ``` 
    10
    5
    ```
* Since I understood how the code works now, I proceeded with Step 3.

**Step 3: Analyze properties of inputs and outputs, identify partitions**
* The input is an array of integers. The type is ensured by compiler, so we mainly care about:
  * Kind of integer (negative, positive, 0)
  * Length of array (empty, single element, multiple elements)
  * Order of elements (asc/desc order)
* We can assume that the order of elements only plays a role for multiple elements
* We can assume that in case of single element, we return 0.
* Requirements forbid negative prices, so we only need to check if it throws and error.

**Step 4: Analyze boundaries (on/off points)**
* We may check if the method throws an error for an array with elements more than 10^5
* We may check if the method throws an error for a price greater than 10^4

**Step 5: Devise concrete test cases**
* Considering the partitions and the edge cases, I came up with following test cases:
* T1: `maxProfit([1]) == 0`
* T2: `maxProfit([1, 5]) == 4`
* T3: `maxProfit([5, 1]) == 0`
* T4: `maxProfit([1, 5, 5]) == 4`
* T5: `maxProfit([-1]) == IllegalArgumentException`
* T6: `maxProfit([]) == IllegalArgumentException`
* T7.1: Array of 10^5 0s and ending with 1 == IllegalArgumentException
* T7.2: Array of (10^5 - 1) 0s and ending with 1 == 1
* T8.1: `maxProfit([10**4+1]) == IllegalArgumentException`
* T8.2: `maxProfit([0, 10**4]) == 10**4`


**Step 6: Implement concrete test cases with JUnit tests**
* A team member implemented tests for AddBinary and I mainly mimic their prom.xml. So we will not copy-paste the documentation of how we learned how to use the tools everywhere. Refer to AddBinary for that.
* To learn how to generate an array with repeated numbers, I found: https://stackoverflow.com/a/14276506
* After the first run, T5, T7.1 and T8.1 failed. Cases where the method should throw an error; because requirements forbid them, work.
* Usually you should argue this with the product owner but since requirements were explicitly stated as constraint, I decide to implement them and enforce them.
  * For the upper limits this makes sense; we prevent misuse of the method by throwing errors when the limit is exceeded.
  * For negative prices, you could argue they may occur in stock market (not a finance bro though)

* While fixing the code, I realized that while I check for empty list, I do not check for null list, so I added
* T9: `maxProfit(null) == IllegalArgumentException`

* I organized my tests in different methods and tests of similar meaning, I placed in the same method.
  * validCases: T1, T2, T3, T4
  * negativePrices: T5
  * noPrices: T6, T9
  * upperPriceArrayBound: T7.1, T7.2
  * upperPriceBound: T8.1, T8.2

**Step 7: Use creativity and experience to augment test suite**
* The tests prior where based on systematic approach.
* Based on my creativity, I also added tests that felt a bit more realistic edge case
* T10: `maxProfit([2, 5, 2, 5]) == 3`
* This case should be covered by T4 but is slightly different, we get profits based buys on different days.
  * This does not make a difference for the method, as it returns the profit without providing info which specific days.
  * Still, I thought it would be worth checking just to be sure.
* T11: `maxProfit([5, 4, 3, 1, 2]) == 1`
  * This should be covered by T2, I preferred having more than two values.
