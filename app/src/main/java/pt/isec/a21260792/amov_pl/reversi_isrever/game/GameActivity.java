package pt.isec.a21260792.amov_pl.reversi_isrever.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pt.isec.a21260792.amov_pl.reversi_isrever.R;

public class GameActivity extends Activity implements View.OnClickListener {

    private GameData gameData;
    private GAME_TYPE mode;

    private Button restart; //TODO: check if we maintain
    private Button leave;
    private Button pass;
    private Button undo;
    private boolean pass_button = false; // Initially turn-off
    private boolean undo_button = false; // Initially turn-off

    private ImageView playerOneImg;
    private ImageView playerTwoImg;
    private TextView whiteCount;
    private TextView blackCount;
    private TextView playerOneName;
    private TextView playerTwoName;

    private GridLayout gridLayout;
    private ImageButton[][] boardButtons = new ImageButton[8][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reversi_game);

        //TODO: get in constructor the game mode

        restart =(Button)findViewById(R.id.RestartBtn);
        restart.setOnClickListener(this);

        leave =(Button) findViewById(R.id.LeaveBtn);
        leave.setOnClickListener(this);

        pass =(Button) findViewById(R.id.PassBtn);
        pass.setOnClickListener(this);

        undo =(Button) findViewById(R.id.UndoBtn);
        undo.setOnClickListener(this);

        whiteCount =(TextView) findViewById(R.id.PlayerPointsTv);
        blackCount =(TextView) findViewById(R.id.OponentPointsTv);
        playerOneName =(TextView) findViewById(R.id.PlayerNameTv);
        playerTwoName =(TextView) findViewById(R.id.OponentNameTv);

        gridLayout = (GridLayout) findViewById(R.id.BoardGl);
        getElementAndSetListener();

        //TODO: gameData = new GameData();
        //TODO: profilesUpdate();
        //TODO: startTurn();
    }

    private void getElementAndSetListener(){
        for(int i = 0; i < gridLayout.getChildCount();i++){
            boardButtons[i%8][i/8]=(ImageButton)gridLayout.getChildAt(i);
            boardButtons[i%8][i/8].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        //TODO: block listeners

        switch (v.getId()){
            case R.id.RestartBtn:
                //TODO: gameData = new GameData(mode,gameData.getPlayerOne(),gameData.getPlayerTwo());
                //TODO: gameData.init();//To initiate the board
//                fillTheBoard(); //To repaint UI
                //TODO: startGame(); //To start the game for the first player, gameData is informed
                break;
            case R.id.LeaveBtn:
                //TODO: create intent and change to previous activity
                //TODO: if in the remote mode reset communication socket and service
                break;
            case R.id.PassBtn:
                //TODO: migrate switch case to gameData and add only gameData.OponentAction();
                    switch(mode){
                        case INDIVIDUAL_RANDOM:
                            //TODO: gameData.itsRandomTime();
                            break;
                        case INDIVIDUAL_AI:
                            //TODO: gameData.itsAITime();
                            break;
                        case REMOTE_MULTIPLAYER:
                            //TODO: send info to Remote play (throught comm class, gamedata??)
                            break;
                    }
//                fillTheBoard(); //To repaint UI
//                pointsUpdate();
                //TODO: startTurn(); //To start the next turn for the first player, gameData is informed
                break;
            case R.id.UndoBtn:
                //TODO: gameData.restorePreviousState();
//                fillTheBoard(); //To repaint UI
                //TODO: pointsUpdate();
                //TODO: startTurn(); //To start the next turn for the first player, gameData is informed
                break;
            default:
                if(v.getContentDescription() == null)
                    return;

                int i = Character.getNumericValue(v.getContentDescription().charAt(0));
                int j = Character.getNumericValue(v.getContentDescription().charAt(1));

                //if(gameData.setAction(i,j)){
//                    fillTheBoard(); //To repaint UI
//                    pointsUpdate();
                    //TODO: gameDataOponentAction() // oponent moves and the turn is over
//                    fillTheBoard(); //To repaint UI
//                    pointsUpdate();
                    //TODO: startTurn(); //To start the next turn for the first player, gameData is informed
                //}
                /*else{
                //TODO: unblock listeners
                certainButtonsBlockage();
            }*/
        }
    }

    private void profilesUpdate(){
        //playerOneName.setText(gameData.getPlayer1().getname);
        // playerTwoName.setText(gameData.getPlayerOneName());
//        blackCount.setText("0 pts");
//        whiteCount.setText("0 pts");
        //playerOneImg.setImageBitmap(gameData.getPlayerOneBitmap());
        //playerTwoImg.setImageBitmap(gameData.getPlayerTwoBitmap());
    }

    private void certainButtonsBlockage(){
        if(!gameData.isBoardAvailable())
            for(int i = 0; i < 8; i++)
                for(int j = 0; j < 8; j++)
                    boardButtons[i][j].setEnabled(false);

        if(!gameData.isPassAvailable())
          pass.setEnabled(false);
        if(!gameData.isUndoAvailable())
            undo.setEnabled(false);
    }

    private void startTurn(){
//        if(gameData.isGameOver()){
//            gameData.saveGame();
//        }
        //TODO unblock buttons
        certainButtonsBlockage();
    }

    private void startGame(){
        //TODO: gameData = new GameData();
        startTurn();
    }

    private void fillTheBoard(){
        for(int i = 0; i < 8; i++){
            for(int j= 0; j< 8; j++){
                if(gameData.getCellColor(i,j) == CELL_STATUS.EMPTY){
                    boardButtons[i][j].setImageResource(R.drawable.transparent);
                }
                else if(gameData.getCellColor(i,j) == CELL_STATUS.IN_BLACK){
                    boardButtons[i][j].setImageResource(R.drawable.black_disk);
                }
                else if(gameData.getCellColor(i,j) == CELL_STATUS.IN_WHITE){
                    boardButtons[i][j].setImageResource(R.drawable.white_disk);
                }
            }
        }
    }

    private void pointsUpdate(){
        blackCount.setText(gameData.getPlayer1().getPoints()+" pts");
        whiteCount.setText(gameData.getPlayer2().getPoints() +" pts");
    }

}
