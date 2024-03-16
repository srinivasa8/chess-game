package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class King extends Piece{

    public King(Color color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Cell targetCell) {
        int targetX = targetCell.getX();
        int targetY = targetCell.getY();

        //0,0. 00. 01
        //10 11 12
        //20 20 22
        for(int i=-1;i<1;i++){
            for(int j=-1;j<1;j++) {
                //target values are verified before this, so no need to verify whether they are within edge or not
                if (x + i == targetX && y + j == targetY) {
                    if (!targetCell.isOccupied() || canCapture(targetCell)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public HashSet<Cell> getPossibleMoves(Cell [][] cells){
        HashSet<Cell> possibleMoves = new HashSet<>();
        //00 01 03
        //10 11 12
        //20 21 22

        //0,0. 00. 01
        //10 11 12
        //20 20 22
        for(int i=-1;i<1;i++){
            for(int j=-1;j<1;j++) {
                //target values are verified before this, so no need to verify whether they are within edge or not
                int newX= x + i;
                int newY= y + j;
                if (withinEdges(newX,newY) && cells[newX][newY].isOccupied() || canCapture(cells[newX][newY])) {
                    possibleMoves.add(cells[newX][newY]);
                }
            }
        }
        return possibleMoves;
    }

}
