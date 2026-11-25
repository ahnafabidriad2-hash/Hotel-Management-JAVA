import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

class Registration {
    public Registration() {
        
        JFrame frame = new JFrame("Registration Page");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);

        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(173, 216, 230));

        
        JLabel titleLabel = new JLabel("Registration");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setBounds(350, 10, 200, 40);
        panel.add(titleLabel);

        
        JLabel userIdLabel = new JLabel("User Id :");
        userIdLabel.setBounds(50, 80, 100, 30);
        panel.add(userIdLabel);

        JTextField userIdText = new JTextField();
        userIdText.setBounds(150, 80, 200, 30);
        panel.add(userIdText);

        JLabel nameLabel = new JLabel("Name :");
        nameLabel.setBounds(50, 120, 100, 30);
        panel.add(nameLabel);

        JTextField nameText = new JTextField();
        nameText.setBounds(150, 120, 200, 30);
        panel.add(nameText);

        JLabel emailLabel = new JLabel("E-mail :");
        emailLabel.setBounds(50, 160, 100, 30);
        panel.add(emailLabel);

        JTextField emailText = new JTextField();
        emailText.setBounds(150, 160, 200, 30);
        panel.add(emailText);

        JLabel phoneLabel = new JLabel("Phone No :");
        phoneLabel.setBounds(50, 200, 100, 30);
        panel.add(phoneLabel);

        JTextField phoneText = new JTextField();
        phoneText.setBounds(150, 200, 200, 30);
        panel.add(phoneText);

        JLabel genderLabel = new JLabel("Gender :");
        genderLabel.setBounds(50, 240, 100, 30);
        panel.add(genderLabel);

        JRadioButton maleButton = new JRadioButton("Male");
        maleButton.setBounds(150, 240, 70, 30);
        maleButton.setBackground(new Color(173, 216, 230));
        panel.add(maleButton);

        JRadioButton femaleButton = new JRadioButton("Female");
        femaleButton.setBounds(220, 240, 80, 30);
        femaleButton.setBackground(new Color(173, 216, 230));
        panel.add(femaleButton);

        JRadioButton preferNotToSayButton = new JRadioButton("Prefer not to say");
        preferNotToSayButton.setBounds(300, 240, 150, 30);
        preferNotToSayButton.setBackground(new Color(173, 216, 230));
        panel.add(preferNotToSayButton);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(preferNotToSayButton);

        JLabel ageLabel = new JLabel("Age :");
        ageLabel.setBounds(50, 280, 100, 30);
        panel.add(ageLabel);

        JTextField ageText = new JTextField();
        ageText.setBounds(150, 280, 200, 30);
        panel.add(ageText);

        JLabel addressLabel = new JLabel("Address :");
        addressLabel.setBounds(50, 320, 100, 30);
        panel.add(addressLabel);

        JTextField addressText = new JTextField();
        addressText.setBounds(150, 320, 200, 30);
        panel.add(addressText);

        
        JLabel securityQuestionsLabel = new JLabel("Security Questions-");
        securityQuestionsLabel.setBounds(500, 80, 200, 30);
        panel.add(securityQuestionsLabel);

        JLabel petNameLabel = new JLabel("1. What is your pet name?");
        petNameLabel.setBounds(500, 120, 200, 30);
        panel.add(petNameLabel);

        JTextField petNameText = new JTextField();
        petNameText.setBounds(500, 150, 200, 30);
        panel.add(petNameText);

        JLabel birthdateLabel = new JLabel("2. Your birthdate?");
        birthdateLabel.setBounds(500, 190, 200, 30);
        panel.add(birthdateLabel);

        JTextField birthdateText = new JTextField();
        birthdateText.setBounds(500, 220, 200, 30);
        panel.add(birthdateText);

        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(500, 260, 200, 30);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(500, 290, 200, 30);
        panel.add(passwordText);

        
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(380, 400, 100, 30);
        panel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdText.getText().trim();
                String password = new String(passwordText.getPassword());

                // save korar age sob check, kono vul thakle message dekhiye fire jai
                String error = null;
                if (userId.isEmpty() || nameText.getText().trim().isEmpty() || password.isEmpty()
                        || petNameText.getText().trim().isEmpty()) {
                    error = "User Id, Name, Pet Name and Password are required.";
                } else if (UserStore.exists(userId)) {
                    error = "This User Id is already taken. Choose another one.";
                } else if (!emailText.getText().trim().isEmpty() && !Validate.isEmail(emailText.getText().trim())) {
                    error = "Please enter a valid email.";
                } else if (!Validate.isPhone(phoneText.getText().trim())) {
                    error = "Phone number must be 11 digits and start with 01.";
                } else if (!Validate.isWholeNumber(ageText.getText(), 1, 120)) {
                    error = "Please enter a valid age.";
                } else if (password.length() < 4) {
                    error = "Password must be at least 4 characters.";
                }
                if (error != null) {
                    JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String gender = maleButton.isSelected() ? "Male" : femaleButton.isSelected() ? "Female" : "Prefer not to say";
                Map<String, String> user = new LinkedHashMap<>();
                user.put("User Id", userId);
                user.put("Name", nameText.getText().trim());
                user.put("Email", emailText.getText().trim());
                user.put("Phone", phoneText.getText().trim());
                user.put("Gender", gender);
                user.put("Age", ageText.getText().trim());
                user.put("Address", addressText.getText().trim());
                user.put("Pet Name", petNameText.getText().trim());
                user.put("Birthdate", birthdateText.getText().trim());
                user.put("Password", password);

                try {
                    UserStore.add(user);
                    JOptionPane.showMessageDialog(frame, "Registration successful! You can now log in with User Id " + userId + ".");
                    frame.dispose();
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(frame, "Could not save your data.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        JButton backButton = new JButton("Back");
        backButton.setBounds(750, 500, 100, 30);
        backButton.setBackground(new Color(139, 0, 0)); 
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
            }
        });
        panel.add(backButton);

        
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new Registration();
    }
}
