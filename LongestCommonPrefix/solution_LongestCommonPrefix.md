# Specification-based Testing

## Step 1: Understand requirements
- Based on the requirements, we have a method that should find the longest common prefix in a given list of strings 
- Input: strs (list[string])
- Output: longest common prefix (string)
- The array contains 1 between 200 strings
- The length of the individual strings is bounded between 0 and 200
- Individual strings consist only of lowercase English letters if not empty
- If no common prefix exists, the functions should return ""

## Step 2: Explore program if is not well-known
- From the requirements, it is clear for me how the function should behave.
- To ensure the function behaves as expected, I tried two basic examples and got the following outputs:
  ```
    System.out.println(longestCommonPrefix(new String[] {"foobar", "foo"})); # outputs "foo"
    System.out.println(longestCommonPrefix(new String[] {"foo", "bar"}));    # outputs ""
  ```
- The function gave the expected outputs

## Step 3: Analyze properties of inputs and outputs, identify partitions

### Input: `strs`
Possible classes of inputs:
- Empty list
- Only one string in the list
- More than one string in the list
  - At least one string is empty
  - At least one string is null
  - At least one string has an invalid character
  - No common prefix
  - Common prefix with one letter
  - Common prefix with multiple letters
  - All strings are same

### Output
- `""` when there is no common prefix
- Non-empty string when there is a prefix

## Step 4: Analyze boundaries
- Allowed list length: 1 and 200
- Allowed string length: 0 and 200

## Step 5: Devise test cases
- T1:  `longestCommonPrefix([""]) == """`
- T2:  `longestCommonPrefix(["foo"]) == "foo"`
- T3:  `longestCommonPrefix(["foo", ""]) == ""`
- T4:  `longestCommonPrefix(["foo", null]) == IllegalArgumentException`
- T5:  `longestCommonPrefix(["foo", "."]) == IllegalArgumentException`
- T6:  `longestCommonPrefix(["foo", "bar"]) == ""`
- T7:  `longestCommonPrefix(["foo", "f"]) == "f"`
- T8:  `longestCommonPrefix(["foo", "foobar"]) == foo`
- T9:  `longestCommonPrefix(["foo"]*200) == "foo"`
- T10:  `longestCommonPrefix(["foo"]*201) == IllegalArgumentException`
- T11:  `longestCommonPrefix(["a"*200]) == "a"*200`
- T12:  `longestCommonPrefix(["a"*201]) == IllegalArgumentException`

- I see that all the tests expecting IllegalArgumentException fail. So I add check for each case:
    ```java
        if (word.length() > 200) {
            throw new IllegalArgumentException("Input array contains more than 200 characters");
        }
        if (containsNonEnglishCharacters(word)) {
            throw new IllegalArgumentException("Input array contains non-English characters");
        }
    ```

## Step 6: Automate the test cases
- Refer to [LongestCommonPrefix](https://github.com/EST-Team-06/assignment-1/blob/add-binary/LongestCommonPrefix/src/test/java/zest/LongestCommonPrefixTest.java)

## Step 7: Use creativity and experience to augment test suite
- I also wanted to test the following case:
- T13:  `longestCommonPrefix(["hello", "hell", "hel", "he"]) == "he"`
- T14:  `longestCommonPrefix(["he", "hel", "hell", "hello"]) == "he"`

# Structural Testing

## Step 1: Perform specification based testing
- Already performed

## Step 2: Read the implementation, and understand the main coding decisions made by the developer
- I see there is a check for rejecting null arrays. I add a test case for that:
- T15: `longestCommonPrefix(null) == IllegalArgumentException`

## Step 3: Run the devised test suite with a coverage tool
- After running `mvn clean test` I got 84% branch coverage. I partially covered English character case, and didn't cover the following branch:
  ```java
      if (prefix.isEmpty()) {
        return "";
      }
  ``` 

## Step 4: For each piece of code that is not covered understand why it was not tested
- I realized this branch is not reachable. If the string is empty (""), the parent if check would be false. So I removed this unnecessary check.
- And the partial coverage was because I didn't cover the second check in the `(c < 'a' || c > 'z')` statement. 

## Step 5: Review the source code and derive additional tests using Step 4
- I add one more test case to cover uncovered letter branch:
- Here I have one uncovered branch in the `(c < 'a' || c > 'z')` statement. To cover this I have to find a c > 'z' case
- I add a case covering this
- T16: `longestCommonPrefix(["foo", "}"]) == IllegalArgumentException`
- After that I got 100% branch coverage
- I tried to reach 100% branch coverage here in this case because I realized it's easy and fast to cover, but I think this case wasn't necessarily needed


# Mutation Testing

- I ran: `mvn test-compile org.pitest:pitest-maven:mutationCoverage` and got
  - 97% Line coverage (class declaration is not tested)
  - 91% Mutation coverage
  - Generated 22 mutants, killed 10

- I looked at the survived mutants and saw that changing the conditional boundary of `strs[i].length() > 200` survived my test cases
- This is because my test case with a string of length 200 doesn't reach this branch since it's the first element and doesn't enter the for loop which starts from the first element. 
- I changed added a slightly adjusted version of T11: `longestCommonPrefix(["aaa", "a"*200]) == "aaa"`
- After that I only have one surviving mutant
- The remaining surviving mutant is due to changing the conditional boundary of ASCII characters. While I can add tests containing a and z, I think this is not necessary. Because changing these limits would exclude these characters and break the intended logic which is unlikely to happen unintentionally
- The class declaration was not covered by the tests, resulting in 97% line coverage 