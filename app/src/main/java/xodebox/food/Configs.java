package xodebox.food;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by MaxAhirwe on 26/07/16.
 */
public abstract class Configs {

    static final String BackEndUrl="http://foodapp.xodebox.com/";
    //used for location and other google api services
    static GoogleApiClient mGoogleApiClient;
    static Location UserLastLocation;
    static final int LocationPersmissionRequestCode=123;


}
