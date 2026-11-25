import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerModuleGUI {
    private String userName = "";
    private String userEmail = "";
    private String userPassword = "";
    private String userPhone = "";
    private String userAddress = "";

    public CustomerModuleGUI() {
        createLoginPage();  // Start by showing the login page
    }

    private void createLoginPage() {
        JFrame loginFrame = new JFrame("Profile Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        loginFrame.add(panel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 20, 80, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(150, 20, 160, 25);
        panel.add(nameText);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 60, 80, 25);
        panel.add(emailLabel);

        JTextField emailText = new JTextField(20);
        emailText.setBounds(150, 60, 160, 25);
        panel.add(emailText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 100, 160, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("OK");
        loginButton.setBounds(150, 150, 80, 25);
        panel.add(loginButton);

        JButton backButton = new JButton("Back to Homepage");
        backButton.setBounds(120, 200, 160, 25);
        panel.add(backButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userName = nameText.getText();
                userEmail = emailText.getText();
                userPassword = new String(passwordText.getPassword());
                loginFrame.dispose();
                createProfileDashboard();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();  // Close the login frame
                new CustomerDashboard().createDashboardPage();  // Go back to the dashboard
            }
        });

        loginFrame.setVisible(true);
    }

    private void createProfileDashboard() {
        JFrame profileFrame = new JFrame("Profile Dashboard");
        profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        profileFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        profileFrame.add(panel);

        JLabel nameLabel = new JLabel("Name: " + userName);
        nameLabel.setBounds(50, 50, 300, 30);
        panel.add(nameLabel);

        JLabel emailLabel = new JLabel("Email: " + userEmail);
        emailLabel.setBounds(50, 80, 300, 30);
        panel.add(emailLabel);

        JButton updateProfileButton = new JButton("Update Profile");
        updateProfileButton.setBounds(50, 120, 150, 30);
        panel.add(updateProfileButton);

        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.setBounds(220, 120, 150, 30);
        panel.add(viewProfileButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(50, 160, 150, 30);
        panel.add(logoutButton);

        JButton backButton = new JButton("Back to Homepage");
        backButton.setBounds(220, 160, 150, 30);
        panel.add(backButton);

        updateProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profileFrame.dispose();
                createUpdateProfilePage();
            }
        });

        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(profileFrame, "Name: " + userName + "\nEmail: " + userEmail + "\nPhone: " + userPhone + "\nAddress: " + userAddress, "Profile Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profileFrame.dispose();
                new CustomerDashboard().createDashboardPage();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profileFrame.dispose();
                new CustomerDashboard().createDashboardPage();  // Return to dashboard
            }
        });

        profileFrame.setVisible(true);
    }

    private void createUpdateProfilePage() {
        JFrame updateFrame = new JFrame("Update Profile");
        updateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateFrame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        updateFrame.add(panel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(userName);
        nameText.setBounds(150, 50, 160, 25);
        panel.add(nameText);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 90, 100, 25);
        panel.add(emailLabel);

        JTextField emailText = new JTextField(userEmail);
        emailText.setBounds(150, 90, 160, 25);
        panel.add(emailText);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 130, 100, 25);
        panel.add(phoneLabel);

        JTextField phoneText = new JTextField(userPhone);
        phoneText.setBounds(150, 130, 160, 25);
        panel.add(phoneText);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 170, 100, 25);
        panel.add(addressLabel);

        JTextField addressText = new JTextField(userAddress);
        addressText.setBounds(150, 170, 160, 25);
        panel.add(addressText);

        JButton saveButton = new JButton("OK");
        saveButton.setBounds(150, 220, 80, 30);
        panel.add(saveButton);

        JButton backButton = new JButton("Back to Homepage");
        backButton.setBounds(120, 270, 160, 30);
        panel.add(backButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userName = nameText.getText();
                userEmail = emailText.getText();
                userPhone = phoneText.getText();
                userAddress = addressText.getText();
                JOptionPane.showMessageDialog(updateFrame, "Profile Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFrame.dispose();
                new CustomerDashboard().createDashboardPage();  // Return to dashboard
            }
        });

        updateFrame.setVisible(true);
    }
    public static void main(String[] args) {
        CustomerModuleGUI customerModule = new CustomerModuleGUI();
        customerModule.createLoginPage();
    }
}
