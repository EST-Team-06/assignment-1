package zest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;
import static zest.FindFirstOccurrence.strStr;

public class FindFirstOccurrenceTest {

    @ParameterizedTest
    @MethodSource("testCases")
    void findFirstOccurrence(String haystack, String needle, int expected) {
        assertThat(strStr(haystack, needle)).isEqualTo(expected);
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
                of("aabbcc", "d", -1),
                of("aabbcc", "a", 0),
                of("aabbcc", "bb", 2),
                of("aabbc", "c", 4),
                of("sadbutsad", "sad", 0),
                of("abc", "abc", 0),
                of("a", "abc", -1),
                of("a".repeat(9999) + "b", "b", 9999),
                of("a".repeat(10000), "a", 0),
                of("haystac", "stack", -1),
                of("aystack", "hay", -1)
        );
    }
}