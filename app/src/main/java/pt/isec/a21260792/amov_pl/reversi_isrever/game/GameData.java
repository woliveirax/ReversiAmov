package pt.isec.a21260792.amov_pl.reversi_isrever.game;

public class GameData {
    private GAME_TYPE mode;
    private Player player1;
    private Player player2;
    private Board board;
    private int turn = 1;
    private Player currentPlayer;

    public void skipTurn() {
        this.currentPlayer = this.currentPlayer == player1 ? player2 : player1;
        turn ++;
    }

    public GameData(GAME_TYPE mode, Player player1, Player player2){
        this.player1=player1;
        this.player2 = player2;
        this.mode = mode;

        board = new Board();
        board.initBoard();
    }

    public boolean isBoardAvailable(){
        if( board.noMoreCellsAvailable() || board.noMoreMovesForPlayer(currentPlayer.getColor()))
            return false;
        return true;
    }

    public boolean isPassAvailable(){
        return (turn > 5) && !currentPlayer.haveSkipped();
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
}
