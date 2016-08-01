package xodebox.food.ui.view;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import xodebox.food.R;
import xodebox.food.common.models.BaseModel;

/**
 * Abstract view for all cards.
 * Remember to call onLoad() method, after construction of this object by the adapter. Otherwise, the
 * card view may not be properly prepared to display.
 * All view objects should inflate their XML layout, and name attribute fields of their modifiable child views.
 * Created by shath on 7/8/2016.
 */
public abstract class AbstractCardView extends FrameLayout{
    private static final String TAG = "AbstractCardView";
    //private ArrayList<String> attributes;       //Todo: use HashMap instead of ArrayList
    private HashMap<String, String> attributes;
    private HashMap<ImageView, String> imageViewStringHashMap;      //Hashmap of all images to be stored
    private BaseModel originalModel;
    boolean loadComplete = false;
    View root;
    Context context;



    /**
     * Construct view from the given data model. Please make sure the attributes of the Model are set.
     * @param model Model object, preferably a child class of BaseModel.
     */
    public AbstractCardView(Context context, BaseModel model) {
        super(context);
       newInstance(model);
    }

    /**
     * Construct with android attribute set.
     * @param context
     * @param model
     * @param attrs
     */
    public AbstractCardView(Context context,  BaseModel model, AttributeSet attrs) {
        super(context, attrs);
        newInstance(model);
    }

    public BaseModel getModel(){
        return originalModel;
    }

    /**
     * This method properly initialize the class.
     * @param model Input Data Model.
     */
    private void newInstance(BaseModel model){
        this.context = getContext();
        this.originalModel = model;     //Store original model to be used later.
        attributes = (HashMap<String, String>) model.getAttributes();       //Copy all the attributes from the model
        imageViewStringHashMap = new HashMap<ImageView, String>();
        root = inflateResource();
        onCreate();
    }

    protected   abstract void onCreate();
    protected abstract View inflateResource();

    /**
     * This method will be executed after it's creator
     */
    public void onLoad(){
        loadImages();
        loadComplete = true;
    }

    /**
     * Should be called when the view needs to be manually refreshed
     */
    public void update(){
        loadImages();
    }

    /**
     * Loads all the images found in the HashMap
     */
    private void loadImages(){
        if(! imageViewStringHashMap.isEmpty()){
            for(ImageView imageView: imageViewStringHashMap.keySet())
            {
                String url = imageViewStringHashMap.get(imageView);
                if(url != null)
                {
                    //new DownloadImageForView(imageView).execute(url);
                    Picasso.with(getContext()).load(url).into(imageView);           //Use Picasso
                }
            }
        }
    }

    /**
     * Add a string to the attribute list.
     * @param attrib String to store
     * @deprecated Avoid modifying the values given by the model.
     */
    public void addAttribute(String key, String attrib){
        attributes.put(key, attrib);
    }


    /**
     * Get a specific attribute from the object's private HashMap.
     * @param key The key string of the attribute. Check out the Model class documentation for available keys.
     * @return The attribute in String format if found. <br/> {@code null} if not found.
     */
    public String getAttribute(String key){
        String value =  attributes.get(key);
        if (value != null)
            return value;

        //Log and return fallback content
        Log.e(TAG, this.getClass() + ".getAttribute: value not set for key '"+key + "'." );
        return getResources().getString(R.string.itemcard_string_fallback);
    }

    /**
     * Sets the value of an attribute to the text view
     * @return
     */
    public TextView setTextView(@IdRes int res, String key){
        TextView textView = (TextView) findViewById(res);
        if(textView == null)
        {
            Log.e(TAG, "setTextView: Resource with id "+ res + " was not found." );
            return  null;
        }
        textView.setText(getAttribute(key));
        return  textView;
    }

    /**
     * Sets the image url. Image set by the card view will be downloaded automatically.
     * @param res Image resource
     * @param key A valid URL in string format
     * @return Image view of the object.
     */
    public ImageView setImageView(@IdRes int res, String key){
        ImageView imageView = (ImageView) findViewById(res);
        if (imageView == null)
        {
            Log.e(TAG, "setImageView: Resourse with id "+res+" was not found.");
        }
        imageViewStringHashMap.put(imageView, getAttribute(key));
        return  imageView;
    }


}

