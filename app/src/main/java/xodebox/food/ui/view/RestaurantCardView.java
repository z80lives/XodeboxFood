package xodebox.food.ui.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import xodebox.food.R;
import xodebox.food.common.models.BaseModel;
import xodebox.food.common.threads.DownloadImageForView;

/**
 *
 * A simple view for the restaurant, to display the picture of the restaurant and
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
   // private TextView tvRestaurantDescription;
    private TextView tvRestaurantName;
    private ImageView ivRestaurantImage;

    private enum Attrib{
        name,
        image_url,
        description
    }

    /**
     *  Construct the View using the data] model
     * @param context
     */
    public RestaurantCardView(Context context, BaseModel model) {
        super(context, model);
    }

    public void onCreate() {
        setTextView(R.id.textview_restaurant_description, Attrib.name.toString());
    }

    @Override
    public void onLoad() {
        String strImageUrl = getAttribute(Attrib.image_url.toString());
        DownloadImageForView downloadTask = new DownloadImageForView(ivRestaurantImage);
        downloadTask.execute(strImageUrl);

    }


    public ImageView getImageView(){
        return ivRestaurantImage;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Local Methods
    ///////////////////////////////////////////////////////////////////////////

    protected void inflateResource(){
        inflate(getContext(), R.layout.restaurant_feature_item, this);
        ivRestaurantImage = (ImageView) findViewById(R.id.imageview_restaurant_image);
    }
}
