//Nelson Nwakaihe
// 21264953

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project;

import javax.swing.*;
import java.awt.*;

public class RegisterWindow extends JFrame {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public RegisterWindow() {
        super("Register");
        initComponents();
        layoutComponents();
        addListeners();
        finalizeLayout();
    }

    private void initComponents() {
        nameField = new JTextField(15);
        surnameField = new JTextField(15);
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        registerButton = new JButton("Register");
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4); // Adds padding around components

        // Adjust constraints and add components
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Name:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_START;
        add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Surname:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_START;
        add(surnameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_START;
        add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);
    }

     private void addListeners() {
        registerButton.addActionListener(e -> performRegistration());
    }

    private void finalizeLayout() {
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    private void performRegistration() {
    String name = nameField.getText();
    String surname = surnameField.getText();
    String username = usernameField.getText();
    char[] password = passwordField.getPassword();

    if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.length == 0) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Registration Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Create an instance of the client class
    TicTacToe client = new TicTacToe();
    
    // Call the registration method on the instance
    try {
        String registrationResult = client.getProxy().register(username, new String(password), name, surname);
        
        if (Integer.parseInt(registrationResult) >= 1) {
            GameLobbyWindow gameLobbyWindow = new GameLobbyWindow(); 
            gameLobbyWindow.setVisible(true);
            this.dispose();
        
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed: " + registrationResult, "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Registration failed: " + e.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
    }
    
    client.dispose();
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterWindow::new);
    }
    

    
}


