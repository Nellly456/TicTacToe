// Nelson Nwakaihe
// 21264953
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class GameLobbyWindow extends JFrame implements ActionListener {
    private JTable openGamesTable;
    private JButton createGameButton;
    private JButton joinGameButton;
    private JButton checkScoreButton;
    private TicTacToe soapClient; // Declare the soapClient field
    TicTacToe client = new TicTacToe();

    public GameLobbyWindow() {
//        this.soapClient = client; // Initialize the soapClient
        setTitle("Game Lobby");
        setSize(400, 400);
        setLayout(new BorderLayout());
        
        String[] columnNames = {"Game ID", "Host Player"};
        Object[][] data = {}; // This should be fetched from your SOAP service
        openGamesTable = new JTable(data, columnNames);
        add(new JScrollPane(openGamesTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        buttonPanel.setBackground(Color.CYAN); // Set a background color to make the panel visible
        buttonPanel.setPreferredSize(new Dimension(400, 100)); // Set a preferred size to ensure visibility
        
        createGameButton = new JButton("Create New Game");
        checkScoreButton = new JButton ("Check Scores");
        joinGameButton = new JButton("Join Selected Game");
        
        

        createGameButton.addActionListener(this);
        checkScoreButton.addActionListener(this);
        joinGameButton.addActionListener(this);

        buttonPanel.add(createGameButton);
        buttonPanel.add(checkScoreButton);
        buttonPanel.add(joinGameButton);
        

        add(buttonPanel, BorderLayout.SOUTH);
    }

//    GameLobbyWindow() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createGameButton) {
            createNewGame();
        } else if (e.getSource() == joinGameButton) {
            joinGame();
        }else if (e.getSource() == checkScoreButton) {
            checkScores();
        }
    }

    private void createNewGame() {
        try {
            String gameId = client.getProxy().newGame(400);
            if (gameId != null) {
                JOptionPane.showMessageDialog(this, "New game created with ID: " + gameId);
               TicTacToeFrame ticTacToeFrame = new TicTacToeFrame();
               ticTacToeFrame.setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error creating new game: " + ex.getMessage());
        }
    }

    private void joinGame() {
        int selectedRow = openGamesTable.getSelectedRow();
    if (selectedRow != -1) {
        String gameId = openGamesTable.getModel().getValueAt(selectedRow, 0).toString(); // Assuming the game ID is in the first column
        try {
            String result = soapClient.joinGame(gameId);
            if ("SUCCESS".equals(result)) {
                JOptionPane.showMessageDialog(this, "Joined game successfully!");
                // Update the UI accordingly, maybe close this window and open the game window
            } else {
                JOptionPane.showMessageDialog(this, "Failed to join the game: " + result);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error joining the game: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a game to join.");
    }
}

    public void loadOpenGames() {
        try {
        Object[][] openGamesData = soapClient.getOpenGames(); // This is a hypothetical method call
        DefaultTableModel model = (DefaultTableModel) openGamesTable.getModel();
        model.setDataVector(openGamesData, new String[]{"Game ID", "Host Player"});
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error loading open games: " + ex.getMessage());
    }
 }
    
     private void checkScores() {
        try {  LeaderboardWindow leaderboardWindow = new LeaderboardWindow(); 
                    leaderboardWindow.setVisible(true);
                    dispose(); // Close the login window7
        
                // Update the UI accordingly
         
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error creating new game: " + ex.getMessage());
        }
    }

}