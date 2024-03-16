package models;

public class Rook extends Piece{

    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Cell targetCell) {
        System.out.println("targetX+-targetY");
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
    */
         int targetX = targetCell.getX();
         int targetY = targetCell.getY();
         for(int i=0;i<8;i++) {
             System.out.println("pair1: "+x+""+i+" pair2: "+i+""+y);
             if (targetX==x && targetY==i || targetX==i && targetY==y) {
                  System.out.println(targetX+"-"+targetY);
                  if(!targetCell.isOccupied() || canCapture(targetCell)) return true;
             }
         }
        return false;
    }

    public static void main(String[] args) {
        Rook r = new Rook(Color.WHITE);
        r.x=6;
        r.y=1;
        r.isValidMove(new Cell(5,5,new Rook(Color.WHITE)));
    }

}
