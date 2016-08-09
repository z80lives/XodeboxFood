package xodebox.food.activities;

import android.os.Bundle;
import android.support.annotation.AnyRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import xodebox.food.R;
import xodebox.food.common.ViewModifier;
import xodebox.food.common.interfaces.ModifiableView;
import xodebox.food.common.interfaces.ViewWithStates;
import xodebox.food.common.models.BaseModel;
import xodebox.food.common.models.Model;
import xodebox.food.ui.managers.DefaultViewSwitcher;
import xodebox.food.ui.managers.XodeboxViewManager;
import xodebox.food.ui.view.MenuInfoCard;
import xodebox.food.ui.view.ReviewItem;

/**
 * Restaurant Activity should be launched when a User clicks a Restaurant Item Card.
 * Should be defined as a child of {@link MainActivity}, in our AppManifest.
 */
public class RestaurantActivity extends AppCompatActivity implements ViewWithStates{
    private static final String TAG = "RestaurantActivity";
    View rootView;
    XodeboxViewManager viewManager;
    Model RestaurantDataModel = null;

    TextView restaurantName;
    TextView areaAndCity;
    MenuInfoCard menuCard;
    ReviewItem topReviewCard;

    View menuContainer;


    ModifiableView modifiableView;

    Map<ImageView, String> imageMap;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewManager = new DefaultViewSwitcher(this);

        View restaurantPage = getLayoutInflater().inflate(R.layout.restaurant_page, viewManager.getRootView(), false);
        View loaderView     = getLayoutInflater().inflate(R.layout.load_view, viewManager.getRootView(), false);


        //viewManager.addView(restaurantPage);
        viewManager.addView(restaurantPage);
        viewManager.addView(loaderView);

        viewManager.setLoadView(loaderView);
        viewManager.setReadyView(restaurantPage);
        //rootView = viewManager.getRootView();

        //setContentView(viewManager.getRootView(),
        //        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setContentView(viewManager.getRootView());
        rootView = restaurantPage;
        fetchUI(rootView);
    }


    /**
     * Initialize the local Views
     */
    private void fetchUI(View v){
        restaurantName = (TextView) v.findViewById(R.id.restaurant_name);
        areaAndCity    = (TextView) v.findViewById(R.id.area);
        menuContainer = v.findViewById(R.id.restaurant_layout_menu_container);
        menuCard      = (MenuInfoCard) v.findViewById(R.id.menu_info_card);
        topReviewCard =  (ReviewItem) v.findViewById(R.id.top_review);
    }

    /**
     * For debug purpose only
     */
    private Model readDummyJSONFile(){
        try {
            InputStream inputStream = getAssets().open("sample_restaurant.json");
            return new Model(inputStream);
        }catch (IOException ex){
            Log.e(TAG, "readDummyJSONFile: ", ex);
        }
        return null;
    }

    private boolean addData(ModifiableView v){
        @AnyRes int[][] textViews = {
                {R.id.tv_restaurant_name, R.string.key_restaurant_name},
                {R.id.tv_restaurant_area, R.string.key_restaurant_area},
                {R.id.field_operating_time, R.string.key_restaurant_time_open}
                              };
       // String[] expectedKeys = getResources().getStringArray(R.array.restaurant);  //Should be define in model_keys.xml file

        for(int[] assoc: textViews)
        {
            String key = getString(assoc[1]);
            if (key == null ) return false;
            v.setTextView(assoc[0],  key);
        }

        v.setImageView(R.id.iv_restaurant_image, getString(R.string.key_restaurant_image_url));

        Log.d(TAG, "addData: "+v.getModel().getProperty(getString(R.string.key_restaurant_payment_type)));
        Log.d(TAG, "addData: "+ v.getModel().getChildArrays().toString());

        String menuArrayString = v.getModel().getProperty(getString(R.string.key_restaurant_menu_images));

        if(menuArrayString == null && menuContainer != null){
            menuContainer.setVisibility(View.GONE);
            //Show fallback item for menu here
        }

        /**
        //Read menu card info using a seperate object
        Model menuCardInfo = (Model) v.getModel().getChildModel(getString(R.string.key_restaurant_menu_info));
        if (menuCardInfo == null) {
            menuCard.setVisibility(View.GONE);
            //show fallback!
        }else
            menuCard.attachModel(menuCardInfo); **/

        //Try to create menu card info from existing data
        String[] menuCardKeys = {getString(R.string.key_restaurant_time_closed),
                                 getString(R.string.key_restaurant_time_open),
                                 getString(R.string.key_restaurant_payment_type),
                                 getString(R.string.key_restaurant_days_open)
                                };

        Model menuCardModel = new Model();
        menuCardModel.copyKeyAttributes(menuCardKeys, RestaurantDataModel);
        menuCard.attachModel(menuCardModel);
        //getString(R.string.key);

        BaseModel topReviewModel = v.getModel().getChildModel(getString(R.string.key_restaurant_top_review));

        if (topReviewModel == null){
            topReviewCard.setVisibility(View.GONE);
        }//else
            //topReviewCard.attachModel

        /*Map<String, String> attribList = m.getAttributes();
        String[] expectedKeys = getResources().getStringArray(R.array.restaurant);  //Should be define in model_keys.xml file

        //Check whether all the expected attributes are defined
        for(String key: expectedKeys){
            String value = attribList.get(key);
            if (value == null) {    //Key not found
                Log.e(TAG, "addData: Attribute "+ key +" not define in the model for "+m.toString()  );
                return false;
            }
        }*/

        //v.setTextView(R.id.tv_restaurant_name, getString(R.string.key_restaurant_name));
        //v.setTextView(R.id.tv_restaurant_area, getString(R.string.key_restaurant_area));
        //v.setImageView(R.id.iv_restaurant_image, getString(R.string.key_restaurant_image_url));

        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        onCreate();
        onLoad();
    }

    @Override
    public void onCreate() {
        imageMap = new HashMap<>();
        viewManager.setLoading(false);
        RestaurantDataModel = readDummyJSONFile();
        modifiableView = new ViewModifier(this, RestaurantDataModel);
        addData(modifiableView);
    }

    @Override
    public void onLoad() {
        loadImages();
    }

    /**
     * Loads all the images found in the HashMap
     */
    private void loadImages(){
        if(! imageMap.isEmpty()){
            for(ImageView imageView: imageMap.keySet())
            {
                String url = imageMap.get(imageView);
                if(url != null)
                {
                    //new DownloadImageForView(imageView).execute(url);
                    Picasso.with(this).load(url).into(imageView);           //Use Picasso
                }
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public View getActiveView() {
        return rootView;
    }

    @Override
    public Map<ImageView, String> getImageMap() {
        return imageMap;
    }
}
