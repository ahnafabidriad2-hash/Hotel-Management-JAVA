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

              
      // age password code er vitore lekha chilo, ekhon admin.txt e thake tai change kora jay
      String[] admin = readAdmin();
      if (enteredUsername.trim().equals(admin[0]) && enteredPassword.equals(admin[1])) {
                   
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
                
      // recovery word mile gele notun password set kora jay
      JTextField userField = new JTextField();
      JTextField wordField = new JTextField();
      JPasswordField newPassField = new JPasswordField();
      JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
      form.add(new JLabel("Username:"));
      form.add(userField);
      form.add(new JLabel("Recovery word:"));
      form.add(wordField);
      form.add(new JLabel("New password:"));
      form.add(newPassField);
      if (JOptionPane.showConfirmDialog(frame, form, "Reset Admin Password", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
      String[] admin = readAdmin();
      String newPass = new String(newPassField.getPassword());
      if (!userField.getText().trim().equals(admin[0]) || !wordField.getText().trim().equalsIgnoreCase(admin[2])) {
          JOptionPane.showMessageDialog(frame, "Username or recovery word is wrong.", "Error", JOptionPane.ERROR_MESSAGE);
      } else if (newPass.length() < 4) {
          JOptionPane.showMessageDialog(frame, "New password must be at least 4 characters.", "Error", JOptionPane.ERROR_MESSAGE);
      } else {
          try (java.io.PrintWriter w = new java.io.PrintWriter("admin.txt")) {
              w.println(admin[0] + "," + newPass + "," + admin[2]);
              JOptionPane.showMessageDialog(frame, "Password changed. You can log in now.");
          } catch (java.io.IOException ex) {
              JOptionPane.showMessageDialog(frame, "Could not save the new password.", "Error", JOptionPane.ERROR_MESSAGE);
          }
      }
       }
      });

        
    frame.setLocationRelativeTo(null);
    UI.show(frame);
    }

    public static void main(String[] args) {
        new AdminLogin();
    }

    // admin.txt: username,password,recovery word. file na thakle purono default ta
    static String[] readAdmin() {
        try (java.io.BufferedReader r = new java.io.BufferedReader(new java.io.FileReader("admin.txt"))) {
            String[] p = r.readLine().split(",", 3);
            if (p.length == 3) return new String[]{p[0].trim(), p[1].trim(), p[2].trim()};
        } catch (Exception e) {
            // file nai ba vul
        }
        return new String[]{"admin", "admin123", "hotel"};
    }
}
