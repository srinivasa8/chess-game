package models;

public class Rook extends Piece{

    public Rook(Color color) {
        super(color, "R");
    }

    @Override
    public boolean isValidMove(Cell targetCell) {

         int targetX = targetCell.getX();
         int targetY = targetCell.getY();

         if(!withinEdges(targetX,targetY) || (x==targetX&&y==targetY)) return false;

         for(int i=0;i<8;i++) {
             if ((targetX==x && targetY==i) || (targetX==i && targetY==y)) {
                  if(!targetCell.isOccupied() || canCapture(targetCell)) return true;
             }
         }
        return false;
    }

}
