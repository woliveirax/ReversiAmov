package pt.isec.a21260792.amov_pl.reversi_isrever;

import java.util.List;

class Piece{
    int x;
    int y;
    boolean filled;
}

public class Player {
    List<Piece> pieceList;
    boolean haveSkipped;
    boolean haveUndone;
    Byte color;
}
