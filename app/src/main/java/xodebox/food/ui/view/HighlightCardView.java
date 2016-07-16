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
    }

    @Override
    public void onCreate() {
        setTextView(R.id.highlight_rest_name, "name");
        setTextView(R.id.highlight_rest_description, "description");
        setTextView(R.id.highlight_rest_dish, "top_dish");
        setTextView(R.id.highlight_rest_review, "top_review");
        setTextView(R.id.highlight_rest_review, "type");
    }

    @Override
    protected void inflateResource() {
        inflate(getContext(), R.layout.news_highlight_item, this);
    }

}
