package wethmar.bernd.simplicity.logic;

import android.provider.BaseColumns;

/**
 * Created by Bernd on 22/12/2017.
 */

public class DatabaseContract {

    private DatabaseContract()
    {
    }

    static class LocalScoreEntry implements BaseColumns
    {
        static final String TABLE_NAME = "localscores";
        static final String _ID = "id";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_SCORE = "score";
        private LocalScoreEntry () {}
    }

}
