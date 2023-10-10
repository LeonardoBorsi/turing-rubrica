package ui;

import classes.*;
import javax.swing.*;
import java.awt.*;

public class AuthFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private Auth auth;
    private Database db;

    public AuthFrame(Auth auth, Database db) {
        this.auth = auth;
        this.db = db;

        setTitle("Login");
        setSize(400, 170);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        registerButton = new JButton("Register");
        add(registerButton);

        loginButton = new JButton("Login");
        add(loginButton);

        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegistration());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        User user = auth.tryLogin(username, password);
        if (user != null) {
            Rubrica rubrica = new Rubrica(db, user);
            new MainFrame(rubrica, user).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid credentials",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        if(!username.isEmpty() && !password.isEmpty()) {
            User user = auth.register(username, password);
            if (user != null) {
                Rubrica rubrica = new Rubrica(db, user);
                new MainFrame(rubrica, user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Error during registration",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Insert both username and password",
                    "Error",
                    JOptionPane.ERROR_MESSAGE)
            ;
        }
    }
}


