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
- String with invalid characters
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
- `> 1` when we have English characters

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


# Structural Testing

## Step 1: Perform specification based testing
- Already performed

## Step 2: Read the implementation, and understand the main coding decisions made by the developer
- I see there is a check for rejecting null strings. I add a test case for that:
- T13: `lengthOfLastWord("null") == IllegalArgumentException`

## Step 3: Run the devised test suite with a coverage tool
- After running `mvn clean test` I got 83% branch coverage. I apparently only partially covered the English character check I added in T2 and T3

## Step 4: For each piece of code that is not covered understand why it was not tested
- Partial coverage was because I didn't cover the uppercase English letters. I read the problem description again to be sure that uppercase characters are also allowed
- Based on the description uppercase characters are not considered invalid. "A string s consisting of English letters and spaces ' '."

## Step 5: Review the source code and derive additional tests using Step 4
- I add three more test case to cover uncovered uppercase letter branches:
- Here I have 3 uncovered branches in the `(c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')` statement. I have to find 3 ASCII character covering all the regions
- I realize I miss the following regions: "Z" < c < "a" and c > "z"
- I add two cases covering these 
- T14: `lengthOfLastWord("Hello_World") == IllegalArgumentException`
- T15: `lengthOfLastWord("Hello}World") == IllegalArgumentException`
- After that I got 100% branch coverage
- I tried to reach 100% branch coverage here in this case because I realized it's easy and fast to cover, but I think these cases weren't necessarily needed 

# Mutation Testing

- I ran: `mvn test-compile org.pitest:pitest-maven:mutationCoverage` and got
  - 96% Line coverage (class declaration is not tested)
  - 83% Mutation coverage
  - Generated 23 mutants, killed 19

- I looked at the survived mutants and saw that changing the conditional boundary of `s.length() > 10000` survived my test cases
- So I added T16: `lengthOfLastWord("a"*10000) == 10000`
- After that 20 mutants were killed out of 23
- The rest of the surviving mutants are due to changing the conditional boundary of ASCII characters. While I can add tests containing a, A, z, Z, I think this is not necessary 
- The class declaration was not covered by the tests, resulting in 96% line coverage 