package xodebox.food.common.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * For use, if necessary.
 * Created by shath on 7/10/2016.
 */
public class ParcealableModel extends BaseModel implements Parcelable{
    private static final String TAG = "ParcealableModel";


    /**
     * Constructs a new instance of {@code Object}.
     *
     * @param in
     */
    private ParcealableModel(Parcel in) {
        super();
        readFromParcel(in);
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags){
       // dest.writeMap(getAttributes());
        Map<String, String> map = getAttributes();
        dest.writeInt(map.size());
        for(Map.Entry<String, String> entry: map.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    /**
     * Read the attributes from parcel.
     *
     * @param in The Parcel object to be read from
     */
    public void readFromParcel(Parcel in){
        int size = in.readInt();
        for(int i=0; i<size; i++){
            String key = in.readString();
            String value = in.readString();
            addProperty(key, value);
        }
    }


    /**
     * Parcelable Creator
     */
    public static final Parcelable.Creator<ParcealableModel> CREATOR = new Parcelable.Creator<ParcealableModel>() {
        public ParcealableModel createFromParcel(Parcel in) {
            return new ParcealableModel(in);
        }

        public ParcealableModel[] newArray(int size) {
            return new ParcealableModel[size];
        }
    };

    ///////////////////////////////////////////////////////////////////////////
    // Untested
    ///////////////////////////////////////////////////////////////////////////
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
