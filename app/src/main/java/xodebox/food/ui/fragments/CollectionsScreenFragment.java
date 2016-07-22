package xodebox.food.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import xodebox.food.R;

/**
 * Created by shath on 7/22/2016.
 */
public class CollectionsScreenFragment extends Fragment {
    private ViewGroup rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = new FrameLayout(getContext());
        return inflater.inflate(R.layout.collections_page, rootView);
    }
}
