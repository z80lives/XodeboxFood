package xodebox.food.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import xodebox.food.R;
import xodebox.food.ui.adapters.ScreenPagerAdapter;
import xodebox.food.ui.interfaces.ActivityWithSearchView;
import xodebox.food.ui.nav.NavBar;
import xodebox.food.ui.view.SearchResultView;
import xodebox.food.ui.viewpagers.MainscreenViewPager;

/**
 * Main activity
 */
public class MainActivity extends FragmentActivity implements ActivityWithSearchView {

   // private ActionBar actionBar;
    private SearchResultView searchViewGroup;
    private SearchView searchView;


    private ViewGroup rootView;
    private ViewGroup MasterView;
    private ViewGroup currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();

        NavBar navBar = new NavBar(this);
        //createSearchView();

        //TODO: Refactor searchView out of Main Activity and let SearchResultView Handle it.
        searchViewGroup = new SearchResultView(this);
        searchView = searchViewGroup.getSearchView();
        setSearchCloseButton();

        ScreenPagerAdapter screenPagerAdapter = new ScreenPagerAdapter(fm, getResources().getStringArray(R.array.nav_items), navBar);

        MasterView = new LinearLayout(this);
        ViewGroup rootView = new LinearLayout(this);
        ViewPager screenPager = new MainscreenViewPager(this);
        this.rootView = rootView;

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

        //This nav bar looks cool. Maybe we can implement it in future.
        /* Nav strip
         PagerTabStrip navStrip = new PagerTabStrip(this);
        navStrip.setLayoutParams(new ViewGroup.LayoutParams(PagerTitleStrip.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        screenPager.addView(navStrip);
        */


        //Add the bottom navigation bar
        screenPager.setOffscreenPageLimit(4);       //Fixme: Cache the data and destroy the fragments instead.


        //Add bottom navigation bar
        navBar.setupWithViewPager(screenPager);
        rootView.addView(navBar);

        MasterView.addView(rootView);
        MasterView.addView(searchViewGroup);
        setContentView(MasterView, vgMatchParent);

        showSearchbar(false);       //Just to set the current View
        this.rootView = rootView;
    }

    boolean doubleBackToExitPressedOnce = false;

    private void setSearchCloseButton(){
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                showSearchbar(false);
                return false;
            }
        });
    }

    /**
     * Code when back button is pressed on main activity
     */
    @Override
    public void onBackPressed() {
        if(currentView == searchViewGroup)
        {
            showSearchbar(false);
            return;
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        //super.onBackPressed();
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void searchButtonClicked(){
        showSearchbar(true);
    }

    /**
     * This method is public because it
     * @param state
     */
    public void showSearchbar(boolean state){
        if (state){
            showView(searchViewGroup);
            hideView(rootView);
            currentView = searchViewGroup;
            searchView.setIconified(false);
            return;
        }
        showView(rootView);
        hideView(searchViewGroup);
        currentView = rootView;
    }

    private void hideView(View v){
        v.setVisibility(View.GONE);
    }

    private void showView(View v){
        v.setVisibility(View.VISIBLE);
    }

}
