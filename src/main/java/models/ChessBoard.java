package models;

public class ChessBoard {
    private Cell[][] board;
    private Player player1;
    private Player player2;

    public ChessBoard() {
        this.board = new Cell[8][8];
    }

    public Cell[][] getBoard() {
        return board;
    }
}
