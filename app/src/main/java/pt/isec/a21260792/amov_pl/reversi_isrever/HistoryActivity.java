package pt.isec.a21260792.amov_pl.reversi_isrever;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import pt.isec.a21260792.amov_pl.reversi_isrever.game.History;

public class HistoryActivity extends Activity {

    private static String filename = "history";

    private String username;
    private ArrayList<History> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        username = ProfileManager.getName(getBaseContext());
        history = getHistory(getBaseContext());

        ListView list = findViewById(R.id.historyList);
        list.setAdapter(new MyAdapter());
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return history.size();
        }

        @Override
        public Object getItem(int i) {
            return history.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View layout = getLayoutInflater().inflate(R.layout.list_row, null);

            String player = username;
            int myPoints = history.get(i).getMyPoints();

            String oponent = history.get(i).getOtherPlayer();
            int oponentPoints = history.get(i).getOponentPoints();

            boolean win = history.get(i).isWin();
            String gameMode = "";

            switch(history.get(i).getMode()){
                case MULTIPLAYER:
                    gameMode = "1v1";
                    break;

                case INDIVIDUAL_AI:
                    gameMode = "AI Hard";
                    break;

                case REMOTE_MULTIPLAYER:
                    gameMode = "LAN";
                    break;

                case INDIVIDUAL_RANDOM:
                    gameMode = "AI Easy";
            }

            TextView opName = layout.findViewById(R.id.openentName);
            TextView opPoints = layout.findViewById(R.id.oponentPoints);

            TextView myName = layout.findViewById(R.id.myName);
            TextView myPointsl = layout.findViewById(R.id.myPoints);

            TextView mode = layout.findViewById(R.id.gameMode);

            ImageView image = layout.findViewById(R.id.imageStatus);
            image.setImageResource(R.drawable.arrow);

            if(win){
                image.setColorFilter(Color.argb(100, 0, 255, 0));

            } else {
                image.setColorFilter(Color.argb(255, 255, 0, 0));
                image.setRotation(180);
            }

            opName.setText(oponent);
            opPoints.setText(oponentPoints + " pts");

            myName.setText(username);
            myPointsl.setText(myPoints + " pts");

            mode.setText(gameMode);

            return layout;
        }
    }

    public static boolean historyExists(Context context){
        File file = context.getFileStreamPath(filename);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    public static void addHistory(History history, Context context){
        ArrayList<History> historyData = null;

        if(historyExists(context)){
            try {

                FileInputStream in = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(in);

                historyData = (ArrayList<History>) is.readObject();

                is.close();
            } catch (FileNotFoundException e) {
                Log.d("History", "Couldn't open file to write");
            } catch (IOException e) {
                Log.d("History", "Couldn't create object input stream");
            } catch (ClassNotFoundException e) {
                Log.d("History", "Couldn't read history data from file");
            }

        } else {
            File file = new File(context.getFilesDir(), filename);
            historyData = new ArrayList<>();
            Log.d("History", "Created new file: " + filename);
        }

        try {
            historyData.add(history);
            FileOutputStream fos = context.openFileOutput(filename,MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fos);

            out.writeObject(historyData);
            fos.getFD().sync();
            out.close();
        } catch (IOException e) {
            Log.d("History", "Couldn't open the file for writing: " + e);
        }
    }


    public static ArrayList<History> getHistory(Context context){
        if(historyExists(context)){
            try {

                FileInputStream in = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(in);

                ArrayList<History> historyData = (ArrayList<History>) is.readObject();
                is.close();

                return historyData;
            } catch (FileNotFoundException e) {
                Log.d("History", "Couldn't open file to write");
            } catch (IOException e) {
                Log.d("History", "Couldn't create object input stream");
            } catch (ClassNotFoundException e) {
                Log.d("History", "Couldn't read history data from file");
            }
        }
        return new ArrayList<>();
    }
}
