import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLogin {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, backButton, forgotPasswordButton;

    public AdminLogin() {
        
        frame = new JFrame("Admin Login");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(170, 120, 215)); 
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        JLabel welcomeLabel = new JLabel("WELCOME ADMIN !!!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.YELLOW);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(welcomeLabel, gbc);

       
        JLabel usernameLabel = new JLabel("User Name:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(usernameLabel, gbc);

        
     usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBackground(new Color(10, 10, 100)); 
        usernameField.setForeground(Color.CYAN);
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(usernameField, gbc);
        
   JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(passwordLabel, gbc);

        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBackground(new Color(10, 10, 100)); 
        passwordField.setForeground(Color.CYAN);
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(passwordField, gbc);

        loginButton = new JButton("Log in");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(255, 50, 50)); 
        loginButton.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(loginButton, gbc);

        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(255, 50, 50)); 
        backButton.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(backButton, gbc);

       
        forgotPasswordButton = new JButton("Forgot password");
        forgotPasswordButton.setFont(new Font("Arial", Font.BOLD, 12));
        forgotPasswordButton.setBackground(new Color(255, 50, 50));
        forgotPasswordButton.setForeground(Color.BLACK);
        gbc.gridx = 1;
        frame.add(forgotPasswordButton, gbc);

       
        loginButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
      String enteredUsername = usernameField.getText();
       String enteredPassword = new String(passwordField.getPassword());

              
      if (enteredUsername.equals("admin") && enteredPassword.equals("admin123")) {
                   
      JOptionPane.showMessageDialog(frame, "Login Successful");
      new AdminDashboard();
     frame.dispose(); 
        } else {
          JOptionPane.showMessageDialog(frame, "Invalid Username or Password");
        }
          }
        });

       
      backButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
        // age shudhu window bondho hoto, kichu khola thakto na
        frame.dispose();
        Login.main(null);
            }
        });

       
      forgotPasswordButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
                
      JOptionPane.showMessageDialog(frame, "Forgot password functionality not implemented");
       }
      });

        
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}
