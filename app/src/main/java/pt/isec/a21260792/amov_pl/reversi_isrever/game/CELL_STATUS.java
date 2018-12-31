package pt.isec.a21260792.amov_pl.reversi_isrever.game;

public enum CELL_STATUS {
    EMPTY       (0),
    IN_BLACK    (1),
    IN_WHITE    (2);

    private int levelCode;

    CELL_STATUS(int levelCode) {
        this.levelCode = levelCode;
    }

    public int getLevelCode(){
        return levelCode;
    }
}
