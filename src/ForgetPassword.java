import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Map;

public class ForgetPassword extends JFrame {
    private JTextField userIdField, petNameField;
    private JPasswordField newPasswordField;
    private JLabel messageLabel;
    private JButton verifyButton;

    public ForgetPassword() {
       
        setTitle("Change Password");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(144, 238, 144)); 

        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(144, 238, 144));
        add(panel);

        
        JLabel titleLabel = new JLabel("Change Password");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setBounds(200, 10, 200, 30);
        panel.add(titleLabel);

        
        addLabelAndField(panel, "User ID:", 50, 60, userIdField = new JTextField());
        addLabelAndField(panel, "Pet Name:", 50, 100, petNameField = new JTextField());

        
        addLabelAndField(panel, "New Password:", 50, 140, newPasswordField = new JPasswordField());

        
        verifyButton = new JButton("Verify");
        verifyButton.setBounds(150, 180, 100, 30);
        panel.add(verifyButton);

        
        // message ta age confirm button er upore porto, tai niche namano holo
        messageLabel = new JLabel();
        messageLabel.setBounds(50, 260, 450, 30);
        panel.add(messageLabel);


        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(150, 220, 100, 30);
        confirmButton.setEnabled(false);
        panel.add(confirmButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(450, 300, 100, 30);
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        panel.add(backButton);
        backButton.addActionListener(e -> {
            dispose();
            Login.main(null);
        });

       
        verifyButton.addActionListener(e -> {
            String userId = userIdField.getText().trim();
            String petName = petNameField.getText().trim();


            if (verifyUserIdAndPetName(userId, petName)) {
                messageLabel.setText("User verified. Enter new password.");
                confirmButton.setEnabled(true); 
            } else {
                messageLabel.setText("Invalid User ID or Pet Name.");
                confirmButton.setEnabled(false); 
            }
        });

        
        confirmButton.addActionListener(e -> {
            String newPassword = new String(newPasswordField.getPassword());
            String userId = userIdField.getText().trim();
            String petName = petNameField.getText().trim();

            if (newPassword.length() < 4) {
                messageLabel.setText("New password must be at least 4 characters.");
                return;
            }

            
            boolean isMatched = updatePasswordIfValid(userId, petName, newPassword);

            if (isMatched) {
                messageLabel.setText("Password changed successfully.");
            } else {
                messageLabel.setText("Information error.");
            }
        });

        setVisible(true);
    }

    private void addLabelAndField(JPanel panel, String labelText, int x, int y, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 100, 30);
        panel.add(label);
        field.setBounds(x + 100, y, 200, 30);
        panel.add(field);
    }

    
    // age User Id er porer line take pet name dhorto, kintu oita asole Name line,
    // tai kokhono match korto na. ekhon field er naam diye thik value ta nei
    private boolean verifyUserIdAndPetName(String userId, String petName) {
        Map<String, String> user = UserStore.find(userId);
        return user != null && petName.equalsIgnoreCase(user.get("Pet Name"));
    }

    // age notun Password line jog hoto ar purono ta theke jeto, ekhon purono tai replace hoy
    private boolean updatePasswordIfValid(String userId, String petName, String newPassword) {
        if (!verifyUserIdAndPetName(userId, petName)) return false;
        Map<String, String> user = UserStore.find(userId);
        user.put("Password", newPassword);
        try {
            UserStore.update(user);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        new ForgetPassword();
    }
}
