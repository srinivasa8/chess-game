package models;

public class Queen extends Piece{

    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Cell targetCell) {

        int targetX = targetCell.getX();
        int targetY = targetCell.getY();

        if(!withinEdges(targetX,targetY) || (x==targetX && y==targetY)) return false;

        boolean isValidMove = false;

        for(int i=0;i<8;i++) {
            if ((targetX==x && targetY==i) || (targetX==i && targetY==y)) {
                    isValidMove =true;
                    break;
            }
        }
        if(!isValidMove) {
            isValidMove = isDiagonallyValidMove(targetX, targetY, isValidMove);

        }
        return isValidMove && (!targetCell.isOccupied() || canCapture(targetCell));
    }

    private boolean isDiagonallyValidMove(int targetX, int targetY, boolean isValidMove) {
        int tempX = x;
        int tempY = y;

        //top right /
        while (tempX - 1 >= 0 && tempY + 1 < 8) {
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
            if ((targetX == tempX - 1) && (targetY == tempY - 1)) {
                isValidMove = true;
                break;
            } else {
                tempX--;
                tempY--;
            }
        }
        return isValidMove;
    }

}
