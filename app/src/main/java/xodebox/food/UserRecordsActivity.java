package xodebox.food;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by shath on 6/14/16.
 *
 * Manages History, Tagged and Favourite Screen
 */
public class UserRecordsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_screen);

/*
        String sitem[] = {"ew", "23"};
        ArrayList<String> item = new ArrayList<String> (Arrays.asList(sitem));

        ArrayAdapter<String> itemAdapter =
                new ArrayAdapter<String>(this, R.layout.text_item_placeholder, item);

        ListView listView = (ListView) findViewById(R.id.history_list_item);
        listView.setAdapter(itemAdapter);
        */

        ArrayList<HistoryItemsModel> items = new ArrayList<HistoryItemsModel>();
        HistoryItemsAdapter adapter = new HistoryItemsAdapter(this, 0, items);

        ListView listView = (ListView) findViewById(R.id.history_list_item);
        listView.setAdapter(adapter);


        items.add(new HistoryItemsModel("Restaurant1", "Nothing"));
        //Add 3 dummy items
        for(int i=0;  i<10; i++)
        {
            items.add(new HistoryItemsModel());
        }
    }

}
