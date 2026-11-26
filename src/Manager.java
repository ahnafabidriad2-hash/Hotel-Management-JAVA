import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Manager {
    public static void main(String[] args) {
       
        JFrame frame = new JFrame("Hotel Manager Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230)); 
        panel.setBounds(0, 0, 900, 500);
        panel.setLayout(null);
        frame.add(panel);

        JLabel titleLabel = new JLabel("Hotel Manager");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setBounds(370, 30, 200, 30);
        panel.add(titleLabel);

        JButton employeeButton = new JButton("Employee Management");
        employeeButton.setBounds(200, 100, 200, 60);
        employeeButton.setBackground(Color.LIGHT_GRAY);
        panel.add(employeeButton);

        JButton customerButton = new JButton("Customer Management");
        customerButton.setBounds(500, 100, 200, 60);
        customerButton.setBackground(Color.LIGHT_GRAY);
        panel.add(customerButton);

        JButton roomButton = new JButton("Room Management");
        roomButton.setBounds(200, 200, 200, 60);
        roomButton.setBackground(Color.LIGHT_GRAY);
        panel.add(roomButton);

        JButton reviewButton = new JButton("Customer Review");
        reviewButton.setBounds(500, 200, 200, 60);
        reviewButton.setBackground(Color.LIGHT_GRAY);
        panel.add(reviewButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(750, 400, 100, 40);
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        panel.add(backButton);

     
        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEmployeeFrame();
            }
        });

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCustomerFrame();
            }
        });

        roomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRoomFrame();
            }
        });

        reviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openReviewFrame();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Login.main(null); // manager dashboard theke back = logout, login page e jay
            }
        });

        
        frame.setLocationRelativeTo(null);
        UI.show(frame);
    }

    private static void openEmployeeFrame() {
        
        JFrame frame = new JFrame("Employee Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230));
        panel.setBounds(0, 0, 900, 500);
        panel.setLayout(null);
        frame.add(panel);

        JLabel titleLabel = new JLabel("Employee");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(370, 30, 200, 30);
        panel.add(titleLabel);

        JButton viewEmployeeButton = new JButton("View Employee");
        viewEmployeeButton.setBounds(150, 120, 200, 60);
        viewEmployeeButton.setBackground(Color.LIGHT_GRAY);
        panel.add(viewEmployeeButton);

        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.setBounds(550, 120, 200, 60);
        addEmployeeButton.setBackground(Color.LIGHT_GRAY);
        panel.add(addEmployeeButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(750, 400, 100, 40);
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        panel.add(backButton);

        
        viewEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openViewEmployeeFrame();
            }
        });

        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddEmployeeFrame();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
            }
        });

        frame.setLocationRelativeTo(null);
        UI.show(frame);
    }

    private static void openViewEmployeeFrame() {
        JFrame frame = new JFrame("View Employees");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(new BorderLayout());
    
        
        // age manager er alada Employee.txt chilo, admin er employees.txt er shathe mil chilo na.
        // ekhon duijon eki list dekhe
        JTextArea textArea = new JTextArea(readFile("employees.txt", "No employees yet."));
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.add(backBar(frame), BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);  
        UI.show(frame);
    }
    

    private static void openAddEmployeeFrame() {
        
        JFrame addEmployeeFrame = new JFrame("Add New Employee");
        addEmployeeFrame.setSize(400, 300);
        addEmployeeFrame.setLayout(null);
        addEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        
        JLabel nameLabel = new JLabel("Employee Name:");
        nameLabel.setBounds(30, 30, 120, 30);
        addEmployeeFrame.add(nameLabel);
    
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 30, 200, 30);
        addEmployeeFrame.add(nameField);
    
        JLabel positionLabel = new JLabel("Position:");
        positionLabel.setBounds(30, 70, 120, 30);
        addEmployeeFrame.add(positionLabel);
    
        JTextField positionField = new JTextField();
        positionField.setBounds(150, 70, 200, 30);
        addEmployeeFrame.add(positionField);
    
        JLabel salaryLabel = new JLabel("Salary:");
        salaryLabel.setBounds(30, 110, 120, 30);
        addEmployeeFrame.add(salaryLabel);
    
        JTextField salaryField = new JTextField();
        salaryField.setBounds(150, 110, 200, 30);
        addEmployeeFrame.add(salaryField);
    
        
        JButton saveButton = new JButton("Save Employee");
        saveButton.setBounds(150, 170, 150, 40);
        addEmployeeFrame.add(saveButton);
    
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String position = positionField.getText();
                String salary = salaryField.getText();
    
                
                if (name.isEmpty() || position.isEmpty() || salary.isEmpty()) {
                    JOptionPane.showMessageDialog(addEmployeeFrame, "All fields must be filled!");
                    return;
                }
                if (!Validate.isAmount(salary)) {
                    JOptionPane.showMessageDialog(addEmployeeFrame, "Salary must be a number!");
                    return;
                }
    
                
                // admin er employee list er format e lekha, ID nijei porer number ta ney
                int newId = nextEmployeeId();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees.txt", true))) {
                    writer.write("Name: " + name.trim() + ", Employee ID: " + newId + ", Position: " + position.trim()
                            + ", Contact: -, Salary: " + salary.trim() + ", Age: -");
                    writer.newLine();
                    JOptionPane.showMessageDialog(addEmployeeFrame, "Employee added successfully! (ID " + newId + ")");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(addEmployeeFrame, "Error saving employee data!");
                    ioException.printStackTrace();
                }
    
                
                nameField.setText("");
                positionField.setText("");
                salaryField.setText("");
            }
        });
    
        JButton addBackButton = new JButton("Back");
        addBackButton.setBounds(150, 220, 150, 35);
        addBackButton.addActionListener(ev -> addEmployeeFrame.dispose());
        addEmployeeFrame.add(addBackButton);

        addEmployeeFrame.setLocationRelativeTo(null);
        UI.show(addEmployeeFrame);
    }
    private static void openCustomerFrame() {
        JFrame frame = new JFrame("Customer Dashboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(null);
    
        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230));
        panel.setBounds(0, 0, 900, 500);
        panel.setLayout(null);
        frame.add(panel);
    
        JLabel titleLabel = new JLabel("Customer Service");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(370, 30, 200, 30);
        panel.add(titleLabel);
    
        
        JButton bookRoomButton = new JButton("Book Room");
        bookRoomButton.setBounds(150, 120, 200, 60);
        bookRoomButton.setBackground(Color.LIGHT_GRAY);
        panel.add(bookRoomButton);
    
     
      JButton viewBookedRoomButton = new JButton("View Booked Room");
      viewBookedRoomButton.setBounds(550, 120, 200, 60);
      viewBookedRoomButton.setBackground(Color.LIGHT_GRAY);
      panel.add(viewBookedRoomButton);
    
        
        JButton cancelBookingButton = new JButton("Cancel Booking");
        cancelBookingButton.setBounds(350, 280, 200, 60);
        cancelBookingButton.setBackground(Color.LIGHT_GRAY);
        panel.add(cancelBookingButton);
    
        
        JButton backButton = new JButton("Back");
        backButton.setBounds(750, 400, 100, 40);
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        panel.add(backButton);
    
    
    bookRoomButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            JPanel bookingPanel = new JPanel();
            bookingPanel.setLayout(new GridLayout(6, 2));             
      
            JLabel guestNameLabel = new JLabel("Guest Name:");
            JTextField guestNameField = new JTextField();
            JLabel mobileNumberLabel = new JLabel("Mobile Number:");
            JTextField mobileNumberField = new JTextField();
            JLabel roomNumberLabel = new JLabel("Room Number:");
            JTextField roomNumberField = new JTextField();
            JLabel roomTypeLabel = new JLabel("Room Type (e.g., Single, Double, Suite):");
            JTextField roomTypeField = new JTextField();
            JLabel checkInDateLabel = new JLabel("Check-In Date (e.g., YYYY-MM-DD):");
            JTextField checkInDateField = new JTextField();
            JLabel checkOutDateLabel = new JLabel("Check-Out Date (e.g., YYYY-MM-DD):");
            JTextField checkOutDateField = new JTextField();
    
            bookingPanel.add(guestNameLabel);
            bookingPanel.add(guestNameField);
            bookingPanel.add(mobileNumberLabel);
            bookingPanel.add(mobileNumberField);
            bookingPanel.add(roomNumberLabel);
            bookingPanel.add(roomNumberField);
            bookingPanel.add(roomTypeLabel);
            bookingPanel.add(roomTypeField);
            bookingPanel.add(checkInDateLabel);
            bookingPanel.add(checkInDateField);
            bookingPanel.add(checkOutDateLabel);
            bookingPanel.add(checkOutDateField);
    
            // age OK chara option chilo na, ekhon Cancel chaple booking hoy na
            if (JOptionPane.showConfirmDialog(frame, bookingPanel, "Enter Booking Details", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return;
    
            
            String guestName = guestNameField.getText();
            String mobileNumber = mobileNumberField.getText();
            String roomNumber = roomNumberField.getText();
            String roomType = roomTypeField.getText();
            String checkInDate = checkInDateField.getText();
            String checkOutDate = checkOutDateField.getText();
    

            if (guestName.isEmpty() || mobileNumber.isEmpty() || roomNumber.isEmpty() || roomType.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled!");
                return;
            }
            // online booking er moto eikhaneo number, room type ar date check
            if (!Validate.isPhone(mobileNumber.trim())) {
                JOptionPane.showMessageDialog(frame, "Mobile number must be 11 digits and start with 01.");
                return;
            }
            int price = RoomBookingGUI.priceFor(roomType);
            if (price < 0) {
                JOptionPane.showMessageDialog(frame, "Room type must be Single or Double.");
                return;
            }
            java.time.LocalDate in, out;
            try {
                in = java.time.LocalDate.parse(checkInDate.trim());
                out = java.time.LocalDate.parse(checkOutDate.trim());
            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(frame, "Dates must look like 2026-12-31.");
                return;
            }
            if (!out.isAfter(in)) {
                JOptionPane.showMessageDialog(frame, "Check-out must be after check-in.");
                return;
            }
            long nights = java.time.temporal.ChronoUnit.DAYS.between(in, out);
            long total = nights * price;
            {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Booking.txt", true))) {
                    // dam o save hoy, jate revenue te walk-in booking er taka o gona hoy
                    writer.write("Guest Name: " + guestName.trim() + ", Mobile Number: " + mobileNumber.trim() + ", Room Number: " + roomNumber.trim() +
                                 ", Room Type: " + roomType.trim() + ", Check-In: " + in + ", Check-Out: " + out + ", Nights: " + nights + ", Total: " + total);
                    writer.newLine();
                    JOptionPane.showMessageDialog(frame, "Room booked successfully! Total: BDT " + total);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(frame, "Error booking the room.");
                    ioException.printStackTrace();
                }
            }
        }
    });
    
    
    viewBookedRoomButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // age shudhu manager er booking dekhato, customer der online booking o ekhane ase
            StringBuilder content = new StringBuilder();
            content.append("WALK-IN BOOKINGS (made by manager)\n").append(readFile("Booking.txt", "None\n"));
            content.append("\nONLINE BOOKINGS (made by customers)\n").append(readFile(RoomBookingGUI.BOOKING_FILE, "None\n"));
                JTextArea contentArea = new JTextArea();
            contentArea.setText(content.toString());
            contentArea.setBounds(50, 10, 800, 250);  
            contentArea.setCaretPosition(0);  
            contentArea.setBackground(Color.LIGHT_GRAY);              contentArea.setLineWrap(true);  
            contentArea.setWrapStyleWord(true);              contentArea.setEditable(false); 
    
                        JFrame viewBookedRoomFrame = new JFrame("Booked Rooms");
            viewBookedRoomFrame.setSize(900, 400);
            viewBookedRoomFrame.setLayout(null);
            viewBookedRoomFrame.add(contentArea);  

            JButton backButton = new JButton("Back");
            backButton.setBounds(750, 300, 100, 40);
            backButton.setBackground(Color.RED);
            backButton.setForeground(Color.WHITE);
            viewBookedRoomFrame.add(backButton);
    
            
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewBookedRoomFrame.dispose(); 
                }
            });
     viewBookedRoomFrame.setLocationRelativeTo(null);  
     UI.show(viewBookedRoomFrame);                                                                                                                        
        }
    });
    
    
      
    cancelBookingButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      
        String cancelMobileNumber = JOptionPane.showInputDialog(frame, "Enter Mobile Number to Cancel the Booking:");
    
        if (cancelMobileNumber != null && !cancelMobileNumber.trim().isEmpty()) {
            File inputFile = new File("Booking.txt");
            StringBuilder fileContents = new StringBuilder();
            boolean found = false;
            
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (Validate.fieldIs(line, "Mobile Number", cancelMobileNumber)) {
                        found = true; 
                    } else {
                        fileContents.append(line).append(System.lineSeparator());
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error processing the cancellation.");
                return;
            }

            if (found && !Validate.confirm(frame, "the booking for " + cancelMobileNumber.trim())) return;
            if (found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
                    writer.write(fileContents.toString());
                    JOptionPane.showMessageDialog(frame, "Booking for Mobile Number " + cancelMobileNumber + " canceled successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error saving the updated file.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Booking with Mobile Number " + cancelMobileNumber + " not found!");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please provide a valid Mobile Number to cancel the booking.");
        }
    }
});

    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    });
    
    frame.setLocationRelativeTo(null);
    UI.show(frame);
    }

    private static void openRoomFrame() {
        JFrame frame = new JFrame("Room Dashboard");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(null);
    
        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 230)); 
        panel.setBounds(0, 0, 900, 500);
        panel.setLayout(null);
        frame.add(panel);
    
        JLabel titleLabel = new JLabel("Room Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(370, 30, 200, 30);
        panel.add(titleLabel);
    
        JButton viewRoomButton = new JButton("View Room");
        viewRoomButton.setBounds(150, 150, 200, 60);
        viewRoomButton.setBackground(Color.LIGHT_GRAY);
        panel.add(viewRoomButton);
    
        JButton monitorRoomButton = new JButton("Monitor Room");
        monitorRoomButton.setBounds(550, 150, 200, 60);
        monitorRoomButton.setBackground(Color.LIGHT_GRAY);
        panel.add(monitorRoomButton);
    
        JButton backButton = new JButton("Back");
        backButton.setBounds(750, 400, 100, 40);
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        panel.add(backButton);
    
        viewRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showText(frame, "All Rooms", readFile("rooms.txt", "No rooms added yet."));
            }
        });
    
        monitorRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // customer thakle room bhora, "no" ba faka hole khali
                StringBuilder busy = new StringBuilder(), free = new StringBuilder();
                int busyCount = 0, freeCount = 0;
                for (String line : readFile("rooms.txt", "").split("\n")) {
                    if (line.isBlank()) continue;
                    String room = Validate.field(line, "Room Number");
                    String guest = Validate.field(line, "Customer");
                    if (guest == null || guest.isEmpty() || guest.equalsIgnoreCase("no")) {
                        free.append("  Room ").append(room).append("\n");
                        freeCount++;
                    } else {
                        busy.append("  Room ").append(room).append(" - ").append(guest).append("\n");
                        busyCount++;
                    }
                }
                showText(frame, "Room Status", "Occupied: " + busyCount + "   Available: " + freeCount + "\n\nOCCUPIED\n"
                        + (busyCount == 0 ? "  None\n" : busy) + "\nAVAILABLE\n" + (freeCount == 0 ? "  None\n" : free));
            }
        });
    
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); 
            }
        });
    
        frame.setLocationRelativeTo(null);
        UI.show(frame);
    }
    
       private static void openReviewFrame() {
            JFrame frame = new JFrame("Customer Review and Feedback");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setLayout(null);
    
            JPanel panel = new JPanel();
            panel.setBackground(new Color(173, 216, 230));
            panel.setBounds(0, 0, 900, 600);
            panel.setLayout(null);
            frame.add(panel);
    
           JLabel titleLabel = new JLabel("Customer Review and Feedback");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setBounds(300, 20, 400, 30);
    panel.add(titleLabel);
    
    JLabel reviewsLabel = new JLabel("Customer Reviews:");
    reviewsLabel.setFont(new Font("Arial", Font.BOLD, 18));
    reviewsLabel.setBounds(50, 70, 200, 30);
    panel.add(reviewsLabel);
    
    // age eikhane 3 ta banano review hardcode kora chilo, ekhon customer der asol review file theke ase
    java.util.List<String> reviewsList = new java.util.ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(BookingHistoryGUI.REVIEW_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isBlank()) reviewsList.add(line);
        }
    } catch (IOException ex) {
        // file na thakle kono review nai
    }
    if (reviewsList.isEmpty()) reviewsList.add("No reviews yet");

    JComboBox<String> reviewsComboBox = new JComboBox<>(reviewsList.toArray(new String[0]));
    reviewsComboBox.setBounds(50, 110, 350, 30);
    panel.add(reviewsComboBox);

    // lomba review combo box e kete jay, tai select kora review ta niche puro dekhai
    JTextArea selectedReviewArea = new JTextArea((String) reviewsComboBox.getSelectedItem());
    selectedReviewArea.setEditable(false);
    selectedReviewArea.setLineWrap(true);
    selectedReviewArea.setWrapStyleWord(true);
    JScrollPane selectedReviewScroll = new JScrollPane(selectedReviewArea);
    selectedReviewScroll.setBounds(50, 150, 350, 110);
    panel.add(selectedReviewScroll);
    reviewsComboBox.addActionListener(ev -> selectedReviewArea.setText((String) reviewsComboBox.getSelectedItem()));
    
    JLabel feedbackLabel = new JLabel("Send Feedback:");
    feedbackLabel.setFont(new Font("Arial", Font.BOLD, 18));
    feedbackLabel.setBounds(450, 70, 200, 30);
    panel.add(feedbackLabel);
    
    JTextArea feedbackArea = new JTextArea();
    feedbackArea.setLineWrap(true);
    feedbackArea.setWrapStyleWord(true);
    JScrollPane feedbackScroll = new JScrollPane(feedbackArea);
    feedbackScroll.setBounds(450, 110, 350, 150);
    panel.add(feedbackScroll);
    
    JButton submitFeedbackButton = new JButton("Send Feedback");
    submitFeedbackButton.setBounds(450, 280, 200, 40);
    submitFeedbackButton.setBackground(Color.LIGHT_GRAY);
    panel.add(submitFeedbackButton);
    
    JButton backButton = new JButton("Back");
    backButton.setBounds(750, 470, 100, 40);
    backButton.setBackground(Color.RED);
    backButton.setForeground(Color.WHITE);
    panel.add(backButton);
    
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    });
    
    submitFeedbackButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedReview = (String) reviewsComboBox.getSelectedItem();             String feedback = feedbackArea.getText();
            if (selectedReview != null && !feedback.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
                    writer.write("Review: " + selectedReview.replace("\n", " ") + " | Reply: " + feedback.trim().replaceAll("\\s+", " "));
                    writer.newLine();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Could not save the feedback.");
                    return;
                }
                JOptionPane.showMessageDialog(frame, "Feedback saved for: \n" + selectedReview + "\n\nFeedback: " + feedback);
                feedbackArea.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a review and enter feedback before submitting.");
            }
        }
    });
    
    frame.setLocationRelativeTo(null);
    UI.show(frame);
    
        }
    

    // file er sob line ekshathe, file na thakle ba faka hole "empty" text
    static String readFile(String file, String empty) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) sb.append(line).append("\n");
            }
        } catch (IOException ex) {
            // file nai
        }
        return sb.length() == 0 ? empty : sb.toString();
    }

    // lomba text scroll kore dekhar dialog
    static void showText(Component parent, String title, String text) {
        JTextArea area = new JTextArea(text, 15, 60);
        area.setEditable(false);
        area.setFont(new Font(UI.FONT, Font.PLAIN, 14));
        JOptionPane.showMessageDialog(parent, new JScrollPane(area), title, JOptionPane.PLAIN_MESSAGE);
    }

    // window er nicher dike ekta Back button
    static JPanel backBar(JFrame frame) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = new JButton("Back");
        back.addActionListener(e -> frame.dispose());
        bar.add(back);
        return bar;
    }

    // sobcheye boro employee ID er porer ta
    static int nextEmployeeId() {
        int max = 0;
        for (String line : readFile("employees.txt", "").split("\n")) {
            String id = Validate.field(line, "Employee ID");
            try {
                if (id != null) max = Math.max(max, Integer.parseInt(id));
            } catch (NumberFormatException e) {
                // number na hole bad
            }
        }
        return max + 1;
    }
}
