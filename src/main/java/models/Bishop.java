package models;

public class Bishop extends Piece{

    public Bishop(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Cell targetCell) {
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
        int targetX = targetCell.getX();
        int targetY = targetCell.getY();
        int tempX = x;
        int tempY = y;
        boolean isValidMove = false;
        //top right /
        while(tempX-1>=0 && tempY+1<8){
            System.out.println("1- tempX:"+(tempX-1)+" tempY:"+(tempY+1));
            if((targetX == tempX-1) && (targetY==tempY+1)){
                isValidMove=true;
                break;
            } else{
                tempX--;
                tempY++;
            }
        }

        tempX = x;
        tempY = y;
        //bottom left
        while(tempX+1<8 && tempY-1>=0 && !isValidMove){
            System.out.println("2-tempX:"+(tempX+1)+" tempY:"+(tempY-1));
            if((targetX == tempX+1) && (targetY==tempY-1)){
                isValidMove=true;
                break;
            } else{
                tempX++;
                tempY--;
            }
        }

        tempX = x;
        tempY = y;
        //bottom right
        while(tempX+1<8 && tempY+1<8 && !isValidMove){
            System.out.println("3-tempX:"+(tempX+1)+" tempY:"+(tempY+1));
            if((targetX == tempX+1) && (targetY==tempY+1)){
                isValidMove=true;
                break;
            } else{
                tempX++;
                tempY++;
            }
        }

        tempX = x;
        tempY = y;
        //top left
        while(tempX-1>=0 && tempY-1>=0 && !isValidMove){
            System.out.println("4-tempX:"+(tempX-1)+" tempY:"+(tempY-1));
            if((targetX == tempX-1) && (targetY==tempY-1)){
                isValidMove=true;
                break;
            } else{
                tempX--;
                tempY--;
            }
        }
        return isValidMove && (!targetCell.isOccupied() || canCapture(targetCell));
    }

    public static void main(String[] args) {
        Bishop b = new Bishop(Color.WHITE);
        b.setX(4);
        b.setY(3);
        b.isValidMove(new Cell(77,77, new Bishop(Color.WHITE)));
    }

}
