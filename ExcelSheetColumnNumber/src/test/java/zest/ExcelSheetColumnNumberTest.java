package zest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static zest.ExcelSheetColumnNumber.titleToNumber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ExcelSheetColumnNumberTest {
    @Test
    void checkNoLetters() {
        assertThatThrownBy(
                ()->{
                    titleToNumber(null);
                }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                ()->{
                    titleToNumber("");
                }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                ()->{
                    titleToNumber("hello");
                }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                ()->{
                    titleToNumber("@");
                }).isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void checkSingleLetters() {
        int start = 65; // A
        int end = 90; // Z
        for (int i=start; i<=end; i++) {
            int expected = i - start + 1; // base-26 number to test
            String title = Character.toString(i);
            assertThat(titleToNumber(title)).isEqualTo(expected);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "AA, 27",
            "ZZ, 702",
            "AAA, 703",
            "ZZZ, 18278",
            "AAAA, 18279",
            "ZZZZ, 475254",
            "AAAAA, 475255",
            "ZZZZZ, 12356630",
            "AAAAAA, 12356631",
            "ZZZZZZ, 321272406",
            "AAAAAAA, 321272407",
            "FXSHRXW, 2147483647"
    })
    void checkMultipleLetters(String title, int number) {
        assertThat(titleToNumber(title)).isEqualTo(number);
    }

    @Test
    void checkBeyondValid() {
        assertThatThrownBy(
                ()->{
                    titleToNumber("ZZZZZZZ");
                }).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                ()->{
                    titleToNumber("ZZZZZZZZ");
                }).isInstanceOf(IllegalArgumentException.class);
    }
}