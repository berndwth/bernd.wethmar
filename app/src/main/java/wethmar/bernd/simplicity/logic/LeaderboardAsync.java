package wethmar.bernd.simplicity.logic;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Iterator;
import java.util.List;

import wethmar.bernd.simplicity.LeaderboardActivity;

/**
 * Created by Bernd on 8/01/2018.
 */

public class LeaderboardAsync extends AsyncTask<Context, Integer, String[]> {
    Context context;

    @Override
    protected String[] doInBackground(Context... contexts) {
        context = contexts[0];
        LocalScoreDAO score_dao = new LocalScoreDAO(context);

        List<LocalScore> score_list = score_dao.getAll();
        String[] array = new String[score_list.size()];
        int index = 0;
        for (Iterator<LocalScore> i = score_list.iterator(); i.hasNext(); ) {
            LocalScore temp_score = i.next();
            array[index] = temp_score.getName() + " - " + temp_score.getScore();
            index++;
        }
        return array;
    }

    @Override
    protected void onPostExecute(String[] array) {
        ((LeaderboardActivity) context).populateLeaderboard(array);
    }
}
