package xodebox.food.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xodebox.food.R;
import xodebox.food.common.models.Model;

/**
 * TODO: Replace listView with RecyclerView. Read and create views from JSON/XML File. Refactor and clean the code.
 * FIXME: Possible bug occurs occasionally while inflating the layout
 * Created by shath on 7/22/2016.
 * @see xodebox.food.ui.adapters.ScreenPagerAdapter
 */
public class CollectionsScreenFragment extends Fragment {
    private static final String TAG = "CollectionsScreen";
    private View rootView;
    private RecyclerView listView;
    private Context context;

    /** {@inheritDoc} **/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView =   inflater.inflate(R.layout.collections_page, null);

        //Maybe we should do this on an Async task
        if(fetchViews()) {
            populateList();
        }

        return rootView;
    }

    /**
     * Fetch views from layout and store them in the class variable.
     */
    private boolean fetchViews(){
        listView = (RecyclerView) rootView.findViewById(R.id.collection_item_list);
        if(listView == null)
            return  false;
        return true;
    }

    /**
     * Populate our list, with temporary data.
     * TODO: Fetch data from the network and populate using an AsyncTask.
     */
    public void populateList(){
        if (listView == null || rootView == null) {
            Log.e(TAG, "populateList: Null Exception occured! " );
            //TextView tv = new TextView();
            //tv.setText("Nothing to display!");
//            /rootView.addView(tv);
            return;
        }

        //Create a temporary list view for now

        //Create and add few items into our list
        String[] names = {"Chalk and cheese", "Olive Garden", "Pizza Hut", "KGB", "Ali Maju", "Nandos"};
        List<Model> models = new ArrayList<>();
        models.add(new Model());
        models.add(new Model());

        //prepare recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        listView.setLayoutManager(layoutManager);//Throws Null pointer exception
        listView.setItemAnimator(new DefaultItemAnimator());

        //A temporary adapter for our list view
       // ArrayAdapter<String> collectionsAdapter = new ArrayAdapter<>(rootView.getContext(), R.layout.collection_item, R.id.name, Arrays.asList(names) );
        RecyclerView.Adapter collectionsAdapter = new CollectionRecyclerAdapter(models);
        //Set the adapter to the view
        listView.setAdapter(collectionsAdapter);
    }
}

class CollectionRecyclerAdapter extends RecyclerView.Adapter <CollectionRecyclerAdapter.ViewHolder> {
    private List<Model> itemList;

    public CollectionRecyclerAdapter(List<Model> itemList) {
        super();
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    //    onBindViewHolder(holder, position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
