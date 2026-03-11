package zest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;
import static zest.LongestCommonPrefix.longestCommonPrefix;

public class LongestCommonPrefixTest {
    @ParameterizedTest
    @MethodSource("testCases")
    void lengthOfLastWordCases(String[] s, String expected) {
        assertThat(longestCommonPrefix(s)).isEqualTo(expected);
    }

    @Test
    void testEmptyArray() {
        assertThatThrownBy(() ->
                longestCommonPrefix(new String[]{}))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidCharacter_1() {
        assertThatThrownBy(() ->
                longestCommonPrefix(new String[]{"foo", "."}))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidCharacter_2() {
        assertThatThrownBy(() ->
                longestCommonPrefix(new String[]{"foo", "}"}))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testTooManyStrings() {
        String[] arr = Collections.nCopies(201, "foo").toArray(new String[0]);

        assertThatThrownBy(() ->
                longestCommonPrefix(arr))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testFirstStringTooLong() {
        assertThatThrownBy(() ->
                longestCommonPrefix(new String[]{"a".repeat(201)}))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testSecondStringTooLong() {
        assertThatThrownBy(() ->
                longestCommonPrefix(new String[]{"a", "a".repeat(201)}))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testNullArray() {
        assertThatThrownBy(() ->
                longestCommonPrefix(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
                of(new String[]{""}, ""),
                of(new String[]{"foo", ""}, ""),
                of(new String[]{"foo"}, "foo"),
                of(new String[]{"foo", "bar"}, ""),
                of(new String[]{"foo", "f"}, "f"),
                of(new String[]{"foo", "foobar"}, "foo"),
                of(Collections.nCopies(200, "foo").toArray(new String[0]), "foo"),
                of(new String[]{"a".repeat(200)}, "a".repeat(200)),
                of(new String[]{"hello", "hell", "hel", "he"}, "he"),
                of(new String[]{"he", "hel", "hell", "hello"}, "he")
        );
    }
}