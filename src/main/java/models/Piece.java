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

    public abstract boolean isValidMove(int targetPosX, int targetPosY);

   // public abstract boolean canCapture(int targetPosX, int targetPosY);

    public boolean withinEdges(int targetX, int targetY){
        if(targetX>=0 && targetX<8 && targetY>=0 && targetY<8) return true;
        return false;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
