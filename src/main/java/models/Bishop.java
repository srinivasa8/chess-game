package models;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color,"B");
    }

    @Override
    public boolean isValidMove(Cell targetCell, Cell[][] cells) {
        int targetX = targetCell.getX();
        int targetY = targetCell.getY();
        int tempX = x;
        int tempY = y;
        boolean isValidMove = false;

        //top right /
        while (tempX - 1 >= 0 && tempY + 1 < 8) {
            if(hasBlocker(cells[tempX-1][tempY+1])) break;
            if ((targetX == tempX - 1) && (targetY == tempY + 1)) {
                isValidMove = true;
                break;
            } else {
                tempX--;
                tempY++;
            }
        }

        tempX = x;
        tempY = y;
        //bottom left
        while (tempX + 1 < 8 && tempY - 1 >= 0 && !isValidMove) {
            if(hasBlocker(cells[tempX+1][tempY-1])) break;
            if ((targetX == tempX + 1) && (targetY == tempY - 1)) {
                isValidMove = true;
                break;
            } else {
                tempX++;
                tempY--;
            }
        }

        tempX = x;
        tempY = y;
        //bottom right
        while (tempX + 1 < 8 && tempY + 1 < 8 && !isValidMove) {
            if(hasBlocker(cells[tempX+1][tempY+1])) break;
            if ((targetX == tempX + 1) && (targetY == tempY + 1)) {
                isValidMove = true;
                break;
            } else {
                tempX++;
                tempY++;
            }
        }

        tempX = x;
        tempY = y;
        //top left
        while (tempX - 1 >= 0 && tempY - 1 >= 0 && !isValidMove) {
            if(hasBlocker(cells[tempX-1][tempY-1])) break;
            if ((targetX == tempX - 1) && (targetY == tempY - 1)) {
                isValidMove = true;
                break;
            } else {
                tempX--;
                tempY--;
            }
        }
        return isValidMove && (!targetCell.isOccupied() || canCapture(targetCell));
    }

    private boolean hasBlocker(Cell targetCell){
        return targetCell.isOccupied() && targetCell.getActivePiece().getColor().equals(color);
    }

}
