package xodebox.food.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xodebox.food.R;
import xodebox.food.common.models.BaseModel;
import xodebox.food.common.models.Model;

/**
 * Created by shath on 7/29/2016.
 */
public class ReviewBox extends AbstractCardView{

    public ReviewBox(Context context)
    {
        super(context, new Model());
        createDummyForEditor();
    }


    public ReviewBox(Context context, AttributeSet attrs) {
        super(context, new Model(), attrs);
        createDummyForEditor();
    }

    public ReviewBox(Context context, BaseModel model) {
        super(context, model);
        createDummyForEditor();
    }

    public ReviewBox(Context context, BaseModel model, AttributeSet attrs) {
        super(context, model, attrs);
    }

    /**
     * Add sample items so it shows up in edit mode
     */
    private void createDummyForEditor(){
        if(isInEditMode()) {
            ListView listView = (ListView) findViewById(R.id.reviewbox_items);

            List<ReviewItem> views = new ArrayList<>();
            views.add(new ReviewItem(context));

            String[] items = {"User1", "User2", "User3"};
            List<String> itemList = Arrays.asList(items);

            ArrayAdapter<String> adapter= new ArrayAdapter<String>(context, R.layout.review_card, R.id.review_username, itemList);


            listView.setAdapter(adapter);
        }

    }


    @Override
    protected void onCreate() {

    }

    @Override
    protected View inflateResource() {
        return inflate(getContext(), R.layout.review_box, this);
    }
}
