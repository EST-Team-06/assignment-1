# Specification-based Testing
## Step 1: Understand Requirements
### Business Rules
The goal of this program is to sort an array of three distinct values in-place so that all elements of the same color are adjacent and appear in the order: red (`0`), white (`1`), blue (`2`). The array must contain between `1` and `300` elements, which can only be `0`,`1` or `2`.
### Inputs
- `nums`: An array of three distinct integers (`0`, `1`, `2`) with length between `1` and `300`.
### Outputs
Technically nothing, as the array needs to be sorted in-place.

## Step 2: Explore Program
It is clear from the requirements how the function should behave, nonetheless we've called it with the example input listed in [README](README.md), and it returned the expected output of `[0,0,1,1,2,2]`:
```java
int[] nums = {2,0,2,1,1,0};
sortColors(nums);
System.out.println(Arrays.toString(nums));
```

## Step 3: Analyze Inputs, Outputs and Identify Partitions
Since there is only one input, we don't need to test combinations of inputs.

### Input: `nums`
Possible classes of this input:
- Array containing exactly one element/color
- Array containing `2 ≤ n ≤ 300` elements
- Array containing the maximum allowed number of elements (`300`)
- Array where all elements are the same color (`0` or `1` or `2`)
- Array containing exactly two distinct colors
- Array containing all three possible colors
- Array that is already sorted in the required order (all `0`s, then `1`s, then `2`s)
- Array that is sorted in reverse order (all `2`s, then `1`s, then `0`s)
- Array with an alternating pattern of values
- Unsorted array
- Empty array (invalid input)
- Array containing more than 300 elements (invalid input)
- Array containing at least one value different from `0`, `1`, or `2` (invalid input)
- Null (invalid input)

### Output
- Sorted array containing only one color (all elements are `0`, or all `1`, or all `2`)
- Sorted array containing exactly two colors (only `0` and `1`, only `0` and `2`, or only `1` and `2`)
- Sorted array containing all three colors (`0`, `1`, and `2`)

## Step 4: Analyze boundaries
We can subdivide the boundaries of the input array into two categories:

### Length Boundaries
Since the input array is only allowed to have between `1` and `300` elements, we need to test arrays with lengths of `0` (off-point), `1` (on-point), `300` (on-point), and `301` (off-point).

### Digit Boundaries
Since the input array is only allowed to have digits between `0` and `2`, we need to test digits `-1` (off-point), `0` (on-point), `2` (on-point), and `3` (off-point).

## Step 5: Devise test cases
| Test Case ID | Input `nums`         | Description                          | Expected Output          |
|--------------|----------------------|--------------------------------------|--------------------------|
| T1           | `[0]`                | Single element 0 (on-point length 1) | `[0]`                    |
| T2           | `[1]`                | Single element 1 (on-point length 1) | `[1]`                    |
| T3           | `[2]`                | Single element 2 (on-point length 1) | `[2]`                    |
| T4           | `[1, 0, 2]`          | All three colors (unsorted)          | `[0, 1, 2]`              |
| T5           | `[0, 0, 1, 1, 2, 2]` | Already sorted                       | `[0, 0, 1, 1, 2, 2]`     |
| T6           | `[2, 2, 1, 1, 0, 0]` | Reverse sorted                       | `[0, 0, 1, 1, 2, 2]`     |
| T7           | `[0, 1, 0, 1]`       | Two colors (0, 1) alternating        | `[0, 0, 1, 1]`           |
| T8           | `[2, 0, 2, 0]`       | Two colors (0, 2)                    | `[0, 0, 2, 2]`           |
| T9           | `[1, 2, 1, 2]`       | Two colors (1, 2)                    | `[1, 1, 2, 2]`           |
| T10          | `[0] * 300`          | Max length (300) all same color      | `[0] * 300`              |
| T11          | `[]`                 | Empty array (off-point length 0)     | IllegalArgumentException |
| T12          | `[-1]`               | Value < 0 (off-point value)          | IllegalArgumentException |
| T13          | `[3]`                | Value > 2 (off-point value)          | IllegalArgumentException |
| T14          | `[0] * 301`          | Length > 300 (off-point length)      | IllegalArgumentException |
| T15          | `null`               | Null                                 | IllegalArgumentException |

## Step 6: Automate the test cases
Refer to [SortColorsTest](/src/test/java/zest/SortColorsTest.java).

To make all tests pass, we had to make the following changes to [SortColorsTest](/src/test/java/zest/SortColorsTest.java):
* Add the pre-condition that the input array must be between `1` and `300` elements long.
* Add the pre-condition that the input array can only contain the integers `0`, `1`, and `2`.

## Step 7: Use creativity and experience to augment test suite
We can't think of any additional test cases.

# Structural Testing
## Step 1: Perform specification based testing
Refer to [Specification-based Testing](#specification-based-testing).

## Step 2: Read the implementation, and understand the main coding decisions made by the developer
We have read the implementation and found nothing out of the ordinary.

## Step 3: Run the devised test suite with a coverage tool
Using the pre-bundled Code Coverage tool in IntelliJ IDEA (or `mvn clean test`), our test suite achieves 100% line and branch coverage for method `sortColors`.

## Step 4: For each piece of code that is not covered understand why it was not tested
Since our test suite already achieves 100& branch coverage, we don't have any piece of code that is not exercised by the test suite.

## Step 5: Review the source code and derive additional tests using Step 4
We don't have any additional tests to derive as nothing in the source code is not already covered by the test suite.

# Mutation Testing
Mutation testing was performed using `mvn -f pom.xml org.pitest:pitest-maven:mutationCoverage`, which generated the following coverage report:
- Number of Classes: 1
- Line Coverage: 93% (24/25)
- Mutation Coverage: 94% (15/16)
- Test Strength: 94% (15/16)

The mutation testing shows 94% mutation coverage and 94% test strength, meaning 15 of the 16 generated mutations were killed by the test suite. The line coverage is 96% because the line `public class SortColors {` is marked as uncovered, but all functional lines in the `sortColors` method are fully covered and tested.

Upon further inspection, we observe that the mutant that mutated `while (mid <= high)` to `while (mid < high)` survived because it exits the loop prematurely when `mid == high`. As a result, the last unprocessed element may never be examined. Therefore, we've added the following test case in addition:

| Test Case ID | Input `nums`   | Description                | Expected Output   |
|--------------|----------------|----------------------------|-------------------|
| T16          | `[1, 0]`       | Two colores reverse sorted | `[0, 1]`          |