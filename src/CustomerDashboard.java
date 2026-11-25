import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

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

        // kaj na kora search box ar "coming soon" button er jaygay ke login kore ase seta dekhai
        Map<String, String> user = Session.userId == null ? null : UserStore.find(Session.userId);
        String name = user == null ? "Guest" : user.get("Name");

        JLabel welcomeLabel = new JLabel("Welcome, " + name + "!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 22));
        welcomeLabel.setBounds(50, 30, 450, 35);
        panel.add(welcomeLabel);

        JLabel infoLabel = new JLabel("What would you like to do today?");
        infoLabel.setBounds(50, 70, 300, 25);
        panel.add(infoLabel);

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
                // age logout korle abar dashboard e chole ashto, ekhon login page e jay
                Session.logout();
                dispose();
                Login.main(null);
            }
        });

        setVisible(true);  // Make the dashboard visible
    }
}
