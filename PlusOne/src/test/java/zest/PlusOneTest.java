package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;
import static zest.PlusOne.plusOne;

public class PlusOneTest {

    @ParameterizedTest
    @MethodSource("testCases")
    void plusOne_WithValidInput_ReturnsExpectedResult(int[] digits, int[] expected) {
        assertThat(plusOne(digits)).isEqualTo(expected);
    }

    @Test
    void plusOne_WithEmptyArray_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> plusOne(new int[0])).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void plusOne_WithNegativeDigit_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> plusOne(new int[]{-1})).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void plusOne_WithDigitGreaterThan9_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> plusOne(new int[]{10})).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void plusOne_WithLeadingZero_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> plusOne(new int[]{0, 1})).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void plusOne_WithArrayLengthGreaterThan100_ThrowsIllegalArgumentException() {
        int[] digits = new int[101];
        for (int i = 0; i < 101; i++) digits[i] = 9;
        assertThatThrownBy(() -> plusOne(digits)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void plusOne_WithNullInput_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> plusOne(null)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> testCases() {
        int[] t7Input = new int[100];
        for (int i = 0; i < 100; i++) t7Input[i] = 9;
        int[] t7Expected = new int[101];
        t7Expected[0] = 1;

        int[] t8Input = new int[100];
        t8Input[0] = 1;
        int[] t8Expected = new int[100];
        t8Expected[0] = 1;
        t8Expected[99] = 1;

        return Stream.of(of(new int[]{0}, new int[]{1}), // T1
                of(new int[]{8}, new int[]{9}), // T2
                of(new int[]{9}, new int[]{1, 0}), // T3
                of(new int[]{1, 2, 3}, new int[]{1, 2, 4}), // T4
                of(new int[]{1, 2, 9}, new int[]{1, 3, 0}), // T5
                of(new int[]{9, 9, 9}, new int[]{1, 0, 0, 0}), // T6
                of(t7Input, t7Expected), // T7
                of(t8Input, t8Expected) // T8
        );
    }
}