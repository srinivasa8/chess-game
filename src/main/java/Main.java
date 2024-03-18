import models.ChessBoard;
import models.Color;
import models.Game;
import models.GameStatus;
import models.Player;

import java.util.ArrayList;
import java.util.List;

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
        Player blackPlayer = new Player("USER2", Color.BLACK);
        ChessBoard chessBoard = new ChessBoard(whitePlayer,blackPlayer);
        Game game = new Game(chessBoard, whitePlayer, blackPlayer,true, GameStatus.ACTIVE);
        game.playGame();
        //moveBatch(game,whitePlayer,blackPlayer,chessBoard);

    }

    static void moveBatch(Game game,Player whitePlayer, Player blackPlayer,ChessBoard chessBoard){
        Player curr = whitePlayer;
        Player other = blackPlayer;
        for(String s : inputArray){
            System.out.print("MOVE ---> "+s);
            String[] commands = s.split(" ");
            int sourceX = Integer.parseInt(commands[0]);
            int sourceY = Integer.parseInt(commands[1]);
            int targetX = Integer.parseInt(commands[2]);
            int targetY = Integer.parseInt(commands[3]);
            game.move(sourceX,sourceY,targetX,targetY,curr,other);
            if(sourceX==3 && sourceY==4 && targetX==3 && targetY==0 || s.equals("3 4 3 0")){
                System.out.print("I'm in");
            }
            if (game.isCheckMate(curr, other)) {
                System.out.print("CHECK MATE! @:"+s);
                System.out.print(curr.getName() + " WON!");
                System.out.print("Congratulations!");
            }
            Player temp =curr;
            curr=other;
            other=temp;
            chessBoard.printBoard();
        }
    }

    void formList(){
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{1,2});
        list.add(new int[]{1,2});
        list.add(new int[]{1,2});
        list.add(new int[]{1,2});
        list.add(new int[]{1,2});
        list.add(new int[]{1,2});
        list.add(new int[]{1,2});
        list.add(new int[]{1,2});



    }

    static String[] inputArray = {
            "6 4 4 4",
            "1 3 2 3",
            "6 2 4 2",
            "0 1 2 2",
            "7 1 5 2",
            "1 4 3 4",
            "7 6 5 5",
            "1 5 3 5",
            "5 5 3 6",
            "0 3 3 6",
            "7 3 5 5",
            "0 0 0 1",
            "6 3 5 3",
            "3 5 4 5",

            "5 5 4 5",
            "3 4 4 5",
            "7 5 6 4",
            "0 5 1 4",
            "7 2 4 5",
            "3 6 4 5",
            "6 6 5 6",
            "4 5 3 4",
            "5 2 4 0",
            "2 2 4 1",
            "7 0 7 2",
            "4 1 6 0",
            "7 2 7 3",
            "3 4 3 0",
            "7 4 7 5",
            "0 2 1 3",
            "7 3 7 2",
            "1 3 5 7",
            "7 5 7 6",
            "6 0 7 2",
            "6 4 7 5",
            "7 2 6 4",
            "7 5 6 4",
            "3 0 7 4",
            "6 4 7 5",
            "7 4 7 5"};

}
