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

## Step 5: Devise test cases
| Test Case ID | Input `x`      | Description                                | Expected Output |
|--------------|----------------|--------------------------------------------|-----------------|
| T1           | `5`            | Positive single-digit                      | `5`             |
| T2           | `-5`           | Negative single-digit                      | `-5`            |
| T3           | `123`          | Positive integer, no trailing zero         | `321`           |
| T4           | `-123`         | Negative integer, no trailing zero         | `-321`          |
| T5           | `120`          | Positive integer, trailing zero            | `21`            |
| T6           | `-120`         | Negative integer, trailing zero            | `-21`           |
| T7           | `1463847412`   | Positive, reverse within range (on-point)  | `2147483641`    |
| T8           | `-1463847412`  | Negative, reverse within range (on-point)  | `-2147483641`   |
| T9           | `1563847412`   | Positive, reverse overflows (off-point)    | `0`             |
| T10          | `-1563847412`  | Negative, reverse overflows (off-point)    | `0`             |
| T11          | `2147483647`   | Maximum boundary value (on-point)          | `0`             |
| T12          | `-2147483648`  | Minimum boundary value (on-point)          | `0`             |
| T13          | `0`            | Zero                                       | `0`             |

## Step 6: Automate the test cases
Refer to [ReverseIntegerTest.java](/src/test/java/zest/ReverseIntegerTest.java)

## Step 7: Use creativity and experience to augment test suite
We can't think of any additional test cases.

# Structural Testing
## Step 1: Perform specification based testing
Refer to [Specification-based Testing](#specification-based-testing)

## Step 2: Read the implementation, and understand the main coding decisions made by the developer
We have read the implementation and found nothing out of the ordinary.

## Step 3: Run the devised test suite with a coverage tool
Using the pre-bundled Code Coverage tool in IntelliJ IDEA, our test suite achieves 100% class, 100% method, 100% line, but only 85% branch coverage.

## Step 4: For each piece of code that is not covered understand why it was not tested
The conditions `(reversed == Integer.MAX_VALUE / 10 && digit > 7)` and `(reversed == Integer.MIN_VALUE / 10 && digit < -8)` are not exercised with a `true` outcome because they cannot be satisfied as long as the input is within the 32-bit integer range. The intention of these checks is to prevent overflow beyond `2'147'483'647` and `-2'147'483'648`. However, producing such cases would require inputs like `8'463'847'412` or `-9'463'847'412`, which are outside the valid `int` range.

## Step 5: Review the source code and derive additional tests using Step 4
As stated in [Step 4](#step-4-for-each-piece-of-code-that-is-not-covered-understand-why-it-was-not-tested), we cannot test the `true` outcome of the conditions `(reversed == Integer.MAX_VALUE / 10 && digit > 7)` and `(reversed == Integer.MIN_VALUE / 10 && digit < -8)` because they cannot be satisfied as long as the input is within the 32-bit integer range. We still believe these checks are good to have if later on the input type would be changed to `long`.

# Mutation Testing
Mutation testing was performed using `mvn -f pom.xml org.pitest:pitest-maven:mutationCoverage`, which generated the following coverage report:
- Number of Classes: 1
- Line Coverage: 92% (11/12)
- Mutation Coverage: 81% (13/16)
- Test Strength: 81% (13/16)

The mutation testing shows 81% mutation coverage and 81% test strength, meaning 3 mutations were not killed by the test suite. However, upon further inspection, just like in [Step 3](#step-3-run-the-devised-test-suite-with-a-coverage-tool), the problem is that it mutates the boundaries, e.g. `=> 7` instead of `>7`, but since this exact boundary cannot be covered because of the 32-bit integer range, the mutant is not killed.