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

## Step 3: Analyze Inputs and Outputs, and Identify Partitions

### Input: `[...]`
Possible classes of inputs:
- [...]
- [...]

### Combination of `[...]` and `[...]`
- [...]

### Output
- [...]

## Step 4: Analyze boundaries
- [...]

## Step 5: Devise test cases
- [...]

## Step 6: Automate the test cases
- Refer to [...]()

## Step 7: Use creativity and experience to augment test suite
- [...]

# Structural Testing
## Step 1: Perform specification based testing
- Already performed

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