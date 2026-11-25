import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Revenue {

    public static void main(String[] args) {
        new Revenue();
    }

    public Revenue() {
        JFrame frame = new JFrame("Revenue");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(155, 77, 202)); 
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        JLabel titleLabel = new JLabel("REVENUE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(titleLabel, gbc);

        
        JLabel totalIncomeLabel = new JLabel("TOTAL Income:");
        totalIncomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField totalIncomeField = new JTextField(20);
        totalIncomeField.setEditable(false);
        totalIncomeField.setBackground(new Color(19, 27, 104)); 
        totalIncomeField.setForeground(Color.WHITE);
        double income = getTotalIncome();
        totalIncomeField.setText("BDT " + income);

        
        JLabel totalSalaryLabel = new JLabel("TOTAL SALARY:");
        totalSalaryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField totalSalaryField = new JTextField(20);
        totalSalaryField.setEditable(false);
        totalSalaryField.setBackground(new Color(19, 27, 104)); 
        totalSalaryField.setForeground(Color.WHITE);
        totalSalaryField.setText("BDT " + getTotalSalary());

       
        JLabel revenueLabel = new JLabel("REVENUE:");
        revenueLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField revenueField = new JTextField(20);
        revenueField.setEditable(false);
        revenueField.setBackground(new Color(19, 27, 104)); 
        revenueField.setForeground(Color.WHITE);

        double revenue = income - getTotalSalary();
        if (revenue > 0) {
            revenueField.setText("BDT " + revenue + " - PROFIT TIME");
        } else if (revenue < 0) {
            revenueField.setText("BDT " + revenue + " - LOSS TIME");
        } else {
            revenueField.setText("BDT " + revenue + " - BALANCED");
        }

       
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(totalIncomeLabel, gbc);
        gbc.gridx = 1;
        frame.add(totalIncomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(totalSalaryLabel, gbc);
        gbc.gridx = 1;
        frame.add(totalSalaryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(revenueLabel, gbc);
        gbc.gridx = 1;
        frame.add(revenueField, gbc);

        
        JButton backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        frame.add(backButton, gbc);

        
        backButton.addActionListener(e -> {
            frame.dispose(); 
            new AdminDashboard(); 
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
    private double getTotalIncome() {
        double totalIncome = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                for (String part : parts) {
                    if (part.trim().startsWith("Payment:")) {
                        String paymentStr = part.split(":")[1].trim();
                        totalIncome += Validate.parseAmount(paymentStr);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // customer der online room booking er taka o income e jog hoy
        try (BufferedReader br = new BufferedReader(new FileReader(RoomBookingGUI.BOOKING_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                int i = line.lastIndexOf("Total:");
                if (i >= 0) totalIncome += Validate.parseAmount(line.substring(i + 6));
            }
        } catch (IOException e) {
            // ekhono kono online booking nai
        }
        return totalIncome;
    }

    
    private double getTotalSalary() {
        double totalSalary = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                for (String part : parts) {
                    if (part.trim().startsWith("Salary:")) {
                        String salaryStr = part.split(":")[1].trim();
                        totalSalary += Validate.parseAmount(salaryStr);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalSalary;
    }
}
