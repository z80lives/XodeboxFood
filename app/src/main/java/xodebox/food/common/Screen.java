package xodebox.food.common;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by shath on 7/25/2016.
 */
public class Screen {
    /**
     * TODO REMOVE THIS METHOD AND USE LAYOUT XML FILES INSTEAD
     * Converts dip to pixel for the set screen pager's margin function.
     * @param context
     * @param dip
     * @return
     */
    public static int convertDip2Pixels(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }
}
