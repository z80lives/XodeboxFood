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
import xodebox.food.ui.nav.NavBar;
import xodebox.food.ui.view.SearchResultView;
import xodebox.food.ui.viewpagers.MainscreenViewPager;

/**
 * Main activity
 */
public class MainActivity extends FragmentActivity {

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

    /**
     * Create the screen to display search result
     * @return
     */
    /*
    private ViewGroup createSearchView(){
        String[] test = {"Cafe 1", "Dim light"};
        List<String> lstData = new ArrayList<>();
        lstData.addAll(Arrays.asList(test));

        searchViewGroup = new LinearLayout(this);
        ((LinearLayout)searchViewGroup).setOrientation(LinearLayout.VERTICAL);
        ((LinearLayout)searchViewGroup).setWeightSum(1);


        LinearLayout.LayoutParams layoutParam  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        ((LinearLayout)searchViewGroup).setLayoutParams(layoutParam);


        final SearchView searchView = new SearchView(this);

        //set dimensions param for search bar
        LinearLayout.LayoutParams itemLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
       // itemLayoutParams.setMargins(0,0,0,10);

        //set dimensions for search result
        ListView searchResults = new ListView(this);
        searchResults.setDividerHeight(10);
        LinearLayout.LayoutParams resultParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 0.9f);
        resultParams.setMargins(0,40,0,0);
        searchResults.setLayoutParams(resultParams);

        final ArrayAdapter<String> searchResultAdapter = new ArrayAdapter<String>(this, R.layout.home_search_result_item,
                R.id.name, lstData);

        searchResults.setAdapter(searchResultAdapter);

        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        searchView.setLayoutParams(itemLayoutParams);
        searchView.setIconified(false);
        searchView.setBackgroundColor(getResources().getColor(android.R.color.background_light));
        EditText searchField = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        if(searchField == null){
            searchField = new EditText(this);
            searchView.addView(searchField);
            searchField.setId(android.support.v7.appcompat.R.id.search_src_text);
        }
        searchField.requestFocus();


        searchViewGroup.addView(searchView);
        searchViewGroup.addView(searchResults);
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchResultAdapter.add(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                showSearchbar(false);
                return false;
            }
        });

        this.searchView = searchView;
        return searchViewGroup;
    }*/

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
