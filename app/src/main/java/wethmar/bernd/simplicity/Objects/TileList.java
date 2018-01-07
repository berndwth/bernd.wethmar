package wethmar.bernd.simplicity.Objects;

import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import wethmar.bernd.simplicity.MainSimplicity;

/**
 * Created by Bernd on 5/11/2017.
 */

public class TileList<Tile> extends ArrayList<Tile> implements Serializable{

    private HashMap<Tile, TileCoordinates> tile_mapping;

    public TileList(HashMap<Tile, TileCoordinates> mMapping) {
        super();
        tile_mapping = mMapping;
    }

    public TileList(int initialCapacity) {
        super(initialCapacity);
        tile_mapping = new HashMap<>();
    }

    public TileList() {
        super();
        tile_mapping = new HashMap<>();
    }

    public TileList(@NonNull Collection c) {
        super(c);
        tile_mapping = new HashMap<>();
    }

    public TileList(Tile t, int size) {
        super();
        tile_mapping = new HashMap<>();
        tile_mapping.put(t, new TileCoordinates(0,size,0,size));
        add(t);
    }

    public void divide(Tile tile, int size, Constructor<Tile> ctr) throws Exception {
        int tile_pos = indexOf(tile);
        Tile tile1 = ctr.newInstance(size + 1, MainSimplicity.standard_colors[0]);
        Tile tile2 = ctr.newInstance(size + 1, MainSimplicity.standard_colors[1]);
        Tile tile3 = ctr.newInstance(size + 1, MainSimplicity.standard_colors[2]);
        Tile tile4 = ctr.newInstance(size + 1, MainSimplicity.standard_colors[3]);

        remove(tile);
        add(tile_pos + 0, tile1);
        add(tile_pos + 1, tile2);
        add(tile_pos + 2, tile3);
        add(tile_pos + 3, tile4);

        TileCoordinates old_coords = tile_mapping.remove(tile);
        TileCoordinates[] new_coords = old_coords.getQuadrants();

        tile_mapping.put(tile1, new_coords[0]);
        tile_mapping.put(tile2, new_coords[1]);
        tile_mapping.put(tile3, new_coords[2]);
        tile_mapping.put(tile4, new_coords[3]);
    }

    public HashMap<Tile, TileCoordinates> getMapping() {
        return tile_mapping;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + tile_mapping.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof TileList)) {
            return false;
        }

        TileList t_list = (TileList) obj;

        //Source: https://stackoverflow.com/questions/20969211/comparing-two-hashmaps-for-equal-values-and-same-key-sets
        try{
            for (Object k : t_list.tile_mapping.keySet())
            {
                if (!this.tile_mapping.get((Tile) k).equals(t_list.tile_mapping.get((Tile) k))) {
                    return false;
                }
            }

        } catch (NullPointerException np) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String s = "TileList: ";

        for (Tile t: this) {
            s += t.toString() + ", ";
        }
        return s;
    }
}
