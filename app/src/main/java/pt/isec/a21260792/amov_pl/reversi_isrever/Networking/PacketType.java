package pt.isec.a21260792.amov_pl.reversi_isrever.Networking;

public interface PacketType {
    int GAME_INFO = 0;
    int LEAVE = 1;
    int REPLAY = 2;
    int PASS = 3;

    void sendGameData(/*GameData gameData*/);
   // void sendGameData(/*GameData gameData*/);
    //void sendGameData(/*GameData gameData*/);
}