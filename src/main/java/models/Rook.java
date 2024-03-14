package models;

public class Rook extends Piece{

    public Rook(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int targetPosX, int targetPosY) {
        return false;
    }

}
