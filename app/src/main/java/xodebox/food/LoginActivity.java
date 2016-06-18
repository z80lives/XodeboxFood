package xodebox.food;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

//import android.support.v4.widget.MaterialProgressDrawable;
//import android.support.v7.util.ThreadUtil;

/**
 * A login screen that offers login via email/password.
 * (This code needs to be refactored )
 */
public class LoginActivity extends FragmentActivity implements LoaderCallbacks<Cursor>, GoogleApiClient.OnConnectionFailedListener,
        OnClickListener{
    private String LOG_TAG = this.getClass().getName();
    private XodeboxBase appBase = XodeboxBase.getInstance();
    private enum LocalView {welcome, register, logIn}
    private LocalView currentView;

    /**register
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private static final int RC_SIGN_IN = 9001;             //Arguement for google login intent

    private GoogleApiClient mGoogleApiClient;

    /* TODO unpack and show register_fragment */


    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "debug@xodebox.com:welcome", "test@sandbox.com:qwerasdzx123"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private boolean  isLoginFormSetup = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentView = LocalView.logIn;

        //Prepare google sign in options
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


        setContentView(R.layout.activity_login);
        //Assign class variables
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        setupLoginForm();

        //showWelcomeScreen(true);
        changeScreen(LocalView.welcome);
/*
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

*/
        /* Assign click event handlers*/
/*        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
*/
        /*
        //Sign in by e-mail
        Button mEmailSignInButton = (Button) findViewById(R.id.email_login_button);


        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        // Google sign in
        SignInButton signInButton = (SignInButton) findViewById(R.id.google_login_button);
        signInButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        //Register button test
        Button regButton = (Button) findViewById(R.id.btn_register);
        regButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterScreen(true);
            }
        });

     */



    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.welcome_register:
                changeScreen(LocalView.register);
                return;
            case R.id.welcome_signin:
                changeScreen(LocalView.logIn);
                return;
            case R.id.regform_btn_back:
                changeScreen(LocalView.welcome);
                return;
            case R.id.regform_btn_submit:
                Toast.makeText(this, "Cannot register", Toast.LENGTH_SHORT).show();
                return;
            case R.id.google_login_button:
                //Google sign in intent
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
                return;
        }
    }

    /**
     * Set up the login screen
     */
    private void setupLoginForm(){
        if(currentView != LocalView.logIn)
        {
            Log.w(LOG_TAG, "currentView must be login, before calling this function.");
        }
        if(isLoginFormSetup) {
            Log.w(LOG_TAG, "Login form setup should be called once.");
            return;
        }

        // Set up the email field
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        //Set up the password field
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        //Sign in by e-mail
        Button mEmailSignInButton = (Button) findViewById(R.id.email_login_button);


        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        // Google sign in
        SignInButton signInButton = (SignInButton) findViewById(R.id.google_login_button);
        signInButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        //Register button test
        Button regButton = (Button) findViewById(R.id.btn_register);
        regButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //showRegisterScreen(true);
                changeScreen(LocalView.register);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Process results from google sign in intent <GoogleSignInApi.getSignInIntent(...)>
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResults(result);
        }
    }

    /**
     * Handles Google Sign in results.
     * @param results GoogleSignInResults processed from the async process.
     */
    private void handleGoogleSignInResults(GoogleSignInResult results) {
        if (results.isSuccess()) {
            //TODO Google sign in success!
            //appBase.setLoginState(true);
            GoogleSignInAccount googleSignInAccount = results.getSignInAccount();
            appBase.setGoogleSession(mGoogleApiClient, googleSignInAccount);
            User currentUser = new User(googleSignInAccount.getId(),
                    googleSignInAccount.getDisplayName(),
                    googleSignInAccount.getEmail());

            //.getPhotoUrl() will return a URL to a JSON file, which contains the photoUri path.
            Uri photoUri = googleSignInAccount.getPhotoUrl();
            try {
                URL photoURL = new URL(googleSignInAccount.getPhotoUrl().toString());
                RetrieveProfilePicture retrieveProfilePictureTask = new RetrieveProfilePicture(this);
                retrieveProfilePictureTask.execute(photoURL);
            }catch(MalformedURLException ex) {
                Log.e(LOG_TAG, "Malformed URL:" + ex.getMessage());
            }catch (NullPointerException ex){
                Log.e(LOG_TAG, "Null pointer exception: " + ex.getMessage());
            }

            currentUser.setPhotoUri(photoUri);


            //Suggestion: We can use start an async task to retrieve the photo and set the photo once the job is done.
            Log.v(LOG_TAG, photoUri.toString());        //photoUri may be null
            //if(photoUri== null)
                //photoUri = R;


            currentUser.setPhotoUri(photoUri);  //Extract the correct Photo Uri from this link // FIXME: 6/9/16
            
            signIn(currentUser,XodeboxBase.sessionManagerType.GOOGLE_SESSION);
            //Toast.makeText(getApplicationContext(), "Google sign in sucess!", Toast.LENGTH_SHORT).show();
        }else
        {
            appBase.setLoginState(false);
            //TODO Google sign out
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            Toast.makeText(getApplicationContext(), "Google sign in failed!", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "Make sure google-services.json is valid.");
        }
    }



    /**
     *  AsyncTask to download the profile picture and save it in current user
     */
    public static class RetrieveProfilePicture extends AsyncTask<URL, Void, Drawable>
    {
        ProgressDialog progressDialog;
        private Activity activity;

        public RetrieveProfilePicture(Activity activity) {
            super();
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            progressDialog = new ProgressDialog(activity.getApplicationContext());
            progressDialog.setMessage("Fetching your profile..");
            progressDialog.setCancelable(false);
            progressDialog.show();*/
        }

        private String LOG_TAG = this.getClass().toString();
        User currentUser;


        @Override
        protected Drawable doInBackground(URL... params) {
            try {
                URL photoURL = params[0];

                currentUser = XodeboxBase.getInstance().getCurrentUser();
                //Bitmap profilePic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                InputStream inputStream = (InputStream) photoURL.getContent();
                Drawable profilePic = (Drawable) Drawable.createFromStream(inputStream, null);
                currentUser.setDrawable(profilePic);
                return profilePic;
            } catch (Exception ex) {
                Log.e(LOG_TAG, "Exception: " + ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            //progressDialog.cancel();
            super.onPostExecute(drawable);
            currentUser.setDrawable(drawable);

        }
    }




    /**
     * This method will be called when the google connection fails.
     * Inherited from GoogleApiClient.OnConnectionFailedListener class
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google API Client: Connection failed.");
    }


    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Validate e-mail
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Validate password
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     *
     * Once the authentication process is complete this method should be called.
     * Prepare the current_user's data before calling this method.
     * @param current_user Contains the personal data that will be shared by the activities
     * @return True on success.
     */
    private boolean signIn(User current_user, XodeboxBase.sessionManagerType sessionType){
        if (appBase.signIn(current_user, sessionType)) {
            Log.v(LOG_TAG, "Login state = "+appBase.getLoginState());
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return true;
        }
        return false;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            Log.v(LOG_TAG, "Credentials not found. Do you want to register a new account.");
            return false;

            //return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {      //doInBackground(..) returned true
                /*SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit();
                prefs.putBoolean(getString(R.string.user_login_state), true);
                prefs.apply();*/
                User user = new User(null, null, mEmail);
                signIn(user, XodeboxBase.sessionManagerType.EMAIL_SESSION);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }



    }
/*
    public void showRegisterScreen(boolean visibility){
        //Fragment transaction using android.support
        //android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.replace(R.id.login_screen_container, new LoginActivity.RegisterScreenFragment());
        //ft.commit();
        android.app.FragmentManager fm = getFragmentManager();
        FrameLayout fl = (FrameLayout) findViewById(R.id.login_screen_container);
        FragmentTransaction ft = fm.beginTransaction();
        //if (visibility) {
            fl.setVisibility(visibility? View.VISIBLE: View.INVISIBLE);
            mLoginFormView.setVisibility(visibility? View.GONE: View.VISIBLE);
            if(visibility)
                currentView = LocalView.register;
            else
                currentView = LocalView.logIn;

            ft.replace(R.id.login_screen_container, new RegisterScreenFragment());
            //ft.add(R.id.login_form, new RegisterScreenFragment());
            ft.commit();

        //}
    }*/

    /**
     * Changes View.
     * TODO Refactor: Replace the fragments, with a single container instead of using the visibility trick.
     * @param targetScreen
     */
    private void changeScreen(LocalView targetScreen)
    {
        //android.app.FragmentManager fm = getFragmentManager();
        FragmentManager fm = getSupportFragmentManager();
        FrameLayout register_container = (FrameLayout) findViewById(R.id.register_screen_container);
        FrameLayout welcome_container = (FrameLayout) findViewById(R.id.welcome_screen_container);
        View login_form = mLoginFormView;
//        FragmentTransaction ft = fm.beginTransaction();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();

        android.support.v4.app.Fragment registerFragment = fm.findFragmentByTag("register_fragment");

        // This can also be implemented through state tables
        switch(targetScreen) {
            case register:
                currentView = LocalView.register;
                welcome_container.setVisibility(View.GONE);
                login_form.setVisibility(View.GONE);
                register_container.setVisibility(View.VISIBLE);
                if(registerFragment == null) {
                    registerFragment = new RegisterScreenFragment();
                    ft.replace(R.id.register_screen_container, registerFragment, "register_fragment");
                    ft.addToBackStack("register_fragment");
                }
                Log.v(LOG_TAG, "Displaying Register screen");
                break;
            case welcome:
                register_container.setVisibility(View.GONE);
                login_form.setVisibility(View.GONE);
                welcome_container.setVisibility(View.VISIBLE);
                Log.v(LOG_TAG, "Displaying Welcome screen");
                if(currentView == LocalView.register)
                {
 //                   fm.popBackStack();
                }else {
                    currentView = LocalView.welcome;
                    ft.replace(R.id.welcome_screen_container, new WelcomeScreenFragment(), "welcome_fragment");
                    ft.addToBackStack(null);
                }
                //ft.replace(R.id.login_screen_container, welcomeFragment, welcomeFragment.getTag());
                break;
            case logIn:
                register_container.setVisibility(View.GONE);
                welcome_container.setVisibility(View.GONE);
                login_form.setVisibility(View.VISIBLE);
                Log.v(LOG_TAG, "Displaying Login screen");
                currentView = LocalView.logIn;
                break;
        }
        ft.commit();

        //Show login container if current state is not login_screen
       // login_container.setVisibility(currentView!=LocalView.logIn? View.VISIBLE: View.GONE);
        //Show login screen if current state is login screen
        //login_form.setVisibility(currentView == LocalView.logIn? View.VISIBLE: View.GONE);
    }


    /**
     *  Register screen fragment
     *
     */
    public static class RegisterScreenFragment extends android.support.v4.app.Fragment{
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //return inflater.inflate(R.layout.register_fragment, container);
            //return super.onCreateView(inflater, container, savedInstanceState);
            inflater.inflate(R.layout.register_fragment, container);

            //Use local click handler for these buttons
            int res_buttons[] = {R.id.regform_btn_submit, R.id.regform_btn_back};
            for(int res_button: res_buttons)
            {
                Button btn = (Button) getActivity().findViewById(res_button);
                btn.setOnClickListener((LoginActivity) getActivity());
            }
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        /*
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.regform_btn_submit:
                    return;
                case R.id.regform_btn_back:

                    return;
                default:
                    //Unhandled click
            }
        }*/
    }

    public static class WelcomeScreenFragment extends android.support.v4.app.Fragment
    {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
            inflater.inflate(R.layout.welcome_fragment, container);
            int idButtons[] = {R.id.welcome_register, R.id.welcome_signin };
            for(int id: idButtons)
            {
                Button btn = (Button) getActivity().findViewById(id);
                btn.setOnClickListener((LoginActivity) getActivity());
            }

            getActivity().findViewById(R.id.google_login_button).setOnClickListener( (LoginActivity) getActivity());

            //Handle view flipper for test
            final ViewFlipper viewFlipper = (ViewFlipper) getActivity().findViewById(R.id.restaurant_flipper);

            viewFlipper.setOnTouchListener(new View.OnTouchListener() {
                private float lastX;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction()){
                        case MotionEvent.ACTION_UP:
                            lastX = event.getX();
                            return true;
                        case MotionEvent.ACTION_DOWN:
                            float current_x = event.getX();

                            //Left to right screen swap
                            if(lastX < current_x)
                            {
                                //set the animation here
                                viewFlipper.showNext();

                            }else
                            //Right to left screen swap
                            {
                                //set the animation here
                                viewFlipper.showPrevious();
                            }

                        default:
                            return false;
                    }
                }
            });

            /**
            viewFlipper.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewFlipper.showNext();
                }
            });**/

            return super.onCreateView(inflater, container, savedInstanceState);
        }
/**
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.welcome_register:
                    getActivity().changeScreen();
                    return;
                case R.id.welcome_signin:

                    return;
                default:
            }
        }**/
    }

/*    public class WelcomeGalleryAdapter extends FragmentPagerAdapter
    {

    }*/

/*
    public static class DownloadProfilePic extends AsyncTask<Uri, Void, Void>
    {
        @Override
        protected Void doInBackground(Uri... params) {
            return null;
        }
    }*/

}

