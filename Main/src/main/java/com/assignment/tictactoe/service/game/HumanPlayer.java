package com.assignment.tictactoe.service.game;

public class HumanPlayer extends Player {

    public HumanPlayer(BoardImpl board) {
        super(board);
    }

    @Override
    public void move(int row, int col) {
        board.updateMove(row , col , Piece.X);
    }
}
