package xodebox.food.ui.view;

import android.content.Context;

import xodebox.food.R;
import xodebox.food.common.models.BaseModel;

/**
 * Created by shath on 7/4/2016.
 * Custom view for Highlight Cards
 */
public class HighlightCardView extends AbstractCardView{
    public HighlightCardView(Context context, BaseModel model) {
        super(context, model);
        init();
    }


    public void init(){
        inflate(getContext(), R.layout.news_highlight_item, this);
    }

}
