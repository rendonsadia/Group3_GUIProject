package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginGUI extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final Map<String, String> credentialsMap = new HashMap<>();
    private final String employeeFile = "src/main/resources/Employee Details.csv";

    public LoginGUI() {
        setTitle("Employee Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        // Load credentials
        loadCredentialsFromTSV();

        // GUI layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel headerLabel = new JLabel("MotorPH Payroll System");
        headerLabel.setFont(headerLabel.getFont().deriveFont(30f)); // Make font slightly larger
        panel.add(headerLabel, gbc);

        // Reset for user input fields
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("UserID:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Login button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        panel.add(loginButton, gbc);

        // Forgot Password button
        gbc.gridx = 0;
        gbc.gridy = 4;
        JButton forgotButton = new JButton("<html><u>Forgot Password?</u></html>");
        forgotButton.setBorderPainted(false);
        forgotButton.setContentAreaFilled(false);
        forgotButton.setForeground(java.awt.Color.BLUE);
        forgotButton.setFocusPainted(false);
        panel.add(forgotButton, gbc);

        // Add functionality (optional)
        forgotButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Please contact HR to retrieve or reset your password.",
                "Forgot Password", JOptionPane.INFORMATION_MESSAGE);
        });

        loginButton.addActionListener(e -> attemptLogin());
        getRootPane().setDefaultButton(loginButton); // Enables Enter key for login

        add(panel);

    }

    private void loadCredentialsFromTSV() {
    // Add hardcoded admin credentials
    credentialsMap.put("admin", "1234");

        try (BufferedReader reader = new BufferedReader(new FileReader(employeeFile))) {
            String line;
            boolean isHeader = true;
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            DateTimeFormatter passwordFormatter = DateTimeFormatter.ofPattern("MMddyyyy");

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] tokens = line.split("\t");
                if (tokens.length >= 4) {
                    String employeeNumber = tokens[0].trim();
                    String birthdayRaw = tokens[3].trim();

                    if (!employeeNumber.isEmpty() && !birthdayRaw.equalsIgnoreCase("TBD")) {
                        try {
                            LocalDate birthday = LocalDate.parse(birthdayRaw, inputFormatter);
                            String formattedPassword = birthday.format(passwordFormatter);  // MMddyyyy
                            credentialsMap.put(employeeNumber, formattedPassword);
                        } catch (Exception e) {
                            System.err.println("Invalid birthday format for employee " + employeeNumber + ": " + birthdayRaw);
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to load employee data.\n" + e.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   private void attemptLogin() {
    String enteredUser = usernameField.getText().trim();
    String enteredPass = new String(passwordField.getPassword()).trim();

    // Optional admin login
    if (enteredUser.equals("admin") && enteredPass.equals("1234")) {
        JOptionPane.showMessageDialog(this, "Admin login successful!", "Welcome Admin", JOptionPane.INFORMATION_MESSAGE);

        // Optional: Open a different admin GUI, e.g., AdminDashboardGUI
        SwingUtilities.invokeLater(() -> {
            new PayrollGUI().setVisible(true); // Change this to AdminDashboardGUI if you have one
        });

        dispose();
        return;
    }

        // Regular employee login
        if (credentialsMap.containsKey(enteredUser)) {
            String expectedPass = credentialsMap.get(enteredUser);
            if (enteredPass.equals(expectedPass)) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Welcome", JOptionPane.INFORMATION_MESSAGE);

                SwingUtilities.invokeLater(() -> {
                    new PayrollGUI().setVisible(true);
                });

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect password (birthday).", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Employee number not found.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }

    // <-- Final class closing brace
    }