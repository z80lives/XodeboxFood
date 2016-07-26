package xodebox.food.ui.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Search View used by the Search Result View layout file.
 * We could have used an edit text or the SearchView class itself.
 * @author shath ibrahim
 */
public class MainSearchView extends android.widget.SearchView {
    /** {@inheritDoc} **/
    public MainSearchView(Context context) {
        super(context);
        init(context);
    }
    /**{@inheritDoc}**/
    public MainSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context){
        //inflate(context, android.support.v7.appcompat.R.layout.abc_search_view, this);
    }

}
