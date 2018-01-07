package wethmar.bernd.simplicity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

public class MainSimplicity extends AppCompatActivity {

    public static int[] standard_colors = new int[]{Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_simplicity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.simplicity);
        }
        checkPermissions();
    }

    public void startClassicPlay(View v) {
        Intent intent = new Intent(this, LevelSelectActivity.class);
        startActivity(intent);
    }

    public void startRandomPlay(View v) {
        Intent intent = new Intent(this, PlayScreen.class);
        intent.putExtra("RANDOM", true);
        startActivity(intent);
    }

    public void goToLeaderboards(View v) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    final private int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 123;
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
        }
    }

    //Bron: https://stackoverflow.com/questions/12908289/how-to-change-language-of-app-when-user-selects-language
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainSimplicity.class);
        startActivity(refresh);
        finish();
    }


    public void changeEn(View view) {
        setLocale("en");
    }

    public void changeNl(View view) {
        setLocale("nl");
    }

    public void changeAf(View view) {
        setLocale("af");
    }
}
