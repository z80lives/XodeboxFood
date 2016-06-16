package xodebox.food;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private boolean userIsLoggedIn = false;
    final private String LOG_TAG = MainActivity.class.getName();
    final private XodeboxBase appBase = XodeboxBase.getInstance();


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

        //Configure toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Set up home screen fragment

        FrameLayout homeFrameLayout = (FrameLayout) findViewById(R.id.home_container);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.home_container, new HomeScreenFragment(), "home_fragment");
        fragmentTransaction.commit();

        /* Set button to listen to current class's event handler method. Add the buttons you want to
         * be handled by our local onClick method */
        int resources[] = {R.id.history_button, R.id.roll_dice_button, R.id.highlights_button, R.id.logout_button};
        for(int rId : resources)
        {
            try {
                View v = findViewById(rId);
                if (v != null) v.setOnClickListener(this);
            }catch(Exception ex)
            {
                Log.e(LOG_TAG, "(Resource not found), " + ex.getMessage() );
            }
        }

        //Set pull up menu data
        User currentUser = appBase.getCurrentUser();
        //ImageView profilePic = (ImageView) findViewById(R.id.pullout_profile_pic);
        //profilePic.setImageURI(currentUser.getPhotoUri());
        //if (currentUser.getPhoto() != null)
        //    profilePic.setImageBitmap(currentUser.getPhoto());
        updateUI();
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
            case R.id.history_button:
                startActivity(new Intent(this, UserRecordsActivity.class));
                return;
            case R.id.roll_dice_button:
                startActivity(new Intent(this, RollActivity.class));
                return;
            case R.id.highlights_button:
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
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/
        switch(item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.action_reset:
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        //Update user name
        User currentUser = appBase.getCurrentUser();
        TextView userName = (TextView) findViewById(R.id.pullout_profile_name);
        userName.setText(currentUser.getName());

        //Update profile picture
        ImageView profilePic = (ImageView) findViewById(R.id.pullout_profile_pic);
        Drawable drawablePic = currentUser.getDrawable();
        if(drawablePic!=null)
            profilePic.setImageDrawable(drawablePic);
        else
            profilePic.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);

    }

    public static class HomeScreenFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            inflater.inflate(R.layout.home_fragment, container);
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }
}
