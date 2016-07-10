package xodebox.food.ui.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import xodebox.food.R;
import xodebox.food.common.models.BaseModel;

/**
 *
 * A simplistic restaurant view, that will display a picture of the restaurant and
 * it's description, stacked vertically.
 * Created by shath on 7/4/2016.
 */
public class RestaurantCardView extends AbstractCardView {
    private static int instances=0;
    private TextView tvRestaurantDescription;
    private ImageView ivRestaurantImage;

    private enum attribute{
        name,
        description
    }

    /**
     *  Construct the View using the data model
     * @param context
     */
    public RestaurantCardView(Context context, BaseModel model) {
        super(context, model);
        inflateResource();
        updateCard();
        instances++;
    }

    @Override
    public void updateCard() {
        tvRestaurantDescription.setText(getAttribute(0));
        //ivRestaurantImage.setImageDrawable(getResources().getDrawable(R.drawable.rest1, getContext().getTheme()));
        //Drawable drawable = new ProgressBar(getContext());
        //ivRestaurantImage.setVisibility(GONE);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Local Methods
    ///////////////////////////////////////////////////////////////////////////

    private void inflateResource(){
        inflate(getContext(), R.layout.restaurant_feature_item, this);
        tvRestaurantDescription = (TextView) findViewById(R.id.textview_restaurant_description);
        ivRestaurantImage = (ImageView) findViewById(R.id.imageview_restaurant_image);
    }
}
