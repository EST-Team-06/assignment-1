package zest;

public class AddBinary {

    /**
     * Adds two binary strings and returns the result as a binary string.
     *
     * @param a first binary string
     * @param b second binary string
     * @return binary sum of a and b
     * @throws IllegalArgumentException if a or b is null
     */
    public static String addBinary(String a, String b) {
        if (a == null || b == null || a.isEmpty() || b.isEmpty()) {
            throw new IllegalArgumentException("Input strings cannot be null");
        }

        StringBuilder result = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;

        if (i+1 > Math.pow(10, 4) || j+1 > Math.pow(10, 4)) {
            throw new IllegalArgumentException("Input strings cannot be greater than 10^4");
        }


        int carry = 0;

        while (i >= 0 || j >= 0) {

            int sum = carry;

            if (i >= 0) {
                sum += a.charAt(i) - '0';
                i--;
            }

            if (j >= 0) {
                sum += b.charAt(j) - '0';
                j--;
            }

            result.append(sum % 2);
            carry = sum / 2;
        }

        if (carry != 0) {
        result.append(carry);
        }

        return result.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(addBinary("1", "1"));
        System.out.println(addBinary("10", "1"));
        System.out.println(addBinary("11", "1"));
        System.out.println(addBinary("", "0"));


    }

}