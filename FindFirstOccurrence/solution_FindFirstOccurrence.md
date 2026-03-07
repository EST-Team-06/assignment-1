# Specification-based Testing

## Step 1: Understand requirements
- Based on the requirements, we have a method that should find the index of the first occurrence in a string
- Input: Haystack (string), Needle (string)
- Output: Index of the first occurrence of needle in haystack (integer)
- If needle is not in haystack, the function should return -1
- Both strings consist only of lowercase English letters
- The length of the both strings is bounded between 1 and 10.000
- Edge cases should also be handled:
  - Needle appears at the beginning or at the end of haystack
  - Multiple occurrences of needle
  - Needle is longer than haystack or equal to haystack 
  - Needle is single character string

## Step 2: Explore program if is not well-known
- From the requirements, it is clear for me how the function should behave.
- To ensure the function behaves as expected, I tried two basic examples and got the following outputs:
  ```
    System.out.println(strStr("aabbcc", "bb")); # outputs 2
    System.out.println(strStr("test", "t"));    # outputs 0
  ```
- The function gave the expected outputs

## Step 3: Analyze properties of inputs and outputs, identify partitions

### Input: `haystack`
Possible classes of inputs:
- String of length 1
- String of length > 1

### Input: `needle`
Possible classes of inputs
- String of length 1
- String of length > 1

### Combination of `haystack` and `needle`
- `needle` is not in `haystack`
- `needle` is at the beginning of `haystack`
- `needle` is at the middle of `haystack`
- `needle` is at the end of `haystack`
- `needle` is multiple times in `haystack`
- `needle` equals `haystack`
- `needle` longer than `haystack`

### Output
- `-1` when needle is not in the `haystack`
- `0` when needle is at the beginning
-  `> 0` when needle is at the middle or end

## Step 4: Analyze boundaries
- The boundary values are at the minimum and maximum allowed input lengths: 1 and 10.000

## Step 5: Devise test cases
- T1: `strStr("aabbcc", "d") == -1`
- T2: `strStr("aabbcc", "a") == 0`
- T3: `strStr("aabbcc", "bb") == 2`
- T4: `strStr("aabbc", "c") == 4`
- T5: `strStr("sadbutsad", "sad") == 0`
- T6: `strStr("abc", "abc") == 0`
- T7: `strStr("a", "abc") == -1`
- T8: `strStr("a"*9999 + "b", "b") == 9999`
- T9: `strStr("a"*10000, "a") == 0`

## Step 6: Automate the test cases
- Refer to [FindFirstOccurrenceTest](https://github.com/EST-Team-06/assignment-1/blob/add-binary/FindFirstOccurrence/src/test/java/zest/FindFirstOccurrenceTest.java)

## Step 7: Use creativity and experience to augment test suite
- I also wanted to test where `needle` is partially contained in `haystack`
- T10: `strStr("haystac", "stack") == -1`
- T11: `strStr("aystack", "hay") == -1`


# Structural Testing

## Step 1: Perform specification based testing
- Already performed

## Step 2: Read the implementation, and understand the main coding decisions made by the developer
- I see there is a check for rejecting null strings. I add two test cases for that:
- T12: `strStr("null", "needle") == IllegalArgumentException`
- T13: `strStr("haystack", "null") == IllegalArgumentException`

## Step 3: Run the devised test suite with a coverage tool
- I got 100% branch coverage with JaCoCo

## Step 4: For each piece of code that is not covered understand why it was not tested
- Since I got 100% branch coverage, it's not applicable

## Step 5: Review the source code and derive additional tests using Step 4
- The 13 test cases I derived in the previous steps seem to be enough