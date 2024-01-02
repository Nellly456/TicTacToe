// Nelson Nwakaihe
// 21264953

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project;

/**
 *
 * @author Geoffray Mongella
 */
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ScoreWindow extends JFrame {
    private JTextArea scoreArea;

    public ScoreWindow() {
        setTitle("Score System");
        setSize(300, 200);
        setLayout(new BorderLayout());
        scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        // Add some mock data, replace with real data retrieved from your SOAP service
        scoreArea.setText("Wins: 10\nLosses: 5\nDraws: 2");
        add(new JScrollPane(scoreArea), BorderLayout.CENTER);
    }

    public void updateScores(String scoreData) {
        scoreArea.setText(scoreData);
    }

    // Method to retrieve scores from the web service
    public void loadScores() {
        // Call your SOAP service to get scores and then call updateScores
    }

    void updateScores(char currentPlayer) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

