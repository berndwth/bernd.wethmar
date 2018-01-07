package wethmar.bernd.simplicity.Objects;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Bernd on 22/11/2017.
 */

public class Leaderboard {

    String mac_addres;
    public Leaderboard(Activity activity) {
        WifiManager wifiMan = (WifiManager) activity.getApplicationContext().getSystemService(
                Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        mac_addres = wifiInf.getMacAddress();
    }

    public void add(int score) {
        int id;
        List<LeaderboardRecord> result = SugarRecord.findWithQuery(LeaderboardRecord.class, "SELECT id, score FROM LeaderboardRecord WHERE identifier = ?", mac_addres);
        if (result != null && !(result.isEmpty())) {
            if (result.get(0).score <= score) {
                result.get(0).delete();
            }
            else {
                return;
            }
        }
        LeaderboardRecord record = new LeaderboardRecord(score, mac_addres);
        record.save();
    }
}
