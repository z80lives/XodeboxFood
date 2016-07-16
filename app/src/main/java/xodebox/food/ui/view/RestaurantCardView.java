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
 * Please set the following attributes.
 * <h3>Attribute list</h3>
 * <table border="1">
 *     <tr>
 *         <th>attribute</th>
 *         <th>Description</th>
 *     </tr>
 *
 *     <tr>
 *         <td>name</td>
 *         <td>Name of the restaurant</td>
 *     </tr>
 *     <tr>
 *         <td>imgurl</td>
 *         <td>URL of the image to be loaded</td>
 *     </tr>
 * </table>
 * Created by shath on 7/4/2016.
 */
public class RestaurantCardView extends AbstractCardView {
    //private static int instances=0;
    private TextView tvRestaurantDescription;
    private ImageView ivRestaurantImage;

    private enum Attrib{
        name,
        imgurl
    }

    /**
     *  Construct the View using the data] model
     * @param context
     */
    public RestaurantCardView(Context context, BaseModel model) {
        super(context, model);
        //inflateResource();
        //instances++;
    }

    public void onCreate() {
        tvRestaurantDescription.setText(getAttribute(Attrib.name.toString()) );
        //ivRestaurantImage.setImageDrawable(getResources().getDrawable(R.drawable.rest1, getContext().getTheme()));
        //Drawable drawable = new ProgressBar(getContext());
        //ivRestaurantImage.setVisibility(GONE);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Local Methods
    ///////////////////////////////////////////////////////////////////////////

    protected void inflateResource(){
        inflate(getContext(), R.layout.restaurant_feature_item, this);
        tvRestaurantDescription = (TextView) findViewById(R.id.textview_restaurant_description);
        ivRestaurantImage = (ImageView) findViewById(R.id.imageview_restaurant_image);
    }
}
