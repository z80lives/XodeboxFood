package xodebox.food;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.UUID;

import xodebox.food.ui.Configurations;

public class login_registrationActivity extends AppCompatActivity {

    private String LOG_TAG = login_registrationActivity.class.getName();

    //firebase references for the app cloud database
    Firebase FirebaseRef;
    Context context;
    CallbackManager callbackManager;
    public static SharedPreferences prefsettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //prefsettings=getPreferences(Configurations.datapref,0);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.login_registration);
        Firebase.setAndroidContext(this);
        FirebaseRef = new Firebase("https://xodefood.firebaseio.com/");
        context = getApplicationContext();
        QuickMethods.ctx=context;
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile,email","user_friends"));


        //facebook login  code
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                final AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // process the data provided by google

                                try {
                                    Log.v("GraphLogbook",response.toString());
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    String link=object.getString("link");
                                    String gender=object.getString("gender");
                                    Log.v("GraphFacebook","name:"+name);
                                    Log.v("GraphFacebook","email:"+email);
                                    Log.v("GraphFacebook","link:"+link);
                                    Log.v("GraphFacebook","gender:"+gender);
                                    Register_user(email,accessToken.getToken());

                                } catch (JSONException e) {
                                    Log.v("GraphFacebook","failed"+e.getMessage());
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,name,link,email,gender");
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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        HttpRequest request = new HttpRequest(this);

        switch (btn.getId()) {

            //http://stackoverflow.com/questions/28120029/how-can-i-return-value-from-function-onresponse-of-volley
            //http://www.javaworld.com/article/2077462/learn-java/java-tip-10--implement-callback-routines-in-java.html
            case R.id.register_btn:

                Register_user(username_field,password_field);

                break;

            case R.id.Login_btn:
                Login_user(username_field,password_field);
                break;
        }
    }



   /**
    * open the next activity after the login/registration process
   **/
    public void GoToNextActivity(){
        Intent intent = new Intent(context,mainscreen.class);
        startActivity(intent);
    }


    public void Login_user(String username,String password_token){
        String URL="http://foodapp.xodebox.com/?type=2&email="+username+"&pass="+password_token;
        HttpRequest request=new HttpRequest(context);
        request.SendGetrequest(URL, new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.v("Volley", "Response is: " + response);
                        if(response.compareTo("true")==0){
                            QuickMethods.Toast("login succesfull");
                            GoToNextActivity();

                        }

                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Volley", "Response is: " + error.getMessage());

                    }
                }
        );

    }

    public void Register_user(final String username, final String password_token){
        String guid=UUID.randomUUID().toString();
        Log.v("guid",guid);
        String URL="http://foodapp.xodebox.com/?type=1&email="+username+"&pass="+password_token+"&guid="+guid;
        Log.v("URLvolley",URL);
        HttpRequest request=new HttpRequest(this);
        request.SendGetrequest(URL, new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.v("Volley", "Response is: " + response);
                        if(response.compareTo("true")==0){
                            QuickMethods.Toast("Registration complete,attempting login...");
                            Login_user(username,password_token);
                        }
                    }
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //chances are the user has alredy created an account
                        Login_user(username,password_token);
                        Log.v("Volley", "Response is: " + error.getMessage());

                    }
                }
        );



    }

}
