// form er input check korar choto helper gula
public class Validate {

    // bangladeshi mobile number: 01 diye shuru, mot 11 digit
    public static boolean isPhone(String text) {
        return text.matches("01\\d{9}");
    }

    public static boolean isEmail(String text) {
        return text.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
    }

    // 0 ba tar beshi number kina (taka, salary egular jonno)
    public static boolean isAmount(String text) {
        try {
            return Double.parseDouble(text.trim()) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isWholeNumber(String text, int min, int max) {
        try {
            int n = Integer.parseInt(text.trim());
            return n >= min && n <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // file er "Payment: 3000" er moto value pore, vul thakle 0 dhore crash thekay
    public static double parseAmount(String text) {
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
