package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ChessBoard {
    private final Cell[][] cells;

    public ChessBoard(Player whitePlayer, Player blackPlayer) {
        this.cells = new Cell[8][8];
        initializeCellsWithPieces(whitePlayer);
        initializeCellsWithPieces(blackPlayer);

    }

    void initializeCellsWithPieces(Player player){
        int firstRowIndex = player.getColor().equals(Color.BLACK) ? 0 : 7;

        Rook [] rooks = player.getRooks();

        cells[firstRowIndex][0] = new Cell(firstRowIndex,0,  rooks[0]);
        cells[firstRowIndex][7] = new Cell(firstRowIndex,7,  rooks[1]);


        Knight[] knights = player.getKnights();

        cells[firstRowIndex][1] = new Cell(firstRowIndex,1,  knights[0]);
        cells[firstRowIndex][6] = new Cell(firstRowIndex,6,  knights[1]);

        Bishop [] bishops = player.getBishops();

        cells[firstRowIndex][2] = new Cell(firstRowIndex,2,  bishops[0]);
        cells[firstRowIndex][5] = new Cell(firstRowIndex,5,  bishops[1]);

        cells[firstRowIndex][3] = new Cell(firstRowIndex,3, player.getQueen());
        cells[firstRowIndex][4] = new Cell(firstRowIndex,4, player.getKing());

        int secondRowIndex = player.getColor().equals(Color.BLACK) ? 1 : 6;

        Pawn[] pawns = player.getPawns();

        for(int i=0;i<pawns.length;i++){
            cells[secondRowIndex][i] = new Cell(firstRowIndex,i, pawns[i]);
        }

    }
    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(int x, int y) {
        if(x<=8 && x>=0 && y<=8 && y>=0) {
            return cells[x][y];
        }
        return null;
    }

    public void setCell(Cell cell){
        cells[cell.getX()][cell.getY()]=cell;
    }


    void addIfValid(HashSet<Cell> list, Cell cell, Piece piece){
        if(piece.isValidMove(cell)){
            list.add(cell);
        }
    }

    public HashSet<Cell> getAllPossibleMovesByPlayer(Player player) {
        Color color = player.getColor();
        HashSet<Cell> possibleMoveSet = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell cell = cells[i][j];
                //skip if cell already has same colored piece
                if (color.equals(cell.getActivePiece().getColor())) continue;
                addIfValid(possibleMoveSet, cell, player.getKing());
                addIfValid(possibleMoveSet, cell, player.getQueen());
                for (Rook rook : player.getRooks()) {
                    addIfValid(possibleMoveSet, cell, rook);
                }
                for (Knight knight : player.getKnights()) {
                    addIfValid(possibleMoveSet, cell, knight);
                }
                for (Bishop bishop : player.getBishops()) {
                    addIfValid(possibleMoveSet, cell, bishop);
                }
                for (Pawn pawn : player.getPawns()) {
                    addIfValid(possibleMoveSet, cell, pawn);
                }
            }
        }
        return possibleMoveSet;
    }


//    void move(int x, int y, int targetX, int targetY){
//      //  if(piece.isValidMove())
//        Cell cell = cells[x][y];
//        if(!cell.isOccupied()){
//            System.out.println("No piece found!");
//            return;
//        }
//            //dd
//        Piece piece = cell.getActivePiece();
//        boolean isPawnCapturable = isPawnCapturable(piece, targetX,targetY);
//
//        if(piece.isValidMove(targetX,targetY) || isPawnCapturable){
//            Cell targetCell = cells[x][y];
//            if(!targetCell.isOccupied() && !isPawnCapturable){ //&& isNotPawnDiagonal()
//                piece.setX(targetX);
//                piece.setY(targetY);
//
//                targetCell.setActivePiece(piece);
//                targetCell.setOccupied(true);
//
//                cell.setOccupied(false);
//                cell.setActivePiece(null);
//            }
//            else if(targetCell.isOccupied() && targetCell.getColor()!=piece.getColor() && canCapture(piece, targetX,targetY)){
//                piece.setX(targetX);
//                piece.setY(targetY);
//
//                //kill opposite
//                KilledPieces.add(targetCell.getActivePiece());
//
//                targetCell.setActivePiece(piece);
//                targetCell.setOccupied(true);
//
//                cell.setOccupied(false);
//                cell.setActivePiece(null);
//            } else {
//                System.out.println("Invalid move!");
//            }
//        } else{
//            System.out.println("Invalid move!");
//          //  return;
//        }
//
//    }
//
//    private boolean isPawnCapturable(Piece piece, int targetX, int targetY){
//        if (piece instanceof Pawn) {
//            boolean isWhite = piece.getColor().equals(Color.WHITE);
//            //white pawn can move upward diagonal to capture piece => (x-1,y-1) , (x-1,y+1)
//            //Black pawn can move downward diagonal to capture piece => (x+1,y-1) , (x+1,y+1)
//            if (isWhite && ((piece.x - 1 == targetX && piece.y - 1 == targetY)
//                    || (piece.x - 1 == targetX && piece.y + 1 == targetY))
//                    || !isWhite && ((piece.x + 1 == targetX && piece.y - 1 == targetY)
//                    || (piece.x + 1 == targetX && piece.y + 1 == targetY))
//            ) {
//                return true;
//            } else{
//                return false;
//            }
//        }
//        //Other type of piece can capture on their steps only pawn has unique way to capture on diagonal way.
//        return false;
//    }
//
//    boolean canCapture(Piece piece, int targetX, int targetY) {
//        if (piece instanceof Pawn) {
//            //white pawn can move upward diagonal to capture piece => (x-1,y-1) , (x-1,y+1)
//            //Black pawn can move downward diagonal to capture piece => (x+1,y-1) , (x+1,y+1)
//            if (piece.getColor().equals(Color.WHITE) && (piece.x - 1 == targetX && piece.y - 1 == targetY)
//                    || piece.getColor().equals(Color.WHITE) && (piece.x - 1 == targetX && piece.y + 1 == targetY)
//                    || piece.getColor().equals(Color.BLACK) && (piece.x + 1 == targetX && piece.y - 1 == targetY)
//                    || piece.getColor().equals(Color.BLACK) && (piece.x + 1 == targetX && piece.y + 1 == targetY)
//            ) {
//                return true;
//            }
//        }
//        //Other type of piece can capture on their steps only pawn has unique way to capture on diagonal way.
//        return true;
//    }
}
