package xodebox.food.ui.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by shath on 7/22/2016.
 */
public class MainSearchView extends android.widget.SearchView {
    public MainSearchView(Context context) {
        super(context);
        init(context);
    }

    public MainSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

  /*  public MainSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }*/

    private void init(Context context){
        //inflate(context, android.support.v7.appcompat.R.layout.abc_search_view, this);
    }

    private void customInflate(){

    }


}
