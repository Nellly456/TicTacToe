// Nelson Nwakaihe
//21264953
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project;

import com.tttws.TicTacToeWS;
import com.tttws.TicTacToeWebService;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {

    private static TicTacToeWebService service;
    private static TicTacToeWS proxy;

    public LoginWindow() {
        service = new TicTacToeWebService();
        proxy = service.getTicTacToeWSPort();

        JTextField name = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                TicTacToe client = new TicTacToe();
                String username = name.getText(); 
                char[] passwordArray = password.getPassword();
                String passwordString = new String(passwordArray);

                // Add logic to validate the user credentials using proxy
                int loginResult = proxy.login(username, passwordString);

                if (loginResult >= 1) {
                    GameLobbyWindow gameLobbyWindow = new GameLobbyWindow(); 
                    gameLobbyWindow.setVisible(true);
                    dispose(); // Close the login window
                } else {
                    JOptionPane.showMessageDialog(LoginWindow.this, "Invalid Credentials", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
                client.dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(name);
        panel.add(new JLabel("Password:"));
        panel.add(password); 
        panel.add(loginButton);

        add(panel);

        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
