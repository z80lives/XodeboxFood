package xodebox.food.ui.viewpagers;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * The view pager that is displayed on the Main Activity
 * @see xodebox.food.activities.MainActivity
 */
public class MainscreenViewPager extends ViewPager {
    private boolean enabled = false;

    /**
     * Default Constructor
     * @param context Android {@link Context}
     */
    public MainscreenViewPager(Context context)
    {
        super(context);
        init();
    }

    /**
     * Constructor with attributes.
     * @param context Android {@link Context}
     */
    public MainscreenViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * If anythings needs to be done after initialization write it here
     */
    private void init(){
    }

    /** {@inheritDoc **/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return enabled ? super.onTouchEvent(event) : false;
    }

    /** {@inheritDoc **/
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return enabled ? super.onInterceptTouchEvent(event) : false;
    }

    /**
     * If paging is enabled you can use gesture to swipe the page.
     * @param enabled Set to {@code true} if you want to enable paging.
     */
    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Checks whether paging is enabled.
     */
    public boolean isPagingEnabled() {
        return enabled;
    }

}
