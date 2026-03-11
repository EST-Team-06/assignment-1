# Specification-based Testing

## Step 1: Understand requirements
- Based on the requirements, we have a method that should find the length of the last word in a string 
- Input: s (string)
- Output: Length of the last word in the sentence (integer)
- The string consists only of English letters and spaces
- The length of the string is bounded between 1 and 10.000
- Edge cases should also be handled:
  - String has trailing spaces
  - String has leading spaces
  - String has multiple consecutive spaces
  - String has only one word

## Step 2: Explore program if is not well-known
- From the requirements, it is clear for me how the function should behave.
- To ensure the function behaves as expected, I tried three basic examples and got the following outputs:
  ```
    System.out.println(lengthOfLastWord("foobar")); # outputs 6
    System.out.println(lengthOfLastWord("foo bar")); # outputs 3
    System.out.println(lengthOfLastWord("foo bar baz")); # outputs 3
  ```
- The function gave the expected outputs

## Step 3: Analyze properties of inputs and outputs, identify partitions

### Input: `s`
Possible classes of inputs:
- Empty string
- String with invalid characters characters
- String with some invalid characters
- String with valid characters
  - String with length > 10⁴
  - String with one word
  - String with multiple words
  - String with trailing spaces
  - String with leading spaces
  - String with only spaces
  - String with length of last word = 1
  - String with length of last word > 1

### Output
- `0` when we only have invalid characters and/or space
- `> 1` when we have english characters

## Step 4: Analyze boundaries
- The boundary values are at the minimum and maximum allowed input length: 1 and 10.000

## Step 5: Devise test cases
- T1:  `lengthOfLastWord("") == IllegalArgumentException`
- T2:  `lengthOfLastWord(".") == IllegalArgumentException`
- T3:  `lengthOfLastWord("hello.world") == IllegalArgumentException`
- T4:  `lengthOfLastWord("a"*10001) == IllegalArgumentException`
- T5:  `lengthOfLastWord("foo") == 3`
- T6:  `lengthOfLastWord("foo bar") == 3`
- T7:  `lengthOfLastWord("foo bar  ") == 3`
- T8:  `lengthOfLastWord("  foo bar") == 3`
- T9:  `lengthOfLastWord("   ") == 0`
- T10:  `lengthOfLastWord("hello w") == 1`
- T11:  `lengthOfLastWord("hello world") == 5`

- I see that the T1-T4 fail, so there is no checks for invalid strings. So I add checks for T1 and T4: 
    ```java
        if (s.length() == 0 || s.length() > 10000) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
    ```
- For T1 and T4, after the basic check above T1 and T4 passed. But for T2 and T3 I looked at the implementation and handled invalid characters:
    ```java
        if (!isEnglishLetter(s.charAt(i))) {
            throw new IllegalArgumentException("All input strings must be valid");
        }
    ```

## Step 6: Automate the test cases
- Refer to [LengthOfLastWordTest](https://github.com/EST-Team-06/assignment-1/blob/add-binary/LengthOfLastWord/src/test/java/zest/LengthOfLastWordTest.java)

## Step 7: Use creativity and experience to augment test suite
- I also wanted to test where `s` has multiple spaces between the words
- T12:  `lengthOfLastWord("foo  bar   baz") == 5`