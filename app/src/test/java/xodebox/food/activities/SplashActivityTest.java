package xodebox.food.activities;


import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import xodebox.food.BuildConfig;

import static junit.framework.Assert.*;

/**
 * Created by shath on 7/23/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class SplashActivityTest {
    private SplashActivity splashActivity;

    @Before
    public void setup(){
        splashActivity = Robolectric.setupActivity(SplashActivity.class);
    }

    @Test
    public void activityCheck(){
        assertNotNull("Activity was not created ", splashActivity);
    }
}