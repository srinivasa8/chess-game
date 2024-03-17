package models;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color, "K");
    }

    @Override
    public boolean isValidMove(Cell targetCell, Cell[][] cells) {
        int targetX = targetCell.getX();
        int targetY = targetCell.getY();
        if (!withinEdges(targetX, targetY)) return false;
        //x,y -> current position of the knight

        // (x-2,y-1), (x-2,y+1) // two steps above and a step on right or left
        // (x+2,y-1), (x+2,y+1) // two steps down and a step on right or left

        // (x-1,y-2), (x-1,y+2) // one step above and two steps on right or left
        // (x+1,y-2), (x+1,y+2) // one step down and two steps on right or left

        int[] row = {-2, -2, 2, 2, -1, -1, 1, 1};
        int[] col = {-1, 1, -1, 1, -2, 2, -2, 2};

        if (x == targetX && y == targetY) return false;
        for (int i = 0; i < 8; i++) {
           // System.out.println("x:"+targetX+" y:"+targetY);
            //5 2
            //5-2 =3,2-1=1=> 3,1
            //3,3. 7,1. 7,3. 4 0. 4 4. 6,0. 6 4
            if (x+row[i] == targetX && y+col[i] == targetY) {
                if (!targetCell.isOccupied() || canCapture(targetCell)) {
                    return true;
                }
            }
        }
        return false;
    }

}
