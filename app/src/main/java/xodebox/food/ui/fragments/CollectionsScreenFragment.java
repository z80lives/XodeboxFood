package xodebox.food.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xodebox.food.R;
import xodebox.food.common.models.BaseModel;

/**
 * TODO: Replace listView with RecyclerView. Read and create views from JSON/XML File. Refactor and clean the code.
 * Created by shath on 7/22/2016.
 */
public class CollectionsScreenFragment extends Fragment {
    private static final String TAG = "CollectionsScreen";
    private View rootView;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = new FrameLayout(getContext());


        rootView =   inflater.inflate(R.layout.collections_page, null);

        //Maybe we should do this on an Async task
        fetchViews();
        populateList();

        return rootView;
    }

    /**
     * Fetch views from layout and store them in the class variable.
     */
    private void fetchViews(){
        listView = (ListView) rootView.findViewById(R.id.collection_item_list);
    }

    /**
     * Populate our list
     */
    public void populateList(){
        if (listView == null || rootView == null) {
            Log.e(TAG, "populateList: Null Exception occured! " );
            TextView tv = new TextView(getContext());
            tv.setText("Nothing to display!");
//            /rootView.addView(tv);
            return;
        }

        //Create a temporary list view for now
        List<BaseModel> collectionItems = new ArrayList<>();
        //Create and add few items into our list
        String[] names = {"Chalk and cheese", "Olive Garden", "Pizza Hut", "KGB", "Ali Maju", "Nandos"};


        //A temporary adapter for our list view
        ArrayAdapter<String> collectionsAdapter = new ArrayAdapter<>(rootView.getContext(), R.layout.collection_item, R.id.thumb_rest_name, Arrays.asList(names) );

        //Set the adapter to the view
        listView.setAdapter(collectionsAdapter);
    }
}
