package models;

public class Rook extends Piece{

    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Cell targetCell) {
        return false;
    }

}
