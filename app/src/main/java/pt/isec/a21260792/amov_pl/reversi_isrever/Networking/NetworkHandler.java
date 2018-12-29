package pt.isec.a21260792.amov_pl.reversi_isrever.Networking;

import android.app.Activity;
import android.content.Intent;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkHandler {
    private Activity activity;

    public NetworkHandler(Activity activity){
        this.activity = activity;
    }

    public void startGameService(){
        Intent service = new Intent(activity, ClientHandler.class);
        activity.startService(service);
    }

    public void sendGameData(){
    }


    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();

            while(en.hasMoreElements())
            {
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> ips = intf.getInetAddresses();

                while(ips.hasMoreElements())
                {
                    InetAddress addr = ips.nextElement();
                    if (!addr.isLoopbackAddress()
                            && addr instanceof Inet4Address) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
