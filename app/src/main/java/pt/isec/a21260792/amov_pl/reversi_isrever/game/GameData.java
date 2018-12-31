package pt.isec.a21260792.amov_pl.reversi_isrever.game;

public class GameData {
    private int mode;
    private Player player1;
    private Player player2;
    private Board board;
    private int turn = 1;
    private Player currentPlayer;
    //TODO save previous state

    public void skipTurn() {
        if((this.currentPlayer = (this.currentPlayer == player1 ? player2 : player1)) == player1)
            turn ++;
    }

    public GameData(int mode){
        player1= new Player(CELL_STATUS.IN_BLACK);
        player2 = new Player(CELL_STATUS.IN_WHITE);
        this.mode = mode;

        board = new Board();
        board.initBoard();
        currentPlayer = player1;
    }

    public void OponentAction(){
        switch(mode){
            case 0:// == GAME_TYPE.INDIVIDUAL_RANDOM.getValue()
                //TODO: gameData.itsRandomTime();
                break;
            case 1:// == GAME_TYPE.INDIVIDUAL_AI.getValue()
                //TODO: gameData.itsAITime();
                break;
            case 3:// == GAME_TYPE.REMOTE_MULTIPLAYER.getValue()
                //TODO: send info to Remote play (throught comm class, gamedata??)
                break;
        }
        skipTurn();
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
