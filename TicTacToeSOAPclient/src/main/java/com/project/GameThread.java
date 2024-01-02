// Nelson Nwakaihe
// 21264953
package com.project;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class GameThread extends Thread {
    private static final int SIZE = 3;
    private char[][] board;
    private boolean gameRunning;
    private JButton[][] buttons;
    private char currentPlayer;
    private ScoreWindow scoreSystemWindow;
     private TicTacToeFrame game;

    public GameThread(JButton[][] buttons, TicTacToeFrame game) {
        this.board = new char[SIZE][SIZE];
        this.gameRunning = true;
        this.buttons = buttons;
        initializeBoard();
        this.currentPlayer = 'X'; // Start with player X
        this.scoreSystemWindow = scoreSystemWindow;
    }

    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = ' ';
            }
        }
    }

    @Override
    public void run() {
        while (gameRunning) {
            // The game logic can be implemented here if needed
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void makeMove(int row, int col) {
        synchronized (this) {
            if (isValidMove(row, col)) {
                board[row][col] = currentPlayer;
                buttons[row][col].setText(String.valueOf(currentPlayer));
                if (checkWin()) {
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                    scoreSystemWindow.updateScores(currentPlayer);
                    gameRunning = false;
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(null, "It's a draw!");
                    scoreSystemWindow.updateScores(' '); // ' ' indicates a draw
                    gameRunning = false;
                } else {
                    switchPlayer();
                }
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return board[row][col] == ' ';
    }

    private boolean checkWin() {
        for (int i = 0; i < SIZE; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                    (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public void stopThread() {
        gameRunning = false;
    }
}
