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
* So we have to make sure that different number combinations are captured
  * Versions with 0s that are equal
  * Versions with leading 0s that are not equal
  * Versions 

**Step 4: Analyze boundaries (on/off points)**

**Step 5: Devise concrete test cases**

**Step 6: Implement concrete test cases with JUnit tests**

**Step 7: Use creativity and experience to augment test suite**