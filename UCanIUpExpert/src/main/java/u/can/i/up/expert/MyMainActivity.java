package u.can.i.up.expert;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.baidu.location.LocationClient;

import u.can.i.up.expert.common.DataSet;

public class MyMainActivity extends Activity {
   
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE); 
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        DataSet.getInstance().locManager = new LocationClient(getApplicationContext());//声明LocationClient类
        DataSet.getInstance().locManager.registerLocationListener(DataSet.getInstance().locationListener);//注册监听函数

    	if(isMyServiceRunning()==false)
    	{
            startService(new Intent(getApplicationContext(), MyService.class));
    		Log.i("com.connect","startService");
    	}
    }
    
	private boolean isMyServiceRunning() {
	    ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MyService.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
}
