import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BookingHistoryGUI {
    private JFrame historyFrame;

    public static void main(String[] args) {
        new BookingHistoryGUI();  // Directly create an instance of BookingHistoryGUI
    }

    public BookingHistoryGUI() {
        createBookingHistoryPage();
    }

    private void createBookingHistoryPage() {
        historyFrame = new JFrame("Booking History");
        historyFrame.setSize(400, 400);
        historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        historyFrame.add(panel);

        JLabel titleLabel = new JLabel("Booking History");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        
        // Reading and displaying booking history from the file
        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        try (BufferedReader reader = new BufferedReader(new FileReader("roombooking_details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                historyArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(historyArea);
        panel.add(scrollPane);

        JButton reviewButton = new JButton("Review");
        reviewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        reviewButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                showReviewDialog();
            }
        });
        panel.add(reviewButton);

        JButton backButton = new JButton("Back to Homepage");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setForeground(Color.RED);
        backButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                historyFrame.dispose();
                // Directly call CustomerDashboard's createDashboardPage method
                new CustomerDashboard().createDashboardPage();  // Create and open the CustomerDashboard page directly
            }
        });
        panel.add(backButton);

        historyFrame.setVisible(true);
    }

    private void showReviewDialog() {
        JDialog reviewDialog = new JDialog(historyFrame, "Review", true);
        reviewDialog.setSize(400, 300);
        reviewDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        reviewDialog.add(panel);

        JLabel ratingLabel = new JLabel("Rate the Room (1-5):");
        ratingLabel.setBounds(50, 20, 150, 25);
        panel.add(ratingLabel);

        JComboBox<String> ratingDropdown = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        ratingDropdown.setBounds(180, 20, 50, 25);
        panel.add(ratingDropdown);

        JLabel commentLabel = new JLabel("Comment:");
        commentLabel.setBounds(50, 60, 100, 25);
        panel.add(commentLabel);

        JTextArea commentArea = new JTextArea();
        commentArea.setBounds(50, 90, 300, 100);
        panel.add(commentArea);

        JButton submitButton = new JButton("Submit Review");
        submitButton.setBounds(50, 200, 150, 30);
        panel.add(submitButton);

        submitButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String rating = (String) ratingDropdown.getSelectedItem();
                String comment = commentArea.getText();
                // Process the review data (e.g., save to a file or display it)
                JOptionPane.showMessageDialog(reviewDialog, "Review Submitted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                reviewDialog.dispose();
            }
        });

        reviewDialog.setVisible(true);
    }
}
