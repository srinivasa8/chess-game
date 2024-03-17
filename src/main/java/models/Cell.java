package models;

import java.util.Objects;

public class Cell {

    private int x;
    private int y;
    private Color color;

    private boolean occupied;

    private Piece activePiece;

    public Cell(int x, int y, Piece activePiece) {
        this.x = x;
        this.y = y;
        this.activePiece = activePiece;
        this.occupied = true;
        activePiece.setPosition(x, y);
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.occupied = false;
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

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Piece getActivePiece() {
        return activePiece;
    }

    public void setActivePiece(Piece activePiece) {
        this.activePiece = activePiece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell cell)) return false;
        return x == cell.x && y == cell.y && occupied == cell.occupied && color == cell.color
                && Objects.equals(activePiece, cell.activePiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, color, occupied, activePiece);
    }
}
