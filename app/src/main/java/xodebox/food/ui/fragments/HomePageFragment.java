package xodebox.food.ui.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import xodebox.food.R;
import xodebox.food.common.Screen;
import xodebox.food.common.models.NewsItem;
import xodebox.food.common.models.Restaurant;
import xodebox.food.ui.adapters.ItemCardAdapter;
import xodebox.food.ui.interfaces.ActivityWithSearchView;
import xodebox.food.ui.interfaces.FragmentWithSearchBar;
import xodebox.food.ui.interfaces.RollDiceInterface;
import xodebox.food.ui.managers.DefaultViewSwitcher;
import xodebox.food.ui.managers.XodeboxViewManager;
import xodebox.food.ui.view.HighlightCardView;
import xodebox.food.ui.view.RestaurantCardView;

/**
 * Load Home page fragment statically using the xml layout files.
 * Fragment for home page.
 * TODO: Allow pagerMargin to be set through XML editor.
 * <br />
 * @see xodebox.food.ui.adapters.ScreenPagerAdapter
 * @author shath
 */
public class HomePageFragment extends Fragment implements RollDiceInterface, FragmentWithSearchBar {
    private static final String TAG = "HomePageFragment";
    private  EditText searchButton;
    private ViewPager homeItemsPager, viewItemsPager;
    private ActivityWithSearchView parentActivity = null;

    View rootView, loaderView;
    XodeboxViewManager localViewManager;

    final private int pagerMargin = 16;

    /**{@inheritDoc}**/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.home_screen, null);
        loaderView = inflater.inflate(R.layout.load_view, null);



        /*
        ((RelativeLayout)loaderView)
                .setLayoutParams(
                        new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.MATCH_PARENT));

        viewSwitcher = new XodeboxViewSwitcher(getContext(), loaderView, rootView);
        //viewSwitcher.setLoadView(loaderView);
        viewSwitcher.setReadyView(rootView);*/


        localViewManager = new DefaultViewSwitcher(getContext());

        localViewManager.addView(loaderView);
        localViewManager.addView(rootView);

        localViewManager.setReadyView(rootView);
        localViewManager.setLoadView(loaderView);

        return localViewManager.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchViews(rootView);
        prepareViews();
        manualSize(rootView);

        /*new DataLoader(
                new DataLoadCallback() {
                    @Override
                    public void onDataLoadSucess() {
                        localViewManager.showView( rootView );
                    }

                    @Override
                    public void onDataLoadFailure() {
                        localViewManager.showView( rootView );
                    }
                }
        ).execute(null, null);*/
        localViewManager.showView(rootView);
        //localViewManager.setLoading(false);

        //viewSwitcher.setLoading(true);
        //viewSwitcher.setLoading(true);
    }

    /**
     * Search and set size of two view items manually. This is a temporary solution.
     * TODO: Remove this function, and instead read from a xml value file to set the weight values.
     * @param root Root view object to search
     */
    void manualSize(View root){
        FrameLayout homeFrame = (FrameLayout) root.findViewById(R.id.home_item_frame);
        //Display display = ((WindowManager) root.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x, height = (int) (size.y*0.4f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
                height);

        homeFrame.setLayoutParams(params);

        FrameLayout highlightFrame = (FrameLayout) root.findViewById(R.id.home_highlight_frame);
        height = (int) (size.y*0.4f);
        params = new LinearLayout.LayoutParams(width,
                height);

        highlightFrame.setLayoutParams(params);
    }

    /**
     * Fetch views and store it inside the class variables.
     * @param root View object to search
     * @return {@code false} on {@link NullPointerException}.
     */
    private boolean fetchViews(View root) {
        if (getActivity() instanceof ActivityWithSearchView)
            parentActivity = (ActivityWithSearchView) getActivity();

        searchButton    =   (EditText)  root.findViewById(R.id.home_search_edittext);
        homeItemsPager  =   (ViewPager) root.findViewById(R.id.home_items_pager);
        viewItemsPager  =   (ViewPager) root.findViewById(R.id.news_item_pager);
        return true;
    }

    /**
     * Prepare all the views
     * Todo: Catch null pointer exceptions and return false.
     * @return False on exception
     */
    private boolean prepareViews(){
        prepareRollButton();
        prepareSearchButton();
        prepareHomeItemsPager(homeItemsPager);
        prepareNewsItemPager(viewItemsPager);
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Prepare View Pager for Banner items.
     * Temporary solution.
     * @param homeItemsPager
     */
    private void prepareHomeItemsPager(ViewPager homeItemsPager){

        //Read dummy files
        InputStream restaurantXMLFile = null;
        try {
            restaurantXMLFile = getActivity().getAssets().open("home_data.xml");
        }catch (Exception ex)
        {
            Log.e(TAG, "onCreateView: " + ex.getMessage() );
        }

        //Prepare array to display
        ArrayList<Restaurant> restaurants= Restaurant.buildArrayList(restaurantXMLFile, Restaurant.class);
        prepareItemsPager(homeItemsPager, restaurants, RestaurantCardView.class);
    }

    /**
     * Prepare ViewPager for news item.
     * Temporary solution.
     * @param newsItemsPager ViewPager for news Item.
     */
    private void prepareNewsItemPager(ViewPager newsItemsPager){
        //Read dummy files
        InputStream restaurantXMLFile = null;
        try {
            restaurantXMLFile = getActivity().getAssets().open("home_data.json");
        }catch (Exception ex)
        {
            Log.e(TAG, "onCreateView: " + ex.getMessage() );
        }
        ArrayList<NewsItem> newsItems= Restaurant.buildArrayList(restaurantXMLFile, NewsItem.class);
        prepareItemsPager(newsItemsPager, newsItems,  HighlightCardView.class);

    }

    /**
     * Prepare Items pager
     * @param itemsPager View Pager for the items to be displayed.
     * @param models ArrayList of {@link xodebox.food.common.models.BaseModel} class.
     * @param viewClass An object of class {@code View}, preferrably {@link xodebox.food.ui.view.AbstractCardView}.
     */
    private void prepareItemsPager(ViewPager itemsPager, ArrayList models, Class<?> viewClass){

        if(!View.class.isAssignableFrom(viewClass))
        {
            Log.e(TAG, "prepareItemsPager: Parameter not a valid View class." );
            return;     //Not a valid view argument
        }
        //Create adapter
        ItemCardAdapter itemCardAdapter = new ItemCardAdapter(models, viewClass);
        itemsPager.setPageMargin(Screen.convertDip2Pixels(getContext(), pagerMargin));
        itemsPager.setAdapter(itemCardAdapter);
        itemsPager.setOffscreenPageLimit(2);
    }



    ///////////////////////////////////////////////////////////////////////////
    // Search button code
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Prepare the search button.
     */
    private void prepareSearchButton(){
        searchButton.setOnClickListener(getSearchListener());
    }

    /** {@inheritDoc} **/
    @Override
    public View getSearchButton() {
        return (View) searchButton;
    }
    /** {@inheritDoc} **/
    @Override
    public View.OnClickListener getSearchListener()  {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(parentActivity != null)
                    parentActivity.searchButtonClicked();
            }
        };
    }

    ///////////////////////////////////////////////////////////////////////////
    // Implement methods for dice rolling
    ///////////////////////////////////////////////////////////////////////////
    private ImageButton rollDiceButton;

    /** {@inheritDoc} **/
    @Override
    public void setRollDiceImage(ImageButton rollDiceImage) {
        rollDiceButton = rollDiceImage;
    }

    /**
     * Make sure to {@link #setRollDiceImage(ImageButton)} before calling this method.
     * @return {@code false} if the roll button image was not set.
     */
    private boolean prepareRollButton(){
        if (rollDiceButton == null) {
            return false;
        }
        rollDiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Roll button clicked", Toast.LENGTH_SHORT).show();
                //viewAnimator.setDisplayedChild( viewAnimator.getDisplayedChild() ^ 1 );
                //localViewManager.showView(rootView);
            }
        });
        return  true;
    }

}
