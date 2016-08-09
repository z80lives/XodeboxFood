package xodebox.food.common;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

import xodebox.food.common.interfaces.ModifiableView;
import xodebox.food.common.interfaces.ViewWithStates;
import xodebox.food.common.models.BaseModel;
import xodebox.food.common.models.Model;

/**
 * Created by shath on 8/9/2016.
 */
public class ViewModifier implements ModifiableView {
    ViewWithStates masterView;
    Model model;
    private static final String TAG = "ViewModifier";

    public ViewModifier(ViewWithStates masterView, Model m) {
        super();
        this.masterView = masterView;
        model = m;
    }

    @Override
    public TextView setTextView(@IdRes int res, @StringRes String key) {
        TextView textView = (TextView) masterView.getActiveView().findViewById(res);
        if(textView == null)
        {
            Log.e(TAG, "setTextView: Resource with id "+ res + " was not found." );
            return  null;
        }
        textView.setText(model.getProperty(key));
        return  textView;
    }

    @Override
    public ImageView setImageView(@IdRes int res, @StringRes String key) {
        ImageView imageView = (ImageView) masterView.getActiveView().findViewById(res);
        if (imageView == null)
        {
            Log.e(TAG, "setImageView: Resourse with id "+res+" was not found.");
        }
        masterView.getImageMap().put(imageView, getAttribute(key));
        return  imageView;
    }


    private String getAttribute(String key){
        Map<String, String> attributes = model.getAttributes();
        String value =  attributes.get(key);
        if (value != null)
            return value;

        //Log and return fallback content
        Log.e(TAG, this.getClass() + ".getAttribute: value not set for key '"+key + "'." );
        return "Not found.";
    }

    @Override
    public BaseModel getModel() {
        return model;
    }
}
