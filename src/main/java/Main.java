import models.ChessBoard;
import models.Color;
import models.Game;
import models.GameStatus;
import models.Player;

public class Main {

    //16 pieces
    //8pawns
    //2 rooks
    //2 horses
    //2 bishops
    //queen
    //king

    public static void main(String[] args) {
        Player whitePlayer = new Player("USER1", Color.WHITE);
        Player blackPlayer = new Player("USER1", Color.WHITE);
        ChessBoard chessBoard = new ChessBoard(whitePlayer,blackPlayer);
        Game game = new Game(chessBoard,whitePlayer,blackPlayer,true, GameStatus.ACTIVE);

    }

}
