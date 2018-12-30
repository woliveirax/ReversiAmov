package pt.isec.a21260792.amov_pl.reversi_isrever;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import pt.isec.a21260792.amov_pl.reversi_isrever.game.History;
import pt.isec.a21260792.amov_pl.reversi_isrever.game.Player;

public class Client {
    private String name;
    private Player avatar;
    private Bitmap icon_avatar;
    private List<History> historic;

    public Client(){
        historic = new ArrayList<>();
    }
    public void addHistory(History h){
        this.historic.add(h);
    }

    public String getName() {
        return name;
    }

    public Player getAvatar() {
        return avatar;
    }

    public Bitmap getIcon_avatar() {
        return icon_avatar;
    }

    public List<History> getHistoric() {
        return historic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(Player avatar) {
        this.avatar = avatar;
    }

    public void setIcon_avatar(Bitmap icon_avatar) {
        this.icon_avatar = icon_avatar;
    }

}
