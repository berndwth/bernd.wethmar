package wethmar.bernd.simplicity;

import android.graphics.Color;

import org.junit.Test;

import wethmar.bernd.simplicity.Objects.Tile;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Bernd on 8/01/2018.
 */

public class UnitTest {
    @Test
    public void createTile() {
        Tile t = new Tile(1, Color.CYAN);
        assertEquals(t.getColor_code(), Color.CYAN);
        assertEquals(t.getSize_factor(), 1);
    }
}
