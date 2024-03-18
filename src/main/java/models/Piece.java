package models;

import java.util.Objects;

public abstract class Piece {

    protected Color color;
    protected int x;
    protected int y;
    protected String name;
    protected boolean isKilled;

    public Piece(Color color, String name) {
        this.color=color;
        this.name=name;
        this.isKilled=false;
    }

    public abstract boolean isValidMove(Cell targetCell, Cell[][] cells);


    public boolean canCapture(Cell targetCell){
        return targetCell==null || !targetCell.getActivePiece().getColor().equals(color);
    }

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

    public void setPosition(int x, int y){
        this.x=x;
        this.y=y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece piece)) return false;
        return x == piece.x && y == piece.y && color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, x, y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isKilled() {
        return isKilled;
    }

    public void setKilled(boolean killed) {
        isKilled = killed;
    }

}
