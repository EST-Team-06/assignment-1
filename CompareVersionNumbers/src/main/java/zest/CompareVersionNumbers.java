package zest;

public class CompareVersionNumbers {

    /**
     * Compares two version strings.
     *
     * @param version1 first version string
     * @param version2 second version string
     * @return -1 if version1 < version2,
     *          1 if version1 > version2,
     *          0 if equal
     * @throws IllegalArgumentException if either input is null
     */
    public static int compareVersion(String version1, String version2) {
        if (version1 == null || version2 == null) {
            throw new IllegalArgumentException("Version strings cannot be null");
        }

        if (version1.length() > 500 || version2.length() > 500) {
            throw new IllegalArgumentException("Version strings cannot be null");
        }

        if (version1.startsWith(".") || version1.endsWith(".") || version2.startsWith(".") || version2.endsWith(".")) {
            throw new IllegalArgumentException("Invalid version string, must not start or end with dot");
        }


        String[] v1Parts = version1.split("\\.");
        String[] v2Parts = version2.split("\\.");

        if (v1Parts.length < 2 || v2Parts.length < 2) {
            throw new IllegalArgumentException("Invalid version string, must contain numbers separated by dots");
        }


        int maxLength = Math.max(v1Parts.length, v2Parts.length);

        for (int i = 0; i < maxLength; i++) {

            int num1 = (i < v1Parts.length) ? Integer.parseInt(v1Parts[i]) : 0;
            int num2 = (i < v2Parts.length) ? Integer.parseInt(v2Parts[i]) : 0;

            if (num1 < 0 || num1 > 9 || num2 < 0 || num2 > 9) {
                throw new IllegalArgumentException("Numbers used in version string must be digits (0-9)");
            }

            if (num1 < num2) {
                return -1;
            } else if (num1 > num2) {
                return 1;
            }
        }

        return 0;
    }

//    public static void main(String[] args) {
//        System.out.println(compareVersion(".", "1.0"));
//    }

}