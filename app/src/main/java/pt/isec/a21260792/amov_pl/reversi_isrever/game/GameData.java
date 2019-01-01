package pt.isec.a21260792.amov_pl.reversi_isrever.game;

import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import org.json.JSONArray;

public class GameData {
    private GAME_TYPE mode;
    private Player player1;
    private Player player2;
    private Board board;
    private int turn = 1;
    private Player currentPlayer;

    private Board previousBoardBlack;
    private Board previousBoardWhite;

    public void skipTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;

        if(currentPlayer == player1){
            turn ++;
        }

    }

    public GameData(int mode){
        player1= new Player(CELL_STATUS.IN_BLACK);
        player2 = new Player(CELL_STATUS.IN_WHITE);
        this.mode = GAME_TYPE.values()[mode];

        board = new Board();
        previousBoardBlack = new Board();
        previousBoardWhite = new Board();
        board.initBoard();
        previousBoardBlack.initBoard();
        previousBoardWhite.initBoard();
        currentPlayer = player1;
    }

    public boolean isBoardAvailable(){
        if( board.noMoreCellsAvailable() || board.noMoreMovesForPlayer(currentPlayer.getColor()))
            return false;
        return true;
    }

    public boolean isGameOver(){
        if(!isBoardAvailable())
            if(currentPlayer.haveUndone() && currentPlayer.haveSkipped())
                return true;
        return false;
    }

    public boolean isPassAvailable(){
        return (turn > 5) && !currentPlayer.haveSkipped();//TODO: repoor5 turnos
    }

    public boolean isUndoAvailable(){
        return (turn > 5) && !currentPlayer.haveUndone();
    }

    public CELL_STATUS getCellColor(int row, int column){
        return board.getCellColor(row, column);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getTurn() {
        return turn;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public boolean setAction(int row, int column){
        if(currentPlayer.getColor()==CELL_STATUS.IN_BLACK) {
            previousBoardBlack.copyBoard(board);
        } else {
            previousBoardWhite.copyBoard(board);
        }

        if(!board.canFormStraigthLine(row,column, currentPlayer.getColor()))
            return false;
        board.flip(row,column,currentPlayer.getColor(),board.getBoard());
        board.placePiece(row, column, currentPlayer.getColor());
        return true;
    }

    public void oponentAction(){
        switch(mode){
            case INDIVIDUAL_RANDOM:
                    board.itsRandomTime(currentPlayer.getColor());
                break;
            case INDIVIDUAL_AI:
                board.itsAITime(currentPlayer.getColor());
                break;
            case MULTIPLAYER:
                break;
            case REMOTE_MULTIPLAYER:
                //TODO: send info to Remote play (throught comm class, gamedata??)
                break;
        }
        skipTurn();
    }

    public void resetBoard(){
        if(currentPlayer.getColor()==CELL_STATUS.IN_BLACK){
            board.copyBoard(previousBoardBlack);
        } else {
            board.copyBoard(previousBoardWhite);
        }
        undoUsed();
    }

    public void passUsed(){
        currentPlayer.setHaveSkipped(true);
    }

    public void undoUsed(){
        currentPlayer.setHaveUndone(true);
    }

    public void returnPlayer(){
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

}
