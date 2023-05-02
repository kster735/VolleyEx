package gr.uniwa.volleyex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    public final String SERVER ="https://4epal-peiraia.att.sch.gr/testPhp/";
    private RequestQueue queue;

    public ArrayList<Customer> pelates;

    private RecyclerView recyclerCustomers;

    // LinearLayout ?

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pelates = new ArrayList<>();

        // Εδώ απλώς εξασφαλίζεται το session ή αλλιώς η σύνδεση με την απομακρυνσμένη βάση
        // δεδομένων.
        queue = AppSingleton.getInstance(MainActivity.this).getRequestQueue();
        createSession();

        recyclerCustomers = (RecyclerView) findViewById(R.id.myRecycler);
        myAdapter = new MyAdapter(pelates, this);
        // Ο πίνακας pelates τελικά δε γεμίζει εδώ, αλλά στην parseJSONMessage(response) που καλείται
        // από τον responseListener
    }

    private void createSession() {
        Map params = new HashMap();
        String sss = "test_app";
        params.put("unique_id", sss);
        Log.d(TAG, "create Session uri = " + SERVER+"create_session.php");
        callServerPost(SERVER+"create_session.php", params);
    }

    public void callServerPost(String uri, Map postParameters) {
        StringRequest stringEx = new MyStringRequest(MainActivity.this,
                Request.Method.POST,
                uri,
                postParameters,
                responseListener,
                errorListener);

        stringEx.setTag(TAG);
        queue.add(stringEx);
    }



    public void getInfo() {
        Map params = new HashMap();
        callServerPost(SERVER + "get_info.php", params);
    }


    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error instanceof NetworkError) {
                Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(getApplicationContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_LONG).show();
            } else if (error instanceof NoConnectionError) {
                Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_LONG).show();
            } else if (error instanceof TimeoutError) {
                Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_LONG).show();
            }
        }
    };


    Response.Listener responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            parseJSONMessage(response);
        }
    };

    private synchronized void parseJSONMessage(String response) {
        try {
            Log.d(TAG, "response: " + response);
            JSONObject obj = new JSONObject(response);
            int type = Integer.parseInt(obj.getString("type"));
            switch (type) {
                case 0:   // create session
                    getInfo();
                    break;
                case 100:
                    JSONArray customers =  obj.getJSONArray("customers");
                    for (int i=0; i<customers.length(); i++) {
                        Customer pelatis = new Customer();
                        JSONObject actobj = new JSONObject(customers.getString(i));
                        pelatis.setLastName(actobj.getString("lastName"));
                        pelatis.setFirstName(actobj.getString("firstName"));
                        pelatis.setRegDate(actobj.getString("regDate"));
                        pelates.add(pelatis);
                        Log.d(TAG, "parseJSONMessage: " + pelatis.toString());
                    }

                    // Τώρα έχει γεμίσει ο πίνακας pelates οπότε εδώ περνάμε τον adapter του πίνακα
                    // μέσα στον recyclerView
                    recyclerCustomers.setAdapter(myAdapter);
                    // Τελικά χρειάζεται και layoutManager για να φανούν τα δεδομένα στον RecyclerView
                    recyclerCustomers.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    break;

            }
        } catch (Throwable t) {
            Log.d(TAG, "Could not parse malformed JSON: \"" + response + "\""+ t.getMessage());
        }
    }
}