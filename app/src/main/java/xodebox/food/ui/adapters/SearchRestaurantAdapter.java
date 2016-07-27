package xodebox.food.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xodebox.food.R;
import xodebox.food.common.models.BaseModel;

/**
 * Recycler adapter for search items.
 * @author Ibrahim
 */
public class  SearchRestaurantAdapter extends RecyclerView.Adapter<SearchRestaurantAdapter.ViewHolder> {
    private List<BaseModel> itemList;

    public SearchRestaurantAdapter(List<BaseModel> itemList) {
        super();
        this.itemList = itemList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /** {@inheritDoc} **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    /** {@inheritDoc} **/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.home_search_result_item, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /** {@inheritDoc} **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }
}
