package models;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    private Cell[][] board;
    private Player player1;
    private Player player2;

    public ChessBoard() {
        this.board = new Cell[8][8];
    }

    public Cell[][] getBoard() {
        return board;
    }

    private List<Piece> KilledPieces= new ArrayList<>();

    void move(int x, int y, int targetX, int targetY){
      //  if(piece.isValidMove())
        Cell cell = board[x][y];
        if(!cell.isOccupied()){
            System.out.println("No piece found!");
            return;
        }
            //dd
        Piece piece = cell.getActivePiece();
        if(piece.isValidMove(targetX,targetY)){
            Cell targetCell = board[x][y];
            if(!targetCell.isOccupied()){
                piece.setX(targetX);
                piece.setY(targetY);

                targetCell.setActivePiece(piece);
                targetCell.setOccupied(true);

                cell.setOccupied(false);
                cell.setActivePiece(null);
            }
            else if(targetCell.isOccupied() && targetCell.getColor()!=piece.getColor() && canCapture(piece, targetX,targetY)){
                piece.setX(targetX);
                piece.setY(targetY);

                //kill opposite
                KilledPieces.add(targetCell.getActivePiece());

                targetCell.setActivePiece(piece);
                targetCell.setOccupied(true);

                cell.setOccupied(false);
                cell.setActivePiece(null);
            } else {
                System.out.println("Invalid move!");
            }
        } else{
            System.out.println("Invalid move!");
          //  return;
        }

    }

    boolean canCapture(Piece piece, int targetX, int targetY) {
        if (piece instanceof Pawn) {
            //white pawn can move upward diagonal to capture piece => (x-1,y-1) , (x-1,y+1)
            //Black pawn can move downward diagonal to capture piece => (x+1,y-1) , (x+1,y+1)
            if (piece.getColor().equals(Color.WHITE) && (piece.x - 1 == targetX && piece.y - 1 == targetY)
                    || piece.getColor().equals(Color.WHITE) && (piece.x - 1 == targetX && piece.y + 1 == targetY)
                    || piece.getColor().equals(Color.BLACK) && (piece.x + 1 == targetX && piece.y - 1 == targetY)
                    || piece.getColor().equals(Color.BLACK) && (piece.x + 1 == targetX && piece.y + 1 == targetY)
            ) {
                return true;
            }
        }
        //Other type of piece can capture on their steps only pawn has unique way to capture on diagonal way.
        return true;
    }
}
