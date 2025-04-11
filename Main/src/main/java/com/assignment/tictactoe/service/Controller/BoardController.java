package com.assignment.tictactoe.service.Controller;

import com.assignment.tictactoe.service.game.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class BoardController implements BoardUI {
    @FXML
    private Button b1;

    @FXML
    private Button eight;

    @FXML
    private Button five;

    @FXML
    private Button four;

    @FXML
    private Button nine;

    @FXML
    private Button one;

    @FXML
    private Button seven;

    @FXML
    private Button six;

    @FXML
    private Button three;
    public Button two;

    BoardImpl board;
    HumanPlayer human;
    AiPlayer aiPlayer;

    boolean isGameOver = false;

    public BoardController() {
        board = new BoardImpl();
        human = new HumanPlayer(board);
        aiPlayer = new AiPlayer(board);
    }

    @FXML
   public void ButtonAction(ActionEvent event) {
        resetButtons();
        board.resetPieces();
        isGameOver = false;

    }
    @Override
    public void updateBoard(int row, int col, boolean isHuman) {
        String symbol = isHuman ? "X" : "O";
        Button button = getButtonByPosition(row, col);
        button.setText(symbol);
    }

    private Button getButtonByPosition(int row, int col) {
        if (row == 0 && col == 0) return one;
        if (row == 0 && col == 1) return two;
        if (row == 0 && col == 2) return three;
        if (row == 1 && col == 0) return four;
        if (row == 1 && col == 1) return five;
        if (row == 1 && col == 2) return six;
        if (row == 2 && col == 0) return seven;
        if (row == 2 && col == 1) return eight;
        if (row == 2 && col == 2) return nine;
        return null;
    }

    @Override
    public void NotifyWinner(Piece winner) {
        new Alert(Alert.AlertType.INFORMATION , (winner + " Won The Game!!")).show();
        // Implement notification logic for the winner
    }

    public void resetButtons() {
        one.setText("");
        two.setText("");
        three.setText("");
        four.setText("");
        five.setText("");
        six.setText("");
        seven.setText("");
        eight.setText("");
        nine.setText("");
    }

    @FXML
    void buttonAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);

        // Human move
        if (!isGameOver) {
            if (board.isLegalMove(row, col)) {
                human.move(row, col);
                updateBoard(row, col, true); // Update UI for human move

                // Check for a winner after human's move
                if (board.checkWinner() != null) {
                    isGameOver = true;
                    NotifyWinner(Piece.X); // Human is X
                    return; // End the game if there's a winner
                }

                // Check for a draw
                if (isBoardFull()) {
                    isGameOver = true;
                    showAlert("Match Drawn!", "The game has ended in a draw.");
                    return; // End the game if it's a draw
                }

                // AI move
                aiPlayer.findBestMove();
                Piece[][] pieces = board.getPieces(); // Get updated board after AI move

                // Find where AI placed its move and update UI
                for (int i = 0; i < pieces.length; i++) {
                    for (int j = 0; j < pieces[i].length; j++) {
                        if (pieces[i][j] == Piece.O && !getButtonByPosition(i, j).getText().equals("O")) {
                            updateBoard(i, j, false); // Update UI for AI move
                            break;
                        }
                    }
                }

                // Check for a winner after AI's move
                if (board.checkWinner() != null) {
                    NotifyWinner(Piece.O); // AI is O
                    isGameOver = true;
                    return; // End the game if there's a winner
                }

                // Check for a draw after AI's move
                if (isBoardFull()) {
                    isGameOver = true;
                    showAlert("Match Drawn!", "The game has ended in a draw.");
                    return; // End the game if it's a draw
                }
            }
        } else {
            System.out.println("Game Over");
        }
    }

    // Check if the board is full
    private boolean isBoardFull() {
        Piece[][] pieces = board.getPieces(); // Assuming this method gets the current state of the board
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] == Piece.EMPTY) { // Assuming EMPTY is a constant representing an empty cell
                    return false; // If any cell is empty, the board is not full
                }
            }
        }
        return true; // All cells are filled
    }

    // Method to show alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
