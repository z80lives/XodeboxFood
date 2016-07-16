package xodebox.food.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import xodebox.food.R;
import xodebox.food.ui.adapters.ScreenPagerAdapter;

public class MainActivity extends FragmentActivity {

   // private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();
      //  actionBar = getActionBar();

        ScreenPagerAdapter screenPagerAdapter = new ScreenPagerAdapter(fm, getResources().getStringArray(R.array.nav_items));

        ViewGroup rootView = new LinearLayout(this);
        ViewPager screenPager = new ViewPager(this);

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

        //Create nav bar
        /* PagerTabStrip navStrip = new PagerTabStrip(this);
        navStrip.setLayoutParams(new ViewGroup.LayoutParams(PagerTitleStrip.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        screenPager.addView(navStrip); */

        TabLayout tabLayout = new TabLayout(this);

        //TabLayout.Tab tab = new tabLayout.newTab();
//        tabLayout.addTab(tabLayout.newTab().setText("Home"));


        tabLayout.setupWithViewPager(screenPager);
        //tabLayout.addTab(tabLayout.newTab().setText("More"));

        rootView.addView(tabLayout);

        setContentView(rootView, vgMatchParent);

    }





}
