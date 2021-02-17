package converter;
import java.util.*;

public class Main {
    private static final Map<String, Integer> strToNum = new HashMap<>();

    static {
        strToNum.put("0", 0);
        strToNum.put("1", 1);
        strToNum.put("2", 2);
        strToNum.put("3", 3);
        strToNum.put("4", 4);
        strToNum.put("5", 5);
        strToNum.put("6", 6);
        strToNum.put("7", 7);
        strToNum.put("8", 8);
        strToNum.put("9", 9);
        strToNum.put("a", 10);
        strToNum.put("b", 11);
        strToNum.put("c", 12);
        strToNum.put("d", 13);
        strToNum.put("e", 14);
        strToNum.put("f", 15);
        strToNum.put("g", 16);
        strToNum.put("h", 17);
        strToNum.put("i", 18);
        strToNum.put("j", 19);
        strToNum.put("k", 20);
        strToNum.put("l", 21);
        strToNum.put("m", 22);
        strToNum.put("n", 23);
        strToNum.put("o", 24);
        strToNum.put("p", 25);
        strToNum.put("q", 26);
        strToNum.put("r", 27);
        strToNum.put("s", 28);
        strToNum.put("t", 29);
        strToNum.put("u", 30);
        strToNum.put("v", 31);
        strToNum.put("w", 32);
        strToNum.put("x", 33);
        strToNum.put("y", 34);
        strToNum.put("z", 35);

    }

    private static final Map<Integer, String> numToStr = new HashMap<>();

    static {
        numToStr.put(0, "0");
        numToStr.put(1, "1");
        numToStr.put(2, "2");
        numToStr.put(3, "3");
        numToStr.put(4, "4");
        numToStr.put(5, "5");
        numToStr.put(6, "6");
        numToStr.put(7, "7");
        numToStr.put(8, "8");
        numToStr.put(9, "9");
        numToStr.put(10, "a");
        numToStr.put(11, "b");
        numToStr.put(12, "c");
        numToStr.put(13, "d");
        numToStr.put(14, "e");
        numToStr.put(15, "f");
        numToStr.put(16, "g");
        numToStr.put(17, "h");
        numToStr.put(18, "i");
        numToStr.put(19, "j");
        numToStr.put(20, "k");
        numToStr.put(21, "l");
        numToStr.put(22, "m");
        numToStr.put(23, "n");
        numToStr.put(24, "o");
        numToStr.put(25, "p");
        numToStr.put(26, "q");
        numToStr.put(27, "r");
        numToStr.put(28, "s");
        numToStr.put(29, "t");
        numToStr.put(30, "u");
        numToStr.put(31, "v");
        numToStr.put(32, "w");
        numToStr.put(33, "x");
        numToStr.put(34, "y");
        numToStr.put(35, "z");
        numToStr.put(36, "a");
    }

    public static void main(String[] args) throws NumberFormatException {
        //Считываем ввод пользователя
        Scanner scanner = new Scanner(System.in);
        try {
            String sR = scanner.nextLine();
            int sourceRadix = Integer.parseInt(sR);

            String sourceNumber = scanner.nextLine();
            boolean isCorrect = checkSourceRadixForNumber(sourceRadix, sourceNumber);

            if (!isCorrect) {
                System.out.println("error!");
                return;
            }
            String tR = scanner.nextLine();
            int targetRadix = Integer.parseInt(tR);


            scanner.close();

            if (sourceRadix < 1 || sourceRadix > 36 || targetRadix < 1 || targetRadix > 36) {
                System.out.println("Error! Wrong input!");
                return;
            }

            String answer;
            boolean isTenthBase = checkIfSourceBaseIsTen(sourceRadix);
            if (!isTenthBase) {
                sourceNumber = turnToTenthBase(sourceNumber, sourceRadix);
            }
            if (sourceNumber == null) {
                System.out.println("Error!");
            } else {
                answer = turnNumberIntoTargetRadix(sourceNumber, targetRadix);
                System.out.println(answer);
            }
        } catch (NumberFormatException e) {
            System.out.println("error");
        }

    }

    private static boolean checkSourceRadixForNumber(int sourceRadix, String sourceNumber) throws NumberFormatException {
        boolean isCorrect = true;
        try {
            if (sourceRadix == 1) {
                String[] number = sourceNumber.split("");
                for (String s : number) {
                    if (!s.equals("1")) {
                        return false;
                    }
                }
                return true;
            } else {
                String[] number = sourceNumber.split("\\.");
                String integer = number[0];
                sourceNumber = convertFromLetterToSymbols(integer);
            }
        } catch (NullPointerException e) {
            System.out.println("Error!");
            return false;
        }
        String[] numbers = sourceNumber.split(" ");
        for (String number : numbers) {
            if (Integer.parseInt(number) >= sourceRadix) {
                isCorrect = false;
            }
        }
        return isCorrect;
    }

    private static String turnToTenthBase(String sourceNumber, int sourceRadix) throws NullPointerException {
        String result;
        StringBuilder builder = new StringBuilder();
        if (sourceRadix == 1) {
            try {
                sourceNumber = convertFromLetterToSymbols(sourceNumber);
            } catch (NullPointerException e) {
                System.out.println("Error!");
                return null;
            }
            result = Integer.toString(sourceNumber.length() / 2);
        } else {
            String[] number = sourceNumber.split("\\.");
            if (number.length == 1) {
                result = convertIntegerToTenthBase(sourceNumber, sourceRadix);
            } else {
                String integer = number[0];
                String fractional = number[1];
                integer = convertIntegerToTenthBase(integer, sourceRadix);
                fractional = convertFractionalToTenth(fractional, sourceRadix);
                builder.append(integer);
                builder.append(".");
                builder.append(fractional);
                result = builder.toString();
            }
        }
        return result;
    }

    private static String convertIntegerToTenthBase(String sourceNumber, int base) throws NullPointerException {
        try {
            sourceNumber = convertFromLetterToSymbols(sourceNumber);
        } catch (NullPointerException e) {
            System.out.println("Error!");
            return null;
        }
        String[] theNumber = sourceNumber.split(" ");
        long result = 0;
        for (int i = 0; i < theNumber.length; i++) {
            result += (long) (Integer.parseInt(theNumber[i]) * Math.pow(base, (theNumber.length - i - 1)));
        }
        return Long.toString(result);
    }

    private static String convertFractionalToTenth(String fractional, int base) throws NullPointerException {
        try {
            fractional = convertFromLetterToSymbols(fractional);
        } catch (NullPointerException e) {
            System.out.println("Error!");
            return null;
        }
        String[] fromFractional = fractional.split(" ");
        double result = 0.0;
        for (int i = 0; i < fromFractional.length; i++) {
            int part = Integer.parseInt(fromFractional[i]);
            result += part / Math.pow(base, i + 1);
        }
        String[] fromDouble = Double.toString(result).split("\\.");
        return fromDouble[1];
    }

    private static boolean checkIfSourceBaseIsTen(int sourceRadix) {
        boolean isTenth = true;
        if (sourceRadix != 10) {
            isTenth = false;
        }
        return isTenth;
    }

    private static String turnNumberIntoTargetRadix(String sourceNumber, int targetRadix) {
        StringBuilder builder = new StringBuilder();
        String[] number = sourceNumber.split("\\.");
        String result;
        if (targetRadix == 1) {
            result = builder.append("1".repeat(Math.max(0, Integer.parseInt(number[0])))).toString();
        } else if (targetRadix == 10) {
            result = turnToTenthBase(sourceNumber, targetRadix);
        } else {
            if (number.length == 1) {
                result = Integer.toString(Integer.parseInt(number[0]), targetRadix);
            } else {
                String integer = number[0];
                String fraction = number[1];
                integer = Long.toString(Long.parseLong(integer), targetRadix);
                fraction = convertFromTenthToRadix(fraction, targetRadix);
                builder.append(integer);
                builder.append(".");
                builder.append(fraction);
                result = builder.toString();
            }
        }
        return result;
    }

    private static String convertFromTenthToRadix(String fraction, int targetRadix) {
        StringBuilder builder = new StringBuilder();
        double number = Double.parseDouble("0." + fraction);
        for (int i = 0; i < 5; i++) {
            number = number * targetRadix;
            int integer = (int) number;
            builder.append(numToStr.get(integer));
            number -= integer;
        }
        return builder.toString();
    }

    private static String convertFromLetterToSymbols(String s) {
        StringBuilder numberToConvert = new StringBuilder();
        String[] numberByDigits = s.split("");
        for (String numberByDigit : numberByDigits) {
            if (strToNum.get(numberByDigit) != null) {
                numberToConvert.append(strToNum.get(numberByDigit));
                if (numberByDigits.length != 1) {
                    numberToConvert.append(" ");
                }
            } else {
                System.out.println("Error! Wrong input!");
                numberToConvert = null;
                break;
            }
        }
        assert numberToConvert != null;
        return numberToConvert.toString();
    }
}
