package pt.isec.a21260792.amov_pl.reversi_isrever.game;

public enum GAME_TYPE {
    INDIVIDUAL_RANDOM (0),
    INDIVIDUAL_AI (1),
    MULTIPLAYER (2),
    REMOTE_MULTIPLAYER (3);

    private int value;

    GAME_TYPE(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
