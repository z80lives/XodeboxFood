package xodebox.food.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created by shath on 7/4/2016.
 */
public class ItemCardAdapter<Model> extends PagerAdapter {
    private static final String TAG = "ItemCardAdapter";
    //private  ArrayAdapter<ViewClass> arrayAdapter;
    private List<Model> itemList;
    private Class<?> ViewClass;

    /**
     * Create ItemCardAdapter from item list.
     * @param itemList List of view items
     */
    public ItemCardAdapter(List<Model> itemList, Class<?> ViewClass){
        super();
        this.ViewClass =  ViewClass;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size()-1;
        //return 1;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object==view;
    }

    /**
     * Instantiate object
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        try {
            /* Use java.reflection to instantiate an object of the ViewClass  */
            Class vClass = Class.forName(ViewClass.getName());              //Retrieve the ViewClass name
            Constructor vConstructor = vClass.getConstructor(Context.class);  // Retrieve our default constructor: ViewClass(Context context)

            Object viewObject = vConstructor.newInstance(container.getContext());  //Create a new instance of that object
            container.addView((View) viewObject);                   // Typecast and try to add the object into our ViewGroup
            return  viewObject;
        }catch(Exception ex){
            Log.e(TAG, "instantiateItem: "+ ex.getMessage());
        }

        TextView tv = new TextView(container.getContext());
        tv.setText("Error occured");
        container.addView(tv);
        return tv;
    }
}
