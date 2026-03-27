# Specification-based Testing
## Step 1: Understand Requirements
### Business Rules
The goal of this method is to reverse the digits of a signed 32-bit integer (_can represent both positive and negative whole numbers_) while preserving the sign.
### Inputs
- `x`: A signed 32-bit integer with numbers between `[-2^31, 2^31 - 1]`.
### Outputs
The program returns the reversed integer. If reversing causes integer overflow, the method should return `0`.

## Step 2: Explore Program
It is clear from the requirements how the function should behave, nonetheless we've called it with the example input listed in [README](README.md), and it returned the expected output of `321`:
```java
System.out.println(reverse(123));
```

## Step 3: Analyze Inputs, Outputs and Identify Partitions
Since there is only one input, we don't need to test combinations of inputs.

### Input: `x`
Possible classes of this input:
- Positive single-digit integers
- Negative single-digit integers
- Positive integers (no trailing zero) whose reverse is within range
- Negative integers (no trailing zero) whose reverse is within range
- Positive integers ending in zero (reverse loses trailing zero)
- Negative integers ending in zero (reverse loses trailing zero)
- Positive integers whose reversed value overflows
- Negative integers whose reversed value overflows
- Maximum boundary value (`2^31 - 1`)
- Minimum boundary value (`-2^31`)
- Zero

### Output
- Positive integers with maximum value of `2^31 - 1`
- Negative integers with minimal value between `-2^31`
- Zero

## Step 4: Analyze boundaries
Since the integer input must fall into the 32-bit range, we need test `-2'147'483'649` (off-point), `-2'147'483'648` (on-point), `2'147'483'647` (on-point), and `2'147'483'648` (off-point). However, since the off-points are not possible to test due to the type constraint, we only test the on-points.

We also need to test the boundaries between returning a valid output and returning zero due to overflow, so we also test `-1'563'847'412` (off-point, which reverses to `-2'147'483'651`, and should return `0`), `-1'463'847'412` (on-point, which reverses to `-2'147'483'641`), `1'463'847'412` (on-point, which reverses to `2'147'483'641`) and `1'563'847'412` (off-point, reverses to `2'147'483'651`, and should return `0`).