package wethmar.bernd.simplicity.Objects;

import java.io.Serializable;

/**
 * Created by Bernd on 5/11/2017.
 */

public class TileCoordinates implements Serializable {
    private int top, bottom, left, right;

    public TileCoordinates(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public TileCoordinates[] getQuadrants() {
        int midline_horizontal = top + ((bottom - top) / 2);
        int midline_vertical = left + ((right - left) / 2);
        TileCoordinates first_quadrant = new TileCoordinates(top, midline_horizontal, left, midline_vertical);
        TileCoordinates second_quadrant = new TileCoordinates(top, midline_horizontal, midline_vertical + 1, right);
        TileCoordinates third_quadrant = new TileCoordinates(midline_horizontal + 1, bottom, left, midline_vertical);
        TileCoordinates fourth_quadrant = new TileCoordinates(midline_horizontal + 1, bottom, midline_vertical + 1, right);
        TileCoordinates[] tiles = {first_quadrant, second_quadrant, third_quadrant, fourth_quadrant};
        return tiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileCoordinates that = (TileCoordinates) o;

        if (top != that.top) return false;
        if (bottom != that.bottom) return false;
        if (left != that.left) return false;
        return right == that.right;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + top;
        result = 31 * result + bottom;
        result = 31 * result + left;
        result = 31 * result + right;
        return result;
    }
}
