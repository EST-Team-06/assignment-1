package zest;

public class ExcelSheetColumnNumber {

    /**
     * Converts an Excel column title (e.g., "A", "AB", "ZY")
     * into its corresponding column number.
     *
     * @param columnTitle the Excel column title
     * @return corresponding column number
     * @throws IllegalArgumentException if columnTitle is null or empty
     */
    public static int titleToNumber(String columnTitle) {
        if (columnTitle == null || columnTitle.isEmpty()) {
            throw new IllegalArgumentException("Column title cannot be null or empty");
        }

        if (columnTitle.length() > 7) {
            throw new IllegalArgumentException("Column title may not contain more than 7 letters at most");
        }

        int result = 0;

        for (int i = 0; i < columnTitle.length(); i++) {
            char c = columnTitle.charAt(i);
            if (c < 'A' || c > 'Z') {
                throw new IllegalArgumentException("Invalid character in column title");
            }
            result = result * 26 + (c - 'A' + 1);

            if (result < 0) {
                throw new IllegalArgumentException("Column title may not exceed FXSHRXW");
            }
        }

        return result;
    }

//    public static void main(String[] args) {
//        int check = Integer.MAX_VALUE;
//        System.out.println(check);
//        System.out.println(titleToNumber("FXSHRXW"));
//    }

}