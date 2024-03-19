package models;

public class Queen extends Piece{

    public Queen(Color color) {
        super(color, "Q");
    }

    @Override
    public boolean isValidMove(Cell targetCell, Cell[][] cells) {

        int targetX = targetCell.getX();
        int targetY = targetCell.getY();

        if(!withinEdges(targetX,targetY) || (x==targetX && y==targetY)) return false;

        boolean isValidMove = false;

        //Before element in x
        for(int i = y-1; i>=0; i--) {
            if(cells[x][i].isOccupied()){
                if(cells[x][i].getActivePiece().getColor().equals(color)){
                    isValidMove =false;
                } else{
                    isValidMove = (targetX == x && targetY == i);
                }
                break;
            } else{
                isValidMove = (targetX == x && targetY == i);
                if(isValidMove) break;
            }
        }

        if(!isValidMove) {
            //After element in x
            for (int i = y + 1; i < 8; i++) {
                if(cells[x][i].isOccupied()){
                    if(cells[x][i].getActivePiece().getColor().equals(color)){
                        isValidMove =false;
                    } else{
                        isValidMove = (targetX == x && targetY == i);
                    }
                    break;
                } else{
                    isValidMove = (targetX == x && targetY == i);
                    if(isValidMove) break;
                }
            }
        }

        if(!isValidMove) {
            //Above element in y
            for (int i = x - 1; i >=0; i--) {
                if(cells[i][y].isOccupied()){
                    if(cells[i][y].getActivePiece().getColor().equals(color)){
                        isValidMove =false;
                    } else{
                        isValidMove = (targetX == i && targetY == y);
                    }
                    break;
                } else{
                    isValidMove = (targetX == i && targetY == y);
                    if(isValidMove) break;
                }
            }
        }
        if(!isValidMove) {
                //Below element in y
                for (int i = x + 1; i < 8; i++) {
                    if(cells[i][y].isOccupied()){
                        if(cells[i][y].getActivePiece().getColor().equals(color)){
                            isValidMove =false;
                        } else{
                            isValidMove = (targetX == i && targetY == y);
                        }
                        break;
                    } else{
                        isValidMove = (targetX == i && targetY == y);
                        if(isValidMove) break;
                    }
               }
        }
        if(!isValidMove) {
            isValidMove = isDiagonallyValidMove(targetX, targetY, isValidMove,cells);

        }
        return isValidMove && (!targetCell.isOccupied() || canCapture(targetCell));
    }

    private boolean hasBlocker(Cell targetCell){
        return targetCell.isOccupied() && targetCell.getActivePiece().getColor().equals(color);
    }

    private boolean isDiagonallyValidMove(int targetX, int targetY, boolean isValidMove, Cell[][] cells) {
        int tempX = x;
        int tempY = y;

        //top right /
        while (tempX - 1 >= 0 && tempY + 1 < 8) {
            if(hasBlocker(cells[tempX-1][tempY+1])) break;
            if ((targetX == tempX - 1) && (targetY == tempY + 1)) {
                isValidMove =true;
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
        return isValidMove;
    }

}
