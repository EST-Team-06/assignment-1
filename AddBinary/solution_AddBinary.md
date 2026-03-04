# Specification-based Testing
**Step 1: Understand requirements**
* Method should perform binary addition
* Method uses strings as input and should output a string
  * String length must be between 1 and 10^4 chars
  * Each string consists only of chars '0' and '1'
  * Output is a binary string of the sum 
  * No string contains leading 0s, except number 0 (so no padding!)
```
addBinary("1", "1") == "10" (valid)
addBinary("001", "001") == "010" (invalid)
addBinary("11", "1") == "100" (valid)
```

**Step 2: Explore program if is not well-known**
* In the book, the author implemented basic unit tests for this step but this is not required. 
* I prefer to just run the program. I don't look at the code but just implement a main method and call the program with different inputs.
* **Bug1**: `addBinary("1", "1")` returns `"0"`.
  * Since we are tasked to fix the bug, I will fix it now and not later on. 
  * Since this is an IDE, I can use the debugger to figure out where the bug occurs.
  * I noticed that the carry is not added to the `result` array (StringBuilder). 
  * Just adding it created a new bug, because `addBinary("1", "1") = "10` but `addBinary("10", "1") = "0101"`
  * I add the carry outside the loop but get `addBinary("10", "1) = "011"`
  * I check that the carry is not zero and try also for `11` + `1`, seems to work. 
* By now I'm more familiar with what the method does and proceed to next step.

**Step 3: Analyze properties of inputs and outputs, identify partitions**
* Thanks to fixing **Bug1**, I have a rough idea of input properties to check for basic functionality
  * `a.length() == b.length() == result.length()` (all same length)
  * `a.length() == b.length() != result.length()` (input same length, output not)
  * `a.length() != b.length()` (input different length, 3 subcategories)
    * `b.length == result.length()`
    * `a.length == result.length()`
    * `a.length() != b.length() != result.length()`

* So we have 3 partitions, with 1 partition have 3 sub-partitions. They are non-equivalent, so testing them should test different parts of the code. 
  * (NOTE, I only looked at the code for debugging the **Bug1**, I did not inspect the code in the detail, so maybe we see later if this is true or not via coverage!)

**Step 4: Analyze boundaries (on/off points)**
* String length cannot be 0 and cannot be larger than 10^4, so we should test empty string and 10^4 just in case.
* While the operation is on binary numbers, the input is string. So Java type checking is useless. We should check what the program does if some random string is passed as input.
  * (We do not have to check for other types, like passing float / Integer, compiler will deal with that)

**Step 5: Devise concrete test cases**
* Since I'm bot a pro at Binary math, I use Python Interpreter to check that I'm doing stuff correctly.
```
>>> bin(1)
'0b1'
>>> bin(2)
'0b10'
>>> bin(3)
'0b11'
```
* T1: `"1" + "0" = "1" `
  * (Observation: all same length only possible if 0 is in input!)
* T2: `"1" + "1" = "10" `
* T3.1: `"10" + "1" = "11"`
* T3.2: `"1"  + "10" = "11"`
* T3.3: `"11" + "1" = "100"`
  * Because addition is associative, maybe T3.1 and T3.2 do not have to be differentiated.
  * However, maybe the code has a dependency on what is right or left, so I keep it.
* T4: `"" + "0" = Throw IllegalArgumentException`
* T5: `"0" + ((10**4 * "1")+1) = Throw IllegalArgumentException`
  * I'm using Python string replication syntax, will look up how to do this for Java but for just test cases, should be fine.
  * I know what it means, just not how to implement it yet. 
* T6:`"1" + ((10**4) * "1") = 1 with (10*4 + 1) 0s`
  * T6 is special because requirements have only a restriction on `a` and `b`, not on `result`
  * So an output of length 10^4 should be allowed

* For T4 - T5, I could do the same test with for the other direction, but I decided not too. 
  * I assume that the bug if one side triggering the throw exception and the other not is unlikely.
  * If the left or right side make a difference, it should be caught by T3.2

**Step 6: Implement concrete test cases with JUnit tests**
* I will not copy-paste my tests but mention what I decided to implement. 
* T1 - T3 can be implemented with ParametrizedTests
  * Prof. Baccheli showed in the lecture that CSV can be used a ssource, instead of implementing this verbose Stream and Method source as shown in the book. 
  * I found a reference on how to implement it here: https://www.baeldung.com/parameterized-tests-junit-5
* T4 - T6 will receive individual tests, as they are more about special edge cases. 
  * While implementing T4, I noticed that empty string is not triggering the Exception I expected.
  * The code checks for null, not empty string. So I decided to modify the code, because requirements refer to the length of the string but also add a test which checks for null strings.
    * The null string check works but IDE will point out that this will not work. I keep the test since I cannot assume everyone uses the same IDE.

* T6
  * I found out how to do string duplication here: https://www.baeldung.com/java-string-of-repeated-characters 
  * I noticed that we might as well test the boundary itself, not just beyond the boundary.
  * Was trying to look into a way to replace the first char, opted for this approach: https://www.geeksforgeeks.org/java/swap-the-first-and-last-character-of-a-string-in-java/
    * Just turn the long string into an array, then replace the first char.

* Implemented Test cases
  * T1 - T3 (Same as before)
  * T4.1: `"0" + "" = Throw IllegalArgumentException`
  * T4.2: `"0" + null = Throw IllegalArgumentException`
  * T5: `"0" + "1" ** ((10**4)+1) = Throw IllegalArgumentException`
  * T6.1: `"0" + "1" ** (10**4) = "1" ** (10**4)`
  * T6.2: `"1" + "1" ** (10**4)` = 1 with (10^4 + 1) 0s

**Step 7: Use creativity and experience to augment test suite**
* T6.2 is somewhat of a magnum opus, as it truly checks if it is working correctly. However, I would also prefer to see cases such as:
* `"1011" + "1" = "1100` (11 + 1 = 12)
* `"101010" + "1000101" = "1101111"` (42 + 69 = 111)
* These are more personal choices because they involve zeros and ones at different positions. Just to make sure that it also works for some choices.

# Specification-based testing
* While reading the book chapters, I also implemented all code the author implemented and learned how to use JaCoCo that way with the help of AI. 
  * I assume that I do not have report this as it was in the past and not specifically to solve this task.
    * The prompt was along the lines of: Give me what I have to add to `prom.xml` to make JaCoCo work
  * I also cross-checked and found this post on StackOverflow: https://stackoverflow.com/a/79507359 (Which is the current one I'm using, not the AI generated one!)
  * So I run `mvn clean test`, it creates the report, which I can then view and check.

* Based on the report, I got 100% Line coverage and 86% Branch coverage on the `addBinary` method
  * I assume I do not have to look at the class, just the method. As we only implement the tests for the method.
  * As for the branches I missed, it turns out that I am not checking all cases, that is:
    * a = null OR b = null OR a.isEmpty() OR b.isEmpty()
    * i+1 > Math.pow(10,4) OR j+1 > Math.pow(10,4)
  * I do not think it is worth implementing extra tests for those because I am using the `||` (short-circuiting OR) and it would not bring that much additional value in my opinion, as it is in the same condition.

# Mutation Testing
* After following the provided documentation, I ran: `mvn test-compile org.pitest:pitest-maven:mutationCoverage` and got:
  * 22 / 23 (96%) Line coverage for mutated classes
  * 28 / 30 Mutants killed (Mutation score 93%)
  * 63 tests were run
* Based on the report, the line that seems to cause problems was:
```
if (i+1 > Math.pow(10, 4) || j+1 > Math.pow(10, 4)) {
```
* Changing the boundary will cause certain tests to pass, they should not.
* Unsure if it is worth changing this, because the bound is given by the requirement. So a mutant where the bound is intentionally changed seems rather odd.
* We can increase mutation coverage to 96% by doing:
```
int aLength = a.length();
int bLength = b.length();
int i = aLength - 1;
int j = bLength - 1;

if (aLength > Math.pow(10, 4) || bLength > Math.pow(10, 4)) {
    throw new IllegalArgumentException("Input strings cannot be greater than 10^4");
}
```
* By doing this, it prevents mutant from existing. The + cannot be replaced by "-" anymore. However, not sure if this is a good solution.
* In my opinion, these two mutants are okay, as they shouldn't really occur in real life. 