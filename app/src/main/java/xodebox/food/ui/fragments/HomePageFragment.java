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
import xodebox.food.ui.view.HighlightCardView;
import xodebox.food.ui.view.RestaurantCardView;

/**
 * Created by shath on 7/25/2016.
 */
public class HomePageFragment extends Fragment implements RollDiceInterface, FragmentWithSearchBar {
    private static final String TAG = "HomePageFragment";
    private  EditText searchButton;
    private ViewPager homeItemsPager, viewItemsPager;
    private ActivityWithSearchView parentActivity = null;

    final private int pagerMargin = 16;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.home_screen, null);
        fetchViews(rootView);
        prepareViews();
        manualSize(rootView);
        return rootView;
    }

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


    private boolean fetchViews(View root){
        if (getActivity() instanceof ActivityWithSearchView)
            parentActivity = (ActivityWithSearchView) getActivity();

        searchButton    =   (EditText)  root.findViewById(R.id.home_search_edittext);
        homeItemsPager  =   (ViewPager) root.findViewById(R.id.home_items_pager);
        viewItemsPager  =   (ViewPager) root.findViewById(R.id.news_item_pager);
        return true;
    }


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
    }



    ///////////////////////////////////////////////////////////////////////////
    // Search button code
    ///////////////////////////////////////////////////////////////////////////

    private void prepareSearchButton(){
        searchButton.setOnClickListener(getSearchListener());
    }

    @Override
    public View getSearchButton() {
        return (View) searchButton;
    }

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

    @Override
    public void setRollDiceImage(ImageButton rollDiceImage) {
        rollDiceButton = rollDiceImage;
    }

    private boolean prepareRollButton(){
        if (rollDiceButton == null) {
            return false;
        }
        rollDiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Roll button clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return  true;
    }

}
