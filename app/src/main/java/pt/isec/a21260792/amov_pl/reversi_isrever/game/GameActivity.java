package pt.isec.a21260792.amov_pl.reversi_isrever.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pt.isec.a21260792.amov_pl.reversi_isrever.R;
import pt.isec.a21260792.amov_pl.reversi_isrever.ReversiMain;

public class GameActivity extends Activity implements View.OnClickListener {

    private GameData gameData;
    private int mode;

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

        mode = getIntent().getExtras().getInt("gameMode");

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
        Log.d("OnCreate_GameActivity","Creating new activity");

                    gameData = new GameData(mode);
        fillTheBoard(); //To repaint UI
        //TODO: profilesUpdate();
        startTurn();
    }

    private void getElementAndSetListener(){
        for(int i = 0; i < gridLayout.getChildCount();i++){
            boardButtons[i/8][i%8]=(ImageButton)gridLayout.getChildAt(i);
            boardButtons[i/8][i%8].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        buttonBlockage();

        switch (v.getId()){
            case R.id.RestartBtn:
                gameData = new GameData(mode);//Also initiate the board
                fillTheBoard(); //To repaint UI
                startGame(); //To start the game for the first player, gameData is informed
                break;

            case R.id.LeaveBtn:
                //TODO: if in the remote mode reset communication socket and service
                Intent myIntent  = new Intent(v.getContext(), ReversiMain.class);
                startActivityForResult(myIntent, 0);
                break;

            case R.id.PassBtn:
                if(!(mode == GAME_TYPE.MULTIPLAYER.getValue())){
                    gameData.passUsed();
                    gameData.skipTurn();
                    gameData.oponentAction();
                }else{
                    gameData.passUsed();
                    gameData.skipTurn();
                }
                updataUI();
                startTurn(); //To start the next turn for the first player, gameData is informed
                break;

            case R.id.UndoBtn:
                gameData.resetBoard();
//                if(mode == GAME_TYPE.MULTIPLAYER.getValue())
//                    gameData.returnPlayer();
                updataUI();
                startTurn(); //To start the next turn for the first player, gameData is informed
                break;

            default: //its the board buttons
                if(v.getContentDescription() == null) //only the the board have content describer
                    return;

                int i = Character.getNumericValue(v.getContentDescription().charAt(0));
                int j = Character.getNumericValue(v.getContentDescription().charAt(1));

                if(gameData.setAction(i-1,j-1)){
                    updataUI();
                    gameData.skipTurn();
                    v.refreshDrawableState();

                    if(mode != GAME_TYPE.MULTIPLAYER.getValue()){
                        gameData.oponentAction();
                        updataUI();
                        if(gameData.isGameOver()){
                            //TODO: gameData.saveGame();
                            Toast.makeText(this,getString(R.string.victory),Toast.LENGTH_LONG);
                        }
                    }
                    startTurn(); //To start the next turn for the first player, gameData is informed
                }
                else{
                    certainButtonsBlockage();
                }
        }
    }

    private void updataUI(){
        fillTheBoard(); //To repaint UI
        pointsUpdate();
    }

    private void profilesUpdate(){
//        playerOneName.setText(gameData.getPlayer1().getname);//TODO: retrieve the user name from registry
//        playerTwoName.setText(gameData.getPlayerOneName()); //TODO: put PC bot or PC AI, if it's single player, and the remote name if it's remote
        blackCount.setText("0 pts");
        whiteCount.setText("0 pts");
        //playerOneImg.setImageBitmap(gameData.getPlayerOneBitmap());
        //playerTwoImg.setImageBitmap(gameData.getPlayerTwoBitmap());
    }

    private void buttonBlockage(){
        gridLayout.setEnabled(false);
        pass.setEnabled(false);
        undo.setEnabled(false);
    }
    private void certainButtonsBlockage(){
        if(!gameData.isBoardAvailable())
            gridLayout.setEnabled(false);//TODO: check if this block the childs
        else gridLayout.setEnabled(true);//TODO: check if this unblock the childs

        if(!gameData.isPassAvailable())
          pass.setEnabled(false);
        else pass.setEnabled(true);

        if(!gameData.isUndoAvailable())
            undo.setEnabled(false);
        else undo.setEnabled(true);
    }

    private void startTurn(){
        if(gameData.isGameOver()){
            //TODO: gameData.saveGame();
            Toast.makeText(this,getString(R.string.lost),Toast.LENGTH_LONG);
        }

        certainButtonsBlockage();
    }

    private void startGame(){
        gameData = new GameData(mode);
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
        blackCount.setText(gameData.getBoard().countPlayerDisks(gameData.getPlayer1().getColor(),gameData.getBoard().getBoard())+" pts");
        whiteCount.setText(gameData.getBoard().countPlayerDisks(gameData.getPlayer2().getColor(),gameData.getBoard().getBoard()) +" pts");
    }


}
