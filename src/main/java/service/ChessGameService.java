package service;

import models.Player;

public interface ChessGameService {

    boolean move(int sourceX, int sourceY, int targetX, int targetY, Player currentPlayer, Player oppositePlayer);

    void playGame();

    boolean isCheckMate(Player currentlayer, Player oppositePlayer);

}
