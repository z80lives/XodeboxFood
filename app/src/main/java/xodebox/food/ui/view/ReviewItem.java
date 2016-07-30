package xodebox.food.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import xodebox.food.R;

/**
 * Created by shath on 7/29/2016.
 */
public class ReviewItem extends FrameLayout {
    public ReviewItem(Context context) {
        super(context);
    }

    public ReviewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate();
    }

    public ReviewItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate();
    }

    public void inflate(){
        inflate(getContext(), R.layout.review_card, this);
    }

}
