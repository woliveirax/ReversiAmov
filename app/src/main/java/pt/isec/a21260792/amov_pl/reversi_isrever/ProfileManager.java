package pt.isec.a21260792.amov_pl.reversi_isrever;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileManager extends Activity {

    public static String PREFERENCES = "MyPreferences";
    public static String USERNAME_FIELD = "USERNAME";

    EditText txt;
    SharedPreferences sharePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manager);

        txt = (EditText) findViewById(R.id.UsernameField);
        sharePrefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        String player = sharePrefs.getString(USERNAME_FIELD,"Player");
        txt.setText(player);
    }

    public void onTakePicture(View view) {
    }

    public void onLeave(View view) {
        finish();
    }

    public void onSave(View view) {
        if(txt.getText().length() < 3 || txt.getText().length() > 9 ||
                txt.getText().toString().contains("\n")) {
            Toast.makeText(getBaseContext(),"Invalid name",Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharePrefs.edit();
        editor.putString(USERNAME_FIELD,txt.getText().toString());
        editor.commit();
        Toast.makeText(getBaseContext(),"Success",Toast.LENGTH_SHORT).show();
        finish();
    }
}
