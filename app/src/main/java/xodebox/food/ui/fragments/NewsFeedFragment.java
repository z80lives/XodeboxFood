package xodebox.food.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import xodebox.food.R;
import xodebox.food.common.models.Model;

/**
 * The fragment code for the Social Feed.
 * @see xodebox.food.ui.adapters.ScreenPagerAdapter
 * @author shath
 */
public class NewsFeedFragment extends Fragment {
    private View rootView;
    private RecyclerView newsFeedItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = new FrameLayout(getContext());
        rootView = inflater.inflate(R.layout.news_feed_page, null);

        fetchViews();
        prepareRecyclerView();
        fillList();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void fetchViews(){
        newsFeedItems = (RecyclerView) rootView.findViewById(R.id.feed_list);
    }

    private void prepareRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        newsFeedItems.setLayoutManager(layoutManager);
        newsFeedItems.setItemAnimator(new DefaultItemAnimator());
    }

    private void fillList(){
        List<Model> modelList = new ArrayList<>();
        for(int i = 0; i<10; i++)
            modelList.add(new Model());
        NewsFeedAdapter adapter = new NewsFeedAdapter(modelList);
        newsFeedItems.setAdapter(adapter);
    }
}

class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>{
    private List<Model> modelList;
    public NewsFeedAdapter(List<Model> modelList) {
        super();
        this.modelList = modelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_feed_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}