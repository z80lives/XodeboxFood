package xodebox.food.common.interfaces;

import android.support.annotation.IdRes;
import android.widget.ImageView;
import android.widget.TextView;

import xodebox.food.common.models.BaseModel;

/**
 * Created by shath on 8/9/2016.
 */
public interface ModifiableView {

    /**
     * Every Modifiable View must use a Base Model
     * @return Base Model upon the View is created
     */
    public BaseModel getModel();

    /**
     * Sets the value of an attribute to the text view
     * @return
     */
    TextView setTextView(@IdRes int res, @IdRes String key);

    /**
     * Sets the image url. Image set by the card view will be downloaded automatically.
     * @param res Image resource
     * @param key A valid URL in string format
     * @return Image view of the object.
     */
    ImageView setImageView(@IdRes int res, @IdRes String key);
}
