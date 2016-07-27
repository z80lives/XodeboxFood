package xodebox.food.ui.managers;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shath on 7/26/2016.
 */
public interface XodeboxViewManager {
    public boolean showView(View v);
    public void addView(View v);

    public View getActiveView();
    public void setLoadView(View v);
    public void setReadyView(View v);
    public void setLoading(boolean val);

    public ViewGroup getRootView();
}
