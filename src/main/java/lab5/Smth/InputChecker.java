package lab5.Smth;

/**
 * Checks if the input number is greater than or equal to our range
 *
 * @author Nagorny Leonid
 */

public class InputChecker {
    public InputChecker() {
    }


    public static boolean integerValidCheck(String s, int min, int max) {
        try {
            int x = Integer.parseInt(s);
            if (x >= min && x <= max) return true;
            System.out.println("Ввод недействителен. Пожалуйста, введите целое число в правильном диапазоне");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Ввод недействителен.Пожалуйста введите целое число");
            return false;
        }

    }

    public boolean longValidCheck(String s, Long min, Long max) {
        try {
            long x = Long.parseLong(s);
            if (x >= min && x <= max) return true;
            System.out.println("Ввод недействителен. Пожалуйста, введите целое число в правильном диапазоне");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Ввод недействителен.Пожалуйста введите число типа long");
            return false;
        }
    }
}
