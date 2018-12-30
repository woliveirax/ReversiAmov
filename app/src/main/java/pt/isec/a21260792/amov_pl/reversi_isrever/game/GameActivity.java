package pt.isec.a21260792.amov_pl.reversi_isrever.game;

import android.app.Activity;
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

    GameData gameData;

    public Button restart; //TODO: check if we maintain
    public Button leave;
    public Button pass;
    public Button undo;
    public boolean pass_button = false; // Initially turn-off
    public boolean undo_button = false; // Initially turn-off

    public TextView whiteCount;
    public TextView blackCount;
    public TextView playerOneName;
    public TextView playerTwoName;

    public ImageButton current;
    public GridLayout gridLayout;
    public ImageButton[][] boardButtons = new ImageButton[8][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reversi_game);

        restart =(Button) findViewById(R.id.RestartBtn);
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

    }

    public void getElementAndSetListener(){
        for(int i = 0; i < gridLayout.getChildCount();i++){
            boardButtons[i%8][i/8]=(ImageButton)gridLayout.getChildAt(i);
            boardButtons[i%8][i/8].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RestartBtn:
                break;
            case R.id.LeaveBtn:
                break;
            case R.id.PassBtn:
                break;
            case R.id.UndoBtn:
                break;
            default:
                Toast.makeText(this,"Ca estamos", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
