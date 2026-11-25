import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class BookingHistoryGUI {
    // customer er review eikhane save hoy, manager er review page eikhan thekei pore
    public static final String REVIEW_FILE = "reviews.txt";

    private JFrame historyFrame;

    public static void main(String[] args) {
        new BookingHistoryGUI();
    }

    public BookingHistoryGUI() {
        createBookingHistoryPage();
    }

    private void createBookingHistoryPage() {
        historyFrame = new JFrame("Booking History");
        historyFrame.setSize(560, 420);
        historyFrame.setLocationRelativeTo(null);
        historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        historyFrame.add(panel);

        JLabel titleLabel = new JLabel("My Bookings");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        // age sob user er booking ekshathe dekhato, ekhon shudhu je login kore ase tar ta
        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        String prefix = "User Id: " + Session.userId + ",";
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(RoomBookingGUI.BOOKING_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(prefix)) {
                    count++;
                    historyArea.append(count + ". " + line.substring(prefix.length()).trim().replace(", ", "\n   ") + "\n\n");
                }
            }
        } catch (IOException e) {
            // file na thakle mane ekhono kono booking nai
        }
        if (count == 0) {
            historyArea.setText("You have no bookings yet.");
        }

        JScrollPane scrollPane = new JScrollPane(historyArea);
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(10));

        JButton reviewButton = new JButton("Write a Review");
        reviewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        reviewButton.setEnabled(count > 0); // booking na thakle review deya jabe na
        reviewButton.addActionListener(e -> showReviewDialog());
        panel.add(reviewButton);
        panel.add(Box.createVerticalStrut(5));

        JButton backButton = new JButton("Back to Homepage");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setForeground(Color.RED);
        backButton.addActionListener(e -> {
            historyFrame.dispose();
            new CustomerDashboard().createDashboardPage();
        });
        panel.add(backButton);

        historyFrame.setVisible(true);
    }

    private void showReviewDialog() {
        JDialog reviewDialog = new JDialog(historyFrame, "Review", true);
        reviewDialog.setSize(400, 300);
        reviewDialog.setLocationRelativeTo(historyFrame);
        reviewDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        reviewDialog.add(panel);

        JLabel ratingLabel = new JLabel("Rate the Room (1-5):");
        ratingLabel.setBounds(50, 20, 150, 25);
        panel.add(ratingLabel);

        JComboBox<String> ratingDropdown = new JComboBox<>(new String[]{"5", "4", "3", "2", "1"});
        ratingDropdown.setBounds(200, 20, 50, 25);
        panel.add(ratingDropdown);

        JLabel commentLabel = new JLabel("Comment:");
        commentLabel.setBounds(50, 60, 100, 25);
        panel.add(commentLabel);

        JTextArea commentArea = new JTextArea();
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        JScrollPane commentScroll = new JScrollPane(commentArea);
        commentScroll.setBounds(50, 90, 300, 100);
        panel.add(commentScroll);

        JButton submitButton = new JButton("Submit Review");
        submitButton.setBounds(50, 205, 150, 30);
        panel.add(submitButton);

        submitButton.addActionListener(e -> {
            String rating = (String) ratingDropdown.getSelectedItem();
            // ek line e save kori tai comment er vitorer new line gula space hoye jay
            String comment = commentArea.getText().trim().replaceAll("\\s+", " ");
            if (comment.isEmpty()) {
                JOptionPane.showMessageDialog(reviewDialog, "Please write a short comment.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // age review submit korle kothao save hoto na, ekhon reviews.txt e jay
            Map<String, String> user = UserStore.find(Session.userId);
            String name = user == null ? "Guest" : user.get("Name");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(REVIEW_FILE, true))) {
                writer.write(name + " (" + rating + "/5): " + comment + "\n");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(reviewDialog, "Could not save your review.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(reviewDialog, "Thanks! Your review was submitted.", "Success", JOptionPane.INFORMATION_MESSAGE);
            reviewDialog.dispose();
        });

        reviewDialog.setVisible(true);
    }
}
