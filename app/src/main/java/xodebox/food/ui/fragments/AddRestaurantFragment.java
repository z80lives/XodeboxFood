package xodebox.food.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xodebox.food.R;

/**
 * Created by shath on 7/2/2016.
 */
public class     AddRestaurantFragment extends Fragment {
    /**
     * Executes when the view is created.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_restaurant_page, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
