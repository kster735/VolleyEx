package gr.uniwa.volleyex;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyStringRequest extends com.android.volley.toolbox.StringRequest{
    private final String TAG = this.getClass().getSimpleName();
    private final Map<String, String> _params;
    private static final String SET_COOKIE_KEY = "**********************";
    private static final String COOKIE_KEY = "******************";
    private static final String SESSION_COOKIE = "************************";
    private Activity activity;

    /**
     * @param method
     * @param url
     * @param params
     *            A {@link HashMap} to post with the request. Null is allowed
     *            and indicates no parameters will be posted along with request.
     * @param listener
     * @param errorListener
     */
    public MyStringRequest(Activity activity, int method, String url, Map<String, String> params, Response.Listener<String> listener,
                           Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.activity=activity;
        _params = params;
    }

    public MyStringRequest(int method, String url, Map<String, String> params, Response.Listener<String> listener,
                           Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        _params = params;
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }

    @Override
    protected Map<String, String> getParams() {
        return _params;
    }

    /* (non-Javadoc)
     * @see com.android.volley.toolbox.StringRequest#parseNetworkResponse(com.android.volley.NetworkResponse)
     */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        // since we don't know which of the two underlying network vehicles
        // will Volley use, we have to handle and store session cookies manually
     //   Log.d(TAG, "inside parseNetworkResponse");
      checkSessionCookie(response.headers);

        return super.parseNetworkResponse(response);
    }

    /* (non-Javadoc)
     * @see com.android.volley.UserAction#getHeaders()
     * Φτιάχνω το header
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }


        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        // εδώ βάζω το cookie στον header φια να φύγει
        addSessionCookie(headers);
        return headers;
    }

    public final void checkSessionCookie(Map<String, String> headers) {
       // if (headers.containsKey(SET_COOKIE_KEY)){
           // Log.d(TAG, "checkSessionCookie: headers.containsKey(SET_COOKIE_KEY)");
           // String c = headers.get(SET_COOKIE_KEY);
           // Log.d(TAG, "checkSessionCookie: cookie="+c);
           // Log.d(TAG, "headers.get(SET_COOKIE_KEY).substring(0,9)="+headers.get(SET_COOKIE_KEY).substring(0,9));
           // if(  headers.get(SET_COOKIE_KEY).substring(0,9).endsWith(SESSION_COOKIE) /* .startsWith(SESSION_COOKIE)*/  ){
           //     Log.d(TAG, "checkSessionCookie: headers.containsKey(SET_COOKIE_KEY) and startsWith(SESSION_COOKIE)");
           // }else{
            //    Log.d(TAG, "checkSessionCookie: headers.containsKey(SET_COOKIE_KEY) and not startsWith(SESSION_COOKIE)");
           // }
       // }else{
           // Log.d(TAG, "checkSessionCookie:  headers havn't COOKIE_KEY");
       // }
        if (headers.containsKey(SET_COOKIE_KEY) && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            //Log.d(TAG, "checkSessionCookie:  headers.containsKey(SET_COOKIE_KEY)");
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                //Log.d(TAG, "checkSessionCookie: cookie.length() > 0");
                cookie = cookie.split(";")[0];
                //cookie = cookie.split(";")[0].split("=")[1];
                //Log.d(TAG, "checkSessionCookie:  cookie="+cookie);
                AppSingleton.cookie=cookie;
            }else{
              //  Log.d(TAG, "checkSessionCookie:  cookie.length() = 0");
            }
        }else{
           // Log.d(TAG, "checkSessionCookie:  headers havn't COOKIE_KEY");
        }
    }



    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */
    public final void addSessionCookie(Map<String, String> headers) {
       // settings= this.activity.getSharedPreferences(PREFS_NAME, 0);
        if(AppSingleton.cookie!=null){
         //   Log.d(TAG,"addSessionCookie: "+StigmAppSingleton.cookie);
            headers.put(COOKIE_KEY, AppSingleton.cookie);
        }
    }
}
