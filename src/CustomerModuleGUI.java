import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

// profile page. age eita abar notun kore naam/email chaito ar update kichui save korto na,
// ekhon je login kore ase tar asol info userinfo.txt theke dekhay ar update korle save hoy
public class CustomerModuleGUI {
    private Map<String, String> user;

    public CustomerModuleGUI() {
        user = Session.userId == null ? null : UserStore.find(Session.userId);
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Please log in first.", "Error", JOptionPane.ERROR_MESSAGE);
            Login.main(null);
            return;
        }
        createProfileDashboard();
    }

    private void createProfileDashboard() {
        JFrame profileFrame = new JFrame("My Profile");
        profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        profileFrame.setSize(450, 420);
        profileFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        profileFrame.add(panel);

        JLabel titleLabel = new JLabel("My Profile");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setBounds(50, 15, 300, 30);
        panel.add(titleLabel);

        // password chara baki sob field dekhai
        String[] shown = {"User Id", "Name", "Email", "Phone", "Gender", "Age", "Address", "Birthdate"};
        int y = 60;
        for (String field : shown) {
            JLabel label = new JLabel(field + ":  " + user.getOrDefault(field, ""));
            label.setBounds(50, y, 350, 25);
            panel.add(label);
            y += 28;
        }

        JButton updateProfileButton = new JButton("Update Profile");
        updateProfileButton.setBounds(50, y + 15, 150, 30);
        panel.add(updateProfileButton);

        JButton backButton = new JButton("Back to Homepage");
        backButton.setBounds(220, y + 15, 160, 30);
        panel.add(backButton);

        updateProfileButton.addActionListener(e -> {
            profileFrame.dispose();
            createUpdateProfilePage();
        });

        backButton.addActionListener(e -> {
            profileFrame.dispose();
            new CustomerDashboard().createDashboardPage();
        });

        profileFrame.setVisible(true);
    }

    private void createUpdateProfilePage() {
        JFrame updateFrame = new JFrame("Update Profile");
        updateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateFrame.setSize(400, 380);
        updateFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        updateFrame.add(panel);

        JTextField nameText = addField(panel, "Name:", 50, user.get("Name"));
        JTextField emailText = addField(panel, "Email:", 90, user.get("Email"));
        JTextField phoneText = addField(panel, "Phone:", 130, user.get("Phone"));
        JTextField addressText = addField(panel, "Address:", 170, user.get("Address"));

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(150, 220, 80, 30);
        panel.add(saveButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 270, 80, 30);
        panel.add(backButton);

        saveButton.addActionListener(e -> {
            String name = nameText.getText().trim();
            String email = emailText.getText().trim();
            String phone = phoneText.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(updateFrame, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.isEmpty() && !Validate.isEmail(email)) {
                JOptionPane.showMessageDialog(updateFrame, "Please enter a valid email.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Validate.isPhone(phone)) {
                JOptionPane.showMessageDialog(updateFrame, "Phone number must be 11 digits and start with 01.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            user.put("Name", name);
            user.put("Email", email);
            user.put("Phone", phone);
            user.put("Address", addressText.getText().trim());
            try {
                UserStore.update(user);
                JOptionPane.showMessageDialog(updateFrame, "Profile Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateFrame.dispose();
                createProfileDashboard();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(updateFrame, "Could not save your profile.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            updateFrame.dispose();
            createProfileDashboard();
        });

        updateFrame.setVisible(true);
    }

    private JTextField addField(JPanel panel, String labelText, int y, String value) {
        JLabel label = new JLabel(labelText);
        label.setBounds(50, y, 100, 25);
        panel.add(label);
        JTextField field = new JTextField(value == null ? "" : value);
        field.setBounds(150, y, 180, 25);
        panel.add(field);
        return field;
    }

    public static void main(String[] args) {
        new CustomerModuleGUI();
    }
}
