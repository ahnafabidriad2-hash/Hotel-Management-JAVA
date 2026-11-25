import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ManagerLogin {

    
    public ManagerLogin() {
        
        JFrame frame = new JFrame("Hotel Manager Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.CYAN);

        
        JLabel titleLabel = new JLabel("Hotel Manager");
        titleLabel.setBounds(150, 20, 200, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(titleLabel);

        
        JLabel managerIdLabel = new JLabel("Manager Id :");
        managerIdLabel.setBounds(50, 80, 100, 30);
        frame.add(managerIdLabel);

        JTextField managerIdField = new JTextField();
        managerIdField.setBounds(150, 80, 200, 30);
        frame.add(managerIdField);

        
        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(50, 130, 100, 30);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 130, 200, 30);
        frame.add(passwordField);

        
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 180, 80, 30);
        frame.add(loginButton);

        
        JButton backButton = new JButton("Back");
        backButton.setBounds(300, 220, 70, 30);
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        frame.add(backButton);

        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String managerId = managerIdField.getText();
                String password = new String(passwordField.getPassword());

                if (authenticate(managerId, password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    frame.dispose();
                    Manager.main(null); 
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Manager ID or Password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        
        frame.setVisible(true);
    }

    
    private static boolean authenticate(String managerId, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("manager.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].equals(managerId) && credentials[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading manager.txt file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    } 

    
    public static void main(String[] args) {
        new ManagerLogin(); 
    }
}
