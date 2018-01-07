package wethmar.bernd.simplicity.Objects;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import com.orm.SugarRecord;

/**
 * Created by Bernd on 22/11/2017.
 */

public class LeaderboardRecord extends SugarRecord {

    int score;
    String identifier;

    public LeaderboardRecord(int score, String identifier) {
        this.score = score;
        this.identifier = identifier;
    }
}
