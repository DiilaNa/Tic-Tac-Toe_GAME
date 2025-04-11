package com.assignment.tictactoe.service.game;

public interface BoardUI {
    void updateBoard(int row, int col, boolean isHuman);
    void NotifyWinner(Piece winner);
}
