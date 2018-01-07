package wethmar.bernd.simplicity.Objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by Bernd on 6/11/2017.
 */

public class BoardView extends View {
    private Board board;
    private Paint mPaint;

    public BoardView(Context context, Board board) {
        super(context);
        this.board = board;
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        HashMap<Tile, TileCoordinates> mapping = board.getBoard().getMapping();
        for (Tile tile : board.getBoard()) {
            TileCoordinates coords = mapping.get(tile);
            Rect rect = new Rect(coords.getLeft(), coords.getTop(), coords.getRight(), coords.getBottom());
            mPaint.setColor(tile.getColor_code());
            canvas.drawRect(rect, mPaint);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                board.divideAt(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }
        System.out.println(x);
        System.out.println(y);
        return true;
    }

    public void setBoard(Board board) {
        this.board = board;
        this.invalidate();
    }

}
