package xodebox.food.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xodebox.food.R;

/**
 * Fragment code for the More Screen.
 * Inflate the more screen layout file.
 * @see xodebox.food.ui.adapters.ScreenPagerAdapter
 * @author shath
 */
public class MoreScreenFragment extends Fragment {

    /** {@inheritDoc}**/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.more_page, null);
    }
}
