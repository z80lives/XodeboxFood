package xodebox.food;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private boolean userIsLoggedIn = false;
    private String LOG_TAG = MainActivity.class.getName();
    private XodeboxBase appBase = XodeboxBase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Go to login screen first
        /* TODO: The following method must be replaced. This implemented is here just for the prototype.
        *  Get the user id, and information from wherever it is stored.*/
        /*
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        userIsLoggedIn = prefs.getBoolean(getString(R.string.user_login_state), false);

        if(!userIsLoggedIn){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(new Intent(loginIntent));
            finish();
            return;
        }*/

        boolean userIsLoggedIn = appBase.getLoginState() ;
        if(!userIsLoggedIn){
            askForLogin();
            return;
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Set button to listen to current class's event handler method. Add the buttons you want to
         * be handled by our local onClick method */
        int resources[] = {R.id.hButton, R.id.logout_button, R.id.viewAnimator};
        for(int rId : resources)
        {
            findViewById(rId).setOnClickListener(this);
        }

        User currentUser = appBase.getCurrentUser();
        ImageView profilePic = (ImageView) findViewById(R.id.pullout_profile_pic);
        profilePic.setImageURI(currentUser.getPhotoUri());
    }

    /**
     * Handles click event. The views/buttons must be assigned first to use this method.
     * Add the view/button
     * @param v View clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.hButton:
                Log.v(LOG_TAG, "Highlights button pressed!");
                return;
            case R.id.logout_button:
                Log.v(LOG_TAG, "Log out button pressed");
                signOut();
                updateUI();
                return;
            case R.id.viewAnimator:
                Toast.makeText(this, "View restaurant", Toast.LENGTH_SHORT).show();
                return;
            default:


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**This method is used only for debugging purpose. Remember to remove it as soon as
     * the authentication code is written
     * */
    public void clearSettings(){
        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(this).edit();
        prefs.clear();
        prefs.apply();
        //userIsLoggedIn = prefs.getBoolean(getString(R.string.user_login_state), false);
        //userIsLoggedIn = false;

    }

    /**
     * Logs out of current session.
     */
    private void signOut(){
        appBase.signOut();
        askForLogin();
    }

    /**
     * Goto the login state without regard to the current User state
     */
    private void askForLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     * Start login activity if there is no user signed in.
     */
    private void updateUI(){
        if(!appBase.getLoginState()) {
            askForLogin();
        }else{
            updatePullOutMenu();
        }
    }

    /**
     * Updates pull out menu
     */
    private void updatePullOutMenu(){
        TextView username = (TextView) findViewById(R.id.pullout_profile_name);
        ImageView imgView = (ImageView) findViewById(R.id.restaurant_image);

        User currentUser = appBase.getCurrentUser();
        username.setText(currentUser.getName());

        imgView.setImageURI(currentUser.getPhotoUri());
    }

}
