package wethmar.bernd.simplicity.logic;

/**
 * Created by Bernd on 22/12/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class LocalScoreDAO {

    private final DBHelper mDbHelper;
    private final Context mContext;

    public LocalScoreDAO(Context context) {
        mContext = context;
        mDbHelper = DBHelper.getInstance(context);
    }

    public LocalScore addLocalScore(LocalScore score) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.LocalScoreEntry.COLUMN_NAME_NAME, score.getName());
        values.put(DatabaseContract.LocalScoreEntry.COLUMN_NAME_SCORE, score.getScore());

        long row_id = db.insert(DatabaseContract.LocalScoreEntry.TABLE_NAME, null, values);
        score.setId(row_id);

        return score;
    }

    public List<LocalScore> getAll() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DatabaseContract.LocalScoreEntry.TABLE_NAME + " ORDER BY " + DatabaseContract.LocalScoreEntry.COLUMN_NAME_SCORE + " DESC LIMIT 10";

        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<LocalScore> scores = new ArrayList<>();

        if (c != null) {
            while(c.moveToNext()){
                LocalScore local_score = new LocalScore();
                local_score.setId(c.getLong(c.getColumnIndex(DatabaseContract.LocalScoreEntry._ID)));
                local_score.setName(c.getString(c.getColumnIndex(DatabaseContract.LocalScoreEntry.COLUMN_NAME_NAME)));
                local_score.setScore(c.getInt(c.getColumnIndex(DatabaseContract.LocalScoreEntry.COLUMN_NAME_SCORE)));
                scores.add(local_score);
            }
            c.close();
        }
        return scores;
    }
}
