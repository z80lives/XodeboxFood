package xodebox.food.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xodebox.food.R;

/**
 * Created by User on 7/3/2016.
 */
public class AddNewRestaurantFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View addNewRestaurantView = inflater.inflate(R.layout.add_restaurant_page, container, false);


        return addNewRestaurantView;
    }
}
