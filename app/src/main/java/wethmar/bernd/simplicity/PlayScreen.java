package wethmar.bernd.simplicity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import wethmar.bernd.simplicity.logic.LocalScore;
import wethmar.bernd.simplicity.logic.LocalScoreDAO;

public class PlayScreen extends AppCompatActivity {

    BoardFragment board_fragment;
    BoardFragment goal_fragment;
    private GameBroadcastReciever reciever;
    int score_for_level;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(R.string.simplicity);
        }

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();

        board_fragment = new BoardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("size", getResources().getDisplayMetrics().widthPixels);
        board_fragment.setArguments(bundle);

        ft.add(R.id.fragment_placeholder, board_fragment, "BoardFragment");
        ft.commit();

        getSupportFragmentManager().executePendingTransactions();

        /*Board b = new Board(getBoardSize(getResources().getDisplayMetrics().widthPixels));
        BoardView boardview = new BoardView(getApplicationContext(), b);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        boardview.setLayoutParams(layoutParams);
        ((RelativeLayout)findViewById(R.id.play_screen)).addView(boardview, layoutParams);*/

        Bundle goal_bundle = new Bundle();
        goal_bundle.putInt("size", getResources().getDisplayMetrics().widthPixels);

        if (getIntent().getBooleanExtra("RANDOM", false)) {
            goal_fragment = new BoardFragment();
            goal_bundle.putBoolean("isRandom", true);
            goal_bundle.putBoolean("isGoal", false);
            goal_fragment.setArguments(goal_bundle);
        } else {
            goal_fragment = new BoardFragment();
            goal_bundle.putString("Level_Nr", getIntent().getStringExtra("Level_Nr"));
            goal_bundle.putBoolean("isRandom", false);
            goal_bundle.putBoolean("isGoal", true);
            goal_fragment.setArguments(goal_bundle);
        }

        findViewById(R.id.fragment_placeholder).invalidate();

        reciever = new GameBroadcastReciever();
        IntentFilter filter = new IntentFilter(GameBroadcastReciever.Game_Ended);
        registerReceiver(reciever, filter);
        score_for_level = 300;
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                score_for_level = score_for_level - 5;
            }

            @Override
            public void onFinish() {
                score_for_level = 0;
            }
        }.start();
    }

    private int getBoardSize(int screen_size) {
        if (screen_size >= 720)
            return 450;
        if (screen_size >= 600)
            return 400;
        if (screen_size >= 480)
            return 350;
        if (screen_size >= 330)
            return 300;
        else
            return 200;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void saveLevel(View v) {
        board_fragment.board.serialize();
        Toast.makeText(getApplicationContext(), "Level saved", Toast.LENGTH_SHORT).show();
    }

    public void resetBoard(View v) {
        board_fragment.resetBoard();
        board_fragment.board.setGoal(goal_fragment.board);
        v.invalidate();
    }

    public void showGoal(View v) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        try {
            ft.remove(board_fragment);
            ft.add(R.id.fragment_placeholder, goal_fragment, "GoalFragment");
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
            findViewById(R.id.fragment_placeholder).invalidate();
        } catch (Exception e){}
    }

    public void hideGoal(View v) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();

        try {
            ft.remove(goal_fragment);
            ft.add(R.id.fragment_placeholder, board_fragment, "BoardFragment");
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
            findViewById(R.id.fragment_placeholder).invalidate();
        } catch (Exception e){}
    }

    public void boardsReady() {
        board_fragment.board.setGoal(goal_fragment.board);
    }

    public class GameBroadcastReciever extends BroadcastReceiver {
        public static final String Game_Ended = "wethmar.bernd.GAME_ENDED";
        @Override
        public void onReceive(Context context, Intent intent) {
                endGame();
        }
    }

    public void endGame() {
        if (score_for_level > 0) {
            Toast.makeText(getApplicationContext(), "Level completed with score: " + score_for_level + "!", Toast.LENGTH_SHORT).show();
            timer.cancel();
            if (goal_fragment.isRandom()) {
                LocalScoreDAO scoreDAO = new LocalScoreDAO(this);
                scoreDAO.addLocalScore(new LocalScore("Speler 1", score_for_level));
            }
        }

        Intent intent;
        if (goal_fragment.isRandom())
            intent = new Intent(this, MainSimplicity.class);
        else
            intent = new Intent(this, LevelSelectActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, MainSimplicity.class);
        startActivity(intent);
    }
}
