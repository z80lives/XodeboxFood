package xodebox.food.ui.view;

import android.content.Context;
import android.widget.FrameLayout;

import xodebox.food.R;

/**
 * Created by shath on 7/4/2016.
 * Custom view for Highlight Cards
 */
public class HighlightCardView extends FrameLayout{
    public HighlightCardView(Context context) {
        super(context);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.news_highlight_item, this);
    }
}
