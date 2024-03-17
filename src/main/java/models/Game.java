package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if(input.contains(GameStatus.RESIGNED.toString())){
            return new MoveInput(true);
        }
        String[] commands = input.split(" ");
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
        Cell currentKingCell = chessBoard.getCell(currentPlayer.getKing().getX(),currentPlayer.getKing().getY());

        if(piece.isValidMove(targetCell) && !isCheck(oppositePlayer, currentKingCell)){//if diagonal empty for pawn, it won't be true;
            System.out.println("[ENTERED]: Valid move + not check");
            if(targetCell.isOccupied() && piece.canCapture(targetCell)){
                piece.setX(targetX);
                piece.setY(targetY);

                //kill opposite
                KilledPieces.add(targetCell.getActivePiece());

                targetCell.setActivePiece(piece);
                targetCell.setOccupied(true);

                cell.setOccupied(false);
                cell.setActivePiece(null);
            }
            else if(!targetCell.isOccupied()){
                System.out.println("[ADDING]: @"+targetX+" @"+targetY);
                piece.setX(targetX);
                piece.setY(targetY);

                targetCell.setActivePiece(piece);
                targetCell.setOccupied(true);

                cell.setOccupied(false);
                cell.setActivePiece(null);
            }
            else {
                System.out.println("[ERROR]: Invalid move!");
                return false;
            }
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



    private boolean withinEdges(int targetX, int targetY){
        return targetX >= 0 && targetX < 8 && targetY >= 0 && targetY < 8;
    }


    //opposite player, current king
    boolean isCheck(Player oppositePlayer, Cell currentKingCell){

        //Only queen, rook, bishop, knight, other king can attack a king

        if(oppositePlayer.getKing().isValidMove(currentKingCell)){
            return true;
        }
        if(oppositePlayer.getQueen().isValidMove(currentKingCell)) {
           return true;
        }
        Rook [] rooks = oppositePlayer.getRooks();

        for(Rook rook : rooks){
            if(rook.isValidMove(currentKingCell)) return true;
        }

        Knight[] knights = oppositePlayer.getKnights();
        for(Knight knight : knights){
            if(knight.isValidMove(currentKingCell)) return true;
        }

        Bishop[] bishops = oppositePlayer.getBishops();

        for(Bishop bishop : bishops){
            if(bishop.isValidMove(currentKingCell)) return true;
        }
        return false;
    }

    boolean isCheckMate(Player player,Player oppositePlayer){
        King king = player.getKing();
        Cell kingCell  = chessBoard.getCell(king.getX(), king.getY());
        if(isCheck(oppositePlayer,kingCell)){
            HashSet<Cell> possibleMoves = king.getPossibleMoves(chessBoard.getCells());
            for(Cell cell : possibleMoves){
                if(!isCheck(oppositePlayer,cell)){
                    return false;
                }
            }
            return true;
        }
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

    public void playGame() {
        gameStatus = GameStatus.ACTIVE;
        q.add(whitePlayer);
        q.add(blackPlayer);
        // while()
        Player oppositePlayer = blackPlayer;
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
            }

            while(true) {
                System.out.print(" Enter your input: ");
                MoveInput moveInput = takeInput();
                if (moveInput.isResigned()) {
                    System.out.print(currentPlayer.getName() + " resigned. so " + oppositePlayer.getName() + " WON!");
                    System.out.print("Congratulations!");
                    break;
                }
                boolean isMoved = move(moveInput.getCurrentX(), moveInput.getCurrentY(), moveInput.getTargetX(), moveInput.getTargetY(), currentPlayer, oppositePlayer);
                if (isMoved) break;
            }
            chessBoard.printBoard();
            if (isCheckMate(currentPlayer, oppositePlayer)) {
                System.out.print("CHECK MATE!");
                System.out.print(currentPlayer.getName() + " WON!");
                System.out.print("Congratulations!");
            }
            q.add(currentPlayer);
        }
        System.out.print("---------------GAME ENDED!---------------");
    }
}
