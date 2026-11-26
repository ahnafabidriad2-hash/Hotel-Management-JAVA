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

    // "Customer ID: 101, Name: Rahim, ..." line theke ekta field er puro value ber kore.
    // age contains/startsWith diye mila hoto, tai "10" dile 101, 102 o mile jeto
    public static String field(String line, String key) {
        for (String part : line.split(",")) {
            int colon = part.indexOf(':');
            if (colon > 0 && part.substring(0, colon).trim().equalsIgnoreCase(key)) {
                return part.substring(colon + 1).trim();
            }
        }
        return null;
    }

    // file e oi ID age theke ase kina (ek ID duibar jate na hoy)
    public static boolean existsInFile(String file, String key, String value) {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (fieldIs(line, key, value)) return true;
            }
        } catch (java.io.IOException e) {
            // file nai mane kichu nai
        }
        return false;
    }

    // delete korar age ekbar jiggesh kore nei
    public static boolean confirm(java.awt.Component parent, String what) {
        return javax.swing.JOptionPane.showConfirmDialog(parent, "Delete " + what + "? This cannot be undone.",
                "Confirm Delete", javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION;
    }

    public static boolean fieldIs(String line, String key, String value) {
        String v = field(line, key);
        return v != null && v.equalsIgnoreCase(value.trim());
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
