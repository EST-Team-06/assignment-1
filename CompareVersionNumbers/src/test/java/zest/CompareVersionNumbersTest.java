package zest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static zest.CompareVersionNumbers.compareVersion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

public class CompareVersionNumbersTest {
    // T1 - T5
    @ParameterizedTest
    @CsvSource({
            "0.0, 0.0, 0",
            "1.0, 0.0, 1",
            "1.1, 1.0, 1",
            "0.0, 1.0, -1",
            "1.0, 1.1, -1",
            "1.000001, 1.1, 0",
            "1.1, 1.000001, 0",
            "1.0, 1.0.1, -1",
            "1.0.1, 1.0, 1",
    })
    void checkValidCases(String version1, String version2, int expectedResult) {
        assertThat(compareVersion(version1, version2)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "., 1.0",
            "0, 1.0",
            "0., 1.0",
            ".0, 1.0",
            "1.10, 1.0",
            "-1.0, 1.0",
            "hello, world",
            "hi.mom, hello.world",
    })
    // T7, T9 and T10
    void checkInvalidCases(String version1, String version2) {
        assertThatThrownBy(
                ()->{
                    compareVersion(version1, version2);
                }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkUpperBoundValid() {
        int regularSize = 499;
        char[] regularArr = new char[regularSize];
        for (int i = 0; i < 499; i++) {
            if (i % 2 == 0) {
                regularArr[i] = '0';
            }
            else {
                regularArr[i] = '.';
            }
        }
        String regular = new String(regularArr);

        int leadingZeroSize = 500;
        char[] leadingZeroArr = new char[leadingZeroSize];
        leadingZeroArr[0] = '0';
        leadingZeroArr[1] = '.';
        for (int i = 2; i < leadingZeroSize; i++) {
            leadingZeroArr[i] = '0';

        }
        String leadingZero = new String(leadingZeroArr);

        assertThat(compareVersion(regular, "0.0")).isEqualTo(0);
        assertThat(compareVersion(leadingZero, "0.0")).isEqualTo(0);
        assertThat(compareVersion("0.0", regular)).isEqualTo(0);
        assertThat(compareVersion("0.0", leadingZero)).isEqualTo(0);
    }

    @Test
    void checkUpperBoundInvalid() {
        int regularSize = 501;
        char[] regularArr = new char[regularSize];
        for (int i = 0; i < 501; i++) {
            if (i % 2 == 0) {
                regularArr[i] = '0';
            }
            else {
                regularArr[i] = '.';
            }
        }
        String regular = new String(regularArr);

        int leadingZeroSize = 501;
        char[] leadingZeroArr = new char[leadingZeroSize];
        leadingZeroArr[0] = '0';
        leadingZeroArr[1] = '.';
        for (int i = 2; i < leadingZeroSize; i++) {
            leadingZeroArr[i] = '0';

        }
        String leadingZero = new String(leadingZeroArr);

        assertThatThrownBy(
                ()->{
                    compareVersion(regular, "0.0");
                }).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(
                ()->{
                    compareVersion(leadingZero, "0.0");
                }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkTypicalInvalidCases() {
        assertThatThrownBy(
                ()->{
                    compareVersion(null, "0.0");
                }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                ()->{
                    compareVersion("", "0.0");
                }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkValidDigits() {
        for (int i=0; i<10; i++) {
            String valid = "%d.0";
            valid = String.format(valid, i);
            assertThat(compareVersion(valid, valid)).isEqualTo(0);
        }
    }
}
