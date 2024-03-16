package models;

public class Bishop extends Piece{

    public Bishop(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Cell targetCell) {
        return false;
    }

}
