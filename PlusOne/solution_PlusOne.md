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
- [...]

## Step 6: Automate the test cases
Refer to [...]()

## Step 7: Use creativity and experience to augment test suite
- [...]

# Structural Testing
## Step 1: Perform specification based testing
Refer to [Specification-based Testing](#specification-based-testing)

## Step 2: Read the implementation, and understand the main coding decisions made by the developer
- [...]

## Step 3: Run the devised test suite with a coverage tool
- [...]

## Step 4: For each piece of code that is not covered understand why it was not tested
- [...]

## Step 5: Review the source code and derive additional tests using Step 4
- [...]

# Mutation Testing
- [...]