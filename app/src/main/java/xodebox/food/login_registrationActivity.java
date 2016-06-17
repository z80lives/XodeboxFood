package xodebox.food;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.UUID;

public class login_registrationActivity extends AppCompatActivity {

    private String LOG_TAG = login_registrationActivity.class.getName();

    //firebase references for the app cloud database
    Firebase FirebaseRef;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_registration);

        Firebase.setAndroidContext(this);

        FirebaseRef = new Firebase("https://xodefood.firebaseio.com/");
        context = getApplicationContext();


        CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile,user_birthday,email"));


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // process the data provided by google

                                Log.v("GraphFacebook",object.toString());

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();



            }

            @Override
            public void onCancel() {

                Log.v("GraphFacebook","cancelled");

            }

            @Override
            public void onError(FacebookException error) {

                Log.v("GraphFacebook",error.getMessage());

            }
        });

        Log.v(LOG_TAG, "Activity created.");
    }


    //handles button clicks
    public void Login_register_account(View btn) {

        String URL;
        RequestQueue queue = Volley.newRequestQueue(context);
        //remove space in the credentials
        String username_field = ((EditText) findViewById(R.id.user_txt)).getText().toString();
        String password_field = ((EditText) findViewById(R.id.pass_txt)).getText().toString();
        String Token = FirebaseInstanceId.getInstance().getToken();
        Log.d("GCM-Token", "Token:" +Token);
        HttpRequest request = new HttpRequest("http://foodapp.xodebox.com/", this);
       // request.SendGetrequest("http://foodapp.xodebox.com/?token="+Token);

        switch (btn.getId()) {


            case R.id.register_btn:
                    String guid=UUID.randomUUID().toString();
                    Log.v("guid",guid);
                URL="http://foodapp.xodebox.com/?type=1&email="+username_field+"&pass="+password_field+"&guid="+guid;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Log.v("Volley", "Response is: " + response);

                                if(response.compareTo("true")==0){
                                    Toast("Registration complete");
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Volley", "Response is: " + error.getMessage());

                    }
                });
                queue.add(stringRequest);


                break;

            case R.id.Login_btn:
               URL="http://foodapp.xodebox.com/?type=2&email="+username_field+"&pass="+password_field;
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Log.v("Volley", "Response is: " + response);
                                if(response.compareTo("true")==0){
                                Toast("login succesfull");

                                    Intent intent = new Intent(context,mainscreen.class);
                                    startActivity(intent);

                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Volley", "Response is: " + error.getMessage());

                    }
                });
                queue.add(stringRequest2);
                break;


        }


    }


    public void Toast(String msg){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();

    }

}
