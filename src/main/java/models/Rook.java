package models;

public class Rook extends Piece{

    public Rook(Color color) {
        super(color, "R");
    }

    @Override
    public boolean isValidMove(Cell targetCell, Cell[][] cells) {

        int targetX = targetCell.getX();
        int targetY = targetCell.getY();

        boolean isValidMove = false;
        if (!withinEdges(targetX, targetY) || (x == targetX && y == targetY)) return false;
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
        return isValidMove && (!targetCell.isOccupied() || canCapture(targetCell));
    }

}
