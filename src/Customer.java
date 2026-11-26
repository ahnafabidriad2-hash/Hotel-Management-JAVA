import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {

    private JFrame frame;
    private JTextField customerInfoField;

    // admin dashboard theke kono customer chara shudhu window ta khola hoy
    public Customer() {
        this("", "", "");
    }

    public Customer(String id, String name, String mobile) {
        super(id, name, mobile);

        
        frame = new JFrame("Customer");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(100, 150, 200));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        JLabel titleLabel = new JLabel("CUSTOMER");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(titleLabel, gbc);

        
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton customerDetailsButton = createButton("Customer Details");
        JButton searchCustomerButton = createButton("Search Customer");
        JButton addCustomerButton = createButton("Add Customer");
        JButton deleteCustomerButton = createButton("Delete Customer");

        buttonPanel.add(customerDetailsButton);
        buttonPanel.add(searchCustomerButton);
        buttonPanel.add(addCustomerButton);
        buttonPanel.add(deleteCustomerButton);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(buttonPanel, gbc);

        
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        frame.add(backButton, gbc);

        
        customerDetailsButton.addActionListener(e -> showDetails());
        searchCustomerButton.addActionListener(e -> searchCustomer());
        addCustomerButton.addActionListener(e -> addPerson());
        deleteCustomerButton.addActionListener(e -> deletePerson());
        backButton.addActionListener(e -> {
            frame.dispose();
            new AdminDashboard();
        });

        
        customerInfoField = new JTextField();
        customerInfoField.setEditable(false);
        customerInfoField.setBackground(new Color(0, 0, 139));
        customerInfoField.setForeground(new Color(135, 206, 235));
        updateCustomerInfo();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(customerInfoField, gbc);

        
        frame.setLocationRelativeTo(null);
        UI.show(frame);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(0, 128, 255));
        button.setForeground(Color.WHITE);
        return button;
    }

    @Override
    public void addPerson() {
        JPanel panel = new JPanel(new GridLayout(7, 2));
        JTextField customerIdField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField roomNumberField = new JTextField();
        JTextField mobileField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField paymentField = new JTextField();
        JTextField acTypeField = new JTextField();

        panel.add(new JLabel("Customer ID:"));
        panel.add(customerIdField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Room Number:"));
        panel.add(roomNumberField);
        panel.add(new JLabel("Mobile:"));
        panel.add(mobileField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Payment:"));
        panel.add(paymentField);
        panel.add(new JLabel("AC/Non-AC:"));
        panel.add(acTypeField);

        int option = JOptionPane.showConfirmDialog(frame, panel, "Add Customer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String customerId = customerIdField.getText();
            String name = nameField.getText();
            String roomNumber = roomNumberField.getText();
            String mobile = mobileField.getText();
            String email = emailField.getText();
            String payment = paymentField.getText();
            String acType = acTypeField.getText();

            if (Validate.existsInFile("customers.txt", "Customer ID", customerId)) {
                JOptionPane.showMessageDialog(frame, "Customer ID " + customerId + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // payment number na hole pore total hishab e crash korto, tai ekhanei atkai
            if (!payment.isEmpty() && !Validate.isAmount(payment)) {
                JOptionPane.showMessageDialog(frame, "Payment must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (customerId.isEmpty() || name.isEmpty() || roomNumber.isEmpty() || mobile.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt", true))) {
                writer.write("Customer ID: " + customerId + ", Name: " + name + ", Room Number: " + roomNumber + ", Mobile: " + mobile + ", Email: " + email + ", Payment: " + payment + ", AC Type: " + acType + "\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error saving customer details.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(frame, "Customer added successfully!");
            updateCustomerInfo();
        }
    }

    @Override
    public void deletePerson() {
        String customerId = JOptionPane.showInputDialog(frame, "Enter Customer ID to Delete:");
        if (customerId == null || customerId.trim().isEmpty()) return;
        customerId = customerId.trim();
        if (!Validate.existsInFile("customers.txt", "Customer ID", customerId)) {
            JOptionPane.showMessageDialog(frame, "Customer not found.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validate.confirm(frame, "customer " + customerId)) return;

        List<String> updatedCustomers = new ArrayList<>();
        boolean customerDeleted = false;

        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!Validate.fieldIs(line, "Customer ID", customerId)) { // thik oi ID, shuru mile na
                    updatedCustomers.add(line);
                } else {
                    customerDeleted = true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading customer details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (customerDeleted) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt"))) {
                for (String customer : updatedCustomers) {
                    writer.write(customer + "\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error updating customer list.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(frame, "Customer deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Customer not found.", "Error", JOptionPane.WARNING_MESSAGE);
        }

        updateCustomerInfo();
    }

    @Override
    public void showDetails() {
        StringBuilder customerDetails = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                customerDetails.append(line).append("\n");
            }
        } catch (IOException e) {
            customerDetails.append("Error reading customer details.");
        }
        JOptionPane.showMessageDialog(frame, customerDetails.toString(), "Customer Details", JOptionPane.INFORMATION_MESSAGE);
    }

    // id ba naam diye khuji, choto boro hater okkhor mane na
    private void searchCustomer() {
        String query = JOptionPane.showInputDialog(frame, "Enter Customer ID or Name:");
        if (query == null || query.trim().isEmpty()) return;
        query = query.trim().toLowerCase();

        StringBuilder found = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains(query)) {
                    found.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading customer details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (found.length() == 0) {
            JOptionPane.showMessageDialog(frame, "No customer found.", "Search Customer", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, found.toString(), "Search Customer", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateCustomerInfo() {
        int totalCustomers = 0;
        double totalMoney = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                totalCustomers++;
                String[] details = line.split(",");
                for (String detail : details) {
                    if (detail.trim().startsWith("Payment:")) {
                        String paymentStr = detail.split(":")[1].trim();
                        totalMoney += Validate.parseAmount(paymentStr);
                    }
                }
            }
        } catch (IOException e) {
            customerInfoField.setText("Error loading customer info.");
            return;
        }

        customerInfoField.setText("Total Customers: " + totalCustomers + " | Total Money: BDT " + totalMoney);
    }

    public static void main(String[] args) {
        new Customer();
    }
}


