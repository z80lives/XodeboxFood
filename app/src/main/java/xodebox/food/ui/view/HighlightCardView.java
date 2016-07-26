package xodebox.food.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import xodebox.food.R;
import xodebox.food.common.models.BaseModel;

/**
 * Custom view for Highlight Card Items Displayed on the screen.
 */
public class HighlightCardView extends AbstractCardView{
    private ImageView ivRestaurantImage;
    public HighlightCardView(Context context, BaseModel model) {
        super(context, model);
    }

    @Override
    protected void onCreate() {
        setTextView(R.id.highlight_rest_name, "name");
        setTextView(R.id.highlight_rest_description, "description");
        setTextView(R.id.highlight_rest_dish, "top_dish");
        setTextView(R.id.highlight_rest_review, "rating");
        setTextView(R.id.highlight_rest_type, "type");

        //ivRestaurantImage = (ImageView) findViewById(R.id.highlight_image);
        setImageView(R.id.highlight_image, "image_url");
    }

/*    @Override
    public void onLoad() {


        String image_url = getAttribute("image_url");
        if (image_url != null) {
            new DownloadImageForView(ivRestaurantImage).execute(getAttribute("image_url"));
        }
    }*/

    protected View inflateResource() {
        return inflate(getContext(), R.layout.news_highlight_item, this);
    }

}
