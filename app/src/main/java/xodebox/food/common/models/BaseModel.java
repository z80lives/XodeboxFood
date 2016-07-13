package xodebox.food.common.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class for the data model objects, to be read mainly by the UI objects.
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

    public Map<String, String> getAttributes(){
        return strProperties;
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
     * Add attributes using JSON data
     * Might throw exception, if the JSONObject is not well defined.
     * TODO: Modify the code so that we can accept JSON objects with inherited objects and arrays.
     * @return True on success <br /> False on failure
     */
    public boolean setAttributes(JSONObject jsonObject) throws JSONException{
        Iterator<String> iKeys = jsonObject.keys();

        while (iKeys.hasNext())
        {
            String key = iKeys.next();
            String value = (String) jsonObject.get(key);        //Type casting here is probably a bad idea. // FIXME: 7/11/2016 

            // We do not accept child object or an array inside our JSON object.
            // if (value instanceof JSONArray || value instanceof JSONObject)
           //     return false;

            addProperty(key, value);
        }

        return true;
    }

    /**
     * Copies attributes from another model. Overwrites existing attributes.
     * @param inModel
     * @return
     */
    public boolean copyAttributes(BaseModel inModel)
    {
        HashMap<String, String> srcHashMap = (HashMap<String, String>) inModel.getAttributes();
        try {
            strProperties.putAll(srcHashMap);
        }catch (Exception ex){
            Log.e(TAG, "setAttributes: "+ex.getMessage() );
            return false;
        }
        return true;
    }

}
