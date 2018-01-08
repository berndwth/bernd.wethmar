package wethmar.bernd.simplicity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import wethmar.bernd.simplicity.logic.LeaderboardAsync;
import wethmar.bernd.simplicity.logic.LocalScore;

public class LeaderboardActivity extends AppCompatActivity {

    private List<LocalScore> score_list;
    private TextView txtItemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(R.string.leaderboard_title);
        }

        new LeaderboardAsync().execute(this);
    }

    public void populateLeaderboard(String[] array) {
        ListView list = (ListView)findViewById(android.R.id.list);
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textview = (TextView) view.findViewById(android.R.id.text1);
                textview.setTextColor(Color.WHITE);
                return view;
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, MainSimplicity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
