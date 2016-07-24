package xodebox.food.ui.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import xodebox.food.ui.fragments.CollectionsScreenFragment;
import xodebox.food.ui.fragments.DynamicScreenFragment;
import xodebox.food.ui.fragments.HomeScreenFragment;
import xodebox.food.ui.fragments.MoreScreenFragment;
import xodebox.food.ui.fragments.NewsFeedFragment;
import xodebox.food.ui.nav.NavBar;

/**
 * Created by shath on 6/29/2016.
 * FIXME The class does not instantiate properly after quick run. This may lead to possible bugs.
 */

public class ScreenPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "ScreenPagerAdapter";
    HashMap<ScreenTabs, Class> screenTabHashMap = new HashMap<>();
    private NavBar navBar = null;

    private String navItems[];
    private int tabCount = 0;

    enum ScreenTabs{
        HOME_SCREEN(0),
        COLLECTION_SCREEN(1),
        FEED_SCREEN(3),
        MORE_SCREEN(4);
        int index;
        ScreenTabs(int i){ index = i; }
        int getIndex(){ return index; }
    }

    /**
     * Constructor for navbar with roll the dice button
     * @param fm
     * @param navItems
     */
    public ScreenPagerAdapter(FragmentManager fm, String[] navItems, NavBar navBar) {
        super(fm);
        this.navItems = navItems;
        createTabs();
        this.navBar = navBar;
        tabCount = navItems.length+1;
    }

    /**
     * Add our screen fragment to the {@code screenTabHashmap} in this method.
     */
    private void  createTabs(){
        screenTabHashMap.put(ScreenTabs.HOME_SCREEN, HomeScreenFragment.class);
        screenTabHashMap.put(ScreenTabs.FEED_SCREEN, NewsFeedFragment.class);
        screenTabHashMap.put(ScreenTabs.COLLECTION_SCREEN, CollectionsScreenFragment.class);
        screenTabHashMap.put(ScreenTabs.MORE_SCREEN, MoreScreenFragment.class);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        //Fragment frag = new HomeScreenFragment();
        //return frag;

        ScreenTabs[] item = ScreenTabs.values();


        if (position > item.length || position < 0)
            return new EmptyFragment();

        //If dice button exists ignore it's index
        if(navBar != null )     //FIXME refactor with short circuit boolean expression
        {
            if( position >= navBar.getDiceButtonPosition())
                position-=1;
        }

        try {
            //Search for the hashmap key first
            if(screenTabHashMap.containsKey(item[position])) {
                Class<Fragment> fragmentClass = screenTabHashMap.get(item[position]);
                Fragment fragment = fragmentClass.newInstance();

                //Special code for home screen fragment
                if (item[position] == ScreenTabs.HOME_SCREEN){
                    /*Implement dice button in navigation for  home screen fragment*/
                    ((HomeScreenFragment)fragment).setRollDiceButton(navBar.getRollDiceButton());
                }

                return fragment;
            }
        }catch (Exception ex){
            Log.e(TAG, "getItem: "+ex.getMessage() );
        }
        //Fallback
        Fragment emptyFragment = new EmptyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", item[position].getIndex());
        bundle.putString("name", item[position].toString());
        emptyFragment.setArguments(bundle);
        return emptyFragment;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return tabCount;
    }

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return "NULL";      //We do not really need this
    }

    /**
     * Test fragment to show empty stuff
     * TODO Remove this before the final product
     */
    public static class EmptyFragment extends DynamicScreenFragment{
        int position;
        String title;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           // return super.onCreateView(inflater, container, savedInstanceState);
            ViewGroup root = createLinearRootView();
            Bundle bundle = this.getArguments();
            position = bundle.getInt("position", 0);
            title = bundle.getString("name", "Unknown");

            TextView textView = new TextView(root.getContext());
            textView.setText("Empty fragment for "+ title+ " at " + position);
            root.addView(textView);
            return root;
        }
    }
}

