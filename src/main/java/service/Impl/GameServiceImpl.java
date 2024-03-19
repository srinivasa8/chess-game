package service.Impl;

import models.Bishop;
import models.Cell;
import models.ChessBoard;
import models.Color;
import models.GameStatus;
import models.King;
import models.Knight;
import models.MoveInput;
import models.Pawn;
import models.Piece;
import models.Player;
import models.PrintType;
import models.Rook;
import service.ChessGameService;
import utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import static models.GameStatus.RESIGN;

public class GameServiceImpl implements ChessGameService {

    private ChessBoard chessBoard;
    private Player whitePlayer;

    private Player blackPlayer;

    private GameStatus gameStatus;

    private Queue<Player> q = new LinkedList<>();

    public GameServiceImpl(ChessBoard chessBoard, Player whitePlayer, Player blackPlayer, GameStatus gameStatus) {
        this.chessBoard = chessBoard;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.gameStatus = GameStatus.ACTIVE;
        this.q = new LinkedList<>();
    }

    private final List<Piece> KilledPieces = new ArrayList<>();

    private MoveInput takeInput() {
        Scanner sc = new Scanner(System.in);
        String[] commands;
        int sourceX=-1;
        int sourceY=-1;
        int targetX=-1;
        int targetY=-1;
        while (true) {
            System.out.println("Enter the input in the form of {sourceX} {sourceY} {targetX} {targetY} or Type RESIGN to resign from the  game!");
            String input = sc.nextLine();
            if (input.contains(RESIGN.toString())) {
                return new MoveInput(true);
            }
            commands = input.split(" ");
            if (commands.length == 4) {
                try {
                    sourceX = Integer.parseInt(commands[0]);
                    sourceY = Integer.parseInt(commands[1]);
                    targetX = Integer.parseInt(commands[2]);
                    targetY = Integer.parseInt(commands[3]);
                    break;
                } catch(Exception e){
                    printMessage("Invalid input. Please enter valid input again: ",PrintType.ERROR);
                }

            } else {
                printMessage("Invalid input. Please enter valid input again: ", PrintType.ERROR);
            }
        }

        return new MoveInput(sourceX, sourceY, targetX, targetY);
    }

    private void printMessage(String message, PrintType printType) {
        System.out.println();
        if (printType.equals(PrintType.ERROR)) {
            System.out.println(Constants.RED_COLOR + "[ERROR]: " + message + Constants.RESET_COLOR);
        } else if (printType.equals(PrintType.ALERT)) {
            System.out.println(Constants.YELLOW_COLOR + message + Constants.RESET_COLOR);
        } else if (printType.equals(PrintType.INFO)) {
            System.out.println(message);
        } else if (printType.equals(PrintType.DEBUG)){
            //System.out.println(message);
        }
    }

    @Override
    public boolean move(int x, int y, int targetX, int targetY, Player currentPlayer, Player oppositePlayer) {

        if (!withinEdges(targetX, targetY)) {
            printMessage("Invalid source or destination positions!", PrintType.ERROR);
            return false;
        }

        Cell cell = chessBoard.getCell(x, y);

        if (cell == null || !cell.isOccupied()) {
            printMessage("No piece found at given position!", PrintType.ERROR);
            return false;
        }

        Piece piece = cell.getActivePiece();
        if (!piece.getColor().equals(currentPlayer.getColor())) {
            printMessage("You can move only your(" + currentPlayer.getColor().toString().toLowerCase() + ") pieces.", PrintType.ERROR);
            return false;
        }

        Cell targetCell = chessBoard.getCell(targetX, targetY);
        if (targetCell == null) return false;
        Cell currentKingCell = chessBoard.getCell(currentPlayer.getKing().getX(), currentPlayer.getKing().getY());
        Cell[][] cells = chessBoard.getCells();
        if (piece.isValidMove(targetCell, cells)) {
            if (targetCell.isOccupied() && piece.canCapture(targetCell)) {
                piece.setX(targetX);
                piece.setY(targetY);

                Piece prevPieceAtTargetCell = targetCell.getActivePiece();

                targetCell.setActivePiece(piece);
                targetCell.setOccupied(true);

                cell.setOccupied(false);
                cell.setActivePiece(null);
                Cell kingCell = piece instanceof King ? targetCell : currentKingCell;
                if (isCheck(oppositePlayer, kingCell)) {
                    printMessage("Invalid move as it can cause check!",PrintType.ERROR);
                    rollBackMove(piece, cell, targetCell, prevPieceAtTargetCell);
                    return false;
                } else {
                    //Assigning capture piece with invalid positions as its moved out of chess board.
                    //prevPieceAtTargetCell.setX(-100);
                    //prevPieceAtTargetCell.setY(-100);
                    prevPieceAtTargetCell.setKilled(true);
                    //Add to kill piece list
                    KilledPieces.add(prevPieceAtTargetCell);
                }
            } else if (!targetCell.isOccupied()) {
                piece.setX(targetX);
                piece.setY(targetY);

                targetCell.setActivePiece(piece);
                targetCell.setOccupied(true);

                cell.setOccupied(false);
                cell.setActivePiece(null);
                Cell kingCell = piece instanceof King ? targetCell : currentKingCell;
                if (isCheck(oppositePlayer, kingCell)) {
                    printMessage("Invalid move as it can cause check!",PrintType.ERROR);
                    rollBackMove(piece, cell, targetCell, null);
                    return false;
                }
            } else {
                printMessage("Invalid move as piece at target cell can't be captured!",PrintType.ERROR);
                return false;
            }
        } else {
            printMessage("Invalid move as requested piece can't move to the target cell!",PrintType.ERROR);
            return false;
        }
        return true;

    }

    private void rollBackMove(Piece piece, Cell currentCell, Cell targetCell, Piece prevPieceAtTarget) {
        if (prevPieceAtTarget != null) {
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

    private boolean withinEdges(int targetX, int targetY) {
        return targetX >= 0 && targetX < 8 && targetY >= 0 && targetY < 8;
    }

    private boolean isCheck(Player oppositePlayer, Cell currentKingCell) {
        Cell[][] cells = chessBoard.getCells();
        if (oppositePlayer.getKing().isValidMove(currentKingCell, cells)) {
            printMessage("Other king threat! x: " + oppositePlayer.getKing().getX() + " y: " + oppositePlayer.getKing().getY(),PrintType.DEBUG);
            return true;
        }
        if (!oppositePlayer.getQueen().isKilled() && oppositePlayer.getQueen().isValidMove(currentKingCell, cells)) {
            printMessage("Other queen threat! x: " + oppositePlayer.getQueen().getX() + " y: " + oppositePlayer.getQueen().getY(),PrintType.DEBUG);
            return true;
        }
        Rook[] rooks = oppositePlayer.getRooks();

        for (Rook rook : rooks) {
            if (!rook.isKilled() && rook.isValidMove(currentKingCell, cells)) {
                printMessage("Other rook threat! x: " + rook.getX() + " y: " + rook.getY(),PrintType.DEBUG);
                return true;
            }
        }

        Knight[] knights = oppositePlayer.getKnights();
        for (Knight knight : knights) {
            if (!knight.isKilled() && knight.isValidMove(currentKingCell, cells)) {
                printMessage("Other knight threat! x: " + knight.getX() + " y: " + knight.getY(),PrintType.DEBUG);
                return true;
            }
        }

        Bishop[] bishops = oppositePlayer.getBishops();

        for (Bishop bishop : bishops) {
            if (!bishop.isKilled() && bishop.isValidMove(currentKingCell, cells)) {
                printMessage("Other bishop threat! x: " + bishop.getX() + " y: " + bishop.getY(),PrintType.DEBUG);
                return true;
            }
        }

        Pawn[] pawns = oppositePlayer.getPawns();
        for (Pawn pawn : pawns) {
            if (!pawn.isKilled() && pawn.isValidMove(currentKingCell, cells)) {
                printMessage("Other pawn threat! x: " + pawn.getX() + " y: " + pawn.getY(),PrintType.DEBUG);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCheckMate(Player currentPlayer, Player oppositePlayer) {
        King king = oppositePlayer.getKing();
        Cell kingCell = chessBoard.getCell(king.getX(), king.getY());
        if (isCheck(currentPlayer, kingCell)) {
            HashSet<Cell> possibleMoves = king.getPossibleMoves(chessBoard.getCells());
            for (Cell cell : possibleMoves) {
                if (!isCheck(currentPlayer, cell)) {
                    return false;
                }
            }
            return !isCheckMateEliminatedWithOtherPieceMoves(currentPlayer, oppositePlayer, kingCell);
        }
        return false;
    }

    private boolean isCheckMateEliminatedWithOtherPieceMoves(Player currentPlayer, Player oppositePlayer, Cell kingCell) {
        HashMap<Cell, List<Cell>> allPossibleMovesByPlayer = chessBoard.getAllPossibleMovesByPlayer(oppositePlayer);
        for (Map.Entry<Cell, List<Cell>> entry : allPossibleMovesByPlayer.entrySet()) {
            Cell currentCell = entry.getKey();
            Piece piece = entry.getKey().getActivePiece();
            for (Cell targetCell : entry.getValue()) {
                Piece prevPieceAtTarget = null;
                if (targetCell.isOccupied()) {
                    prevPieceAtTarget = targetCell.getActivePiece();
                }
                move(currentCell.getX(), currentCell.getY(), targetCell.getX(), targetCell.getY(), currentPlayer, oppositePlayer);
                if (!isCheck(oppositePlayer, kingCell)) {
                    System.out.println("Checkmate was possible but can be eliminated by moving piece from x: " + piece.getX() + ", " +
                            "y: " + piece.getX() + " to tx:" + targetCell.getX() + " ty:" + targetCell.getY());
                    rollBackMove(piece, currentCell, targetCell, prevPieceAtTarget);
                    return true;
                }
                rollBackMove(piece, currentCell, targetCell, prevPieceAtTarget);
            }
        }
        return false;
    }

    @Override
    public void playGame() {
        q.add(whitePlayer);
        q.add(blackPlayer);
        Player oppositePlayer = null;
        Player currentPlayer = null;
        printMessage("---------------GAME STARTED!---------------", PrintType.INFO);
        System.out.println();
        while (!q.isEmpty()) {
            currentPlayer = q.poll();
            if (!currentPlayer.getColor().equals(Color.WHITE)) {
                printMessage(currentPlayer.getName() + " (BLACK TURN PLAYER) TURN: ", PrintType.INFO);
                oppositePlayer = whitePlayer;
            } else {
                printMessage(currentPlayer.getName() + " (WHITE PLAYER) TURN: ",PrintType.INFO);
                oppositePlayer = blackPlayer;
            }
            boolean isResigned = false;
            while (true) {
                MoveInput moveInput = takeInput();
                if (moveInput.isResigned()) {
                    printMessage(currentPlayer.getName() + " resigned. So " + oppositePlayer.getName() + " WON! Congratulations!",PrintType.ALERT);
                    isResigned = true;
                    gameStatus = GameStatus.RESIGNED;
                    break;
                }
                boolean isMoved = move(moveInput.getCurrentX(), moveInput.getCurrentY(), moveInput.getTargetX(), moveInput.getTargetY(), currentPlayer, oppositePlayer);
                if (isMoved) break;
            }
            if (isResigned) break;
            chessBoard.printBoard();
            if (isCheckMate(currentPlayer, oppositePlayer)) {
                printMessage("CHECK MATE! ",PrintType.ALERT);
                gameStatus = currentPlayer.getColor().equals(Color.WHITE) ? GameStatus.WHITE_PLAYER_WON : GameStatus.BLACK_PLAYER_WON;
                printMessage(currentPlayer.getName() + " WON! Congratulations!",PrintType.ALERT);
                break;
            }
            q.add(currentPlayer);
        }
        printMessage("---------------GAME ENDED!---------------", PrintType.INFO);
    }

}
