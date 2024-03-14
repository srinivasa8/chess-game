package models;

public class Rook extends Piece{

    public Rook(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int startPosX, int startPosY, int endPosX, int endPosY) {
        return false;
    }

}
