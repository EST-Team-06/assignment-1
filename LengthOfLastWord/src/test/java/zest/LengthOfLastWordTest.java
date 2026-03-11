package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;
import static zest.LengthOfLastWord.lengthOfLastWord;;

public class LengthOfLastWordTest {

    @ParameterizedTest
    @MethodSource("testCases")
    void lengthOfLastWordCases(String s, int expected) {
        assertThat(lengthOfLastWord(s)).isEqualTo(expected);
    }

    @Test
    void testEmptyString() {
        assertThatThrownBy(()->{
            lengthOfLastWord("");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidInput() {
        assertThatThrownBy(()->{
            lengthOfLastWord(".");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testStringWithInvalidAndValidCharacters() {
        assertThatThrownBy(()->{
            lengthOfLastWord("hello.world");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testBoundaryString() {
        assertThatThrownBy(()->{
            lengthOfLastWord("a".repeat(10001));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
                of("foo", 3),
                of("foo bar", 3),
                of("  foo bar", 3),
                of("foo bar  ", 3),
                of("   ", 0),
                of("hello w", 1),
                of("hello world", 5),
                of("foo  bar   baz", 3)
                
        );
    }
}