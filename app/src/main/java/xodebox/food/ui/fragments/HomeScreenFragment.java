package xodebox.food.ui.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = new LinearLayout(getContext());
        ((LinearLayout)rootView).setWeightSum(1);
        ((LinearLayout)rootView).setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getContext());
        //textView.setText("Home screen: "+ instance);

        //FragmentManager fm = getFragmentManager();
        //FragmentTransaction ft = fm.beginTransaction();

        FrameLayout restaurantItemFrameLayout = new FrameLayout(getContext());
        FrameLayout newsHighlightItemFrameLayout = new FrameLayout(getContext());

        View restaurantItemView = inflater.inflate(R.layout.restaurant_feature_item, restaurantItemFrameLayout);
        View newsHighlightItemView = inflater.inflate(R.layout.news_highlight_item, newsHighlightItemFrameLayout);

        setStyle(restaurantItemFrameLayout, R.style.restaurant_item_framelayout);
        fillParent(restaurantItemView);

        setStyle(newsHighlightItemFrameLayout, R.style.news_highlight_item_framelayout);
        fillParent(newsHighlightItemView);
       // v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        //rootView.addView(textView);
        rootView.addView(restaurantItemFrameLayout);
        rootView.addView(newsHighlightItemFrameLayout);

        return rootView;
        //return super.onCreateView(inflater, container, savedInstanceState);
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
    private void setStyle(ViewGroup viewGroup, int resourceId){
        int[] attrs = {android.R.attr.layout_weight, android.R.attr.layout_width, android.R.attr.layout_height};
        TypedArray typedArray = getContext().obtainStyledAttributes(resourceId, attrs);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                               0); //TODO Use xml attributes (TypedArray[1,2]) for dimensions

        layoutParams.weight = (float) typedArray.getFloat(0, 0.5f);
        typedArray.recycle();

        viewGroup.setLayoutParams(layoutParams);
    }

    /**
     * Fill the parent view
     * @param view
     */
    private void fillParent(View view){
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * Get the name of the fragment to be displayed on the tab title.
     * @return String
     */
    public String getTitle(){
        String navItems[] = getResources().getStringArray(R.array.nav_items);
        assert(navItems!=null);
        String title = navItems[0];
        assert (title != null);
        return title;
    }
}