package models;

public class Queen extends Piece{

    public Queen(Color color) {
        super(color, "Q");
    }

    @Override
    public boolean isValidMove(Cell targetCell) {

        int targetX = targetCell.getX();
        int targetY = targetCell.getY();

        if(!withinEdges(targetX,targetY) || (x==targetX && y==targetY)) return false;

        boolean isValidMove = false;
        /*
                 00 01 02 03 04 05 06 07
                 10 11 12 13 14 15 16 17
                 20 21 22 23 24 25 26 27
                 30 31 32 33 34 35 36 37
                 40 41 42 43 44 45 46 47
                 50 51 52 53 54 55 56 57
                 60 61 62 63 64 65 66 67 | 68
                 70 71 72 73 74 75 76 77 | 78 79
                 (x-1,y-1), (x-1,y+1)
                 P  P  P  P  P  P  P  P
                 R  H  B  Q  K  B  H  R
         */
        // 0 1
        // 0 8
        //4 4
        //0 4
        Cell[][] cells =null;
        for(int i = x-1; i>=0; i--) {
            System.out.println("nx:"+x+" ny:"+y);
            if(hasBlocker(cells[x][i])|| hasBlocker(cells[i][y])) return false;
            if ((targetX==x && targetY==i) || (targetX==i && targetY==y)) {
                isValidMove = true;
                break;
            }
        }

        for(int i = x+1; i<8; i++) {
            System.out.println("nx:"+x+" ny:"+y);
            if(hasBlocker(targetCell)) return false;
            if ((targetX==x && targetY==i) || (targetX==i && targetY==y)) {
                isValidMove = true;
                break;
            }
        }

//        for(int i=x;i<8-x;i++) {
//            if(hasBlocker(targetCell)) return false;
//            if ((targetX==x && targetY==i) || (targetX==i && targetY==y)) {
//                    isValidMove =true;
//                    break;
//            }
//        }
//        int tempX = x;
//        int tempY = y;
//
//        //top right /
//        //if(x!==targetX)
//        boolean hasXBlocker = false;
//        while (x==targetX && tempY + 1 < 8) {
//            if (targetY == tempY + 1) {
//                isValidMove = true;
//                if(hasBlocker(targetCell))
//                break;
//            } else {
//                tempX--;
//                tempY++;
//            }
//        }

        if(!isValidMove) {
            isValidMove = isDiagonallyValidMove(targetCell, targetX, targetY, isValidMove);

        }
        return isValidMove && (!targetCell.isOccupied() || canCapture(targetCell));
    }

    boolean hasBlocker(Cell targetCell){
        return targetCell.isOccupied() && targetCell.getActivePiece().getColor().equals(color);
    }
    private boolean isDiagonallyValidMove(Cell targetCell, int targetX, int targetY, boolean isValidMove) {
        int tempX = x;
        int tempY = y;

        //top right /
        while (tempX - 1 >= 0 && tempY + 1 < 8) {
            System.out.println("tempX:"+(tempX-1)+" tempY:"+(tempY+1));
            if(hasBlocker(targetCell)) return false;
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
            System.out.println("tempX:"+(tempX-1)+" tempY:"+(tempY-1));
            if(hasBlocker(targetCell)) return false;
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
            System.out.println("tempX:"+(tempX+1)+" tempY:"+(tempY+1));
            if(hasBlocker(targetCell)) return false;
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
            System.out.println("tempX:"+(tempX-1)+" tempY:"+(tempY-1));
            if(hasBlocker(targetCell)) return false;
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
