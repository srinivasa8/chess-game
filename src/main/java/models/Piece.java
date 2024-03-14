package models;

public abstract class Piece {
    protected Color color;
    protected int x;
    protected int y;

    public Piece(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color=color;
    }

    public abstract boolean isValidMove(int startPosX, int startPosY, int endPosX, int endPosY);

}
