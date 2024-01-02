//Nelson Nwakaihe
// 21264953
package com.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LeaderboardWindow extends JFrame {
    private JTable leaderboardTable;
    private TicTacToe soapClient; // Assuming TicTacToe has methods related to the SOAP client

    public LeaderboardWindow() {
        //this.soapClient = client; // Initialize the soapClient field
        initComponents();
    }

    private void initComponents() {
        setTitle("Leaderboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Player", "Wins", "Losses", "Draws"};
        Object[][] data = {}; // Data should be fetched from your SOAP service
        leaderboardTable = new JTable(new DefaultTableModel(data, columnNames));
        add(new JScrollPane(leaderboardTable), BorderLayout.CENTER);

        setLocationRelativeTo(null); // Center the window
        loadLeaderboardData(); // Load data when the window is created
    }

    public void loadLeaderboardData() {
        try {
            Object[][] leaderboardData = soapClient.getLeaderboardData();
            updateLeaderboard(leaderboardData);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error retrieving leaderboard data: " + e.getMessage());
        }
    }

    public void updateLeaderboard(Object[][] newData) {
        DefaultTableModel model = (DefaultTableModel) leaderboardTable.getModel();
        model.setDataVector(newData, new String[]{"Player", "Wins", "Losses", "Draws"});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LeaderboardWindow().setVisible(true);
        });
    }
}
