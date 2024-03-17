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
        //
        //Before element in x
        for (int i = x - 1; i >= 0; i--) {
            System.out.println("nx:" + x + " ny:" + y);
            if (hasBlocker(cells[x][i])) break;
            if ((targetX == x && targetY == i)) {
                isValidMove = true;
                break;
            }
        }
        if (!isValidMove) {
            //After element in x
            for (int i = x - 1; i >= 0; i--) {
                System.out.println("nx:" + x + " ny:" + y);
                if (hasBlocker(cells[i][y])) break;
                if ((targetX == i && targetY == y)) {
                    isValidMove = true;
                    break;
                }
            }
        }

        if (!isValidMove) {
            //Above element in y
            System.out.println("nx:" + x + " ny:" + y);
            for (int i = x + 1; i < 8; i++) {
                System.out.println("nx:" + x + " ny:" + y);
                if (hasBlocker(cells[x][i])) break;
                if ((targetX == x && targetY == i)) {
                    //if(hasBlocker(cells[x][i])) break;
                    isValidMove = true;
                    break;
                }
            }
        }
        if (!isValidMove) {
            //Below element in y
            for (int i = x + 1; i < 8; i++) {
                System.out.println("nx:" + x + " ny:" + y);
                if (hasBlocker(cells[i][y])) break;
                if (targetX == i && targetY == y) {
                    //if(hasBlocker(cells[i][y])) break;
                    isValidMove = true;
                    break;
                }
            }
        }
        return isValidMove && (!targetCell.isOccupied() || canCapture(targetCell));
    }

    private boolean hasBlocker(Cell targetCell){
        return targetCell.isOccupied() && targetCell.getActivePiece().getColor().equals(color);
    }

}
