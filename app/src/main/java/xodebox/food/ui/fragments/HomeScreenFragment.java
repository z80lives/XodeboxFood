package xodebox.food.ui.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import xodebox.food.R;
import xodebox.food.common.models.Restaurant;
import xodebox.food.ui.adapters.ItemCardAdapter;
import xodebox.food.ui.view.HighlightCardView;
import xodebox.food.ui.view.RestaurantCardView;

/**
 * Created by shath on 6/29/2016.
 * Fragment for home screen.
 */
public class HomeScreenFragment extends Fragment  {
    private static final String TAG = "HomeScreenFragment";
    private static int instance=0;

    /**
     * Default constructor
     */
    public HomeScreenFragment(){
        instance++;     //Count instance for debug purpose.
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //Create the root view, we are not really inflating home_screen.xml here.
        ViewGroup rootView = createLinearRootView();

        //Create the toolbar on top
        Toolbar toolbar = new Toolbar(getContext());
        toolbar.inflateMenu(R.menu.home_screen_actionbar);
        rootView.addView(toolbar);

        //Create the two frame layout, as a container for the two main UI objects we want to show.
        FrameLayout restaurantItemFrameLayout = new FrameLayout(getContext());
        FrameLayout newsItemFrameLayout = new FrameLayout(getContext());

        // Prepare data models we are going to display.
        Restaurant aliMaju = new Restaurant();
        Restaurant pizzaHut = new Restaurant();

        aliMaju.addProperty("name", "Ali Maju Bistro");
        aliMaju.addProperty("description", "This place is okay.");

        pizzaHut.addProperty("name", "Pizza Hut");
        pizzaHut.addProperty("description", "This place is bad.");

        ArrayList<Restaurant> restaurants= new ArrayList<Restaurant>();
        restaurants.add(aliMaju);
        restaurants.add(pizzaHut);

        /* Add the stack display items for the home screen here*/
        HomeScreenViewItem[] homeScreenViewItems = {
                //Featured Restaurants
                new HomeScreenViewItem(restaurantItemFrameLayout,
                        new ItemCardAdapter(restaurants, RestaurantCardView.class),
                        R.style.restaurant_item_framelayout),

                //News Highlights
                new HomeScreenViewItem(newsItemFrameLayout,
                        new ItemCardAdapter(restaurants, HighlightCardView.class),
                        R.style.news_highlight_item_framelayout)
        };


        for(HomeScreenViewItem item : homeScreenViewItems)
        {
            FrameLayout frameLayout = item.frameLayout = new FrameLayout(getContext());
            //inflater.inflate(resource, frameLayout);
            item.frameLayout.addView(item.getPager());

            setStyle(frameLayout, item.styleResource);
            rootView.addView(frameLayout);

        }

        return rootView;
    }

    private LinearLayout createLinearRootView(){
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setWeightSum(1);
        rootView.setOrientation(LinearLayout.VERTICAL);
        return rootView;
    }

    /**
     * Retrieve the layout attributes from xml file and use it.
     * Before calling the function make sure you have defined the following items in XML resource.
     *  <ul>
     *      <li>android:layout_weight</li>
     *      <li>android:layout_height</li>
     *      <li>android:layout_width</li>
     *  </ul>
     * @param viewGroup The viewGroup to apply the attributes
     * @param resourceId Style XML resource
     */
    private LinearLayout.LayoutParams setStyle(ViewGroup viewGroup, int resourceId){
        int[] attrs = {android.R.attr.layout_weight, android.R.attr.layout_width, android.R.attr.layout_height};
        TypedArray typedArray = getContext().obtainStyledAttributes(resourceId, attrs);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                               0); //TODO Use xml attributes (TypedArray[1,2]) for dimensions

        layoutParams.weight = typedArray.getFloat(0, 0.5f);
        typedArray.recycle();

        viewGroup.setLayoutParams(layoutParams);
        return layoutParams;
    }

    /**
     * A Helper class for the UI objects in this screen.
     * This is the linear stack object that will be displayed on the homescreen.
     * Should be the container for RestaurantItem View flipper and NewsHighlight View flipper Items.
     */
    private class HomeScreenViewItem {
        HomeScreenViewItem(FrameLayout fm, PagerAdapter pagerAdapter, @StyleRes int resStyle){
            frameLayout=fm;
            styleResource= resStyle;
            pager = new ViewPager(getContext());
            this.pagerAdapter = pagerAdapter;
            //new FrameLayout(getContext());
            pager.setAdapter(pagerAdapter);
        }
        FrameLayout frameLayout;
        int styleResource;
        PagerAdapter pagerAdapter;
        ViewPager pager;

        public ViewPager getPager() {
            return pager;
        }

    };
}

