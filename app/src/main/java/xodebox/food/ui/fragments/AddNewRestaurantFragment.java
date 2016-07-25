package xodebox.food.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xodebox.food.R;

/**
 * This fragment is unused in this project.
 * TODO: Decide and remove this fragment and it's layout.
 */
public class AddNewRestaurantFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View addNewRestaurantView = inflater.inflate(R.layout.add_restaurant_page, container, false);

        return addNewRestaurantView;
    }
}
