package models;

import utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChessBoard {
    private final Cell[][] cells;

    public ChessBoard(Player whitePlayer, Player blackPlayer) {
        this.cells = new Cell[8][8];
        initializeCellsWithPieces(whitePlayer);
        initializeCellsWithPieces(blackPlayer);
        initializeEmptyCells();
    }

    private void initializeCellsWithPieces(Player player) {
        int firstRowIndex = player.getColor().equals(Color.BLACK) ? 0 : 7;

        Rook[] rooks = player.getRooks();

        cells[firstRowIndex][0] = new Cell(firstRowIndex, 0, rooks[0]);
        cells[firstRowIndex][7] = new Cell(firstRowIndex, 7, rooks[1]);


        Knight[] knights = player.getKnights();

        cells[firstRowIndex][1] = new Cell(firstRowIndex, 1, knights[0]);
        cells[firstRowIndex][6] = new Cell(firstRowIndex, 6, knights[1]);

        Bishop[] bishops = player.getBishops();

        cells[firstRowIndex][2] = new Cell(firstRowIndex, 2, bishops[0]);
        cells[firstRowIndex][5] = new Cell(firstRowIndex, 5, bishops[1]);

        cells[firstRowIndex][3] = new Cell(firstRowIndex, 3, player.getQueen());
        cells[firstRowIndex][4] = new Cell(firstRowIndex, 4, player.getKing());

        int secondRowIndex = player.getColor().equals(Color.BLACK) ? 1 : 6;

        Pawn[] pawns = player.getPawns();

        for (int i = 0; i < pawns.length; i++) {
            cells[secondRowIndex][i] = new Cell(secondRowIndex, i, pawns[i]);
        }

    }

    private void initializeEmptyCells() {
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(int x, int y) {
        if (x <= 8 && x >= 0 && y <= 8 && y >= 0) {
            return cells[x][y];
        }
        return null;
    }

    public void setCell(Cell cell) {
        cells[cell.getX()][cell.getY()] = cell;
    }


    private void addIfValid(HashMap<Cell, List<Cell>> possibleMovesMap, Piece piece, Cell targetCell) {
        if (!piece.isKilled() && piece.isValidMove(targetCell, cells)) {
            Cell currentCell = cells[piece.getX()][piece.getY()];
            possibleMovesMap.getOrDefault(currentCell, new ArrayList<>()).add(targetCell);
        }
    }

    public HashMap<Cell, List<Cell>> getAllPossibleMovesByPlayer(Player player) {
        Color color = player.getColor();
        HashMap<Cell, List<Cell>> possibleMovesMap = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell targetCell = cells[i][j];
                //skip if cell already diff has same colored piece
                if (targetCell.isOccupied() && !color.equals(targetCell.getActivePiece().getColor())) continue;
                //addIfValid(possibleMovesMap, player.getKing(), targetCell);
                addIfValid(possibleMovesMap, player.getQueen(), targetCell);
                for (Rook rook : player.getRooks()) {
                    addIfValid(possibleMovesMap, rook, targetCell);
                }
                for (Knight knight : player.getKnights()) {
                    addIfValid(possibleMovesMap, knight, targetCell);
                }
                for (Bishop bishop : player.getBishops()) {
                    addIfValid(possibleMovesMap, bishop, targetCell);
                }
                for (Pawn pawn : player.getPawns()) {
                    addIfValid(possibleMovesMap, pawn, targetCell);
                }
            }
        }
        return possibleMovesMap;
    }

    public void printBoard() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                String print = cell.isOccupied() ? cell.getActivePiece().getName() : "X";
                String color = Constants.BLACK_COLOR;
                if (cell.isOccupied() && cell.getActivePiece().getColor().equals(Color.WHITE)) {
                    color = Constants.WHITE_COLOR;
                }
                if (print.equals("X")) {
                    color = Constants.YELLOW_COLOR;
                }
                System.out.print(color + " " + print + " " + Constants.RESET_COLOR);
            }
            System.out.println();
        }
    }

}
