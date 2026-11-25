import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class RoomBookingGUI {
    // protiti booking ek line e, kon user korse seta o thake jate history te shudhu nijer ta dekhay
    public static final String BOOKING_FILE = "roombooking_details.txt";

    private static final String[] ROOM_TYPES = {"Single", "Double"};
    private static final int[] PRICE_PER_NIGHT = {3000, 5000}; // BDT
    private static final int[] MAX_GUESTS = {2, 4};

    private JFrame bookingFrame, formFrame, paymentFrame;
    private int selectedRoom;
    private LocalDate checkInDate, checkOutDate;
    private int numGuests;

    public static void main(String[] args) {
        new RoomBookingGUI();
    }

    public RoomBookingGUI() {
        createRoomBookingPage();
    }

    private void createRoomBookingPage() {
        bookingFrame = new JFrame("Room Booking");
        bookingFrame.setSize(420, 300);
        bookingFrame.setLocationRelativeTo(null);
        bookingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        bookingFrame.add(panel);

        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setBounds(50, 20, 100, 25);
        panel.add(roomTypeLabel);

        JComboBox<String> roomTypeDropdown = new JComboBox<>(ROOM_TYPES);
        roomTypeDropdown.setBounds(150, 20, 160, 25);
        panel.add(roomTypeDropdown);

        // age sob room er dam "$100" lekha chilo, ekhon room type onujayi dam dekhay
        JLabel priceLabel = new JLabel();
        priceLabel.setBounds(50, 60, 300, 25);
        panel.add(priceLabel);

        JLabel guestsLabel = new JLabel();
        guestsLabel.setBounds(50, 85, 300, 25);
        panel.add(guestsLabel);

        Runnable showPrice = () -> {
            int i = roomTypeDropdown.getSelectedIndex();
            priceLabel.setText("Price: BDT " + PRICE_PER_NIGHT[i] + " per night");
            guestsLabel.setText("Up to " + MAX_GUESTS[i] + " guests");
        };
        showPrice.run();
        roomTypeDropdown.addActionListener(e -> showPrice.run());

        JLabel amenitiesLabel = new JLabel("Amenities: WiFi, Breakfast, TV");
        amenitiesLabel.setBounds(50, 115, 300, 25);
        panel.add(amenitiesLabel);

        JButton bookNowButton = new JButton("Book Now");
        bookNowButton.setBounds(50, 155, 150, 30);
        panel.add(bookNowButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 205, 100, 30);
        backButton.setForeground(Color.RED);
        panel.add(backButton);

        bookNowButton.addActionListener(e -> {
            selectedRoom = roomTypeDropdown.getSelectedIndex();
            bookingFrame.setVisible(false);
            createBookingForm();
        });

        backButton.addActionListener(e -> {
            bookingFrame.dispose();
            new CustomerDashboard().createDashboardPage();
        });

        bookingFrame.setVisible(true);
    }

    private void createBookingForm() {
        formFrame = new JFrame("Booking Form - " + ROOM_TYPES[selectedRoom] + " Room");
        formFrame.setSize(420, 320);
        formFrame.setLocationRelativeTo(null);
        formFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        formFrame.add(panel);

        JLabel hintLabel = new JLabel("Dates format: yyyy-mm-dd (e.g. " + LocalDate.now().plusDays(1) + ")");
        hintLabel.setBounds(50, 10, 330, 25);
        panel.add(hintLabel);

        JLabel checkInLabel = new JLabel("Check-in Date:");
        checkInLabel.setBounds(50, 45, 120, 25);
        panel.add(checkInLabel);
        JTextField checkInField = new JTextField();
        checkInField.setBounds(180, 45, 150, 25);
        panel.add(checkInField);

        JLabel checkOutLabel = new JLabel("Check-out Date:");
        checkOutLabel.setBounds(50, 85, 120, 25);
        panel.add(checkOutLabel);
        JTextField checkOutField = new JTextField();
        checkOutField.setBounds(180, 85, 150, 25);
        panel.add(checkOutField);

        JLabel guestLabel = new JLabel("Number of Guests:");
        guestLabel.setBounds(50, 125, 120, 25);
        panel.add(guestLabel);
        JTextField guestField = new JTextField();
        guestField.setBounds(180, 125, 150, 25);
        panel.add(guestField);

        JButton confirmButton = new JButton("Continue to Payment");
        confirmButton.setBounds(50, 170, 180, 30);
        panel.add(confirmButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 220, 100, 30);
        backButton.setForeground(Color.RED);
        panel.add(backButton);

        confirmButton.addActionListener(e -> {
            // date ar guest thik ase kina check, na hole payment e jete dei na
            LocalDate in, out;
            try {
                in = LocalDate.parse(checkInField.getText().trim());
                out = LocalDate.parse(checkOutField.getText().trim());
            } catch (DateTimeParseException ex) {
                showError(formFrame, "Please write dates like " + LocalDate.now().plusDays(1));
                return;
            }
            if (in.isBefore(LocalDate.now())) {
                showError(formFrame, "Check-in date cannot be in the past.");
                return;
            }
            if (!out.isAfter(in)) {
                showError(formFrame, "Check-out must be after check-in.");
                return;
            }
            if (!Validate.isWholeNumber(guestField.getText(), 1, MAX_GUESTS[selectedRoom])) {
                showError(formFrame, "Guests must be between 1 and " + MAX_GUESTS[selectedRoom] + " for a "
                        + ROOM_TYPES[selectedRoom] + " room.");
                return;
            }
            checkInDate = in;
            checkOutDate = out;
            numGuests = Integer.parseInt(guestField.getText().trim());
            formFrame.setVisible(false);
            createPaymentPage();
        });

        backButton.addActionListener(e -> {
            formFrame.dispose();
            bookingFrame.setVisible(true);
        });

        formFrame.setVisible(true);
    }

    private void createPaymentPage() {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        long total = nights * PRICE_PER_NIGHT[selectedRoom];

        paymentFrame = new JFrame("Payment");
        paymentFrame.setSize(420, 360);
        paymentFrame.setLocationRelativeTo(null);
        paymentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        paymentFrame.add(panel);

        // pay korar age ki er jonno koto taka dicche seta dekhai
        JLabel summaryLabel = new JLabel("<html>" + ROOM_TYPES[selectedRoom] + " room, " + numGuests + " guest(s)<br>"
                + checkInDate + " to " + checkOutDate + " (" + nights + " night" + (nights > 1 ? "s" : "") + ")<br>"
                + "<b>Total: BDT " + total + "</b></html>");
        summaryLabel.setBounds(50, 10, 330, 60);
        panel.add(summaryLabel);

        JLabel paymentLabel = new JLabel("Payment Method: Bkash");
        paymentLabel.setBounds(50, 80, 200, 25);
        panel.add(paymentLabel);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(50, 115, 100, 25);
        panel.add(phoneLabel);
        JTextField phoneField = new JTextField();
        phoneField.setBounds(160, 115, 160, 25);
        panel.add(phoneField);

        JLabel pinLabel = new JLabel("Bkash PIN:");
        pinLabel.setBounds(50, 155, 100, 25);
        panel.add(pinLabel);
        JPasswordField pinField = new JPasswordField();
        pinField.setBounds(160, 155, 160, 25);
        panel.add(pinField);

        JButton payButton = new JButton("Pay BDT " + total);
        payButton.setBounds(50, 195, 150, 30);
        panel.add(payButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 250, 100, 30);
        backButton.setForeground(Color.RED);
        panel.add(backButton);

        JButton homeButton = new JButton("Cancel Booking");
        homeButton.setBounds(180, 250, 180, 30);
        panel.add(homeButton);

        payButton.addActionListener(e -> {
            if (!Validate.isPhone(phoneField.getText().trim())) {
                showError(paymentFrame, "Bkash number must be 11 digits and start with 01.");
                return;
            }
            if (!new String(pinField.getPassword()).matches("\\d{5}")) {
                showError(paymentFrame, "Bkash PIN must be 5 digits.");
                return;
            }
            // age payment er agei booking save hoye jeto, ekhon pay korar porei save hoy
            if (!saveBookingDetails(nights, total)) {
                showError(paymentFrame, "Could not save your booking. Please try again.");
                return;
            }
            JOptionPane.showMessageDialog(paymentFrame, "Payment Successful! Your " + ROOM_TYPES[selectedRoom]
                    + " room is booked.", "Success", JOptionPane.INFORMATION_MESSAGE);
            closeAll();
            new CustomerDashboard().createDashboardPage();
        });

        backButton.addActionListener(e -> {
            paymentFrame.dispose();
            formFrame.setVisible(true);
        });

        homeButton.addActionListener(e -> {
            closeAll();
            new CustomerDashboard().createDashboardPage();
        });

        paymentFrame.setVisible(true);
    }

    private boolean saveBookingDetails(long nights, long total) {
        String userId = Session.userId == null ? "guest" : Session.userId;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKING_FILE, true))) {
            writer.write("User Id: " + userId + ", Room Type: " + ROOM_TYPES[selectedRoom]
                    + ", Check-In: " + checkInDate + ", Check-Out: " + checkOutDate
                    + ", Nights: " + nights + ", Guests: " + numGuests + ", Total: " + total + "\n");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void closeAll() {
        if (paymentFrame != null) paymentFrame.dispose();
        if (formFrame != null) formFrame.dispose();
        if (bookingFrame != null) bookingFrame.dispose();
    }

    private void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
