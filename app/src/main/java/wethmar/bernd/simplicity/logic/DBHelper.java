package wethmar.bernd.simplicity.logic;

/**
 * Created by Bernd on 22/12/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static wethmar.bernd.simplicity.logic.DatabaseContract.*;

public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "favourite_artists.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";

    private static final String SQL_CREATE_SCORE_ENTRY =
            "CREATE TABLE " +  LocalScoreEntry.TABLE_NAME + " (" +
                    LocalScoreEntry._ID + TYPE_INTEGER + " PRIMARY KEY," +
                    LocalScoreEntry.COLUMN_NAME_NAME + TYPE_TEXT + "," +
                    LocalScoreEntry.COLUMN_NAME_SCORE + TYPE_INTEGER + ")";

    private static DBHelper sInstance;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_SCORE_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //TODO
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
    }
}
