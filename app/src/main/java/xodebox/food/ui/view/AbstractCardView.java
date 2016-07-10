package xodebox.food.ui.view;

import android.content.Context;
import android.widget.FrameLayout;

import java.util.ArrayList;

import xodebox.food.common.models.BaseModel;

/**
 * Abstract view for all cards.
 * All view objects should inflate their XML layout, and name attribute fields of their modifiable child views.
 * Created by shath on 7/8/2016.
 */
public abstract class AbstractCardView extends FrameLayout{
    private ArrayList<String> attributes;

    public AbstractCardView(Context context, BaseModel model) {
        super(context);
        attributes = model.getAttributesList();
    }

    public void updateCard(){

    }

    /**
     * Add a string to the attribute list
     * @param attrib String to store
     */
    public void addAttribute(String attrib){
        attributes.add(attrib);
    }

    /**
     * Get a string from attribute array list.
     * @param position
     */
    public String getAttribute(int position){
        return attributes.get(position);
    }

    public void ReadAttributes(BaseModel m){
        m.getAttributesList();
    }
}
