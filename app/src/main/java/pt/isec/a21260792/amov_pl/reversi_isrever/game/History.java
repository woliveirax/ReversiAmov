package pt.isec.a21260792.amov_pl.reversi_isrever.game;

import android.os.Parcelable;

import java.io.Serializable;

public class History implements Serializable {
    boolean win;
    private String otherPlayer;
    private GAME_TYPE mode;
    private int myPoints;
    private int oponentPoints;

    public History(boolean win, String otherPlayer, GAME_TYPE mode, int myPoints, int oponentPoints) {
        this.win = win;
        this.otherPlayer = otherPlayer;
        this.mode = mode;
        this.myPoints = myPoints;
        this.oponentPoints = oponentPoints;
    }

    public boolean isWin() {
        return win;
    }

    public String getOtherPlayer() {
        return otherPlayer;
    }

    public GAME_TYPE getMode() {
        return mode;
    }

    public int getMyPoints() {
        return myPoints;
    }

    public int getOponentPoints() {
        return oponentPoints;
    }
}
