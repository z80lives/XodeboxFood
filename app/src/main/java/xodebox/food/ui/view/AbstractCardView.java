package xodebox.food.ui.view;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashMap;

import xodebox.food.R;
import xodebox.food.common.models.BaseModel;

/**
 * Abstract view for all cards.
 * All view objects should inflate their XML layout, and name attribute fields of their modifiable child views.
 * Created by shath on 7/8/2016.
 */
public abstract class AbstractCardView extends FrameLayout{
    private static final String TAG = "AbstractCardView";
    //private ArrayList<String> attributes;       //Todo: use HashMap instead of ArrayList
    private HashMap<String, String> attributes;

    /**
     * Construct view from the given data model. Please make sure the attributes of the Model are set.
     * @param model Model object, preferably a child class of BaseModel.
     */
    public AbstractCardView(Context context, BaseModel model) {
        super(context);
        //attributes = model.getAttributesList();
        attributes = (HashMap<String, String>) model.getAttributes();
        inflateResource();
        onCreate();
    }

    public  abstract void onCreate();
    protected abstract void inflateResource();
    public void onLoad(){}

    /**
     * Add a string to the attribute list.
     * @param attrib String to store
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
            Log.e(TAG, "setTextView: Resource with id "+ res + "not found." );
            return  null;
        }
        textView.setText(getAttribute(key));
        return  textView;
    }
}
