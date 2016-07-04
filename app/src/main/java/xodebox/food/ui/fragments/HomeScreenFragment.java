package xodebox.food.ui.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
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
import android.widget.TextView;

import xodebox.food.R;

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
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Toolbar toolbar = new Toolbar(getContext());
        toolbar.inflateMenu(R.menu.home_screen_actionbar);

        ViewGroup rootView = createLinearRootView();   //Create a Linear class for root

        rootView.addView(toolbar);

        FrameLayout restaurantItemFrameLayout = new FrameLayout(getContext()), newsItemFrameLayout = new FrameLayout(getContext());


        /* Add the stack display items for the home screen here*/
        HomeScreenViewItem[] homeScreenViewItems = {
                new HomeScreenViewItem(restaurantItemFrameLayout, R.layout.restaurant_feature_item, R.style.restaurant_item_framelayout),
                new HomeScreenViewItem(newsItemFrameLayout, R.layout.news_highlight_item, R.style.news_highlight_item_framelayout)
        };

        for(HomeScreenViewItem homeScreenViewItem : homeScreenViewItems)
        {
            FrameLayout frameLayout = homeScreenViewItem.frameLayout = new FrameLayout(getContext());
            int resource = homeScreenViewItem.resourceId;

            //inflater.inflate(resource, frameLayout);


            setStyle(frameLayout, homeScreenViewItem.styleResource);
            rootView.addView(frameLayout);

        }

        ViewPager restaurantItemViewPager = new ViewPager(getContext());
        PagerAdapter restaurantPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }


            @Override
            public boolean isViewFromObject(View view, Object object) {
                return false;
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
                TextView textView = new TextView(getContext());
                textView.setText("Hi!");
                return  textView;

                //container.addView(textView);
               // return super.instantiateItem(container, position);
            }
        };

        homeScreenViewItems[0].frameLayout.addView(restaurantItemViewPager);
        //restaurantItemViewPager.setBackgroundColor(getResources().getColor(android.R.color.background_dark)); //TEST
        restaurantItemViewPager.setAdapter(restaurantPagerAdapter);

        View itemView = new TextView(getContext());
        ((TextView)itemView).setText("Show highlights here.");
        homeScreenViewItems[1].frameLayout.addView( itemView);

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
     * The linear stack object that will be displayed on the homescreen.
     * Should be the container for RestaurantItem View flipper and NewsHighlight View flipper Items.
     */
    private class HomeScreenViewItem {
        HomeScreenViewItem(FrameLayout fm, @LayoutRes int resId, @StyleRes int resStyle){
            frameLayout=fm;
            resourceId=resId;
            styleResource= resStyle;
            new FrameLayout(getContext());
        }
        FrameLayout frameLayout;
        int resourceId;
        int styleResource;
    };
}

