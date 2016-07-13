package xodebox.food.common.models;

/**
 * The data model intended for the RestaurantCard, displayed on the home screen.
 * Created by shath on 7/9/2016.
 */
public class Restaurant extends BaseModel {

    /**
     * All properties are String objects
     */
    public enum Attrib{
        name,
        description,
        imgurl
    }


    public void set(Attrib attrib, String value){
        String key = attrib.toString();
        addProperty(key, value);
    }

    /**
     * Empty constructor
     */
    public  Restaurant(){
       // this(Parcel.obtain());
    }

}
