import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private JFrame frame;
    private JTextField roomNumberField, priceField, customerNameField, acTypeField;
    private JLabel roomCountLabel; 

  public Room() {
        
 frame = new JFrame("Room Management");
 frame.setSize(600, 450);
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 frame.getContentPane().setBackground(new Color(170, 120, 215)); 
   frame.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
     gbc.fill = GridBagConstraints.HORIZONTAL;

       
    JLabel titleLabel = new JLabel("ROOM");
   titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setForeground(Color.YELLOW);
   titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
   gbc.gridwidth = 2;
   gbc.gridx = 0;
    gbc.gridy = 0;
    frame.add(titleLabel, gbc);

        
 JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));  

        
  JButton roomDetailsButton = createButton("Room Details");
  JButton searchRoomButton = createButton("Search Room");
 JButton addRoomButton = createButton("Add Room");
   JButton deleteRoomButton = createButton("Delete Room");

       
  buttonPanel.add(roomDetailsButton);
  buttonPanel.add(searchRoomButton);
  buttonPanel.add(addRoomButton);
  buttonPanel.add(deleteRoomButton);

        
   gbc.gridwidth = 2;
   gbc.gridx = 0;
   gbc.gridy = 1;
   frame.add(buttonPanel, gbc);

       
     roomCountLabel = new JLabel("Total Rooms: " + countRooms());
     roomCountLabel.setFont(new Font("Arial", Font.BOLD, 18));
    roomCountLabel.setForeground(new Color(135, 206, 250)); 
    roomCountLabel.setOpaque(true);
     roomCountLabel.setBackground(new Color(0, 0, 139));
    gbc.gridwidth = 2;
     gbc.gridx = 0;
     gbc.gridy = 2;
    frame.add(roomCountLabel, gbc);
    gbc.gridwidth = 1; 

        
    JButton backButton = new JButton("Back");
    backButton.setFont(new Font("Arial", Font.BOLD, 16));
    backButton.setBackground(Color.RED); 
    backButton.setForeground(Color.WHITE); 
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2; 
    frame.add(backButton, gbc);

        
        roomDetailsButton.addActionListener(e -> showRoomDetails());
        searchRoomButton.addActionListener(e -> searchRoom());
        addRoomButton.addActionListener(e -> {
            addRoom();
            updateRoomCount(); 
        });
        deleteRoomButton.addActionListener(e -> {
            deleteRoom();
            updateRoomCount(); 
        });
        backButton.addActionListener(e -> {
            frame.dispose(); 
            new AdminDashboard(); 
        });

        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(0, 0, 255)); 
        button.setForeground(Color.WHITE); 
        return button;
    }


    private void showRoomDetails() {
        StringBuilder roomDetails = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("rooms.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                roomDetails.append(line).append("\n");
            }
        } catch (IOException e) {
            roomDetails.append("Error reading room details.");
        }
        JOptionPane.showMessageDialog(frame, roomDetails.toString(), "Room Details", JOptionPane.INFORMATION_MESSAGE);
    }

    
    private void searchRoom() {
        String roomNumber = JOptionPane.showInputDialog(frame, "Enter Room Number to Search:");
        if (roomNumber == null || roomNumber.trim().isEmpty()) return;

        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader("rooms.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Room Number: " + roomNumber)) {
                    JOptionPane.showMessageDialog(frame, line, "Room Found", JOptionPane.INFORMATION_MESSAGE);
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading room details.");
        }
        if (!found) {
            JOptionPane.showMessageDialog(frame, "Room not found.", "Search Result", JOptionPane.WARNING_MESSAGE);
        }
    }

    
    private void addRoom() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        roomNumberField = new JTextField();
        priceField = new JTextField();
        customerNameField = new JTextField();
        acTypeField = new JTextField();

        panel.add(new JLabel("Room Number:"));
        panel.add(roomNumberField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Customer Name:"));
        panel.add(customerNameField);
        panel.add(new JLabel("AC/Non-AC:"));
        panel.add(acTypeField);

        int option = JOptionPane.showConfirmDialog(frame, panel, "Add Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String roomNumber = roomNumberField.getText();
            String price = priceField.getText();
            String customerName = customerNameField.getText();
            String acType = acTypeField.getText();

            if (roomNumber.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Room Number and Price are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Validate.isAmount(price)) {
                JOptionPane.showMessageDialog(frame, "Price must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("rooms.txt", true))) {
                writer.write("Room Number: " + roomNumber + ", Price: " + price + ", Customer: " + customerName + ", AC Type: " + acType + "\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error saving room details.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(frame, "Room added successfully!");
        }
    }

    
    private void deleteRoom() {
        String roomNumber = JOptionPane.showInputDialog(frame, "Enter Room Number to Delete:");
        if (roomNumber == null || roomNumber.trim().isEmpty()) return;

        List<String> updatedRooms = new ArrayList<>();
        boolean roomDeleted = false;

        try (BufferedReader br = new BufferedReader(new FileReader("rooms.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("Room Number: " + roomNumber)) {
                    updatedRooms.add(line);
                } else {
                    roomDeleted = true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading room details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (roomDeleted) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("rooms.txt"))) {
                for (String room : updatedRooms) {
                    writer.write(room + "\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error updating room list.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(frame, "Room deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Room not found.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    
    private int countRooms() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("rooms.txt"))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            return 0;
        }
        return count;
    }

    
    private void updateRoomCount() {
        roomCountLabel.setText("Total Rooms: " + countRooms());
    }

    public static void main(String[] args) {
        new Room();
    }
}

