package models;

public class Queen extends Piece{

    public Queen(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int targetPosX, int targetPosY) {
        return false;
    }

}
