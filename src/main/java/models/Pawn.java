package models;

public class Pawn extends Piece{

    public Pawn(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int startPosX, int startPosY, int endPosX, int endPosY) {
        return false;
    }
}
