package xodebox.food.common.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shath on 7/9/2016.
 */
public abstract class BaseModel {
    private static final String TAG = "BaseModel";
    private Map<String, String> strProperties;

    /**
     * Constructs a new empty model.
     */
    public BaseModel() {
        strProperties = new HashMap<String, String>();
    }

    /**
     * Store all attribute into one array and return them
     * @return
     */
    public ArrayList<String> getAttributesList(){
        ArrayList<String> ret = new ArrayList<String>();
        ret.addAll(strProperties.values());
        return  ret;
    }

    /**
     * Add new attribute to the object.
     * @param key
     * @param value
     */
    public void addProperty(String key, String value){
        strProperties.put(key, value);
    }

    public String getProperty(String key)
    {
        return strProperties.get(key);
    }

    /**
     * Make a new creator. Creator should be static for each Parcelable.
     * @return
     */
    public static Parcelable.Creator<BaseModel> newCreator(){
        return new Parcelable.Creator<BaseModel>() {
            public BaseModel createFromParcel(Parcel in) {
                try
                {
                    // Try to create and instantiate object of the child class, then return it
                    Class tClass = this.getClass();
                    Constructor tConstructor = tClass.getConstructor(Parcel.class);
                    Object instant = tConstructor.newInstance(in);
                    return (BaseModel) instant;
                }catch (Exception ex)
                {
                    Log.e(TAG, "createFromParcel: Exception, "+ ex.getMessage());
                    return null;
                }
            }

            public BaseModel[] newArray(int size) {
                return new BaseModel[size];
            }
        };
    }




}
