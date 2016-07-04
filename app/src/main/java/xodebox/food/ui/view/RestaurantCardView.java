package xodebox.food.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import xodebox.food.R;

/**
 * Created by shath on 7/4/2016.
 * A simplistic restaurant view, that will display a picture of the restaurant and
 * it's description, stacked vertically.
 */
public class RestaurantCardView extends FrameLayout{
    private static int instances=0;
    private TextView tvRestaurantDescription;

    public RestaurantCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *  Constructor with attribute
     */
    public RestaurantCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateResource();
    }

    /**
     *  Basic constructor
     * @param context
     */
    public RestaurantCardView(Context context) {
        super(context);
        inflateResource();
        instances++;
        tvRestaurantDescription.setText("Restaurant card "+instances);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Private Methods
    ///////////////////////////////////////////////////////////////////////////
    private void inflateResource(){
        inflate(getContext(), R.layout.restaurant_feature_item, this);
        tvRestaurantDescription = (TextView) findViewById(R.id.textview_restaurant_description);
    }
}
