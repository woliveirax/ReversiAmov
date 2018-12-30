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

    public void activateMode(){
        switch(mode){
            case MULTIPLAYER:
                break;
            case INDIVIDUAL_AI:
                //boolean movePossible = selectMostPiecesPossiblieAI();
                break;
            case INDIVIDUAL_RANDOM:
                //boolean movePossible =selectRandom();
                break;
            case REMOTE_MULTIPLAYER:
                break;
            default:
                break;
        }
    }




}
