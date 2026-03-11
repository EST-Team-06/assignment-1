package zest;

public class LengthOfLastWord {

    /**
     * Returns the length of the last word in the given string.
     * A word is defined as a maximal substring consisting of non-space characters.
     *
     * @param s the input string
     * @return length of the last word
     * @throws IllegalArgumentException if s is null
     */
    public static int lengthOfLastWord(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }

        if (s.length() == 0 || s.length() > 10000) {
            throw new IllegalArgumentException("Input string must have a length between 1 and 10000");
        }

        int length = 0;
        int i = s.length() - 1;

        // Skip trailing spaces
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }

        // Count characters of last word
        while (i >= 0 && s.charAt(i) != ' ') {
            if (!isEnglishLetter(s.charAt(i))) {
                throw new IllegalArgumentException("All input strings must be valid");
            }
            length++;
            i--;
        }

        return length;
    }

    private static boolean isEnglishLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLastWord("foobar"));
        System.out.println(lengthOfLastWord("foo bar"));
        System.out.println(lengthOfLastWord("foo bar baz"));
    }
}