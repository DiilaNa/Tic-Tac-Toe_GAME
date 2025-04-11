package com.assignment.tictactoe.service.game;

public interface Board {
    void initializeBoard();

    boolean isLegalMove(int row, int col);

    void updateMove(int row, int col, Piece piece);

    Winner checkWinner();

    void printBoard();
}


