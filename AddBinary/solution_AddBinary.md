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
* T2: `"1" + "1" = "11" `
* T3.1: `"10" + "1" = "11"`
* T3.2: `"1"  + "10" = "11"`
* T3.3: `"11" + "1" = "100"`
  * Because addition is associative, maybe T3.1 and T3.2 do not have to be differentiated.
  * However, maybe the code has a dependency on what is right or left, so I keep it.
* T4: `"" + "0" = Throw IllegalArgumentException`
* T5: `"0" + (10**4 * "1") = Throw IllegalArgumentException`
  * I'm using Python string replication syntax, will look up how to do this for Java but for just test cases, should be fine.
  * I know what it means, just not how to implement it yet. 
* T6:`"1" + ((10**4 - 1) * "1") = a 1 with (10*4 -1) 0s`
  * T6 is special because requirements have only a restriction on `a` and `b`, not on `result`
  * So an output of length 10^4 should be allowed

**Step 6: Implement concrete test cases with JUnit tests**

**Step 7: Use creativity and experience to augment test suite**



