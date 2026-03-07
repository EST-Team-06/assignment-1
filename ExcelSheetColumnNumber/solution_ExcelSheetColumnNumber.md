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
* The compiler doesn't throw any error, it just wraps to tne negative side.
* To make this method conform with the requirements, I implement a check based 
  * on character length 
  * a check based on ASCII code sum
    * I do not use ASCII code sum only because it may suffer from the same if enough chars are used!
  ```
  int sum = 0;
  int bound = 'F' + 'X' + 'S' + 'H' + 'R' + 'X' + 'W';
  
  sum += c;
  if (sum > bound) {
      throw new IllegalArgumentException("Column title may not exceed FXSHRXW");
  }
  ```
  * Before this, I check if the column title length is above 7 and throw an IllegalArgumentException if that is the case.
  * Otherwise, by using the ASCII code sum, I can prevent hitting the 32-bit signed integer limit without ever having to compute it.

**Step 7: Use creativity and experience to augment test suite**
* I cannot come up with more ways to test this. As suspected, the code does check if input strings contain only valid uppercase letters using ASCII codes.
  * A test that uses a non-valid ASCII code exists, T1.3, no point in adding more.
* Most creativity went into fixing the bug of 32-bit signed integer limit.

# Structural testing
* To understand the setup, refer to AddBinary
* I ran Jacoco and got:
  * Line coverage 93%
  * Branch coverage 85%
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

# Mutation testing
* Refer to AddBinary to understand how Pitest was setup.
* After running Pitest I got:
  * Line coverage: 94%
  * Mutation score: 100%
* The line coverage is not 100% because the constructor was not used, which is fine.


