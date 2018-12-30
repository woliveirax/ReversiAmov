package pt.isec.a21260792.amov_pl.reversi_isrever.game;

import android.graphics.Point;

import java.util.List;

public class Player {
    private boolean haveSkipped = false;
    private boolean haveUndone = false;
    CELL_STATUS color;

    public Player(CELL_STATUS color) {
        this.color = color;
    }

    public boolean hasSkipped() {
        return haveSkipped;
    }

    public void setHaveSkipped(boolean haveSkipped) {
        this.haveSkipped = haveSkipped;
    }

    public boolean HasUndone() {
        return haveUndone;
    }

    public void setHaveUndone(boolean haveUndone) {
        this.haveUndone = haveUndone;
    }

    public CELL_STATUS getColor() {
        return color;
    }
}
