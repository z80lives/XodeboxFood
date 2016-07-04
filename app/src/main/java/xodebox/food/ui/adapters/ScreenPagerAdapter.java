package xodebox.food.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import xodebox.food.ui.fragments.AddNewRestaurantFragment;
import xodebox.food.ui.fragments.HomeScreenFragment;

/**
 * Created by shath on 6/29/2016.
 */

public class ScreenPagerAdapter extends FragmentPagerAdapter {

    private String navItems[];

    private enum tabElements {homeTab, collectionsTab, addRestaurantTab, feedTab, moreTab}

    ;

    // HomeScreenFragment homeScreen;
    public ScreenPagerAdapter(FragmentManager fm, String[] navItems) {
        super(fm);
        this.navItems = navItems;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        tabElements index = tabElements.values()[position];
        switch (index)
        {
            case homeTab:
                return new HomeScreenFragment();
            case addRestaurantTab:
                return new AddNewRestaurantFragment();

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
        assert(navItems!=null && navItems.length >= position);

        return navItems[position].toString();
    }
}