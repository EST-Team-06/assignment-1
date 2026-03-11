package zest;

public class LongestCommonPrefix {

    /**
     * Returns the longest common prefix string amongst an array of strings.
     * If there is no common prefix, returns an empty string "".
     *
     * @param strs array of strings
     * @return longest common prefix
     * @throws IllegalArgumentException if strs is null
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }

        if (strs.length == 0 || strs.length > 200) {
            throw new IllegalArgumentException("Input array cannot be empty");
        }

        String prefix = strs[0];
        if (strs[0].length() > 200) {
            throw new IllegalArgumentException("Input array contains more than 200 characters");
        }

        for (int i = 1; i < strs.length; i++) {
            if (strs[i].length() > 200) {
                throw new IllegalArgumentException("Input array contains more than 200 characters");
            }
            if (containsNonEnglishCharacters(strs[i])) {
                throw new IllegalArgumentException("Input array contains non-English characters");
            }
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }

        return prefix;
    }

    private static boolean containsNonEnglishCharacters(String str) {
        str = str.toLowerCase();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < 'a' || c > 'z') {
                return true;
            }
        }
        return false;
    }

//    public static void main(String[] args) {
//        System.out.println(longestCommonPrefix(new String[] {"foobar", "foo"}));
//        System.out.println(longestCommonPrefix(new String[] {"foo", "bar"}));
//    }
}