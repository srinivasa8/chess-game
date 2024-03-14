package models;

public class Bishop extends Piece{

    public Bishop(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int targetPosX, int targetPosY) {
        return false;
    }

}
