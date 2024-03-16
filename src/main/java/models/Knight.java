package models;

public class Knight extends Piece{
    public Knight(Color color) {
        super(color);
    }

    /*
     00 01 02 03 04 05 06 07
     10 11 12 13 14 15 16 17
     20 21 22 23 24 25 26 27
     30 31 32 33 34 35 36 37
     40 41 42 43 44 45 46 47
     50 51 52 53 54 55 56 57
     60 61 62 63 64 65 66 67 | 68
     70 71 72 73 74 75 76 77 | 78 79

     P  P  P  P  P  P  P  P
     R  H  B  Q  K  B  H  R

     Eg. knight @76
      64 55 57  | invalid : 68 84

                  23------
                    |

      Kinght @23
      2    4
      11   15
      31   35
      42 44

     */
    Cell[][] cells = new Cell[8][8];
    @Override
    public boolean isValidMove(Cell targetCell) {
        int targetX = targetCell.getX();
        int targetY = targetCell.getY();
        if (!withinEdges(targetX, targetY)) return false;
        //x,y -> current position of the knight

        // (x-2,y-1), (x-2,y+1) // two steps above and a step on right or left
        // (x+2,y-1), (x+2,y+1) // two steps down and a step on right or left

        // (x-1,y-2), (x-1,y+2) // one step above and two steps on right or left
        // (x+1,y-2), (x+1,y+2) // one step down and two steps on right or left

        int[] row = {-2,-2,2,2,-1,-1,1,1};
        int[] col = {-1,1,-1,1,-2,2,-2,2 };

        if(x==targetCell.getX() && y== targetCell.getY()) return false;
        for(int i=0;i<8;i++) {
            if (row[i] == targetX && col[i] == targetY) {
                 if (!targetCell.isOccupied() || canCapture(targetCell)) {
                    return true;
                 }
            }
        }

        return false;
    }

}
