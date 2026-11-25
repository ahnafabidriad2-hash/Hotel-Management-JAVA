import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RoomBookingGUI {
    private JFrame bookingFrame, formFrame, paymentFrame;
    private String selectedRoomType, checkInDate, checkOutDate, numGuests;
    private CustomerDashboard customerDashboard;  // Store reference of CustomerDashboard

    public static void main(String[] args) {
        new RoomBookingGUI(); // Directly create the RoomBookingGUI without invokeLater
    }

    public RoomBookingGUI() {
        customerDashboard = new CustomerDashboard(); // Initialize CustomerDashboard object
        createRoomBookingPage();
    }

    private void createRoomBookingPage() {
        bookingFrame = new JFrame("Room Booking");
        bookingFrame.setSize(400, 300);
        bookingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        bookingFrame.add(panel);

        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setBounds(50, 20, 100, 25);
        panel.add(roomTypeLabel);

        String[] roomTypes = {"Single", "Double"};
        JComboBox<String> roomTypeDropdown = new JComboBox<>(roomTypes);
        roomTypeDropdown.setBounds(150, 20, 160, 25);
        panel.add(roomTypeDropdown);

        JLabel priceLabel = new JLabel("Price: $100 per night");
        priceLabel.setBounds(50, 60, 200, 25);
        panel.add(priceLabel);

        JLabel amenitiesLabel = new JLabel("Amenities: WiFi, Breakfast, TV");
        amenitiesLabel.setBounds(50, 100, 300, 25);
        panel.add(amenitiesLabel);

        JButton bookNowButton = new JButton("Book Now");
        bookNowButton.setBounds(50, 140, 150, 30);
        panel.add(bookNowButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 200, 100, 30);
        backButton.setForeground(Color.RED);
        panel.add(backButton);

        bookNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedRoomType = (String) roomTypeDropdown.getSelectedItem();
                bookingFrame.dispose();
                createBookingForm();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookingFrame.dispose();
                new CustomerDashboard().createDashboardPage(); // Open CustomerDashboard without closing
            }
        });

        bookingFrame.setVisible(true);
    }

    private void createBookingForm() {
        formFrame = new JFrame("Booking Form");
        formFrame.setSize(400, 350);
        formFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        formFrame.add(panel);

        JLabel checkInLabel = new JLabel("Check-in Date:");
        checkInLabel.setBounds(50, 20, 100, 25);
        panel.add(checkInLabel);
        JTextField checkInField = new JTextField();
        checkInField.setBounds(150, 20, 160, 25);
        panel.add(checkInField);

        JLabel checkOutLabel = new JLabel("Check-out Date:");
        checkOutLabel.setBounds(50, 60, 100, 25);
        panel.add(checkOutLabel);
        JTextField checkOutField = new JTextField();
        checkOutField.setBounds(150, 60, 160, 25);
        panel.add(checkOutField);

        JLabel guestLabel = new JLabel("Number of Guests:");
        guestLabel.setBounds(50, 100, 120, 25);
        panel.add(guestLabel);
        JTextField guestField = new JTextField();
        guestField.setBounds(180, 100, 130, 25);
        panel.add(guestField);

        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.setBounds(50, 140, 150, 30);
        panel.add(confirmButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 200, 100, 30);
        backButton.setForeground(Color.RED);
        panel.add(backButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkInDate = checkInField.getText();
                checkOutDate = checkOutField.getText();
                numGuests = guestField.getText();
                saveBookingDetails();
                formFrame.dispose();
                createPaymentPage();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formFrame.dispose();
                bookingFrame.setVisible(true); // Open Room Booking page
            }
        });

        formFrame.setVisible(true);
    }

    private void createPaymentPage() {
        paymentFrame = new JFrame("Payment");
        paymentFrame.setSize(400, 300);
        paymentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        paymentFrame.add(panel);

        JLabel paymentLabel = new JLabel("Payment Method: Bkash");
        paymentLabel.setBounds(50, 20, 200, 25);
        panel.add(paymentLabel);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(50, 60, 100, 25);
        panel.add(phoneLabel);
        JTextField phoneField = new JTextField();
        phoneField.setBounds(150, 60, 160, 25);
        panel.add(phoneField);

        JLabel pinLabel = new JLabel("Bkash PIN:");
        pinLabel.setBounds(50, 100, 100, 25);
        panel.add(pinLabel);
        JPasswordField pinField = new JPasswordField();
        pinField.setBounds(150, 100, 160, 25);
        panel.add(pinField);

        JButton payButton = new JButton("Pay");
        payButton.setBounds(50, 140, 100, 30);
        panel.add(payButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 200, 100, 30);
        backButton.setForeground(Color.RED);
        panel.add(backButton);

        JButton homeButton = new JButton("Back to Homepage");
        homeButton.setBounds(180, 200, 180, 30);
        panel.add(homeButton);

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(paymentFrame, "Payment Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                paymentFrame.dispose();
                new CustomerDashboard().createDashboardPage(); // Open CustomerDashboard after payment
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentFrame.dispose();
                formFrame.setVisible(true); // Open Booking Form page
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentFrame.dispose();
                new CustomerDashboard().createDashboardPage(); // Open CustomerDashboard from Payment page
            }
        });

        paymentFrame.setVisible(true);
    }

    private void saveBookingDetails() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("roombooking_details.txt", true))) {
            writer.write("Room Type: " + selectedRoomType + "\n");
            writer.write("Check-in Date: " + checkInDate + "\n");
            writer.write("Check-out Date: " + checkOutDate + "\n");
            writer.write("Number of Guests: " + numGuests + "\n");
            writer.write("----------------------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
