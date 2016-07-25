package xodebox.food.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.View;
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

    /**
     * Not really necessary, this is just here for readability.
     */
    private enum Attrib{
        name,
        image_url,
        description
    }

    /**
     *  Construct the View from a data model related to {@link BaseModel}
     * @param context Context
     */
    public RestaurantCardView(Context context, BaseModel model) {
        super(context, model);
    }

    /**
     * Create
     */
    protected void onCreate() {
        setTextView(R.id.textview_restaurant_description, Attrib.name.toString());
    }

    /**{@inheritDoc}**/
    @Override
    public void onLoad() {
        String strImageUrl = getAttribute(Attrib.image_url.toString());
        DownloadImageForView downloadTask = new DownloadImageForView(ivRestaurantImage);
        downloadTask.execute(strImageUrl);

    }

    ///////////////////////////////////////////////////////////////////////////
    // Local Methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Inflate restuarant Card
     * @return view
     */
    protected View inflateResource(){
        View rootView = inflate(getContext(), R.layout.restaurant_feature_item, this);
        ivRestaurantImage = (ImageView) findViewById(R.id.imageview_restaurant_image);

        return rootView;

    }

    @TargetApi(21)
    private  void elevate(){
        this.setElevation(0.5f);
    }
}
