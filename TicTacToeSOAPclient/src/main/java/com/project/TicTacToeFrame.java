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

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Main game frame for Tic Tac Toe.
 */
public class TicTacToeFrame extends JFrame implements ActionListener {

    private int currentPlayer;
    private JButton[][] buttons = new JButton[3][3];
    private int[][] board = new int[3][3]; // 0 = empty, 1 = player 1, 2 = player 2
    private static final int EMPTY = 0;
    private static final int PLAYER_ONE = 1;
    private static final int PLAYER_TWO = 2; 
    GameThread gameThread = new GameThread(buttons, this);

    public TicTacToeFrame() {
        currentPlayer = PLAYER_ONE;
        initializeGameBoard();
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setLayout(new GridLayout(3, 3));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        gameThread.start();
    }

    private void initializeGameBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].addActionListener(this);
                buttons[i][j].setBackground(Color.WHITE);
                add(buttons[i][j]);
                board[i][j] = EMPTY;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (sourceButton == buttons[i][j] && board[i][j] == EMPTY) {
                    if (currentPlayer == PLAYER_ONE) {
                        buttons[i][j].setText("X");
                        board[i][j] = PLAYER_ONE;
                    } else {
                        buttons[i][j].setText("O");
                        board[i][j] = PLAYER_TWO;
                    }
                    sourceButton.setEnabled(false);
                    if (checkForWin()) {
                        JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                        resetBoard();
                    } else if (isBoardFull()) {
                        JOptionPane.showMessageDialog(null, "It's a tie!");
                        resetBoard();
                    } else {
                        currentPlayer = (currentPlayer == PLAYER_ONE) ? PLAYER_TWO : PLAYER_ONE;
                    }
                    break;
//                    gameThread.makeMove(i, j);
//                    break;
                }
            }
        }
    }

    private boolean checkForWin() {
        for (int i = 0; i < 3; i++) {
        // Check rows and columns
        if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
            (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
            return true;
        }
    }
    // Check diagonals
    if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
        (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)) {
        return true;
    }
        return false; // Placeholder for win checking logic
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
                board[i][j] = EMPTY;
            }
        }
        currentPlayer = PLAYER_ONE;
    }

    public static void main(String[] args) {
        new TicTacToeFrame();
    }
}
