package xodebox.food.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import xodebox.food.R;
import xodebox.food.ui.managers.DefaultViewSwitcher;
import xodebox.food.ui.managers.XodeboxViewManager;

public class RestaurantActivity extends AppCompatActivity {
    XodeboxViewManager viewManager;
    //View rootView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewManager = new DefaultViewSwitcher(this);

        View restaurantPage = getLayoutInflater().inflate(R.layout.restaurant_page, viewManager.getRootView(), false);
        View loaderView     = getLayoutInflater().inflate(R.layout.load_view, viewManager.getRootView(), false);

        //viewManager.addView(restaurantPage);
        viewManager.addView(restaurantPage);
        viewManager.addView(loaderView);

        viewManager.setLoadView(loaderView);
        viewManager.setReadyView(restaurantPage);
        //rootView = viewManager.getRootView();

        //setContentView(viewManager.getRootView(),
        //        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(viewManager.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewManager.setLoading(false);
    }
}
