package wethmar.bernd.simplicity.Objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import wethmar.bernd.simplicity.PlayScreen;

/**
 * Created by Bernd on 31/10/2017.
 */

public class Board implements Cloneable {
    private static int BROADCAST_COUNTER = 0;
    private TileList<Tile> board;
    private boolean isGoal = false;
    private Board goal = null;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;


    public Board getGoal() {
        return goal;
    }

    public void setGoal(Board goal) {
        this.goal = goal;
    }

    public Board(int size) {
        board = new TileList<>(new Tile(), size);
    }

    public Board(String level) {
        board = deserialize(level);
        isGoal = true;
    }

    public TileList<Tile> getBoard() {
        return board;
    }

    public void divideAt(int posX, int posY) {
        HashMap<Tile, TileCoordinates> mapping = board.getMapping();
        for (Tile tile : board) {
            TileCoordinates coords = mapping.get(tile);
            if (posX > coords.getLeft() && posX <= coords.getRight() && posY > coords.getTop() && posY <= coords.getBottom()) {
                divideAt(tile);
                return;
            }
        }
    }

    public boolean divideAt(Tile tile) {
        if (board == null)
            return false;
        if (!board.contains(tile))
            return false;
        if (tile.getSize_factor() >= 5)
            return false;
        if (isGoal)
            return false;

        try {
            board.divide(tile, tile.getSize_factor(), Tile.class.getConstructor(int.class, int.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (goal != null)
            checkGameDone();
        return true;
    }

    private void checkGameDone() {
        if (board.toString().equals(goal.getBoard().toString())) {
            Intent intent = new Intent();
            intent.setAction(PlayScreen.GameBroadcastReciever.Game_Ended);
            context.sendBroadcast(intent);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Board board_clone = (Board) super.clone();
        for (Iterator<Tile> i = board.iterator(); i.hasNext();) {
            board_clone.board = new TileList<>();
            board_clone.board.add((Tile) i.next().clone());
        }
        return board_clone;
    }

    private static int level_counter = 1;
    public void serialize() {
        File folder = new File(Environment.getExternalStorageDirectory().getPath() + "/Simplicity/Levels");
        if (!folder.exists()){
            folder.mkdirs();
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath() + "/Simplicity/Levels/level_8.bin")));
            oos.writeObject(board);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static TileList<Tile> deserialize(String level) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(Environment.getExternalStorageDirectory().getPath() + "/Simplicity/Levels/level_" + level + ".bin")));
            TileList<Tile> loaded_mapping = (TileList<Tile>) ois.readObject();
            return loaded_mapping;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Board)) {
            return false;
        }

        Board b = (Board) obj;

        return (b.board.equals(this.board));
    }

    @Override
    public int hashCode() {
        return board != null ? board.hashCode() : 0;
    }

    public static Board generateRandom(int size) {
        Board board = new Board(size);
        Random rand = new Random();
        int max_index;
        for (int i = 0; i < 10; i++) {
            max_index = board.getBoard().size();
            int index = rand.nextInt(max_index);
            Tile t = board.getBoard().get(index);
            board.divideAt(t);
        }
        board.isGoal = true;
        return board;
    }
}
