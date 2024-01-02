// Nelson Nwakaihe
//21264953
package com.project;

import com.tttws.TicTacToeWS;
import com.tttws.TicTacToeWebService;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JPanel;


public class TicTacToe extends JFrame implements ActionListener {

    private TicTacToeWebService service;
    private TicTacToeWS proxy;
    private final JButton login;
    private final JButton register;
    private JTextField usernameField, nameField, surnameField;
    private JPasswordField passwordField;
    private int userId; // This should be set after a successful login
    private int gameId; // This should be set after starting or joining a game
    
    public TicTacToe() {
        service = new TicTacToeWebService();
        proxy = service.getTicTacToeWSPort();
        // Set up the main window
        setTitle("Tic Tac Toe Game");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2, 10, 10));
        JPanel banner = new JPanel();
        banner.setLayout(new GridLayout(2,2,10,10));
        JLabel myLabel = new JLabel("Welcome to TicTacToe");
        JLabel instructions = new JLabel("Choose one of the options here to proceed.");
        banner.add(myLabel);
        banner.add(instructions);
        
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(10,10,2,2));
        login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a new window when the button is clicked
               LoginWindow loginWindow = new LoginWindow();
               loginWindow.setVisible(true); 
            }
        });
     
        
     /// login.addActionListener(this);
      ///  System.out.print(this);
        
        register = new JButton ("Register");
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
         RegisterWindow registerWindow = new RegisterWindow();
         registerWindow.setVisible(true);
        
             
            }
        });
        
        menu.add(login);
        menu.add(register);

        add(banner);
        add(menu);       
        
        // Adding username and password fields
       /// add(new JLabel("Username:"));
        /// usernameField = new JTextField();
        /// add(usernameField);

        /// add(new JLabel("Password:"));
        /// passwordField = new JPasswordField();
        /// add(passwordField);
        
        // Display the window
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }    
    
    public TicTacToeWS getProxy() {
         return proxy;
    }
    
   
    
    @Override
    public void actionPerformed(ActionEvent e) {
       ///  String username = usernameField.getText();
        /// String password = new String(passwordField.getPassword());

        // You need to figure out where to get name and surname from
        // For now, adding placeholder values
        String name = "Name";
        String surname = "Surname";

        try {
            /// String registrationResult = proxy.register(username, password, name, surname);
            // Handling registration result...
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
        }  
    }

    public static void main (String[] args) {
        TicTacToe myTicTacToe = new TicTacToe();
    }

    String performRegistration(String username, String string, String name, String surname) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Object[][] getOpenGames() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String joinGame(String gameId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String createNewGame() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Object[][] getLeaderboardData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String performRegister(String username, String password, String name, String surname) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
  


   

    
              
  


