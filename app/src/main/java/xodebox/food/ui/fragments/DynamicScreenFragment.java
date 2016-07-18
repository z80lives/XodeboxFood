package xodebox.food.ui.fragments;

import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

/**
 * Created by shath on 7/17/2016.
 */
public abstract class DynamicScreenFragment extends Fragment {

    /**
     * Create the root layout
     * @return rootLayout
     */
    protected LinearLayout createLinearRootView(){
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setWeightSum(1);
        rootView.setOrientation(LinearLayout.VERTICAL);
        return rootView;
    }
}
