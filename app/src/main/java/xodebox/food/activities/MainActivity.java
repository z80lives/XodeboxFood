package xodebox.food.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import xodebox.food.R;
import xodebox.food.ui.adapters.ScreenPagerAdapter;
import xodebox.food.ui.nav.NavBar;
import xodebox.food.ui.viewpagers.MainscreenViewPager;

public class MainActivity extends FragmentActivity {

   // private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();

        NavBar navBar = new NavBar(this);

        ScreenPagerAdapter screenPagerAdapter = new ScreenPagerAdapter(fm, getResources().getStringArray(R.array.nav_items), navBar);

        ViewGroup rootView = new LinearLayout(this);
        ViewPager screenPager = new MainscreenViewPager(this);

        ViewGroup.LayoutParams vgMatchParent = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams childLayoutParam= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);

        //Set the properties for the root Linear layout
        ((LinearLayout) rootView).setWeightSum(1);
        ((LinearLayout) rootView).setOrientation(LinearLayout.VERTICAL);

        //Set the properties for the ViewPager
        screenPager.setId(R.id.screen_pager_id);

        //screenPager.setLayoutParams(new ViewGroup.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        childLayoutParam.weight = 1;
        screenPager.setLayoutParams(childLayoutParam);
        screenPager.setAdapter(screenPagerAdapter);

      //  screenPager.setBackgroundColor(getColor(R.color.cardview_dark_background));

        rootView.addView(screenPager);

        //setContentView(R.layout.home_screen);

        //This one looks cool. Maybe we can implement it in future.
        /* Nav strip
         PagerTabStrip navStrip = new PagerTabStrip(this);
        navStrip.setLayoutParams(new ViewGroup.LayoutParams(PagerTitleStrip.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        screenPager.addView(navStrip);
        */


        //Add the bottom navigation bar
        screenPager.setOffscreenPageLimit(4);       //Fixme: Cache the data and destroy the fragments instead.


        navBar.setupWithViewPager(screenPager);
        rootView.addView(navBar);

        setContentView(rootView, vgMatchParent);

    }





}
