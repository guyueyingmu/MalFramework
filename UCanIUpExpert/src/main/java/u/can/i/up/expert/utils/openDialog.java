package u.can.i.up.expert.utils;

import android.os.AsyncTask;

/**
 * Created by lczgywzyy on 2015/1/13.
 */
public class openDialog extends AsyncTask<String, Void, String> {
    String i = "";
    String j = "";
    public openDialog(String i, String j) {
        this.i = i;
        this.j = j;
    }
    @Override
    protected String doInBackground(String... params) {

//			  	  Intent intent = new Intent(getApplicationContext(), Dialog.class);
//			  	  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			  	  intent.putExtra("Title", i);
//			  	  intent.putExtra("Message", j);
//			  	  startActivity(intent);
//
//			        try {
//						getInputStreamFromUrl(URL + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("urlPost", "") + "UID=" + PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("AndroidID", "") + "&Data=", "Opened Dialog: " + i + " : " + j);
//					} catch (UnsupportedEncodingException e) {
//
//						e.printStackTrace();
//					}
//
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