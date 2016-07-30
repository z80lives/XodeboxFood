package xodebox.food.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import xodebox.food.R;

/**
 * Custom View to display Ratings.
 */
public class RatingView extends FrameLayout{
    public RatingView(Context context) {
        super(context);
        init();
    }

    public RatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.rating, this);
    }
}
