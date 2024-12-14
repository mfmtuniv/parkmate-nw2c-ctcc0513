package parkmate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class Parkmate {

    private static Map<String, String> userDatabase = new HashMap<>();
    private static JFrame mainFrame;

    public static void main(String[] args) {

        mainFrame = new JFrame("Login and Sign-Up App");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        mainFrame.setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(new Color(240, 248, 255));
        
        JLabel loginLabel = new JLabel("Login", JLabel.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JTextField loginUsernameField = new JTextField(15);
        JPasswordField loginPasswordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        JButton goToSignUpButton = new JButton("Sign Up");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(loginLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        loginPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        loginPanel.add(loginUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        loginPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        loginPanel.add(loginPasswordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        loginPanel.add(loginButton, gbc);

        gbc.gridx = 1;
        loginPanel.add(goToSignUpButton, gbc);

        JPanel signUpPanel = new JPanel();
        signUpPanel.setLayout(new GridBagLayout());
        signUpPanel.setBackground(new Color(240, 255, 240));
        
        JLabel signUpLabel = new JLabel("Sign Up", JLabel.CENTER);
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JTextField signUpUsernameField = new JTextField(15);
        JPasswordField signUpPasswordField = new JPasswordField(15);

        JButton signUpButton = new JButton("Sign Up");
        JButton goToLoginButton = new JButton("Back to Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        signUpPanel.add(signUpLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        signUpPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        signUpPanel.add(signUpUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        signUpPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        signUpPanel.add(signUpPasswordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        signUpPanel.add(signUpButton, gbc);

        gbc.gridx = 1;
        signUpPanel.add(goToLoginButton, gbc);

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(signUpPanel, "Sign Up");

        loginPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });

        signUpPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    signUpButton.doClick();
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginUsernameField.getText().trim();
                String password = new String(loginPasswordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, 
                        "Please fill in all fields!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
                    JOptionPane.showMessageDialog(mainFrame, 
                        "Login successful! Welcome, " + username + "!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    loginUsernameField.setText("");
                    loginPasswordField.setText("");
                    
                    launchParkingSystem(username);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, 
                        "Invalid username or password!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    loginPasswordField.setText("");
                }
            }
        });

        goToSignUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Sign Up");
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = signUpUsernameField.getText().trim();
                String password = new String(signUpPasswordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, 
                        "Please fill out all fields!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                } else if (username.length() < 3) {
                    JOptionPane.showMessageDialog(mainFrame, 
                        "Username must be at least 3 characters long!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                } else if (password.length() < 6) {
                    JOptionPane.showMessageDialog(mainFrame, 
                        "Password must be at least 6 characters long!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                } else if (userDatabase.containsKey(username)) {
                    JOptionPane.showMessageDialog(mainFrame, 
                        "Username already exists!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    userDatabase.put(username, password);
                    JOptionPane.showMessageDialog(mainFrame, 
                        "Sign-up successful! You can now log in.", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    signUpUsernameField.setText("");
                    signUpPasswordField.setText("");
                    cardLayout.show(mainPanel, "Login");
                }
            }
        });

        goToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Login");
            }
        });

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private static void launchParkingSystem(String username) {
        mainFrame.setVisible(false);
        
        SwingUtilities.invokeLater(() -> {
            try {
                ParkingBookingGUI parkingSystem = new ParkingBookingGUI(username);
                parkingSystem.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        mainFrame.setLocationRelativeTo(null);
                        mainFrame.setVisible(true);
                    }
                });
                parkingSystem.setVisible(true);
            } catch (Exception e) {
                mainFrame.setVisible(true);
                JOptionPane.showMessageDialog(mainFrame,
                    "Error launching parking system: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
