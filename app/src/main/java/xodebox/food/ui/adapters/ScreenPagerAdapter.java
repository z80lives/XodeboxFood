package xodebox.food.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import xodebox.food.ui.fragments.AddRestaurantFragment;
import xodebox.food.ui.fragments.HistoryScreenFragment;
import xodebox.food.ui.fragments.HomeScreenFragment;

/**
 * Created by shath on 6/29/2016.
 * FIXME The class does not instantiate properly after quick run. This may lead to possible bugs.
 */

public class ScreenPagerAdapter extends FragmentPagerAdapter {

    private String navItems[];

    // HomeScreenFragment homeScreen;
    public ScreenPagerAdapter(FragmentManager fm, String[] navItems) {
        super(fm);
        this.navItems = navItems;
        // homeScreen = new HomeScreenFragment();
    }

    public void  createTabs(){
        ScreenTab[] screenTabs = {
                new ScreenTab(new HomeScreenFragment(), 0),
                new ScreenTab(new HistoryScreenFragment(), 1)
        };
        //return this;
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
        switch (position)
        {
            case 0:
                return new HomeScreenFragment();
            case 2:
                return new AddRestaurantFragment();
            default:
                return new Fragment();
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return navItems.length;
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
        //Fragment frag = getItem(position);
       // return ((HomeScreenFragment) frag).getTitle();
        //String arrNavItems[] = ;
        assert(navItems!=null && navItems.length >= position);

        return navItems[position].toString();
    }

    /**
     * To associate the screen fragment with an index
     */
    private class ScreenTab{
        Fragment fragment;
        int position;
        public ScreenTab(Fragment fragment, int index){
            this.fragment = fragment;
            this.position = index;
        }

        /** Getters for this class **/
        public Fragment getFragment() {
            return fragment;
        }
        public int getPosition() {
            return position;
        }
    }
}