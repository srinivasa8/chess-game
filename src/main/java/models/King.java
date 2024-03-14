package models;

public class King extends Piece{

    public King(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int targetPosX, int targetPosY) {
        return false;
    }

}
