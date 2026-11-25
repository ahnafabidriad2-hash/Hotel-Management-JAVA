import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDashboard extends JFrame {
    public static void main(String[] args) {
        CustomerDashboard dashboard = new CustomerDashboard();
        dashboard.createDashboardPage();
    }

    public CustomerDashboard() {
        setTitle("Customer Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(null);  // Layout set for custom positioning
        setLocationRelativeTo(null); // Centering the frame
    }

    public void createDashboardPage() {
        JPanel panel = new JPanel();
        panel.setLayout(null);  // Set panel layout as null
        panel.setBounds(0, 0, 600, 400);  // Full size of the JFrame
        add(panel);  // Add panel to the frame

        JLabel profilePicLabel = new JLabel("Profile Picture (Upload):");
        profilePicLabel.setBounds(50, 20, 150, 25);
        panel.add(profilePicLabel);

        JButton uploadButton = new JButton("Upload Picture");
        uploadButton.setBounds(200, 20, 150, 25);
        panel.add(uploadButton);

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(CustomerDashboard.this, "Feature coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JTextField searchBox = new JTextField("Search...");
        searchBox.setBounds(50, 60, 300, 30);
        panel.add(searchBox);

        JButton profileButton = new JButton("Profile");
        profileButton.setBounds(50, 120, 200, 40);
        panel.add(profileButton);

        JButton roomBookingButton = new JButton("Room Booking");
        roomBookingButton.setBounds(50, 180, 200, 40);
        panel.add(roomBookingButton);

        JButton bookingHistoryButton = new JButton("Booking History");
        bookingHistoryButton.setBounds(50, 240, 200, 40);
        panel.add(bookingHistoryButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(50, 300, 200, 40);
        panel.add(logoutButton);

        // Action to open Profile GUI
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Dispose current frame
                new CustomerModuleGUI(); // Launch the profile screen
            }
        });

        // Action to open Room Booking GUI
        roomBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Dispose current frame
                new RoomBookingGUI();  // Open the RoomBookingGUI
            }
        });

        // Action to open Booking History GUI
        bookingHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Dispose current frame
                new BookingHistoryGUI();  // Open the BookingHistoryGUI
            }
        });

        // Action to handle Logout
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(CustomerDashboard.this, "Logging out...", "Logout", JOptionPane.INFORMATION_MESSAGE);
                dispose();  // Close the current frame
                // Create a new instance of CustomerDashboard when logging out
                new CustomerDashboard().createDashboardPage();  // Re-launch the dashboard
            }
        });

        setVisible(true);  // Make the dashboard visible
    }
}
