# Specification-based Testing
## Step 1: Understand Requirements
### Business Rules
The goal of this method is to determine whether a given positive integer is a perfect square. A perfect square is an integer that can be expressed as the product of an integer multiplied by itself.
### Inputs
- `num`: A positive integer between `1` and `2^31 - 1` (32-bit integer maximum)
### Outputs
The program returns `true` whenever the input is a perfect square, otherwise it returns `false`.

## Step 2: Explore Program
It is clear from the requirements how the function should behave, nonetheless we've called it with the example input listed in [README](README.md), and it returned the expected output of `true`:
```java
System.out.println(ValidPerfectSquare.isPerfectSquare(16));
```

## Step 3: Analyze Inputs, Outputs and Identify Partitions
Since there is only one input, we don't need to test combinations of inputs.

### Input: `num`
Possible classes of this input:
- Perfect squares
- Non-perfect squares
- Minimum boundary value (`1`)
- Maximum boundary value (`2^31 - 1`)
- Negative numbers (invalid input)
- Zero (invalid input)

### Output
- `true`
- `false`

## Step 4: Analyze boundaries
Since we need to both test the minimum and maximum boundary values for the input `num`, but also at the borders between perfect and non-perfect squares, we can subdivide the boundaries into two categories:

### Input Boundaries
* `0` (off-point), `1` (on-point), `2^31 - 1` (on-point), and `2^31` (off-point, which can't realistically be tested though because of the 32-bit integer constraint)

### Perfect-Square Boundaries
* `1`/`2` (on/off-point), to exercise the transition from the smallest perfect square.
* `63`/`64`/`65` (off/on/off-points), to exercise numbers around a perfect square.
* `2147395599`/`2147395600`/`2147395601` (off/on/off-points), to exercise values around the largest perfect square within the valid input range.

## Step 5: Devise test cases
| Test Case ID | Input `num`  | Description                                  | Expected Output          |
|--------------|--------------|----------------------------------------------|--------------------------|
| T1           | `1`          | Smallest perfect square                      | `true`                   |
| T2           | `2`          | Non-perfect square after smallest            | `false`                  |
| T3           | `63`         | Non-perfect square before a perfect square   | `false`                  |
| T4           | `64`         | Perfect square (boundary)                    | `true`                   |
| T5           | `65`         | Non-perfect square after a perfect square    | `false`                  |
| T6           | `2147395599` | Non-perfect square before largest perfect sq | `false`                  |
| T7           | `2147395600` | Largest perfect square (46340^2)             | `true`                   |
| T8           | `2147395601` | Non-perfect square after largest perfect sq  | `false`                  |
| T9           | `2147483647` | Maximum boundary value (non-perfect)         | `false`                  |
| T10          | `0`          | Boundary value 0                             | IllegalArgumentException |
| T11          | `-1`         | Negative input                               | IllegalArgumentException |

## Step 6: Automate the test cases
Refer to [ValidPerfectSquareTest](/src/test/java/zest/ValidPerfectSquareTest.java).

To make all tests pass, we had to make the following changes to [ValidPerfectSquareTest](/src/test/java/zest/ValidPerfectSquareTest.java):
* Change `while (left < right)` to `while (left <= right)`, because with `<`, the binary search will terminate when `left == right` without checking the final candidate, causing perfect squares to be missed.

## Step 7: Use creativity and experience to augment test suite
We can't think of any additional test cases.

# Structural Testing
## Step 1: Perform specification based testing
Refer to [Specification-based Testing](#specification-based-testing).

## Step 2: Read the implementation, and understand the main coding decisions made by the developer
We have read the implementation and found nothing out of the ordinary.

## Step 3: Run the devised test suite with a coverage tool
Using the pre-bundled Code Coverage tool in IntelliJ IDEA (or `mvn clean test`), our test suite achieves 100% line and branch coverage for method `isPerfectSqaure`

## Step 4: For each piece of code that is not covered understand why it was not tested
Since our test suite already achieves 100% branch coverage, we don't have any piece of code that is not exercised by the test suite.

## Step 5: Review the source code and derive additional tests using Step 4
We don't have any additional tests to derive as nothing in the source code is not already covered by the test suite.

# Mutation Testing
Mutation testing was performed using `mvn -f pom.xml org.pitest:pitest-maven:mutationCoverage`, which generated the following coverage report:
- Number of Classes: 1
- Line Coverage: 94% (16/17)
- Mutation Coverage: 94% (16/17)
- Test Strength: 94% (16/17)

The mutation testing shows 94% mutation coverage and 94% test strength, meaning 16 of the 17 generated mutations were killed by the test suite. The line coverage is 94% because the line `public class ValidPerfectSquare  {` is marked as uncovered, but all functional lines in the `isPerfectSquare` method are fully covered and tested.

Upon further inspection, we observe that the mutant that mutated `else if (square < num)` to `else if (square <= num)` survived, because by the time the program reaches that else if, the condition `square == num` is already `false`. Therefore, we don't need to augment our test suite with any additional test cases.