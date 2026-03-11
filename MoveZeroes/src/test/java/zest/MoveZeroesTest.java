package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;
import static zest.MoveZeroes.moveZeroes;

public class MoveZeroesTest {
    @ParameterizedTest
    @MethodSource("testCases")
    void moveZeroesCases(int[] numbers, int[] expected) {
        assertThat(applyMoveZeroes(numbers)).isEqualTo(expected);
    }

    @Test
    void testEmptyArray() {
        assertThatThrownBy(()->{
            applyMoveZeroes(new int[]{});
        }).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
                of(new int[]{0}, new int[]{0}),
                of(new int[]{1}, new int[]{1}),
                of(new int[]{1,2,3}, new int[]{1,2,3}),
                of(new int[]{1,3,0,2}, new int[]{1,3,2,0}),
                of(new int[]{0,0,0,0,0}, new int[]{0,0,0,0,0}),
                of(new int[]{1,3,0,2,0}, new int[]{1,3,2,0,0}),
                of(new int[]{0,1,2}, new int[]{1,2,0}),
                of(new int[]{1,0,2}, new int[]{1,2,0}),
                of(new int[]{1,2,0}, new int[]{1,2,0}),
                of(new int[]{1,0,0,0,3,2}, new int[]{1,3,2,0,0,0}),
                of(new int[]{0,0,0,0,0,1}, new int[]{1,0,0,0,0,0})
        );
    }

    private static int[] applyMoveZeroes(int[] numbers) {
        int[] originalRef = numbers;
        moveZeroes(numbers);
        assertThat(numbers).isSameAs(originalRef); // modified in-place?
        return numbers;
    }
}