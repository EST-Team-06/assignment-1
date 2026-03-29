# Specification-based Testing
**Step 1: Understand requirements**
* The method must transform a string into a number
* The input string consists of uppercase chars, A-Z
* It must adhere to an upper-case letter base-26 format
  * A = 1
  * B = 2
  * Z = 26
  * AA = 27
* The input string is restricted between A and FXSHRXW
  * A = 1
  * FXSHRXW = 2147483647 (signed 32-bit integer limit)
    * W= 23 * 26^0  
    * X= 24 * 26^1
    * R= 18 * 26^2
    * H = 8 * 26^3
    * S = 19 * 26^4
    * X = 24 * 26^5
    * F = 6 * 26^6

* Edge cases such as single chars, multi char and max char input should be handled.
* Observation: Testing the limit will be tricky as we can only throw errors after evaluation, which already exceed integer limit.

**Step 2: Explore program if is not well-known**
* While reading the requirements, I did the math myself and have a rough idea what the method should do.
* When testing the method with print statements in main, I got:
  ```
  System.out.println(titleToNumber("A")); // 0
  ```
  * It should be 26. Since I didn't want to read the whole code, as the math was a bit of a pain as well. I used the debugger.
  * In Java, there seems to be a notation
  ``` 
  c - 'A';
  ```
  * While observing the debugger and seeing `c = 'A'65`, I suspect it is a way to convert chars into their ASCII encoding.
  ``` 
  result = result * 26 + (c - 'A');
  ```
  * If `c` is 65 and `A` is 65, removing `A` results to 0. If result starts at 0, the whole thing is 0.
  * To fix it, we just add 1 to it. In a sense, we are re-indexing it at 1 rather than 0 to make it work for us.
* After that fix, the method worked for `A` and also for the example `ZY`, I decided to proceed with Step 3.
* I keep in mind that internally, the method will make use of the ASCII encoding to solve this task. It could become relevant for tests later on.

**Step 3: Analyze properties of inputs and outputs, identify partitions**
* Properties of the input string are:
  * It must consist of uppercase letters (A-Z)
    * Any other character; including ASCII; is invalid!
  * The base-26 number may not exceed the 32-bit integer limit.
    * The input string must be bound between **a range** between A to FXSHRXW
    * "a range" means it does not have to be in terms of base-26 but anything else that is valid.
    * Implies that the character length is between 1 and 7
  * The method must return the correct base-26 form given a valid string.

* Partitions
  * 0 letter strings (null, empty string, any string whose ASCII code < 65 or > 90)
  * 1 letter strings (26 options)
  * 2 letter strings (26 * 26 options)
  * 3 letter strings (26^3 options)
  * 4 letter strings (26^4 options)
  * 5 letter string (26^5 options)
  * 6 letter strings (26^6 options)
  * 7 letter strings (26^7 options)
  * outside strings (valid strings but their base-26 number exceeds the 32-bit signed integer limit)

**Step 4: Analyze boundaries (on/off points)**
* The most interesting boundary is between 7 letter strings and outside strings.
  * Testing the limit by 32-bit integer limit may work but is not elegant. 
  * Rather than checking if the 32-bit integer is reached, we could pre-emptively predict if it will happen or not.
  * Check for character length
  * Check for the sum of ASCII codes
  * Since ASCII codes are monotonically increasing between A and Z, we can exploit this property
    * The relation that exists for base-26 numbers is preserved with the sum of ASCII codes
    * A < Z holds
    * A < AA holds
    * etc.

**Step 5: Devise concrete test cases**
* T1: Null, Empty and String contains char whose ASCII code > 65 or ASCII code < 90:
  * T1.1: titleToNumber(null) = Throw IllegalArgumentException
  * T1.2: titleToNumber("") = Throw IllegalArgumentException
  * T1.3: titleToNumber("hello") = Throw IllegalArgumentException
  * T1.4: titleToNumber("@") = Throw IllegalArgumentException
* T2: 1 letter strings
  * Test all 26 letters; as they are base letters and can be easily tested via loops.
* T3: 2 letter strings
  * T3.1: titleToNumber("AA") == 27
  * T3.1: titleToNumber("ZZ") == 702
* T4: 3 letter strings
  * T4.1: titleToNumber("AAA") == 703
  * T4.2: titleToNumber("ZZZ") == 18278
* T5: 4 letter strings
  * T5.1: titleToNumber("AAAA") == 18279
  * T5.2: titleToNumber("ZZZZ") == 475254
* T6: 5 letter strings
  * T6.1: titleToNumber("AAAAA") == 475255
  * T6.2: titleToNumber("ZZZZZ") == 12356630
* T7: 6 letter strings
  * T7.1: titleToNumber("AAAAAA") == 12356631
  * T7.2: titleToNumber("ZZZZZZ") == 321272406
* T8: 7 letter strings
  * T8.1: titleToNumber("AAAAAA") == 321272407
  * T8.2: titleToNumber("FXSHRXW") == 2147483647
* T9: outside strings
  * T9: titleToNumber("ZZZZZZZ") == Throw IllegalArgumentException.
* T10: More than 7 chars
  * T10: titleToNumber("ZZZZZZZZ") == Throw IllegalArgumentException.

* It is important to note here, that we do not want to re-implement the algorithm inside the test to the test the algorithm.
* But we want to be cheap in how we design our tests, so the cheapest but most meaningful values to compute are indeed the boundaries
  * base-26 of Z is not that easy but we observe it is always base-26 of A of an additional char minus 1
  * base-26 of A is the easiest to compute for us humans, as just add 26^n together.
* In T9, I want the test to throw an IllegalArgumentException and do not rely on the compiler to create the 32-bit signed integer exception. 
  * I want it to be intentional, especially because the requirements never specify the 32-bit signed integer limit
    * 32-bit signed integer limit is something I realized by reading the problem and doing the math. 
  * The requirements only specify that it may not exceed "FXSHRXW", which means anything beyond this, although calculatable manually, is considered illegal argument.

**Step 6: Implement concrete test cases with JUnit tests**
* Instead of writing 26 lines to test the 26 base character, I got lazy, looked into a way to convert ASCII code to Strings and found: https://stackoverflow.com/a/69227199
* I implemented all tests and everything passed except T9.
* I went back to main and printed the output for ZZZZZZZ and got:
  * `-236852010`
* The compiler doesn't throw any error, it just wraps to the negative side.
* To make this method conform with the requirements, I implement a check based 
  * on character length 
  * if result not negative
* The second check is not immediately obvious but if we have an output that exceeds the MAX Integer limit, we wrap to negative integers.
* We have a guarantee thanks to character length check, that the all of these invalid cases are between `FXSHRXW` and `ZZZZZZZ` 
  * so we know the highest possible unhandled case will be still negative; adding a check to check for negativity should capture this.

**Step 7: Use creativity and experience to augment test suite**
* I cannot come up with more ways to test this. As suspected, the code does check if input strings contain only valid uppercase letters using ASCII codes.
  * A test that uses a non-valid ASCII code exists, T1.3, no point in adding more.
* Most creativity went into fixing the bug of 32-bit signed integer limit.
  * Initially, there a different approach of testing for 31-bit signed integer limit involving the ASCII sum
  * So if the sum of ASCII codes of `FXSHRXW` is exceeded by what was calculated from input raise the out of bound exception
  * However, sum of ASCII does not deal well with edge cases, where the sum is not exceeded but the wrap still occured
    * E.g, `ZZZZAAA` would still pass and trigger integer underflow; since the sum does not care about the order of characters, whereas the column number does

**Reflection on Partitions & Equivalence Classes**: 
* While reviewing our tests before submission; we wondered if the equivalence classes placed here were perhaps an overkill. Perhaps, you do not have to partition based on string length but it is sufficient to distinguish between 1 letter and multi-letter input.
* The core question is; can 2-letter and 3-letter inputs be considered "equivalent", that is, will the method behave the same way for both of them?
* For specification-testing, we do not look at the code and have to think rationally if it makes sense for the method to behave the same way.
  * Saying A-Z is an equivalence class makes sense because it is just incrementing the integer, A=1, B=2 and Z=26
  * We can also safely argue that Z and AA must belong to different classes because it does not make naturally sense to go from Z to AA, the program changes behavior as soon we have more than 1 letter.
  * The question is, do we have to keep checking this behavior change afterward. Is it enough to check it only once, when going from single to double or should we check it each time the length increases?
  * Because as Z and AA are different, so are ZZ and AAA
  * If we partition based on single and multiple characters, we would have two partitions but cannot be certain if all subpartitions inside the second partition are also equivalent.
  * If you learn about the code later in structural testing; you can argue it is obvious that the inputs are equivalent but during specification testing, we avoid looking at the source code and just implement what we think makes sense.
* We decided to leave original version and not retroactively remove the tests but we added this reflection to signal the TAs that we thought about it.


# Structural testing
## Step 1: Perform specification based testing
Refer to [Specification-based Testing](#specification-based-testing)

## Step 2: Read the implementation, and understand the main coding decisions made by the developer
* While implementing specification-based tests, I had to understand the problem and derived the indirect requirement involving the 32-bit signed integer limit.
* Most of the errors thrown were from me as well.
* The essential part of the code was the loop which seemed correct to me.

## Step 3: Run the devised test suite with a coverage tool
* I ran `mvn clean test` with Jacoco and got:
  * Line coverage 93%
  * Branch coverage 85%

## Step 4: For each piece of code that is not covered understand why it was not tested
* It turns out, I missed implementing a test for covering more than 7 chars.
* After that, I got:
  * Line coverage 100%
  * Branch coverage 92%
* The branch that was not fully covered was:
  ``` 
  if (c < 'A' || c > 'Z') {
  ```
  * This just meant adding non-valid inputs with ASCII code < 65 (A)
  * T1.3 was "hello", all chars above ASCII code > 90 (Z)
* I added one more as it was not much effort and got:
  * Line coverage 100%
  * Branch coverage 100%

## Step 5: Review the source code and derive additional tests using Step 4
* I was unable to find more tests and called it a day.

# Mutation testing
* Refer to AddBinary to understand how Pitest was set up.
* I ran: `mvn test-compile org.pitest:pitest-maven:mutationCoverage` and got:
  * Line coverage: 93%
  * Mutation score: 94%
* The line coverage is not 100% because the constructor was not used, which is fine.
* The mutation score is 94% because there was no test that failed for:
``` 
if (result < 0) {	
        throw new IllegalArgumentException("Column title may not exceed FXSHRXW");
```
* However, this means that if I the result was 0, the code would have thrown an error and no test would have failed.
* I was unable to come up with a case that would reach this mutation though because:
  * The null and empty column check prevent input from being of length 0
  * The column title length check prevents input to be large enough to trigger a wrap around from negative back to 0
* The check itself is only there to account for cases where the result is negative, which can occur due to wrap around. 
  * Even if occurs for other reasons, it would be wrong to return a negative value
  * Requirements specify returning the number corresponding to the non-zero column title, it cannot be negative.
* With those consideration, I think this mutation is fine.