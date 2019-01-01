package pt.isec.a21260792.amov_pl.reversi_isrever.game;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class Pos{
    private final int x;
    private final int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

public class Board {

    private final int BOARDSIZE = 8;
    private CELL_STATUS[][]board = new CELL_STATUS[BOARDSIZE][BOARDSIZE];

    public Board(){
        initBoard();
    }

    public JSONArray toJsonArray(){
        JSONArray array = new JSONArray();


        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length;j++){
                try {

                    JSONObject obj = new JSONObject();
                    obj.put("x", i);
                    obj.put("y", j);
                    obj.put("value",board[i][j].getLevelCode());

                    array.put(obj);

                } catch (JSONException e) {
                    Log.d("BoardToJSONConversion", "Error trying to create the JSON object");
                }
            }
        }

        return array;
    }

    public void updateBoard(JSONArray array){
        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject obj = array.getJSONObject(i);

                int x = obj.getInt("x");
                int y = obj.getInt("y");
                int value = obj.getInt("value");

                board[x][y] = CELL_STATUS.values()[value];

            } catch (JSONException e) {
                Log.d("ResultUpdateBoard", "Error trying to update board");
            }
        }
    }

    public void itsRandomTime(CELL_STATUS color){
        ArrayList<Pos> possiblePos = new ArrayList<>();
        possiblePos = getValidMoves(possiblePos,color);

        if(noMoreCellsAvailable() || possiblePos.isEmpty()){
            return;
        }

        Collections.shuffle(possiblePos);
        int posX = possiblePos.get(0).getX();
        int posY = possiblePos.get(0).getY();

        flip(posX,posY,color,board);
        placePiece(posX,posY,color);
    }

    public void itsAITime(CELL_STATUS color){
        int max = 0, pos = 0;
        ArrayList<Pos> possiblePos = new ArrayList<>();
        possiblePos = getValidMoves(possiblePos,color);

        if(noMoreCellsAvailable() || possiblePos.isEmpty()){
            return;
        }

        CELL_STATUS[][]copy = new CELL_STATUS[BOARDSIZE][BOARDSIZE];

        for(int i = 0; i < possiblePos.size(); i++){
            copy=boardCopy(copy,board);
            flip(possiblePos.get(i).getX(),possiblePos.get(i).getY(),color,copy);
            if(countPlayerDisks(color,copy) > max){
                max = countPlayerDisks(color,copy);
                pos = i;
            }
        }

        int posX = possiblePos.get(pos).getX();
        int posY = possiblePos.get(pos).getY();

        flip(posX,posY,color,board);
        placePiece(posX,posY,color);
    }

    private CELL_STATUS[][] boardCopy(CELL_STATUS[][]copy_to, CELL_STATUS[][]copy_from){
        for(int i = 0; i < BOARDSIZE; i++)
            for(int j = 0; j < BOARDSIZE; j++)
                copy_to[i][j]=copy_from[i][j];
        return copy_to;
    }
    private ArrayList<Pos> getValidMoves(ArrayList<Pos> array, CELL_STATUS color){
        for(int i = 0; i < BOARDSIZE; i ++){
            for (int j = 0; j < BOARDSIZE; j++)
                if(canFormStraigthLine(i,j,color))
                    array.add(new Pos(i,j));
        }

        return array;
    }

    //Fill the board
    public void initBoard(){
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                board[i][j] = CELL_STATUS.EMPTY;
            }
        }
        //Ocupy central cells
        board[3][4]=board[4][3]=CELL_STATUS.IN_BLACK;
        board[3][3]=board[4][4]=CELL_STATUS.IN_WHITE;

    }

    //Place reversi disk at board[row][col]
    public void placePiece(int row, int column, CELL_STATUS playerColor){
        if(board[row][column] == CELL_STATUS.EMPTY){
            if(playerColor == CELL_STATUS.IN_BLACK)
                board[row][column] = CELL_STATUS.IN_BLACK;
            else
                board[row][column] = CELL_STATUS.IN_WHITE;
        }
    }

    // Verify if the slot is available
    public boolean canFormStraigthLine(int row, int column, CELL_STATUS playerColor) {
        boolean possible = false;
        //CELL_STATUS playerColor = player == CURRENTPLAYER.PLAYER1 ? CELL_STATUS.IN_BLACK : CELL_STATUS.IN_WHITE;

        //Verify if it's empty
        if(board[row][column] != CELL_STATUS.EMPTY) {
            return false;
        }

        // Scan all directions
        for (int dirRow = -1; dirRow < 2; dirRow++) { //from -1 to 1. (left and right positions)
            for (int dirCol = -1; dirCol < 2; dirCol++) { //from -1 to 1 (down and up positions)

                // Ignore (0,0) -> current position
                if(dirRow == 0 && dirCol == 0) {
                    continue;
                }

                // board[newRow][newCol] are the neighbors
                int newRow = row + dirRow;
                int newCol = column + dirCol;

                // Check new cell in the board
                if (newRow > -1 && newRow < BOARDSIZE && newCol > -1 && newCol < BOARDSIZE) {

                    // Verify if the color of board[newRow][newCol] is the opposite from the player
                    CELL_STATUS oppColor = (playerColor == CELL_STATUS.IN_BLACK)
                            ? CELL_STATUS.IN_WHITE : CELL_STATUS.IN_BLACK;

                    if (board[newRow][newCol] == oppColor) {
                        for (int range = 1; range < BOARDSIZE; range++) {//check in all directions

                            int nRow = row + range * dirRow;
                            int nCol = column + range * dirCol;

                            // Skip the positions exterior to the board
                            if(nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }

                            // break if we scan the empty slot
                            if(board[nRow][nCol] == CELL_STATUS.EMPTY) {
                                break;
                            }
                            //if we find the player color, the placement is possible
                            if(board[nRow][nCol] == playerColor) {
                                possible = true;
                                break;
                            }
                        }
                    }
                }

//                if(possible) {
//                    return possible;
//                }
            }
        }
        return possible;
    }

    // Flip Disks
    public boolean flip(int currentRow, int currentCol, CELL_STATUS playerColor,CELL_STATUS[][]board) {
        boolean isValid = false;

        // Search all directions
        for(int dirRow = -1; dirRow < 2; dirRow++) {
            for(int dirCol = -1; dirCol < 2; dirCol++) {

                // ignore (0,0)
                if(dirRow == 0 && dirCol == 0) {
                    continue;
                }

                // board[newRow][newCol] is the slot nearby current slot
                int newRow = currentRow + dirRow;
                int newCol = currentCol + dirCol;

                // Check board[newRow][newCol] in the board
                if(newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7) {

                    // return true if board[newRow][newCol] is opposite color to currentPlayer
                    CELL_STATUS oppoColor =
                            playerColor == CELL_STATUS.IN_WHITE ? CELL_STATUS.IN_BLACK : CELL_STATUS.IN_WHITE;
                    if(board[newRow][newCol] == oppoColor) {
                        for(int range = 0; range < BOARDSIZE; range++) {

                            int nRow = currentRow + range * dirRow;
                            int nCol = currentCol + range * dirCol;

                            // Skip the case outside the board
                            if(nRow < 0 || nRow > 7 || nCol < 0 || nCol > 7) {
                                continue;
                            }

                            // Check if we can flip in this direction
                            if(board[nRow][nCol] == playerColor) {
                                boolean canFlip = true;
                                for (int dist = 1; dist < range; dist++) {

                                    int testRow = currentRow + dist * dirRow;
                                    int testCol = currentCol + dist * dirCol;

                                    if (board[testRow][testCol] != oppoColor) {
                                        canFlip = false;
                                    }
                                }

                                // Flip
                                if(canFlip) {
                                    for(int flipDist = 1; flipDist < range; flipDist++) {

                                        int finalRow = currentRow + flipDist * dirRow;
                                        int finalCol = currentCol + flipDist * dirCol;

                                        if(board[finalRow][finalCol] == oppoColor) {
                                            board[finalRow][finalCol] = playerColor;
                                        }
                                    }
                                }
                                isValid = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return isValid;
    }

    public int countPlayerDisks(CELL_STATUS color, CELL_STATUS[][]board){
        int num = 0;
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int col = 0; col < BOARDSIZE; col++) {
                if(board[row][col] == color) {
                    num++;
                }
            }
        }
        return num;
    }

    public boolean noMoreCellsAvailable() {
        int slotsLeft = 0;
        for(int i = 0; i < BOARDSIZE; i++) {
            for(int j = 0; j < BOARDSIZE; j++) {
                if(board[i][j] == CELL_STATUS.EMPTY) {
                    slotsLeft++;
                }
            }
        }
        return slotsLeft == 0;
    }

    public boolean noMoreMovesForPlayer(CELL_STATUS player_color){
        for(int i = 0; i < BOARDSIZE; i++){
            for(int j = 0; j < BOARDSIZE; j++){
                if (board[i][j] == CELL_STATUS.EMPTY)
                    if (canFormStraigthLine(i, j, player_color))
                        return false;
            }
        }
        return true;
    }

    // get board
    public CELL_STATUS[][] getBoard() {
        return board;
    }

    public CELL_STATUS getCellColor(int r, int c){ return board[r][c];}
}
