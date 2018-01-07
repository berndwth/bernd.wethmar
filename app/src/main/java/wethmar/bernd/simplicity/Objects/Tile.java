package wethmar.bernd.simplicity.Objects;

import java.io.Serializable;

import wethmar.bernd.simplicity.MainSimplicity;

/**
 * Created by Bernd on 31/10/2017.
 */

public class Tile implements Cloneable, Serializable{
    private int size_factor;
    private int color_code;

    public Tile() {
        size_factor = 1;
        color_code = MainSimplicity.standard_colors[1];
    }

    public Tile(int size_factor, int color_code) {
        this.color_code = color_code;
        this.size_factor = size_factor;
    }

    public int getSize_factor() {
        return size_factor;
    }

    public int getColor_code() {
        return color_code;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Tile tile_clone = (Tile) super.clone();
        tile_clone.size_factor = size_factor;
        tile_clone.color_code = color_code;
        return tile_clone;
    }

    @Override
    public String toString() {
        return "(S:" + size_factor + " & C:" + color_code + ")";
    }
}
