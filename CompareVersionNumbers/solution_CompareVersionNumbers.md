# Specification-based Testing
**Step 1: Understand requirements**
* The method we need to test receives two strings as input
  * Their length is restricted between 1 and 500
  * They may only contains digits and dots
  * Each revision value fits within a 32-bit signed integer (-2^31 to 2^31)
    * Negative numbers are "technically" not explicitly restricted
    * But since the string can only contain digits and dots, `-` is excluded
    * Therefore, we can only have values from 0 to 2^31
* The method outputs the indication of the comparison
  * version1 < version2 returns -1
  * version1 > version2 returns 1
  * version1 = version2 returns 0

* Requirements also mentioned how it should work and mentions edge cases
  * 1.01 vs 1.001, 1.01 is greater 
  * 1.0 vs 1.0.0.0, they are the same. 

* I started to wonder how to implement it but then realized I don't have to. Just need to test and fix it ;)

**Step 2: Explore program if is not well-known**
* To understand the code, I just test it by running some print statements in the main method.
  * The author used simple unit tests but that's his thing for exploration, not required.
  ```  
  System.out.println(compareVersion("1.2", "1.1"));
  System.out.println(compareVersion("1.1", "1.2"));
  ```
* I got:
  ```  
  -1 // version1 > version2
  1 // version1 < version2
  ```
  * This seems correct but when you re-check the requirements, you notice that
  * "Return -1 if version1 < version2"
  * "Return 1 if version1 > version2"
  * Here we observe the opposite, so I corrected the `<` and `>` operators.
* Although I am still curious how exactly it implemented. In specification-based testing, I just check if it works and if I get it and worry about implementation later in structural testing. I proceed with Step 3.
  * For quick bug fixes, I only look at the required parts and avoid looking at the code in its entirety. 
  * This subtask is supposed to train me in specification-based testing, so I consider the specs as my main focus point.

**Step 3: Analyze properties of inputs and outputs, identify partitions**
* At first glance, there could be endless partitions as any combination of digits and dots would be valid.
* We only care if one is greater / lesser than the other, so testing special cases should be sufficient.
* Key properties: 
  * P1: consists of numeric revisions separated by dots $\implies$ a version must contain at least one dot
  * P2: each revision is integer, ignoring leading 0s $\implies$ a version must contain at least two integers (revisions) 
    * Actually, each revision must be a digit, a value between 0 and 9; the 32-signed integer property is not too relevant
* Define `numInt` as the number of integers and `numDot` as the number of dots
  * Both `numInt` and `numDot` are non-negative integers (no floats or negative counts)
* Partitions 
  * Valid versions (`numInt>1` AND `numDot>0` AND `numInt == numDot + 1`)
    * Includes versions where version1 > version2
    * Includes versions where version1 < version2
  * Invalid versions (`numInt<=1` OR `numDot=0` OR `numInt != numDot + 1`)
  * Versions with leading 0s 
    * Can be a subset of the above, do not have any impact because program must ignore leading 0s

**Step 4: Analyze boundaries (on/off points)**
* We will certainly check the boundary between valid and invalid versions by checking `numInt` and `numDot` variations
* We can also check how the method behaves if the version length is greater than 500
* We can check what happens if a digit is beyond 0-9, so -1 and 10. The signed 32-bit integer limit does not need to be checked.

**Step 5: Devise concrete test cases**
* Valid cases, check method is working based on requirements
  * T1: `compareVersions("0.0", "0.0") == 0`
  * T2.1: `compareVersions("1.0" "0.0") == 1` 
  * T2.2: `compareVersions("1.1" "1.0") == 1`
  * T3.1: `compareVersions("0.0" "1.0") == -1`
  * T3.2: `compareVersions("1.0" "1.1") == -1`
  * T4.1: `compareVersions("1.000001" "1.1") == 0`
  * T4.2: `compareVersions("1.1", "1.000001") == 0`
  * T5.1: `compareVersions("1.0" "1.0.1") == -1`
  * T5.2: `compareVersions("1.0.1" "1.0") == 1`
* Version at / beyond maximum length 500
  * Consider `numInt>1` AND `numDot>0` AND `numInt == numDot + 1`
  * T6.1: The maximum valid length of a version with no leading 0s must be 499, 250 integers and 249 dots
  * T6.2: A maximum valid length of a version with leading 0s would be 1 integer, 1 dot and 498 0s after.
  * T6.3: Test one with 1 more integer and 1 dot (501 chars)
  * T6.4: Test one with 1 more zero attached (501 chars)
* Invalid cases
  * T7.1: `compareVersions(".", "1.0") == Throw IllegalArgumentException`
  * T7.2: `compareVersions("0", "1.0") == Throw IllegalArgumentException`
  * T7.3: `compareVersions("0.", "1.0") == Throw IllegalArgumentException`
  * T7.4: `compareVersions(".0", "1.0") == Throw IllegalArgumentException`
* Typical invalid cases, check handling
  * T8.1: `compareVersions(null, "1.0") == Throw IllegalArgumentException`
  * T8.2: `compareVersions("", "1.0") == Throw IllegalArgumentException`
  * T8.3: `compareVersions("hello", "world") == Throw IllegalArgumentException`
* Non-digits are invalid
  * T9.1: `compareVersions("1.10", "1.0") == Throw IllegalArgumentException`
  * T9.2: `compareVersions("-1.0", "1.0") == Throw IllegalArgumentException`


**Step 6: Implement concrete test cases with JUnit tests**
* T6 tests were slightly more verbose to implement but worth it because they check if the code still works for long valid inputs and if it fails if the limit is exceeded.
  * It also considers what happens when many leading 0s are added, as it is still valid.
  * And while it is leading 0s should be ignored, if the leading 0s cause the maximum length to be exceeded, the test must fail.
  * T6.3 and T6.4 failed and I adjusted the code to check for the input length.
  ```  
  if (version1.length() > 500 || version2.length() > 500) {
      throw new IllegalArgumentException("Version strings cannot be null");
  }
  ```
  * While fixing, I realized that non-digits should be also invalid and added T9
* Most of T7 and T9 failed
  * Code does not check correctly for number of dots & number of digits
  * Code does not check if the numbers are digits (between 0 and 9)
* The digit issue can be handled by adding:
  ```
  if (num1 < 0 || num1 > 9 || num2 < 0 || num2 > 9) {
      throw new IllegalArgumentException("Numbers used in version string must be digits (0-9)");
  }
  ```
  * So each time the integer is parsed, we check if it is a digit.
* The `numInt >=1 AND numDot >=0 AND numInt == numDot + 1` condition is harder to keep.
  * The method splits the string at the dots.
  * All non-dot substrings are parsed as integer
    * So if it is a not a valid integer string, `parseInt` should fail
    * Leading 0s will be ignored
  * Edge cases are when the dot position is off or `.split` results into invalid substring counts
  ``` 
  if (version1.startsWith(".") || version1.endsWith(".") || version2.startsWith(".") || version2.endsWith(".")) {
      throw new IllegalArgumentException("Invalid version string, must not start or end with dot");
  }
  ```
  * By checking if the start and end of the input and calling `.split`, we ensure that we have numbers separated by dots

  ```
  if (v1Parts.length < 2 || v2Parts.length < 2) {
      throw new IllegalArgumentException("Invalid version string, must contain numbers separated by dots");
  }
  ```
  * By checking the parts lengths, we can check if the minimum requirement, `numInt>=1` holds


**Step 7: Use creativity and experience to augment test suite**
* In Step6, I dealt with invalid string cases. Just to be on the safe side, I decided to add more.
* T10.1: `compareVersions("hello", "world") == Throw IllegalArgumentException`
* T10.2: `compareVersions("hi.mom", "hello.world") == Throw IllegalArgumentException`
* All tests still passed, so I proceed to the next stage.

# Structural testing
* Refer to AddBinary to understand how everything was set up.
* I ran `mvn clean test` to get the Jacoco coverage report and got:
  * 100% instruction coverage
  * 81% branch coverage
* Based on my current experience, I already can guess that it may be due to my own code changes that involved many if-branches with OR conditions and I was right:
  ``` 
  if (version1 == null || version2 == null) {
  
  if (version1.length() > 500 || version2.length() > 500) {
  
  if (version1.startsWith(".") || version1.endsWith(".") || version2.startsWith(".") || version2.endsWith(".")) {

  if (v1Parts.length < 2 || v2Parts.length < 2) {
   
  if (num1 < 0 || num1 > 9 || num2 < 0 || num2 > 9) {
  ```
  * All these conditions are used for input validation
  * I do not see a net benefit for implementing tests that cover all cases simply to achieve 100% branch coverage.

# Mutation testing
* Refer to AddBinary to understand how Pitest was set up.
* I ran `mvn test-compile org.pitest:pitest-maven:mutationCoverage`
  * Line coverage 95% (21/22)
  * Mutation score 88% (30 / 34 mutants killed)
``` 
if (version1.length() > 500 || version2.length() > 500) {

for (int i = 0; i < maxLength; i++) {

if (num1 < 0 || num1 > 9 || num2 < 0 || num2 > 9) {
```
* The valid digit check we can actually test by adding a test just checks all integers 0 to 9
* Added that tests and re-ran Pitest
  * Line coverage 95% (21/22)
  * Mutation score 94% (32 / 34 mutants killed)
* The version length > 500 check lets mutants survive because we only test with 500 on one side but not the other
  * The condition is valid, it makes sense why mutants would fail
  * Implementing tests to kill the mutant is relatively easy, we just copy two lines and swap the inputs
* I re-ran Pitest
  * Line coverage 95% (21/22)
  * Mutation score 97% (33 / 34 mutants killed)
* As for the for-loop, I do not think it's worth testing because if `<=` is used, that can lead to index out of bounds.
  * But it does not because we have a ternary operation
  ```
  int num1 = (i < v1Parts.length) ? Integer.parseInt(v1Parts[i]) : 0;
  ```
  * Which gives us a guarantee that `i` will not be beyond the index. 
  * I do not know why Pitest didn't mutate the ternary, perhaps too complex for it.
  * I decide to leave as the ternary seems safe enough in my opinion.