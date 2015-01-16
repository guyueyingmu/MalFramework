package u.can.i.up.expert.framework;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import u.can.i.up.expert.common.DataSet;

public class PhoneListener extends PhoneStateListener
{
    private Context context;

    public PhoneListener(Context c) {
        context = c;
    }

    public void onCallStateChanged (int state, String incomingNumber)
    {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:// 当前电话处于闲置状态
                Boolean stopped = context.stopService(new Intent(context, RecordService.class));

                break;
            case TelephonyManager.CALL_STATE_RINGING:// 当前电话处于零响状态
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:// 当前电话处于接听状态
                if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("RecordCalls", false))
                {
//                    Log.i(DataSet.getInstance().LOG_TAG + "." + this.getClass().getName(), "LISTENING....");
//                    Log.i(DataSet.getInstance().LOG_TAG + "." + this.getClass().getName(), PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("File", ""));
                    Intent callIntent = new Intent(context, RecordService.class);
                    ComponentName name = context.startService(callIntent);
                    if (null == name) {
                    } else {
                    }
                }
                break;
        }
    }
}
