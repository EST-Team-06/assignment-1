package zest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static zest.AddBinary.addBinary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;


public class AddBinaryTest {
    @ParameterizedTest
    @CsvSource({
            "1,0,1",                    //T1
            "1,1,10",                   //T2
            "10,1,11",                  //T3.1
            "1,10,11",                  //T3.2
            "11,1,100",                 //T3.3
            "1011,1,1100",              //Personal Choice1
            "101010,1000101,1101111"    //Personal Choice2
    })//T3.3
    void checkValidCases(String a, String b, String expectedResult) {
        assertThat(addBinary(a, b)).isEqualTo(expectedResult);
    }

    @Test
    void checkIsEmpty() {
        assertThatThrownBy(
                ()->{
                    addBinary("", "0");
                }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkIsNull() {
        assertThatThrownBy(
                ()->{
                    addBinary(null, "0");
                }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkTooLongInput() {
        String longString = "1".repeat((int) Math.pow(10, 4)+1);
        assertThatThrownBy(
                ()->{
                    addBinary("0", longString);
                }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkBoundLongInput() {
        String longString = "1".repeat((int) Math.pow(10, 4));
        assertThat(addBinary("0", longString)).isEqualTo(longString);
    }

    @Test
    void checkTooLongOutput() {
        String longInputValid = "1".repeat((int) Math.pow(10, 4));
        String longOutputValid = "0".repeat((int) Math.pow(10, 4)+1);
        char [] longOutputValidArr = longOutputValid.toCharArray();
        longOutputValidArr[0] = '1';
        longOutputValid = String.valueOf(longOutputValidArr);
        assertThat(addBinary("1", longInputValid)).isEqualTo(longOutputValid);
    }



}