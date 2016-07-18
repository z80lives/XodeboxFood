package xodebox.food.ui.nav;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;
import android.widget.ImageView;

import xodebox.food.R;

/**
 * Created by shath on 7/16/2016.
 */
public class NavBar extends TabLayout {
    private Tab[] tabs;
    private int diceButtonIndex = 0;
    private ImageButton rollDiceButton;

    /**
     * Our default constructor
     * @param context
     */
    public NavBar(Context context) {
        super(context);
        setTabMode(MODE_FIXED);
        setSelectedTabIndicatorHeight(0);
        setTabGravity(GRAVITY_FILL);
        setBackgroundResource(R.color.colorPrimary);
        setTabTextColors(getResources().getColor(android.R.color.primary_text_dark),
                          getResources().getColor(android.R.color.holo_orange_light));
    }

    /**
     * Generate the tab buttons using the nav_item in xml resource file
     */
    public void makeTabs(){
        removeAllTabs();
        String[] tabLabels = getResources().getStringArray(R.array.nav_items);
        tabs = new Tab[tabLabels.length];

        for(int i=0; i<tabLabels.length; i++) {
            Tab tab = newTab();
            tab.setText(tabLabels[i]);
            tabs[i] = tab;
           // tab.setCustomView(R.layout.nav_button);
        }
        //Place all the tabs along with the roll button
        for(int i=0,c=0; i<tabLabels.length+1; i++)
        {
            int middle = (tabLabels.length/2);
            diceButtonIndex = middle;
            if(i==middle)
            {
                //View diceView = inflate(getContext(), R.layout.nav_button, null);
                ImageButton imageButton = new ImageButton(getContext());
                //Resources.Theme diceTheme = getResources().newTheme();
                imageButton.setImageDrawable(getResources().getDrawable(R.drawable.roll_the_dice));
                imageButton.setBackgroundResource(R.color.colorPrimary);
                imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                rollDiceButton = imageButton;
                addTab(newTab().setCustomView(imageButton));
                continue;
            }
            addTab(tabs[c++]);
        }
        //setBackgroundResource(R.drawable.round_tag);
    }

    /**
     * Modified code from the super method
     * @param viewPager
     */
    @Override
    public void setupWithViewPager(@NonNull ViewPager viewPager) {
        //super.setupWithViewPager(viewPager);
        final PagerAdapter pagerAdapter = viewPager.getAdapter();
        if (pagerAdapter == null) {
            throw new IllegalArgumentException("View pager does not have pager adapter set.");
        }
        makeTabs();
        viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this));
        setOnTabSelectedListener(new NavPagerOnTabSelectedListener(viewPager, diceButtonIndex));


    }

    public ImageButton getRollDiceButton(){
        return rollDiceButton;
    }

    public int getDiceButtonPosition(){
        return diceButtonIndex;
    }
}


/**
 * Modified the tab listener to ignore the die button and redirect to zeroth index (Home tab)
 * when called.
 */
 class NavPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener{
     int diceButtonIndex;
     ViewPager mViewPager;

     public NavPagerOnTabSelectedListener(ViewPager viewPager, int diceIndex) {
         //super();
         mViewPager = viewPager;
         diceButtonIndex = diceIndex;
     }

     @Override
     public void onTabSelected(TabLayout.Tab tab) {
         int pos = tab.getPosition();
         if(pos == diceButtonIndex)
             pos = 0;
         mViewPager.setCurrentItem(pos);
     }

     @Override
     public void onTabUnselected(TabLayout.Tab tab) {

     }

     @Override
     public void onTabReselected(TabLayout.Tab tab) {

     }
 }
