package models;

public class Pawn extends Piece{

    private boolean isFirst; // first time pan can take two upward steps otherwise single step only.

    public Pawn(int x, int y, Color color) {
        super(x, y, color);
        this.isFirst=true;
    }

    @Override
    public boolean isValidMove(int targetX, int targetY) {

        if (!withinEdges(targetX, targetY)) return false;

        boolean isWhite = color.equals(Color.WHITE);

        if (isWhite && ((isFirst && (x + 2 == targetX && y == targetY))
                || (x - 1 == targetX && y - 1 == targetX)
                || (x - 1 == targetX && y + 1 == targetY))) {
            return true;
        } else if (!isWhite && ((isFirst && (x - 2 == targetX && y == targetY))
                || (x + 1 == targetX && y - 1 == targetY)
                || (x + 1 == targetX && y + 1 == targetX))) {
            return true;
        }

        if (color.equals(Color.WHITE) && (x - 1 == targetX && y - 1 == targetX)
                || color.equals(Color.WHITE) && (x - 1 == targetX && y + 1 == targetY)
                || color.equals(Color.BLACK) && (x + 1 == targetX && y - 1 == targetY)
                || color.equals(Color.BLACK) && (x + 1 == targetX && y + 1 == targetX)
        ) {
            return true;
        }
        isFirst = false;
        return false;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

}
