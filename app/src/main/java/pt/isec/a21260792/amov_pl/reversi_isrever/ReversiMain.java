package pt.isec.a21260792.amov_pl.reversi_isrever;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pt.isec.a21260792.amov_pl.reversi_isrever.game.GameActivity;

public class ReversiMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reversi_menu);

        Button start_game = (Button)findViewById(R.id.UniplayerBtn);

        start_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent  = new Intent(view.getContext(), GameActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

}
