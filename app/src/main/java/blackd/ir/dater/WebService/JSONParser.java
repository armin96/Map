package blackd.ir.dater.WebService;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONParser {
    /********
     * URLS
     *******/
    private static final String MAIN_URL = "http://pratikbutani.x10.mx/json_data.json";
    /**
     * TAGs Defined Here...
     */
    public static final String TAG = "TAG";
    /**
     * Key to Send
     */
    private static final String KEY_USER_ID = "user_id";
    /**
     * Response
     */
    private static Response response;
    /**
     * Get Table Booking Charge
     *
     * @return JSON Object
     */
    public static JSONObject getDataFromWeb() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MAIN_URL)
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
}