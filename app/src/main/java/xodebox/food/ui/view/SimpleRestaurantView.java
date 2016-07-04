package xodebox.food.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by shath on 7/4/2016.
 * A simplistic restaurant view, that will display a picture of the restaurant and
 * it's description, stacked vertically.
 */
public class SimpleRestaurantView extends View{
    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p/>
     * <p/>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     * @see #View(Context, AttributeSet, int)
     */
    public SimpleRestaurantView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
