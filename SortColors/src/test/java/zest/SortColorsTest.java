package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;
import static zest.SortColors.sortColors;

public class SortColorsTest {

    @ParameterizedTest
    @MethodSource("testCases")
    void sortColors_WithValidInput_ReturnsExpectedResult(int[] nums, int[] expected) {
        sortColors(nums);
        assertThat(nums).isEqualTo(expected);
    }

    @Test
    void sortColors_WithEmptyArray_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> sortColors(new int[0])).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sortColors_WithNegativeDigit_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> sortColors(new int[]{-1})).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sortColors_WithDigitGreaterThan2_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> sortColors(new int[]{3})).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sortColors_WithArrayLengthGreaterThan300_ThrowsIllegalArgumentException() {
        int[] nums = new int[301];
        assertThatThrownBy(() -> sortColors(nums)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void sortColors_WithNullInput_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> sortColors(null)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> testCases() {
        int[] t10Input = new int[300];
        int[] t10Expected = new int[300];

        return Stream.of(
                of(new int[]{0}, new int[]{0}), // T1
                of(new int[]{1}, new int[]{1}), // T2
                of(new int[]{2}, new int[]{2}), // T3
                of(new int[]{1, 0, 2}, new int[]{0, 1, 2}), // T4
                of(new int[]{0, 0, 1, 1, 2, 2}, new int[]{0, 0, 1, 1, 2, 2}), // T5
                of(new int[]{2, 2, 1, 1, 0, 0}, new int[]{0, 0, 1, 1, 2, 2}), // T6
                of(new int[]{0, 1, 0, 1}, new int[]{0, 0, 1, 1}), // T7
                of(new int[]{2, 0, 2, 0}, new int[]{0, 0, 2, 2}), // T8
                of(new int[]{1, 2, 1, 2}, new int[]{1, 1, 2, 2}), // T9
                of(t10Input, t10Expected), // T10
                of(new int[]{1, 0}, new int[]{0, 1}) // T16
        );
    }
}