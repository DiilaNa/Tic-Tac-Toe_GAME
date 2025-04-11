package com.assignment.tictactoe.service.game;

// AiPlayer inherits from the Player class.
//  use the Minimax algorithm to determine the best  move.
public class AiPlayer extends Player {

    // Constructor initializes the AI player with a game board
    public AiPlayer(BoardImpl board) {
        super(board);
    }

    // Overrides method, making a move on the board if it's legal
    @Override
    public void move(int row, int col) {
        if (board.isLegalMove(row, col)) {
            board.updateMove(row, col, Piece.O); // AI player uses 'O' as their piece
        }
    }

    // Method to determine and perform the best move using Minimax
    public void findBestMove() {
        int bestValue = Integer.MIN_VALUE; // Initialize best value as the minimum
        int bestRow = -1; // Track the best row for the move
        int bestCol = -1; // Track the best column for the move
        Piece[][] pieces = board.getPieces(); // Get current board configuration

        // Loop through each cell on the board
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                // Check if the cell is empty
                if (pieces[i][j] == Piece.EMPTY) {
                    // Make a hypothetical move with 'O'
                    pieces[i][j] = Piece.O;

                    // Evaluate the move with minimax algorithm
                    int moveValue = minimax(pieces, 0, false);

                    // Undo the move after evaluation
                    pieces[i][j] = Piece.EMPTY;

                    // Update best move if this move's value is better
                    if (moveValue > bestValue) {
                        bestRow = i;
                        bestCol = j;
                        bestValue = moveValue;
                    }
                }
            }
        }

        // Perform the best move if one was found
        if (bestRow != -1 && bestCol != -1) {
            move(bestRow, bestCol);
        }
    }

    // Minimax algorithm to evaluate the board and find the optimal move
    private int minimax(Piece[][] pieces, int depth, boolean isMaximizing) {
        // Check if the game has a winner or is a draw
        Winner winner = board.checkWinner();
        if (winner != null) {
            // Return score based on who won; smaller depth values are prioritized
            if (winner.getWinningPiece() == Piece.O) {
                return 10 - depth; // AI wins, with higher scores for quicker wins
            } else if (winner.getWinningPiece() == Piece.X) {
                return depth - 10; // Opponent wins, with lower scores for faster losses
            }
        }

        // If the board is full and there's no winner, it's a draw
        if (board.isBoardFull()) {
            return 0;
        }

        // If it's the maximizing player's (AI's) turn
        if (isMaximizing) {
            int bestValue = Integer.MIN_VALUE; // Initialize best value as minimum

            // Check every cell for the best maximizing move
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j] == Piece.EMPTY) {
                        pieces[i][j] = Piece.O; // Try placing 'O'
                        bestValue = Math.max(bestValue, minimax(pieces, depth + 1, false));
                        pieces[i][j] = Piece.EMPTY; // Undo move
                    }
                }
            }
            return bestValue;

            // If it's the minimizing player's (opponent's) turn
        } else {
            int bestValue = Integer.MAX_VALUE; // Initialize best value as maximum

            // Check every cell for the best minimizing move
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j] == Piece.EMPTY) {
                        pieces[i][j] = Piece.X; // Try placing 'X'
                        bestValue = Math.min(bestValue, minimax(pieces, depth + 1, true));
                        pieces[i][j] = Piece.EMPTY; // Undo move
                    }
                }
            }
            return bestValue;
        }
    }
}
