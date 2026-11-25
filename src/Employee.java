import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Employee extends Person {

    private JFrame frame;
    private JTextField nameField, idField, positionField, contactField, salaryField, ageField;
    private JLabel employeeCountField, totalSalaryField;

    // admin dashboard theke kono employee chara shudhu window ta khola hoy
    public Employee() {
        this("", "", "");
    }

    public Employee(String id, String name, String mobile) {
        super(id, name, mobile); // Calling the constructor of the Person class

        // Set up the frame and layout
        frame = new JFrame("Employee Management");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(150, 180, 250));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("EMPLOYEE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(titleLabel, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton employeeDetailsButton = createButton("Employee Details");
        JButton searchEmployeeButton = createButton("Search Employee");
        JButton addEmployeeButton = createButton("Add Employee");
        JButton deleteEmployeeButton = createButton("Delete Employee");

        buttonPanel.add(employeeDetailsButton);
        buttonPanel.add(searchEmployeeButton);
        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(deleteEmployeeButton);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(buttonPanel, gbc);

        // Display employee count
        employeeCountField = new JLabel("Total Employees: " + getEmployeeCount());
        employeeCountField.setOpaque(true);
        employeeCountField.setBackground(new Color(0, 0, 139));
        employeeCountField.setForeground(new Color(135, 206, 250));
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(employeeCountField, gbc);

        // Total Salary Field
        totalSalaryField = new JLabel("Total Salary: " + getTotalSalary());
        totalSalaryField.setOpaque(true);
        totalSalaryField.setBackground(new Color(0, 0, 139));
        totalSalaryField.setForeground(new Color(135, 206, 250));
        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(totalSalaryField, gbc);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        frame.add(backButton, gbc);

        // Add action listeners for the buttons
        employeeDetailsButton.addActionListener(e -> showDetails());
        searchEmployeeButton.addActionListener(e -> searchEmployee());
        addEmployeeButton.addActionListener(e -> addPerson());  // Action for adding employee
        deleteEmployeeButton.addActionListener(e -> deletePerson());  // Action for deleting employee
        backButton.addActionListener(e -> {
            frame.setVisible(false);
            new AdminDashboard();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        return button;
    }

    @Override
    public void addPerson() {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        nameField = new JTextField();
        idField = new JTextField();
        positionField = new JTextField();
        contactField = new JTextField();
        salaryField = new JTextField();
        ageField = new JTextField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Employee ID:"));
        panel.add(idField);
        panel.add(new JLabel("Position:"));
        panel.add(positionField);
        panel.add(new JLabel("Contact:"));
        panel.add(contactField);
        panel.add(new JLabel("Salary:"));
        panel.add(salaryField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);

        int option = JOptionPane.showConfirmDialog(frame, panel, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // naam faka ba salary number na hole save kori na
            if (nameField.getText().trim().isEmpty() || idField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name and Employee ID are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Validate.isAmount(salaryField.getText())) {
                JOptionPane.showMessageDialog(frame, "Salary must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees.txt", true))) {
                writer.write("Name: " + nameField.getText() + ", Employee ID: " + idField.getText() + ", Position: " + positionField.getText() + ", Contact: " + contactField.getText() + ", Salary: " + salaryField.getText() + ", Age: " + ageField.getText() + "\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error saving employee details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(frame, "Employee added successfully!");
            employeeCountField.setText("Total Employees: " + getEmployeeCount());
            totalSalaryField.setText("Total Salary: " + getTotalSalary());
        }
    }

    @Override
    public void deletePerson() {
        String employeeId = JOptionPane.showInputDialog(frame, "Enter Employee ID to Delete:");
        if (employeeId == null || employeeId.trim().isEmpty()) return;

        List<String> updatedEmployees = new ArrayList<>();
        boolean employeeDeleted = false;

        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.contains("Employee ID: " + employeeId)) {  // Delete by Employee ID
                    updatedEmployees.add(line);
                } else {
                    employeeDeleted = true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading employee details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (employeeDeleted) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees.txt"))) {
                for (String employee : updatedEmployees) {
                    writer.write(employee + "\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error updating employee list.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(frame, "Employee deleted successfully!");
            employeeCountField.setText("Total Employees: " + getEmployeeCount());
            totalSalaryField.setText("Total Salary: " + getTotalSalary());
        } else {
            JOptionPane.showMessageDialog(frame, "Employee not found.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void showDetails() {
        StringBuilder employeeDetails = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                employeeDetails.append(line).append("\n");
            }
        } catch (IOException e) {
            employeeDetails.append("Error reading employee details.");
        }
        JOptionPane.showMessageDialog(frame, employeeDetails.toString(), "Employee Details", JOptionPane.INFORMATION_MESSAGE);
    }

    // id ba naam diye khuji, choto boro hater okkhor mane na
    private void searchEmployee() {
        String query = JOptionPane.showInputDialog(frame, "Enter Employee ID or Name:");
        if (query == null || query.trim().isEmpty()) return;
        query = query.trim().toLowerCase();

        StringBuilder found = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains(query)) {
                    found.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading employee details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (found.length() == 0) {
            JOptionPane.showMessageDialog(frame, "No employee found.", "Search Employee", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, found.toString(), "Search Employee", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private int getEmployeeCount() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("employees.txt"))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            return 0;
        }
        return count;
    }

    // Calculate total salary of all employees
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
            return 0;
        }
        return totalSalary;
    }

    public static void main(String[] args) {
        new Employee();
    }
}
