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

/**
 * Created by shath on 7/4/2016.
 */
public class ItemCardAdapter extends PagerAdapter {
    private static final String TAG = "ItemCardAdapter";
    //private  ArrayAdapter<ViewClass> arrayAdapter;
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

    @Override
    public int getCount() {
        return itemList.size();
        //return 1;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object==view;
    }

    /**
     * Insantiate an object of the previously defined class, in the constructor. The class must be a
     * valid ViewGroup/View class which will be added into the container.
     * @param container
     * @param position
     * @return View object of ViewClass on success. Fallback content on failure.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        try {
            /* Use java.reflection to instantiate an object of the ViewClass  */
            Class vClass = Class.forName(ViewClass.getName());              //Retrieve the ViewClass name
            Constructor vConstructor = vClass.getConstructor(Context.class, BaseModel.class);  // Retrieve our default constructor: ViewClass(Context context)

            Object viewObject = vConstructor.newInstance(container.getContext(), itemList.get(position));  //Create a new instance of that object

            container.addView((View) viewObject);                   // Typecast and try to add the object into our ViewGroup

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
