package xodebox.food.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import xodebox.food.common.models.BaseModel;

/**
 * Created by shath on 7/26/2016.
 */
public abstract class AbstractClickableCardView extends AbstractCardView implements View.OnClickListener{
    private static final String TAG = AbstractCardView.class.getSimpleName();

    public AbstractClickableCardView(Context context, BaseModel model) {
        super(context, model);
        init();
    }
    public AbstractClickableCardView(Context context, BaseModel model, AttributeSet attrs) {
        super(context, model, attrs);
        init();
    }

    private void init(){
        setOnClickListener(this);


        //For debug purpose
        /**try {
            getClickListener();
        }catch (Exception ex)
        {
            Log.e(TAG, "init: ", ex);
        }**/
    }

    /**
     * Get local click listener for the card view
     * @return {@link android.view.View.OnClickListener}
     * @throws NoClickListenerException
     */
    public View.OnClickListener getClickListener() throws NoClickListenerException {
        if(!isClickable()) return null;
        return this;
    }


    /**
     * Thrown when the card view is not clickable
     */
    public static class NoClickListenerException extends Exception{
        public NoClickListenerException() {
            super();
        }

        public NoClickListenerException(String detailMessage) {
            super(detailMessage);
        }
    }

}
