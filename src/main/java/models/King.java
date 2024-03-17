package models;

import java.util.HashSet;

public class King extends Piece {

    public King(Color color) {
        super(color, "Kg");
    }

    @Override
    public boolean isValidMove(Cell targetCell,  Cell[][] cells) {
        int targetX = targetCell.getX();
        int targetY = targetCell.getY();
        if (!withinEdges(targetX, targetY) || (x == targetY && y == targetY)) return false;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if(i==0 && j==0) continue;
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

    public HashSet<Cell> getPossibleMoves(Cell[][] cells) {
        HashSet<Cell> possibleMoves = new HashSet<>();
        for (int i = -1; i < 1; i++) {
            for (int j = -1; j < 1; j++) {
                if(i==0 && j==0) continue;
                int newX = x + i;
                int newY = y + j;
                if (withinEdges(newX, newY) && (!cells[newX][newY].isOccupied() || canCapture(cells[newX][newY]))) {
                    possibleMoves.add(cells[newX][newY]);//note
                }
            }
        }
        return possibleMoves;
    }

}
