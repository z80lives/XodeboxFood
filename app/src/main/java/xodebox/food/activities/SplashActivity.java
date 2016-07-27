package xodebox.food.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import xodebox.food.R;

/**
 * Created by Ibrahim shath on 5/28/2016.
 * Note: We don't need to create a layout for this activity, since this is just shown during
 * the load screen.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        this.setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        //startActivity(new Intent(this,login_registrationActivity.class));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
