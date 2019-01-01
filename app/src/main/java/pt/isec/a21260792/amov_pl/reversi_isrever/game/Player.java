package pt.isec.a21260792.amov_pl.reversi_isrever.game;

import android.graphics.Point;

import java.util.List;

public class Player {
    private boolean haveSkipped = false;
    private boolean haveUndone = false;
    private String name = "Player 2";

    CELL_STATUS color;

    public Player(CELL_STATUS color) {
        this.color = color;
    }

    public Player(CELL_STATUS color, String name) {
        this.color = color;
        this.name = name;
    }

    public Player(CELL_STATUS color, GAME_TYPE mode) {
        this.color = color;

        switch (mode){
            case INDIVIDUAL_AI:
                name = "PC_AI";
                break;
            case INDIVIDUAL_RANDOM:
                name = "PC_BOT";
                break;
        }
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

    public boolean haveSkipped() {
        return haveSkipped;
    }

    public boolean haveUndone() {
        return haveUndone;
    }

    public String getName() {
        return name;
    }
}
