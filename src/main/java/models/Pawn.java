package models;

public class Pawn extends Piece {

    private boolean isFirst; // first time pan can take two upward steps otherwise single step only.

    public Pawn(Color color) {
        super(color);
        this.isFirst = true;
    }

    @Override
    public boolean isValidMove(Cell targetCell) {
        int targetX = targetCell.getX();
        int targetY = targetCell.getY();
        //outside the dges or can't be same cell
        if (!withinEdges(targetX, targetY) || (x == targetX && y == targetY)) return false;

        boolean isWhite = color.equals(Color.WHITE);
        boolean isValidMove = false;

        //For the column direction
        if (isWhite && ((isFirst && (x - 2 == targetCell.getX() && y == targetCell.getY()))
                || (x - 1 == targetCell.getX() && y == targetCell.getY()))) {
            isValidMove = true;
        } else if (!isWhite && ((isFirst && (x + 2 == targetCell.getX() && y == targetCell.getY()))
                || (x - 1 == targetCell.getX() && y == targetCell.getY()))) {
            isValidMove = true;
        }

        //For the diagonal direction, since pawn can capture
        if (targetCell.isOccupied() && canCapture(targetCell)) {
            isValidMove = true;
        }
        if (isValidMove) {
            isFirst = false;
        }
        return isValidMove;
    }


    @Override
    public boolean canCapture(Cell targetCell) {

        boolean isWhite = getColor().equals(Color.WHITE);
        //white pawn can move upward diagonal to capture piece => (x-1,y-1) , (x-1,y+1)
        //Black pawn can move downward diagonal to capture piece => (x+1,y-1) , (x+1,y+1)
        boolean canCapture = false;

        if (isWhite && ((x - 1 == targetCell.getX() && y - 1 == targetCell.getY())
                || (x - 1 == targetCell.getX() && y + 1 == targetCell.getY()))
                || !isWhite && ((x + 1 == targetCell.getX() && y - 1 == targetCell.getY())
                || (x + 1 == targetCell.getX() && y + 1 == targetCell.getY()))) {
            canCapture = true;
        }
        return super.canCapture(targetCell) && targetCell.isOccupied() && canCapture;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

}
