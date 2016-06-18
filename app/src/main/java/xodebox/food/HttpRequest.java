package xodebox.food;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by MAX on 2/6/2016.
 */
public class HttpRequest {

    String ServerURL = "http://foodapp.xodebox.com/";
    Context context;
    String Latestresponse;

    public HttpRequest(String Server,Context context) {
        this.ServerURL = Server;
        this.context = context;
    }


    public void SendPostrequest( final Map<String, String> data) {

        //send a request to our server to check if volley is handling requests properly
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.v("Volley", "Response is: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.v("Volley", "Response is: " + error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams()  {
                //Map<String,String> params = new HashMap<String, String>();
                //params.put("token","");
                return data;
                //return super.getParams();
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    public String SendGetrequest(final String URL) {
        //send a request to our server to check if volley is handling requests properly
        //Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.v("Volley", "Response is: " + response);
                        Latestresponse=response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Volley", "Response is: " + error.getMessage());
                Latestresponse=error.getMessage();
            }
        });
        queue.add(stringRequest);

        return Latestresponse;
}

}
