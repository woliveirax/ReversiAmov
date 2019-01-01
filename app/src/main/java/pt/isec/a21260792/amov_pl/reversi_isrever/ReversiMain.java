package pt.isec.a21260792.amov_pl.reversi_isrever;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.SocketHandler;

import pt.isec.a21260792.amov_pl.reversi_isrever.game.CELL_STATUS;
import pt.isec.a21260792.amov_pl.reversi_isrever.game.GAME_TYPE;
import pt.isec.a21260792.amov_pl.reversi_isrever.game.GameActivity;
import pt.isec.a21260792.amov_pl.reversi_isrever.game.History;

public class ReversiMain extends Activity {

    private Button singlePlayerVsBot;
    private Button singlePlayerVsAI;
    private Button multiplayer;
    private Button remoteServer;
    private Button remoteClient;
    private Button profile;
    private Button history;
    private Button credits;
    private ProgressDialog pd;
    private static final int PORT = 8899;
    private static final int PORTaux = 9988;
    private Socket socketGame = null;
    private ServerSocket serverSocket = null;
    private Handler procMsg = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reversi_menu);

        singlePlayerVsBot = findViewById(R.id.UniplayerVSBotBtn);
        singlePlayerVsAI = findViewById(R.id.UniplayerVSAIBtn);
        multiplayer = findViewById(R.id.MultiplayerBtn);
        remoteServer = findViewById(R.id.RemoteServerBtn);
        remoteClient = findViewById(R.id.RemoteClientBtn);
        history = findViewById(R.id.HistoricBtn);
        profile = findViewById(R.id.ProfileBtn);
        credits = findViewById(R.id.CreditsBtn);

        singlePlayerVsBot.setOnClickListener(new GameListener(GAME_TYPE.INDIVIDUAL_RANDOM));
        singlePlayerVsAI.setOnClickListener(new GameListener(GAME_TYPE.INDIVIDUAL_AI));
        multiplayer.setOnClickListener(new GameListener(GAME_TYPE.MULTIPLAYER));

/*        remoteClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientDlg();
            }
        });

        remoteServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server();
            }
        });*/

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHistory(v);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfile(v);
            }
        });

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Credits.class);
                startActivity(intent);
            }
        });

        procMsg = new Handler();
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    void server() {
        String ip = getLocalIpAddress();

        pd = new ProgressDialog(this);
        pd.setMessage("\n(IP: " + ip + ")");
        pd.setTitle("Waiting For Connection");
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
                if (serverSocket!=null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                    }
                    serverSocket=null;
                }
            }
        });
        pd.show();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(PORT);
                    socketGame = serverSocket.accept();
                    serverSocket.close();
                    serverSocket=null;
                    //TODO:add Socket handler
                } catch (Exception e) {
                    e.printStackTrace();
                    socketGame = null;
                }
                procMsg.post(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        if (socketGame == null)
                            finish();
                    }
                });
            }
        });
        t.start();
    }

    void clientDlg() {
        final EditText edtIP = new EditText(this);
        edtIP.setText("192.168.1.89");
        AlertDialog ad = new AlertDialog.Builder(this).setTitle("RPS Client")
                .setMessage("Server IP").setView(edtIP)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        client(edtIP.getText().toString(), PORT); // to test with emulators: PORTaux);
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                }).create();
        ad.show();
    }

    void client(final String strIP, final int Port) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("RPS", "Connecting to the server  " + strIP);
                    socketGame = new Socket(strIP, Port);
                } catch (Exception e) {
                    socketGame = null;
                }
                if (socketGame == null) {
                    procMsg.post(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                    return;
                }
                //TODO:Add Soclet Handler
                //commThread.start();
            }
        });
        t.start();
    }

    public void onHistory(View view) {
        Intent intent = new Intent(view.getContext(), HistoryActivity.class);
        startActivity(intent);
    }

    public void onProfile(View view) {
        Intent intent = new Intent(view.getContext(), ProfileManager.class);
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
