package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Game {

    private ChessBoard chessBoard;
    private Player whitePlayer;

    private Player blackPlayer;

    private boolean isWhitePlayerTurn;

    private GameStatus gameStatus;

    private Queue<Player> q = new LinkedList<>();

    public Game(ChessBoard chessBoard, Player whitePlayer, Player blackPlayer, boolean isWhitePlayerTurn, GameStatus gameStatus) {
        this.chessBoard = chessBoard;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.isWhitePlayerTurn = isWhitePlayerTurn;
        this.gameStatus = gameStatus;
        this.q = new LinkedList<>();
    }

    private final List<Piece> KilledPieces = new ArrayList<>();

    private MoveInput takeInput(){
        //while(true)
        Scanner sc = new Scanner(System.in);
        String[] commands;
        boolean isValidInput = false;
        while(true) {
            System.out.println(" Enter the input in the form of {sourceX} {sourceY} {targetX} {targetY} or {RESIGN}");
            String input = sc.nextLine();
            if (input.contains(GameStatus.RESIGN.toString())) {
                return new MoveInput(true);
            }
             commands = input.split(" ");
            if(commands.length == 4 ) {
                isValidInput = true;
                break;
            } else{
               System.out.println(" Please enter valid input again: ");
            }
        }
        int sourceX = Integer.parseInt(commands[0]);
        int sourceY = Integer.parseInt(commands[1]);
        int targetX = Integer.parseInt(commands[2]);
        int targetY = Integer.parseInt(commands[3]);
        return new MoveInput(sourceX,sourceY,targetX,targetY);
    }

    //take input
    //if user resigned - break out and announce Other user as winner
    //makeMove
    //element @src belong to same user ? if not throw not valid piece or your piece
    //isInvalid?
    public boolean move(int x, int y, int targetX, int targetY, Player currentPlayer, Player oppositePlayer ){

        if(!withinEdges(targetX, targetY)){
            System.out.println("[ERROR]: No piece found!");
            return false;
        }

        Cell cell = chessBoard.getCell(x,y);//[x][y];

        if(cell==null || !cell.isOccupied()){
            System.out.println("[ERROR]: No piece found!");
            return false;
        }

        Piece piece = cell.getActivePiece();
        if(!piece.getColor().equals(currentPlayer.getColor())){
            System.out.println("[ERROR]: You can move only your colored piece!");
            return false;
        }
//        Player currentPlayer= whitePlayer;
//        Player oppositePlayer= blackPlayer;
//        if(!isWhitePlayerTurn){
//            currentPlayer=blackPlayer;
//            oppositePlayer=whitePlayer;
//        }
        Cell targetCell = chessBoard.getCell(targetX,targetY);
        if(targetCell==null) return false;
        Cell currentKingCell = chessBoard.getCell(currentPlayer.getKing().getX(), currentPlayer.getKing().getY());
        Cell[][] cells = chessBoard.getCells();
        if(piece.isValidMove(targetCell,cells)){//if diagonal empty for pawn, it won't be true;
            System.out.println("[ENTERED]: Valid move + not check");
            if(targetCell.isOccupied() && piece.canCapture(targetCell)){
                piece.setX(targetX);
                piece.setY(targetY);

                Piece prevPieceAtTargetCell = targetCell.getActivePiece();
                //kill opposite
                KilledPieces.add(prevPieceAtTargetCell);

                targetCell.setActivePiece(piece);
                targetCell.setOccupied(true);

                cell.setOccupied(false);
                cell.setActivePiece(null);
                Cell kingCell  = piece instanceof King ? targetCell : currentKingCell;
                if(isCheck(oppositePlayer, kingCell)){
                    System.out.println("[ERROR]: Invalid move as it can cause check mate!");
                    rollBackMove(piece,cell,targetCell,prevPieceAtTargetCell);
                    return false;
                } else{
                    //Assigning capture piece with invalid positions as its moved out of chess board.
                    //prevPieceAtTargetCell.setX(-100);
                    //prevPieceAtTargetCell.setY(-100);
                    prevPieceAtTargetCell.setKilled(true);
                }
            }
            else if(!targetCell.isOccupied()){
                System.out.println("[ADDING]: @"+targetX+" @"+targetY);
                piece.setX(targetX);
                piece.setY(targetY);

                targetCell.setActivePiece(piece);
                targetCell.setOccupied(true);

                cell.setOccupied(false);
                cell.setActivePiece(null);
                Cell kingCell  = piece instanceof King ? targetCell : currentKingCell;
                if(isCheck(oppositePlayer, kingCell)){
                    System.out.println("[ERROR]: Invalid move as it can cause check mate!");
                    rollBackMove(piece,cell,targetCell,null);
                    return false;
                }
            }
            else {
                System.out.println("[ERROR]: Invalid move!");
                return false;
            }
            //need to add logic for safe move , need to do like move, check if threatful, then undo move
        } else{
            System.out.println("[ERROR2]:Invalid move......!");
             return false;
        }
        return true;

    }

//    private boolean isPawnDiagonal(Piece piece, int targetX, int targetY){
//        if (piece instanceof Pawn) {
//           return piece.canCapture(targetX, targetY);
//        }//pawn can only move in forward steps but can capture on diagonal but
//        //Other type of piece can capture on their steps only pawn has unique way to capture on diagonal way.
//        return false;
//    }

private void rollBackMove(Piece piece, Cell currentCell, Cell targetCell, Piece prevPieceAtTarget){
        //rollBack
        if(prevPieceAtTarget!=null){
            targetCell.setActivePiece(prevPieceAtTarget);
            targetCell.setOccupied(true);
        } else {
            targetCell.setOccupied(false);
        }
        piece.setX(currentCell.getX());
        piece.setY(currentCell.getY());
        currentCell.setActivePiece(piece);
        currentCell.setOccupied(true);
}

    private boolean withinEdges(int targetX, int targetY){
        return targetX >= 0 && targetX < 8 && targetY >= 0 && targetY < 8;
    }


    //opposite player, current king
    boolean isCheck(Player oppositePlayer, Cell currentKingCell){

        //Only queen, rook, bishop, knight, other king can attack a king
        Cell[][] cells = chessBoard.getCells();
        if(oppositePlayer.getKing().isValidMove(currentKingCell,cells)){
            System.out.println("Other king threat! x: "+oppositePlayer.getKing().getX()+" y: "+oppositePlayer.getKing().getY());
            return true;
        }
        if(!oppositePlayer.getQueen().isKilled() && oppositePlayer.getQueen().isValidMove(currentKingCell,cells)) {
            System.out.println("Other queen threat! x: "+oppositePlayer.getQueen().getX()+" y: "+oppositePlayer.getQueen().getY());
           return true;
        }
        Rook [] rooks = oppositePlayer.getRooks();

        for(Rook rook : rooks){
            if(!rook.isKilled() && rook.isValidMove(currentKingCell,cells)) {
                System.out.println("Other rook threat! x: "+rook.getX()+" y: "+rook.getY());
                return true;
            }
        }

        Knight[] knights = oppositePlayer.getKnights();
        for(Knight knight : knights){
            if(!knight.isKilled() && knight.isValidMove(currentKingCell,cells)) {
                System.out.println("Other knight threat! x: "+knight.getX()+" y: "+knight.getY());
                return true;
            }
        }

        Bishop[] bishops = oppositePlayer.getBishops();

        for(Bishop bishop : bishops){
            if(!bishop.isKilled() && bishop.isValidMove(currentKingCell,cells)) {
                System.out.println("Other bishop threat! x: "+bishop.getX()+" y: "+bishop.getY());
                return true;
            }
        }

        Pawn[] pawns = oppositePlayer.getPawns();
        for(Pawn pawn : pawns){
            if(!pawn.isKilled() && pawn.isValidMove(currentKingCell,cells)) {
                System.out.println("Other pawn threat! x: "+pawn.getX()+" y: "+pawn.getY());
                return true;
            }
        }
        return false;
    }

    boolean isCheckMate2(Player currentPlayer, Player oppositePlayer) {
        King king = oppositePlayer.getKing();
        Cell kingCell = chessBoard.getCell(king.getX(), king.getY());
        if (isCheck(currentPlayer, kingCell)) {
            HashSet<Cell> possibleMoves = king.getPossibleMoves(chessBoard.getCells());
            System.out.println("set: " + possibleMoves);
            for (Cell cell : possibleMoves) {
                if (!isCheck(currentPlayer, cell)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isCheckMate(Player currentPlayer, Player oppositePlayer) {
        King king = oppositePlayer.getKing();
        Cell kingCell = chessBoard.getCell(king.getX(), king.getY());
        if (isCheck(currentPlayer, kingCell)) {
            HashSet<Cell> possibleMoves = king.getPossibleMoves(chessBoard.getCells());
            System.out.println("set: " + possibleMoves);
            for (Cell cell : possibleMoves) {
                if (!isCheck(currentPlayer, cell)) {
                    return false;
                }
            }
            return !isCheckMateEliminatedWithOtherPieceMoves(currentPlayer,oppositePlayer,kingCell);
        }
        return false;
    }

    boolean isCheckMateEliminatedWithOtherPieceMoves(Player currentPlayer, Player oppositePlayer, Cell kingCell) {
        //King king = oppositePlayer.getKing();
        HashMap<Cell, List<Cell>> allPossibleMovesByPlayer = chessBoard.getAllPossibleMovesByPlayer(oppositePlayer);
        //getAllPossibleMovesByPlayer
        //Cell kingCell = chessBoard.getCell(king.getX(), king.getY());
        for(Map.Entry<Cell, List<Cell>> entry : allPossibleMovesByPlayer.entrySet()){
            Cell currentCell = entry.getKey();
            Piece piece = entry.getKey().getActivePiece();
            for(Cell targetCell : entry.getValue()) {
                Piece prevPieceAtTarget = null;
                if(targetCell.isOccupied()){
                    prevPieceAtTarget = targetCell.getActivePiece();
                }
                testPrint("BEFORE DUMMY MOVE");
                move(currentCell.getX(),currentCell.getY(), targetCell.getX(), targetCell.getY(), currentPlayer,oppositePlayer);
                testPrint("AFTER DUMMY MOVE");
                if(!isCheck(oppositePlayer,kingCell)){
                    System.out.println("Checkmate was possible but can be eliminated by moving piece from x: "+piece.getX()+", " +
                            "y: "+piece.getX()+" to tx:"+targetCell.getX()+" ty:"+targetCell.getY());
                    rollBackMove(piece,currentCell,targetCell,prevPieceAtTarget);
                    testPrint("AFTER DUMMY ROLLBACK");
                    return true;
                }
                rollBackMove(piece,currentCell,targetCell,prevPieceAtTarget);
                testPrint("AFTER DUMMY ROLLBACK");
            }
        }

//        if (isCheck(currentPlayer, kingCell)) {
//            HashSet<Cell> possibleMoves = king.getPossibleMoves(chessBoard.getCells());
//            System.out.println("set: " + possibleMoves);
//            for (Cell cell : possibleMoves) {
//                if (!isCheck(currentPlayer, cell)) {
//                    return false;
//                }
//            }
//            return true;
//        }
        return false;
    }

    /*
      boolean isCheck(Player oppositePlayer, Cell kingCell){
          //All possible moves from black player(opposite)
          HashSet<Cell> allPossibleMoves = chessBoard.getAllPossibleMovesByPlayer(oppositePlayer);
          for(Cell cell : allPossibleMoves){
              if(cell.equals(kingCell)){
                  return true;
              }
          }
          return false;
      }
     */

    void testPrint(String status){
        System.out.print("=============================start============================================");
        System.out.print(status+": ");
        chessBoard.printBoard();
        System.out.print("===============================end==========================================");
    }

    public void playGame() {
        gameStatus = GameStatus.ACTIVE;
        q.add(whitePlayer);
        q.add(blackPlayer);
        // while()
        Player oppositePlayer = null;
        Player currentPlayer = null;
        System.out.print("---------------GAME STARTED!---------------");
        while (!q.isEmpty()) {
            currentPlayer = q.poll();
            System.out.print("color:: "+currentPlayer.getColor() +" userId: "+ currentPlayer.getName());
            if (!currentPlayer.getColor().equals(Color.WHITE)) {
                System.out.print(" BLACK TURN: ");
                oppositePlayer = whitePlayer;
            } else{
                System.out.print(" WHITE TURN: ");
                oppositePlayer=blackPlayer;
            }
            boolean isResigned=false;
            while(true) {
                //System.out.print(" Enter your input: ");
                MoveInput moveInput = takeInput();
                if (moveInput.isResigned()) {
                    System.out.print(currentPlayer.getName() + " resigned. so " + oppositePlayer.getName() + " WON!");
                    System.out.print("Congratulations!");
                    isResigned=true;
                    break;
                }
                boolean isMoved = move(moveInput.getCurrentX(), moveInput.getCurrentY(), moveInput.getTargetX(), moveInput.getTargetY(), currentPlayer, oppositePlayer);
                if (isMoved) break;
            }
            if(isResigned) break;
            chessBoard.printBoard();
            if (isCheckMate(currentPlayer, oppositePlayer)) {
                System.out.println("CHECK MATE! ");
                System.out.print(currentPlayer.getName() + " WON! Congratulations!");
                break;
            }
            q.add(currentPlayer);
        }
        System.out.print("---------------GAME ENDED!---------------");
    }

//    void batchMoves(){
//        move()
//    }
}
