package zest;

public class PlusOne {

    /**
     * Increments a large integer represented as an array of digits.
     *
     * @param digits the array representing the integer
     * @return a new array representing the incremented integer
     * @throws IllegalArgumentException if digits is null or empty
     */
    public static int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0 || digits.length > 100) {
            throw new IllegalArgumentException("Input array length must be between 1 and 100");
        }

        if (digits.length > 1 && digits[0] == 0) {
            throw new IllegalArgumentException("Input array cannot have leading zeros");
        }

        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 0 || digits[i] > 9) {
                throw new IllegalArgumentException("Digits must be between 0 and 9");
            }
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            digits[i] = 0;
        }

        // If we reach here, all digits were 9 (e.g., 999)
        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }

}