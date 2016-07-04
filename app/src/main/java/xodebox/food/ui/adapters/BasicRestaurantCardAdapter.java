package xodebox.food.ui.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import xodebox.food.ui.view.RestaurantCardView;

/**
 * Created by shath on 7/4/2016.
 * Creates a restuarant card for homescreen with view inflated.
 */
public class BasicRestaurantCardAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object==view;
    }

    /**
     * Create the page for the given position.  The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate(ViewGroup)}.
     *
     * @param container The containing View in which the page will be shown.
     * @param position  The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not
     * need to be a View, but can be some other container of the page.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RestaurantCardView restaurantItem = new RestaurantCardView(container.getContext());
        container.addView(restaurantItem);
        return  restaurantItem;
    }
}
