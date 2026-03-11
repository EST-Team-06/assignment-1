# Specification-based Testing

## Step 1: Understand requirements
- Based on the requirements, we have a method that should move zeroes to the end of the array
- Input: numbers (list[int])
- Output: array having zeroes at the end (list[int])
- Edge cases should also be handled:
    - Array only contains zeroes
    - Array contains no zeroes
    - Array contains multiple consecutive zeroes
    - Array only contains an element

## Step 2: Explore program if is not well-known
- From the requirements, it is clear for me how the function should behave.
- To ensure the function behaves as expected, I tried two basic examples and got the following outputs:
  ```
    moveZeroes(int[]{1,2,0,3});      # modifies in-place to [1,2,3,0]
    moveZeroes(int[]{3,0,1,0});      # modifies in-place to [3,1,0,0]
  ```
- The function gave the expected outputs

## Step 3: Analyze properties of inputs and outputs, identify partitions

### Input: `numbers`
Possible classes of inputs:
- Empty array
- One element
  - Zero
  - Non-zero
- No zeroes
- One zero
- Multiple zeroes
  - Only zeroes
  - Zeroes at the beginning
  - Zeroes in the middle
  - Zeroes at the end
  - Consecutive zeroes

### Output
- In-place modified array with zeroes being at the end

## Step 4: Analyze boundaries
- Based on the requirements, there is no boundary for the array length, nor for the individual integer values

## Step 5: Devise test cases
- T1:  `moveZeroes([]) == IllegalArgumentException`
- T2:  `moveZeroes([0]) == [0]`
- T3:  `moveZeroes([1]) == [1]`
- T4:  `moveZeroes([1,2,3]) == [1,2,3]`
- T5:  `moveZeroes([1,3,0,2]) == [1,3,2,0]`
- T6:  `moveZeroes([1,3,0,2,0]) == [1,3,2,0,0]`
- T7:  `moveZeroes([0,0,0,0,0]) == [0,0,0,0,0]`
- T8:  `moveZeroes([0,1,2]) == [1,2,0]`
- T9:  `moveZeroes([1,0,2]) == [1,2,0]`
- T10: `moveZeroes([1,2,0]) == [1,2,0]`
- T11: `moveZeroes([1,0,0,0,3,2]) == [1,3,2,0,0,0]`

- Surprisingly, only 3 tests pass, so I look for a bug in the code.
- I see that when moving non-zero elements to the front, the last element is not considered, so it's place is always overwritten by 0
- I make sure this is the case by looking at the passing cases. They all have 0 as the last element.
- I fix `i < numbers.length-1` to `i < numbers.length`, now the last element is also considered.
- I also add a check for empty array, as this also failed

## Step 6: Automate the test cases
- This problem was a bit tricky to test. Because in addition to asserting the expected output, I also have to assert the original reference of the array to see if it was indeed modified in-place.
- For this goal I added a helper function `applyMoveZeroes`
- Refer to [LengthOfLastWordTest](https://github.com/EST-Team-06/assignment-1/blob/add-binary/LengthOfLastWord/src/test/java/zest/LengthOfLastWordTest.java)

## Step 7: Use creativity and experience to augment test suite
- I also want to test a case where we have zeroes everywhere except the last element, even though this is in the partition class of T8
- T12: `moveZeroes([0,0,0,0,0,1], [1,0,0,0,0,0])`