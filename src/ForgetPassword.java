import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

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

        
        messageLabel = new JLabel();
        messageLabel.setBounds(50, 220, 400, 30);
        panel.add(messageLabel);

        
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(150, 220, 100, 30);
        confirmButton.setEnabled(false); 
        panel.add(confirmButton);

       
        verifyButton.addActionListener(e -> {
            String userId = userIdField.getText();
            String petName = petNameField.getText();

            
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
            String userId = userIdField.getText();
            String petName = petNameField.getText();

            
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

    
    private boolean verifyUserIdAndPetName(String userId, String petName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("userinfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("User Id: " + userId)) {
                    String storedPetName = reader.readLine().substring(10).trim();
                    if (storedPetName.equals(petName)) {
                        return true; 
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    private boolean updatePasswordIfValid(String userId, String petName, String newPassword) {
        try {
            StringBuilder fileContent = new StringBuilder();
            boolean isMatched = false;
            try (BufferedReader reader = new BufferedReader(new FileReader("userinfo.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");
                    if (line.startsWith("User Id: " + userId)) {
                        String storedPetName = reader.readLine().substring(10).trim();
                        if (storedPetName.equals(petName)) {
                            isMatched = true;
                            
                            for (int i = 0; i > 0; i++) reader.readLine();
                            fileContent.append("Password: ").append(newPassword).append("\n");
                        }
                    }
                }
            }

            
            if (isMatched) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("userinfo.txt"))) {
                    writer.write(fileContent.toString());
                }
            }
            return isMatched;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        new ForgetPassword();
    }
}
