package xodebox.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shath on 6/14/16.
 */
public class HistoryItemsAdapter extends ArrayAdapter<HistoryItemsModel> {

    public HistoryItemsAdapter(Context context, int resource, ArrayList<HistoryItemsModel> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryItemsModel item = getItem(position);

        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_item_restaurant_history, parent, false);

        }
        updateContent(convertView, item);
        return convertView;
    }

    private void updateContent(View view, HistoryItemsModel item){
        TextView restaurant_name = (TextView) view.findViewById(R.id.restaurant_name);
        TextView restaurant_detail = (TextView) view.findViewById(R.id.restaurant_detail);

        restaurant_name.setText(item.getRestaurant_name());
        restaurant_detail.setText(item.getRestaurant_details());
    }
}
