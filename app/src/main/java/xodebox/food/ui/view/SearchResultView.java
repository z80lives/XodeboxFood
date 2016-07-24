package xodebox.food.ui.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import xodebox.food.R;
import xodebox.food.common.models.BaseModel;
import xodebox.food.ui.adapters.SearchRestaurantAdapter;

/**
 * Currently inflating native SearchView instead of support SearchView.
 * TODO: Use support SearchView, update support library inside the app build gradle file to do that.
 * Created by shath on 7/22/2016.
 */
public class SearchResultView extends LinearLayoutCompat {
    private static final String TAG = "SearchResultView";

    private View rootView;
    private Context context;
    private SearchView searchView;
    private RecyclerView recyclerView;


    public SearchResultView(Context context) {
        super(context);
        this.context = context;
        try {
            rootView = inflate(context, R.layout.search_result_layout, this);
        }catch (Exception ex)
        {

            Log.e(TAG, "SearchResultView: Cannot Create class. " + ex.getMessage());
        }
        // addView(rootView);
        if( fetchViews() ) {
            prepareSearchView();
            prepareRecyclerView();
            populateResultList();
        }else {
            Log.e(TAG, "SearchResultView: "+ "Cannot fetch the views" );
            //TODO Fallback content
        }
    }



    private boolean fetchViews(){
        //FIXME: Throws null pointer exception sometimes. Display fallback content if it happens.
        searchView = (SearchView) findViewById(R.id.searchview_id);
        recyclerView = (RecyclerView) findViewById(R.id.search_result_listview);
        if (recyclerView == null) {
            Log.e(TAG, "fetchViews: Cannot find recycler view." );
            return  false;
        }
        return true;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    private void prepareRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);//Throws Null pointer exception
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void prepareSearchView(){
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
    }

    private void populateResultList(){
        List<BaseModel> searchResultModels = new ArrayList<>();
        for(int i=0; i < 3; i++){
            BaseModel resultModel = new BaseModel() {};
            resultModel.addProperty("name", "item "+i);
            searchResultModels.add(resultModel);
        }

        //ListAdapter<String> stringListAdapter =
        //final ArrayAdapter<String> searchResultAdapter = new ArrayAdapter<String>(context, R.layout.home_search_result_item,
        //        R.id.name, lstData);

        RecyclerView.Adapter searchResultAdapter = new SearchRestaurantAdapter(searchResultModels);

        RecyclerView resultsView = (RecyclerView) findViewById(R.id.search_result_listview);
        resultsView.setAdapter(searchResultAdapter);
    }

}
