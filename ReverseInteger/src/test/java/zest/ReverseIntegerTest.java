package zest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;
import static zest.ReverseInteger.reverse;

public class ReverseIntegerTest {

    @ParameterizedTest
    @MethodSource("testCases")
    void reverse_WithValidInput_ReturnsExpectedResult(int x, int expected) {
        assertThat(reverse(x)).isEqualTo(expected);
    }

    static Stream<Arguments> testCases() {
        return Stream.of(of(5, 5), // T1
                of(-5, -5), // T2
                of(123, 321), // T3
                of(-123, -321), // T4
                of(120, 21), // T5
                of(-120, -21), // T6
                of(1463847412, 2147483641), // T7
                of(-1463847412, -2147483641), // T8
                of(1563847412, 0), // T9
                of(-1563847412, 0), // T10
                of(2147483647, 0), // T11
                of(-2147483648, 0), // T12
                of(0, 0) // T13
        );
    }
}
