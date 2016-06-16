package xodebox.food;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by shath on 6/5/2016.
 * Base singleton.
 * Use this class to share objects globally.
 * We should probably avoid using this class, and use
 * other communication meathods provided by Android
 */
public class XodeboxBase { //extends Application {
    private String LOG_TAG = XodeboxBase.class.toString();

    enum sessionManagerType{EMAIL_SESSION, GOOGLE_SESSION, FACEBOOK_SESSION}

    private sessionManagerType currentSessionManager;
    private static XodeboxBase ourInstance = new XodeboxBase();

    public static XodeboxBase getInstance() {
        return ourInstance;
    }

    // Instances of this class should not be created
    private XodeboxBase() {}

    private boolean userIsLoggedIn;
    private User currentUser;

    //Google API classes
    private GoogleSignInAccount googleSignInAccount;
    private GoogleApiClient mGoogleApiClient;

    /**
     * Checks whether the program is in 'LoggedIn' State.
     * @return true when user is logged in.
     */
    public boolean getLoginState(){return userIsLoggedIn;}
    public void setLoginState(boolean state){userIsLoggedIn=state;}

    /**
     * Sets the global sign in state
     * @param user Set the user attributes before passing it in.
     * @param session Type of the login session. Email, facebook or google.
     * @return True, on success.
     */
    public boolean signIn(User user, sessionManagerType session){
        currentUser = user;
        currentSessionManager = session;
        userIsLoggedIn = true;
        return true;
    }

    /**
     * Clear the global Sign in state
     * @return True on success. False with an error message log, on failure.
     */
    public boolean signOut()
    {
        currentUser = null;     //I'm assuming that java destroys unused objects.
        userIsLoggedIn = false;

        //Sign out for google session
        if(currentSessionManager == sessionManagerType.GOOGLE_SESSION)
        {
            //Check whether google api is connected before calling signout
            if(!mGoogleApiClient.isConnected()) {
                Log.e(LOG_TAG, "Google API must be connected before you call sign out.");
                return false;
            }

            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            //GOOGLE SIGNOUT
                        }
                    }
            );
            //Auth.
        }
        return true;
    }


    public void setGoogleSession(GoogleApiClient googleApiClient,GoogleSignInAccount googleSignInAccount)
    {
        mGoogleApiClient = googleApiClient;
        this.googleSignInAccount = googleSignInAccount;
    }


    public User getCurrentUser() {
        if (currentUser == null) return new User(null, "Unknown", "Unknown");   //Maybe we should throw an error..
        return currentUser;
    }

    /*
    public void setCurrentUser(User currentUser) { //setter for currentUser is not necessary yet.
        this.currentUser = currentUser;
    }
    */
}
