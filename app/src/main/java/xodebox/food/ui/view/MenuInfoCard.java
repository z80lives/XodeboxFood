package xodebox.food.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xodebox.food.R;
import xodebox.food.common.models.BaseModel;
import xodebox.food.common.models.Model;

/**
 * Created by shath on 7/31/2016.
 */
public class MenuInfoCard extends AbstractCardView {
    private static final String TAG = "MenuInfoCard";
    enum daysInWeek{
        SUN,
        MON,
        TUE,
        WED,
        THU,
        FRI,
        SAT
        }

    public MenuInfoCard(Context context, AttributeSet attributeSet) {
        super(context, new Model(), attributeSet);
    }

        public MenuInfoCard(Context context, BaseModel model) {
        super(context, model);
    }

    public MenuInfoCard(Context context, BaseModel model, AttributeSet attrs) {
        super(context, model, attrs);
    }

    @Override
    protected void onCreate() {

        if(getModel() != null){
            //String textTime = getModel().getProperty(getResources().getString(R.string.key_restaurant_time_open));
            setTextView(R.id.tv_ric_time, getResources().getString(R.string.key_restaurant_time_open) );
            ViewGroup operatingDays = (ViewGroup) findViewById(R.id.field_operating_days);

            ArrayList<View> dayTags = new ArrayList<>();

            //Fetch sunday tag
            View sundayTag = operatingDays.findViewById(R.id.daytag_sunday);

            //Fetch rest of the day tags
            int sunday_index = operatingDays.indexOfChild(sundayTag);
            for(int i=0; i<operatingDays.getChildCount()-1; i++){
                View child = operatingDays.getChildAt(sunday_index++);
                if(child != null)
                    dayTags.add(child);
            }

            //Hide all of them
            for(View dayTag : dayTags){
                dayTag.setVisibility(
                        View.GONE
                );
            }

            String[] daysOpen = getModel().getChildArray(getResources()
                                                            .getString(R.string.key_restaurant_days_open));
            //Then show day tags if they exists in Model
            if (daysOpen != null){
                for(String day: daysOpen) {
                    day = day.toUpperCase();
                    try {
                        day = day.substring(0,3);
                        int index = daysInWeek.valueOf(day).ordinal();
                        dayTags.get(index).setVisibility(View.VISIBLE);
                    }catch (IllegalArgumentException ex){
                        Log.e(TAG, "onCreate: Day value is improper.", ex);
                    }catch (IndexOutOfBoundsException ex){
                        Log.e(TAG, "onCreate: The value of day string must be atleast three characters long.", ex);
                    }
                }
                operatingDays.setVisibility(View.VISIBLE);
            }//else put fallback
            else{
                operatingDays.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected View inflateResource() {
        return inflate(getContext(), R.layout.restaurant_info_card, this);
    }
}
