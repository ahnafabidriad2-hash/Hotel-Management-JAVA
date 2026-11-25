import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// userinfo.txt er sob user eikhan theke pora ar lekha hoy, jate protita page e
// alada kore file parse korte na hoy. file er format age jemon chilo temoni ase
public class UserStore {
    private static final String FILE = "userinfo.txt";
    private static final String SEPARATOR = "-------------------------------------------";

    // file e field gula ei order e thake
    public static final String[] FIELDS = {
        "User Id", "Name", "Email", "Phone", "Gender", "Age", "Address", "Pet Name", "Birthdate", "Password"
    };

    // ekjon user = field er naam -> value
    public static List<Map<String, String>> loadAll() {
        List<Map<String, String>> users = new ArrayList<>();
        Map<String, String> current = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("---")) {
                    if (!current.isEmpty()) users.add(current);
                    current = new LinkedHashMap<>();
                    continue;
                }
                int colon = line.indexOf(':');
                if (colon > 0) {
                    current.put(line.substring(0, colon).trim(), line.substring(colon + 1).trim());
                }
            }
        } catch (IOException e) {
            // file na thakle khali list
        }
        if (!current.isEmpty()) users.add(current);
        return users;
    }

    public static Map<String, String> find(String userId) {
        for (Map<String, String> user : loadAll()) {
            if (userId.equals(user.get("User Id"))) return user;
        }
        return null;
    }

    public static boolean exists(String userId) {
        return find(userId) != null;
    }

    public static void add(Map<String, String> user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, true))) {
            writeUser(writer, user);
        }
    }

    // ekjon user er info change kore puro file abar likhe dey
    public static void update(Map<String, String> changed) throws IOException {
        List<Map<String, String>> users = loadAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).get("User Id").equals(changed.get("User Id"))) {
                users.set(i, changed);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            for (Map<String, String> user : users) writeUser(writer, user);
        }
    }

    private static void writeUser(BufferedWriter writer, Map<String, String> user) throws IOException {
        for (String field : FIELDS) {
            writer.write(field + ": " + user.getOrDefault(field, "") + "\n");
        }
        writer.write(SEPARATOR + "\n");
    }
}
