package nl.saxion.fundamentals.utils;

public class HexBinUtils {
    private static final String LEADING = "00000000000000000000000000000000000";

    public static String getHexadecimalPrompt(int length) {
        return "0x"+LEADING.substring(0, length/4);
    }

    public static String getBinaryPrompt(int length) {
        return "0b"+LEADING.substring(0, length);
    }

    public static String toBinary(int value, int length) {
        String result = Integer.toBinaryString(value);
        return "0b"+LEADING.substring(0, length-result.length())+result;
    }

    public static String toDecimal(int value, int length) {
        return "" + value;
    }

    public static String toHexadecimal(int value, int length) {
        String result = Integer.toHexString(value);
        return "0x"+LEADING.substring(0, length-result.length())+result;
    }

    public static int parseHexadecimal(String answer) throws NumberFormatException {
        if (answer.startsWith("0x")) {
            answer = answer.substring(2);
        }
        if (answer.trim().length()==0) {
            return 0;
        } else {
            // @TODO Why does 0x10000 return 4096???
            return Integer.parseInt(answer, 16);
        }
    }

    public static int parseDecimalAnswer(String answer) throws NumberFormatException {
        if (answer.trim().length()==0) {
            return 0;
        } else {
            return Integer.parseInt(answer);
        }
    }

    public static int parseBinaryAnswer(String answer) throws NumberFormatException {
        if (answer.startsWith("0b")) {
            answer = answer.substring(2);
        }
        if (answer.trim().length()==0) {
            return 0;
        } else {
            return Integer.parseInt(answer, 2);
        }
    }
}
