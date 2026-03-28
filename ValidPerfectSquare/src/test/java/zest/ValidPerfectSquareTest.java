package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;
import static zest.ValidPerfectSquare.isPerfectSquare;

public class ValidPerfectSquareTest {

    @ParameterizedTest
    @MethodSource("testCases")
    void isPerfectSquare_WithValidInput_ReturnsExpectedResult(int num, boolean expected) {
        assertThat(isPerfectSquare(num)).isEqualTo(expected);
    }

    @Test
    void isPerfectSquare_WithZero_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> isPerfectSquare(0)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void isPerfectSquare_WithNegative_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> isPerfectSquare(-1)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
                of(1, true), // T1
                of(2, false), // T2
                of(63, false), // T3
                of(64, true), // T4
                of(65, false), // T5
                of(2147395599, false), // T6
                of(2147395600, true), // T7
                of(2147395601, false), // T8
                of(2147483647, false) // T9
        );
    }
}