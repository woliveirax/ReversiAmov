package pt.isec.a21260792.amov_pl.reversi_isrever;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pt.isec.a21260792.amov_pl.reversi_isrever.game.CELL_STATUS;
import pt.isec.a21260792.amov_pl.reversi_isrever.game.GAME_TYPE;
import pt.isec.a21260792.amov_pl.reversi_isrever.game.GameActivity;
import pt.isec.a21260792.amov_pl.reversi_isrever.game.History;

public class ReversiMain extends Activity {

//    public final static int INDIVIDUAL_RANDOM = 0;
//    public final static int INDIVIDUAL_AI = 1;
//    public final static int MULTIPLAYER  = 2;
//    public final static int REMOTE_MULTIPLAYER = 3;

    private Button singlePlayerVsBot;
    private Button singlePlayerVsAI;
    private Button multiplayer;
    private Button remote;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reversi_menu);

        singlePlayerVsBot = findViewById(R.id.UniplayerVSBotBtn);
        singlePlayerVsAI = findViewById(R.id.UniplayerVSAIBtn);
        multiplayer = findViewById(R.id.MultiplayerBtn);
        remote = findViewById(R.id.RemoteMultiplayerBtn);

        singlePlayerVsBot.setOnClickListener(new GameListener(GAME_TYPE.INDIVIDUAL_RANDOM));
        singlePlayerVsAI.setOnClickListener(new GameListener(GAME_TYPE.INDIVIDUAL_AI));
        multiplayer.setOnClickListener(new GameListener(GAME_TYPE.MULTIPLAYER));
        remote.setOnClickListener(new GameListener(GAME_TYPE.REMOTE_MULTIPLAYER));

    }

    private void clientDlg() {
        final EditText edtIP = new EditText(this);
        edtIP.setText("10.0.2.2");
        AlertDialog ad = new AlertDialog.Builder(this).setTitle("RPS Client")
                .setMessage("Server IP").setView(edtIP)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //client(edtIP.getText().toString(), PORT); // to test with emulators: PORTaux);
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                }).create();
        ad.show();
    }

    public void onHistory(View view) {
        Intent intent = new Intent(view.getContext(), HistoryActivity.class);
        startActivity(intent);
    }

    class GameListener implements View.OnClickListener{
        GAME_TYPE type;

        public GameListener(GAME_TYPE type){
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            Intent myIntent  = new Intent(v.getContext(), GameActivity.class);
            myIntent.putExtra("gameMode",type.getValue());
            startActivityForResult(myIntent, 0);
        }
    }
}
