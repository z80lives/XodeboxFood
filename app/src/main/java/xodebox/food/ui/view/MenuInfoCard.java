package xodebox.food.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import xodebox.food.R;
import xodebox.food.common.models.BaseModel;
import xodebox.food.common.models.Model;

/**
 * Created by shath on 7/31/2016.
 */
public class MenuInfoCard extends AbstractCardView {

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

    }

    @Override
    protected View inflateResource() {
        return inflate(getContext(), R.layout.restaurant_info_card, this);
    }
}
