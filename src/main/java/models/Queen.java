package models;

public class Queen extends Piece{

    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Cell targetCell) {
        return false;
    }

}
