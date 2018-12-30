package pt.isec.a21260792.amov_pl.reversi_isrever.Networking;

import android.app.IntentService;
import android.content.Intent;

public class ClientHandler extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ClientHandler(String name) {
        super(name);
    }

    public ClientHandler(){
        super("ClientHandler");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
