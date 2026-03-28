# Specification-based Testing
## Step 1: Understand Requirements
### Business Rules
The goal of this method is to increment a large integer, represented as an array of digits, by one. Each element in the array corresponds to a single digit of the integer, ordered from most significant (left) to least significant (right).
### Inputs
- `digits`: An integer array digits representing a non-negative integer. The integer array contains 1 to 100 integers, with each integer being between 0 and 9. It does not contain leading zeros.
### Outputs
The program returns a new integer array representing the incremented integer. The result may have a different length if a carry creates an additional digit.

## Step 2: Explore Program
It is clear from the requirements how the function should behave, nonetheless we've called it with the example input listed in [README](README.md), and it returned the expected output of `[1,2,4]`:
```java
int[] arr = {1,2,3};
System.out.println(Arrays.toString(plusOne(arr)));
```

## Step 3: Analyze Inputs, Outputs and Identify Partitions
Since there is only one input, we don't need to test combinations of inputs.

### Input: `digits`
Possible classes of this input:
- Single digit array
- Multiple digit array (between 1 and 100 elements)
- Array with the least significant digit as 9
- Empty array (invalid input)
- Array with negative numbers (invalid input)
- Array with leading zeros (invalid input)
- Array with non-digit elements (invalid input)
- Array with more than 100 elements (invalid input)
- Array with digits greater than 9 (invalid input)

### Output
- Single digit array
- Multiple digit array (between 1 and 100 elements)
- Multiple digit array with 101 elements
- Array with the least significant digit as 0

## Step 4: Analyze boundaries
We can subdivide the boundaries of the input array into two categories:

### Length Boundaries
Since the input array is only allowed to have between 1 and 100 elements, we need to test arrays with lengths of 0 (off-point), 1 (on-point), 100 (on-point), and 101 (off-point).

### Digit Boundaries
Since the input array is only allowed to have digits between 0 and 9, we need to test digits -1 (off-point), 0 (on-point), 9 (on-point), and 10 (off-point).

## Step 5: Devise test cases
| Test Case ID | Input `digits`     | Description                               | Expected Output            |
|--------------|--------------------|-------------------------------------------|----------------------------|
| T1           | `[0]`              | Single digit 0                            | `[1]`                      |
| T2           | `[8]`              | Single digit < 9                          | `[9]`                      |
| T3           | `[9]`              | Single digit 9                            | `[1, 0]`                   |
| T4           | `[1, 2, 3]`        | Multi-digit, no carry                     | `[1, 2, 4]`                |
| T5           | `[1, 2, 9]`        | Multi-digit, LSD 9, partial carry         | `[1, 3, 0]`                |
| T6           | `[9, 9, 9]`        | Multi-digit, all 9, carry                 | `[1, 0, 0, 0]`             |
| T7           | `[9]` * 100        | Max length array (100 elements), all 9    | `[1]` + `[0]` * 100        |
| T8           | `[1]` + `[0]` * 99 | Max length array (100 elements), no carry | `[1]` + `[0]` * 98 + `[1]` |
| T9           | `[]`               | Empty array                               | IllegalArgumentException   |
| T10          | `[-1]`             | Negative digit                            | IllegalArgumentException   |
| T11          | `[10]`             | Digit > 9                                 | IllegalArgumentException   |
| T12          | `[0, 1]`           | Leading zero                              | IllegalArgumentException   |
| T13          | `[9]` * 101        | Array length > 100                        | IllegalArgumentException   |
| T14          | `null`             | Null                                      | IllegalArgumentException   |

## Step 6: Automate the test cases
Refer to [PlusOneTest.java](/src/test/java/zest/PlusOneTest.java)

To make all tests pass, we had to make the following changes to [PlusOne.java](/src/main/java/zest/PlusOne.java):
* Add the pre-condition that the input array must be `<=100` elements long.
* Add the pre-condition that the input array must not contain any leading zeros.
* Change the pre-condition that the input array must not contain any digits smaller than 0 and greater than 9.
* Fix the bug that made the resulting array the same size as the input array when carrying.

## Step 7: Use creativity and experience to augment test suite
We can't think of any additional test cases.

# Structural Testing
## Step 1: Perform specification based testing
Refer to [Specification-based Testing](#specification-based-testing)

## Step 2: Read the implementation, and understand the main coding decisions made by the developer
We have read the implementation and found nothing out of the ordinary.

## Step 3: Run the devised test suite with a coverage tool
Using the pre-bundled Code Coverage tool in IntelliJ IDEA, our test suite achieves 100% class, method, line and branch coverage.

## Step 4: For each piece of code that is not covered understand why it was not tested
Since our test suite already achieves 100& branch coverage, we don't have any piece of code that is not exercised by the test suite.

## Step 5: Review the source code and derive additional tests using Step 4
We don't have any additional tests to derive as nothing in the source code is not already covered by the test suite.

# Mutation Testing
Mutation testing was performed using `mvn -f pom.xml org.pitest:pitest-maven:mutationCoverage`, which generated the following coverage report:
- Number of Classes: 1
- Line Coverage: 93% (14/15)
- Mutation Coverage: 100% (20/20)
- Test Strength: 100% (20/20)

The mutation testing shows 100% mutation coverage and 100% test strength, meaning all 20 generated mutations were killed by the test suite. The line coverage is 93% because the line `public class PlusOne {` is marked as uncovered, but all functional lines in the `plusOne` method are fully covered and tested.