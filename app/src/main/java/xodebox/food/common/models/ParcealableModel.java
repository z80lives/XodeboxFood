package xodebox.food.common.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Constructor;

/**
 * For use, if necessary.
 * Created by shath on 7/10/2016.
 */
public abstract class ParcealableModel extends BaseModel implements Parcelable{
    private static final String TAG = "ParcealableModel";

    /**
     * Constructs a new instance of {@code Object}.
     *
     * @param in
     */
    public ParcealableModel(Parcel in) {
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
    public abstract void writeToParcel(Parcel dest, int flags);

    public abstract void readFromParcel(Parcel in);
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
