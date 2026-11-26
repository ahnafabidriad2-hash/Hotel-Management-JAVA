import javax.swing.*;
import java.awt.*;

public class AdminDashboard {
    private JFrame frame;

    public AdminDashboard() {
        
        frame = new JFrame("Admin Dashboard");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(170, 120, 215)); 
        frame.setLayout(null); 

        
        JLabel headerLabel = new JLabel("ADMIN DASHBOARD", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(Color.YELLOW);
        headerLabel.setBounds(150, 30, 300, 40); 
        frame.add(headerLabel);

        
        JButton roomButton = createStyledButton("ROOM");
        roomButton.setBounds(200, 100, 200, 40); 
        frame.add(roomButton);

        JButton customerButton = createStyledButton("CUSTOMER");
        customerButton.setBounds(200, 160, 200, 40); 
        frame.add(customerButton);

        JButton employeeButton = createStyledButton("EMPLOYEE");
        employeeButton.setBounds(200, 220, 200, 40); 
        frame.add(employeeButton);

        JButton revenueButton = createStyledButton("REVENUE");
        revenueButton.setBounds(200, 280, 200, 40); 
        frame.add(revenueButton);

        
        JButton backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setBackground(new Color(255, 50, 50));
        backButton.setForeground(Color.BLACK);
        backButton.setBounds(450, 350, 100, 40);
        frame.add(backButton);

        
        roomButton.addActionListener(e -> openRoom());
        customerButton.addActionListener(e -> openCustomer());
        employeeButton.addActionListener(e -> openEmployee());
        revenueButton.addActionListener(e -> openRevenue());
        backButton.addActionListener(e -> {
            frame.dispose(); 
            JOptionPane.showMessageDialog(null, "Returning to Login Screen");
            new AdminLogin(); 
        });

        
        frame.setLocationRelativeTo(null);
        UI.show(frame);
    }

    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(0, 0, 139)); 
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    
    private void openRoom() {
        frame.dispose(); 
        new Room(); 
    }

    
    private void openCustomer() {
        frame.dispose(); 
        new Customer();
    }

    
    private void openEmployee() {
        frame.dispose(); 
        new Employee();
    }

    
    private void openRevenue() {
        frame.dispose();
        new Revenue(); 
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
