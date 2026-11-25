import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Login{

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Hotel Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(135, 206, 250)); 
        
        JLabel titleLabel = new JLabel("Hotel Management System");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        titleLabel.setBounds(180, 10, 250, 30);
        panel.add(titleLabel);

        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setBounds(250, 40, 100, 30);
        panel.add(welcomeLabel);

        JLabel userLoginLabel = new JLabel("Login as user");
        userLoginLabel.setBounds(50, 80, 100, 30);
        panel.add(userLoginLabel);

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setBounds(50, 120, 100, 30);
        panel.add(userIdLabel);

        JTextField userIdText = new JTextField();
        userIdText.setBounds(120, 120, 150, 30);
        panel.add(userIdText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 160, 100, 30);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(120, 160, 150, 30);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(120, 200, 80, 30);
        panel.add(loginButton);

        JButton forgetPasswordButton = new JButton("Forget password?");
        forgetPasswordButton.setBounds(210, 200, 150, 30);
        panel.add(forgetPasswordButton);

        forgetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 frame.dispose();
                 new ForgetPassword(); 
            }
        });


        JLabel accountLabel = new JLabel("Do you have an account?");
        accountLabel.setBounds(50, 240, 150, 30);
        panel.add(accountLabel);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(210, 240, 100, 30);
        panel.add(registerButton);

        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        new Registration();             }
        });

        JButton adminLoginButton = new JButton("Login (Admin)");
        adminLoginButton.setBounds(380, 100, 150, 30);
        panel.add(adminLoginButton);

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 frame.dispose();
                     new AdminLogin(); 
            }
        });

          
        JButton managerLoginButton = new JButton("Login (Manager)");
        managerLoginButton.setBounds(380, 140, 150, 30);
        panel.add(managerLoginButton);

 
        managerLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 frame.dispose();
                 new ManagerLogin(); 
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdText.getText().trim();
                String password = new String(passwordText.getPassword());

                if (userId.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter User ID and Password", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // line gune gune na pore, UserStore theke user er record niye password mila
                Map<String, String> user = UserStore.find(userId);
                if (user != null && password.equals(user.get("Password"))) {
                    Session.userId = userId;
                    frame.dispose();
                    new CustomerDashboard().createDashboardPage();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid User ID or Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // password e enter chaple o login hobe
        passwordText.addActionListener(e -> loginButton.doClick());
  
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
