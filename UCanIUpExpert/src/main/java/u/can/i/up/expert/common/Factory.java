package u.can.i.up.expert.common;

import android.location.Location;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class Factory {
    //********************************************************************************************************************************************************
    public static void updateWithNewLocation(Location location) {
        if (location != null) {
            DataSet.getInstance().latitude = location.getLatitude();
            DataSet.getInstance().longitude = location.getLongitude();
        }
    }
    public static String removeBlankSpace(StringBuilder sb) {
        int j = 0;
        for(int i = 0; i < sb.length(); i++) {
            if (!Character.isWhitespace(sb.charAt(i))) {
                sb.setCharAt(j++, sb.charAt(i));
            }
        }
        sb.delete(j, sb.length());
        return sb.toString();
    }
    public static InputStream getInputStreamFromUrl(String urlBase, String urlData) throws UnsupportedEncodingException {

        Log.i(DataSet.getInstance().LOG_TAG + Factory.class.getName(), "base:" + urlBase);
        Log.i(DataSet.getInstance().LOG_TAG + Factory.class.getName(), "data:" + urlData);

        String urlDataFormatted=urlData;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");
        String currentDateandTime = "[" + sdf.format(new Date()) + "] - ";
        currentDateandTime = URLEncoder.encode(currentDateandTime, "UTF-8");

        if(urlData.length()>1)
        {
            Log.d(DataSet.getInstance().LOG_TAG + Factory.class.getName(), urlBase + urlData);

            urlData = currentDateandTime + URLEncoder.encode (urlData, "UTF-8");
            urlDataFormatted = urlData.replaceAll("\\.", "~period");

            Log.i(DataSet.getInstance().LOG_TAG + Factory.class.getName(), urlBase + urlDataFormatted);
        }

        if(isNetworkAvailable())
        {
            InputStream content = null;
            try
            {
                Log.i(DataSet.getInstance().LOG_TAG + Factory.class.getName(), "network push POST");
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(new HttpGet(urlBase + urlDataFormatted));
                content = response.getEntity().getContent();
                httpclient.getConnectionManager().shutdown();
            } catch (Exception e) {
                Log.e(DataSet.getInstance().LOG_TAG + Factory.class.getName(), "exception", e);
            }
            return content;
        }
        return null;
    }
    public static boolean isNetworkAvailable() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return true;

//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        if (netInfo != null && netInfo.isConnected()) {
//            try {
//                URL url = new URL("http://www.google.com");
//                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
//                urlc.setConnectTimeout(3000);
//                urlc.connect();
//                if (urlc.getResponseCode() == 200) {
//                    return new Boolean(true);
//                }
//            } catch (MalformedURLException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return false;
    }
    public static void sendRawLine(String text, Socket sock) {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            out.write(text+"\n");
            out.flush();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
