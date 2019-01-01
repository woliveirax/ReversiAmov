package pt.isec.a21260792.amov_pl.reversi_isrever;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Credits extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.credits);
        TextView text = findViewById(R.id.textView);
        text.setText("CREDITS:\n\n Joana Barata\n21260792\n " +
                "\nWallace Oliveira\n21230618");

        text.startAnimation(animation);

    }
}
