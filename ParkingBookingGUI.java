package parkmate;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import parkmate.ParkingBookingSystem;

public class ParkingBookingGUI extends JFrame {
    private JLabel titleLabel, availableSpotsLabel, bookedSpotsLabel, userNameLabel, plateNumberLabel, bookingTimeLabel;
    private JTextField userNameField, plateNumberField;
    private JComboBox<String> timeSelector;
    private JButton bookButton, cancelButton, logoutButton, exitButton;
    private JTextArea bookingListTextArea;
    private JScrollPane scrollPane;
    private ParkingBookingSystem parkingSystem;
    private String currentUser;

    public ParkingBookingGUI(String username) {
        this.currentUser = username;
        initializeUI();
        
        userNameField.setText(username);
        userNameField.setEditable(false);
    }

    private void initializeUI() {
        parkingSystem = new ParkingBookingSystem(10);

        setTitle("Parking Booking System - Welcome " + currentUser);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(72, 85, 99); 
                Color color2 = new Color(34, 45, 50); 
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        setContentPane(backgroundPanel); 
        backgroundPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false); 
        titleLabel = new JLabel("ParkMate Your Booking Companion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Sans-Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        backgroundPanel.add(titlePanel, BorderLayout.NORTH); 

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        availableSpotsLabel = new JLabel("Available Spots: " + parkingSystem.getAvailableSpots());
        bookedSpotsLabel = new JLabel("Booked Spots: " + parkingSystem.getBookedSpots());
        availableSpotsLabel.setForeground(Color.WHITE);
        bookedSpotsLabel.setForeground(Color.WHITE);

        userNameLabel = new JLabel("Enter Your Name:");
        userNameLabel.setForeground(Color.WHITE);
        plateNumberLabel = new JLabel("Enter Vehicle Plate Number:");
        plateNumberLabel.setForeground(Color.WHITE);
        bookingTimeLabel = new JLabel("Select Booking Time:");
        bookingTimeLabel.setForeground(Color.WHITE);

        userNameField = new JTextField(20);
        plateNumberField = new JTextField(20);

        String[] times = {"08:00 AM", "08:30 AM", "09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "01:00 PM", "01:30 PM", "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM"};
        timeSelector = new JComboBox<>(times);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(userNameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(userNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(plateNumberLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(plateNumberField, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(bookingTimeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(timeSelector, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(availableSpotsLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(bookedSpotsLabel, gbc);

        backgroundPanel.add(formPanel, BorderLayout.CENTER); 

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        bookButton = new JButton("Book Parking");
        bookButton.setBackground(new Color(76, 175, 80));
        bookButton.setForeground(Color.WHITE);

        cancelButton = new JButton("Cancel Booking");
        cancelButton.setBackground(new Color(244, 67, 54));
        cancelButton.setForeground(Color.WHITE);

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(255, 152, 0));  
        logoutButton.setForeground(Color.WHITE);

        exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(33, 150, 243));
        exitButton.setForeground(Color.WHITE);

        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(exitButton);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH); 

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);

        JLabel bookingListLabel = new JLabel("Booking List:");
        bookingListLabel.setForeground(Color.WHITE);
        footerPanel.add(bookingListLabel, BorderLayout.NORTH);

        bookingListTextArea = new JTextArea(10, 40);
        bookingListTextArea.setEditable(false);
        bookingListTextArea.setBackground(new Color(240, 240, 240));
        bookingListTextArea.setForeground(Color.BLACK);

        scrollPane = new JScrollPane(bookingListTextArea);
        footerPanel.add(scrollPane, BorderLayout.CENTER);

        backgroundPanel.add(footerPanel, BorderLayout.EAST); 
        bookButton.addActionListener(e -> bookParking());
        cancelButton.addActionListener(e -> cancelBooking());
        logoutButton.addActionListener(e -> logout());
        exitButton.addActionListener(e -> exitApplication());
    }

private void bookParking() {
    String userName = userNameField.getText().trim();
    String plateNumber = plateNumberField.getText().trim().toUpperCase();
    String selectedTime = (String) timeSelector.getSelectedItem();

    if (userName.isEmpty() || plateNumber.isEmpty() || selectedTime == null) {
        JOptionPane.showMessageDialog(this, 
            "Please enter all fields, including booking time.",
            "Missing Information",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!plateNumber.matches("[A-Z0-9 -]{1,10}")) {
        JOptionPane.showMessageDialog(this,
            "Please enter a valid plate number.\nAllowed: Letters, numbers, hyphens, and spaces.\nMaximum 10 characters.",
            "Invalid Input",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    plateNumberField.setText(plateNumber);

    if (parkingSystem.bookParking(userName, plateNumber, selectedTime)) {
        availableSpotsLabel.setText("Available Spots: " + parkingSystem.getAvailableSpots());
        bookedSpotsLabel.setText("Booked Spots: " + parkingSystem.getBookedSpots());
        updateBookingList();
        JOptionPane.showMessageDialog(this, "Booking Successful!\nDetails:\n" + 
            "Name: " + userName + "\nPlate: " + plateNumber + "\nTime: " + selectedTime);
    } else {
        JOptionPane.showMessageDialog(this, "No available spots.");
    }
}

private void cancelBooking() {
    String userName = userNameField.getText().trim();
    String plateNumber = plateNumberField.getText().trim();
    String selectedTime = (String) timeSelector.getSelectedItem();

    if (userName.isEmpty() || plateNumber.isEmpty() || selectedTime == null) {
        JOptionPane.showMessageDialog(this, "Please enter all fields, including booking time.");
        return;
    }

    if (parkingSystem.cancelBooking(userName, plateNumber, selectedTime)) {
        availableSpotsLabel.setText("Available Spots: " + parkingSystem.getAvailableSpots());
        bookedSpotsLabel.setText("Booked Spots: " + parkingSystem.getBookedSpots());
        updateBookingList();
        JOptionPane.showMessageDialog(this, "Booking Cancelled for: " + userName);
    } else {
        JOptionPane.showMessageDialog(this, "No booking found for the given details.");
    }
}

    private void updateBookingList() {
        ArrayList<ParkingBookingSystem.Booking> bookings = parkingSystem.getBookings();
        StringBuilder bookingInfo = new StringBuilder("Current Bookings:\n");
        bookings.forEach(booking -> bookingInfo.append(booking).append("\n"));
        bookingListTextArea.setText(bookingInfo.toString());
    }

    private void logout() {
        int response = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Logout", 
            JOptionPane.YES_NO_OPTION);
            
        if (response == JOptionPane.YES_OPTION) {
            for (WindowListener listener : getWindowListeners()) {
                listener.windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
            this.dispose();
        }
    }

    private void exitApplication() {
        int response = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to exit the application?", 
            "Exit", 
            JOptionPane.YES_NO_OPTION);
            
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ParkingBookingGUI("TestUser").setVisible(true));
    }
}
