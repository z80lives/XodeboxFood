package xodebox.food.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import xodebox.food.common.models.BaseModel;
import xodebox.food.ui.view.AbstractCardView;

/**
 * Adapter for viewPager, intended to list a group of items like a slide show.
 * <ol>
 * <li>To use pass the item list and Class to be constructed like this: <br/>
 * {@code ItemCardAdapter adapter = new ItemCardAdapter(imageViewCards, ImageViewCard.class); }
 * </li><br />
 * <li>Then attach it to the page view. <br /> {@code myPager.setAdapter(adapter);} </li>
 *</ol>
 * @see android.support.v4.view.ViewPager
 */
public class ItemCardAdapter extends PagerAdapter {
    private static final String TAG = "ItemCardAdapter";
    private ArrayList<BaseModel> itemList;
    private Class<?> ViewClass;

    /**
     * Create ItemCardAdapter from item list.
     * @param itemList List of view items
     */
    public ItemCardAdapter(ArrayList itemList, Class<?> ViewClass){
        super();
        this.ViewClass =  ViewClass;
        this.itemList = itemList;
    }

    /**
     *
     * @return The size of the item list.
     */
    @Override
    public int getCount() {
        return itemList.size();
    }

    /**
     * Android might crash if memory is not properly released.
     * @param container It is necessary to check that this is not null.
     * @param position Position of the object being released
     * @param object The object being released.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // super.destroyItem(container, position, object);       //Removed because of a bug
        ((ViewGroup) container).removeView((View)object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object==view;
    }


    /** {@inheritDoc} **/
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /* I am using java reflection to construct an object of vClass here.
         This is just a temporary solution.
         I have to refactor this code, and get rid of the reflection methods before
         something unintended happens because of this. (Ibrahim, shath)
         */
        try {
            /* Use java.reflection to instantiate an object of the ViewClass  */
            Class vClass = Class.forName(ViewClass.getName());              //Retrieve the ViewClass name
            Constructor vConstructor = vClass.getConstructor(Context.class, BaseModel.class);  // Retrieve our default constructor: ViewClass(Context context)

            Object viewObject = vConstructor.newInstance(container.getContext(), itemList.get(position));  //Create a new instance of that object

            container.addView((View) viewObject);                   // Typecast and try to add the object into our ViewGroup

            ((AbstractCardView) viewObject).onLoad();

            return  viewObject;
        }
        catch (NoSuchMethodException ex)  //Thrown possibly by getConstructor()
        {
            Log.e(TAG, "instantiateItem: getConstructor: No such method exception occurred. "  );
        }
        catch(Exception ex){
            Log.e(TAG, "instantiateItem: "+ ex.getMessage());
        }

        //Display a fallback item
        TextView tv = new TextView(container.getContext());
        tv.setText("Error occured! ");
        container.addView(tv);
        return tv;

    }

}
