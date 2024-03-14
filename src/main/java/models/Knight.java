package models;

public class Knight extends Piece{
    public Knight(int x, int y, Color color) {
        super(x, y, color);
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
    public boolean isValidMove(int startPosX, int startPosY, int endPosX, int endPosY) {
        //x,y -> my current position

        // (x-2,y-1),  (x-2, y+1);
        // (x+2, y-1), (x+2, y+1)

        //(x-1,y-2), (x-1,y+2)
        //(x+1,y-2), (x+1,y+2)

        //======================================
        //{-2,-2,-2,-2, 1, 0, 1, 2, 2}//-2,-1,2
        //{-1, 1,-2, 2,-2, 0, 2, -2,1}//-1,1,-2,2-2,

        //(x-2,y-1),
        //(x-2,y+1);
        //(x-1,y-2),
        //(x-1,y+2)
        //(x+1,y-2),
        //(x,y),
        //(x+1,y+2)
        //(x+2, y-1),
        //(x+2, y+1)

        //x-1, x-2,x+1,x+2
        //y-2, y-1, y-2, y+1
//        0,0, (x-1,y+1), (x-2,y+2)
//        0,0, (x+1,y-1), (x+2,y-2)

        int[] row = {-2, -2, -1, -1, 1, 1, 2, 2};//-2,-1,2
        int[] col = {-1, 1, -2, 2, -2, 2, -1, 1};//-1,1,-2,2-2,

        if(x==endPosX && y==endPosY) return false;
        for(int i=0;i<8;i++) {
            if (row[i] == endPosX && col[i] == endPosY && isWithinEdge(row[i], col[i])) {
                if (!cells[row[i]][col[i]].isOccupied() || cells[row[i]][col[i]].getActivePiece().color != color) {
                    return true;
                }
            }
        }
//
//        if(x-2==endPosX && y+1==endPosY && isWithinEdge(x-2,y+1)){
//            if(!cells[x-2][y+1].isOccupied() || cells[x-2][y+1].getActivePiece().color!=color){
//                return true;
//            }
//        }
//
//        if(x-2==endPosX && y+1==endPosY && isWithinEdge(x-2,y+1)){
//            if(!cells[x-2][y+1].isOccupied() || cells[x-2][y+1].getActivePiece().color!=color){
//                return true;
//            }
//        }

        return false;
    }

    private boolean isWithinEdge(int posX, int posY){
        if(posX>=0 && posX<8 && posY>=0 && posY<8) return true;
        return false;
    }
}
