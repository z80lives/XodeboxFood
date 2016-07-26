package xodebox.food.ui.interfaces;

import android.view.View;

/**
 * To be used within an Activity with a Search View if necessary.
 * Currently not used. (25/7/2016)
 */
public interface FragmentWithSearchBar {
    /**
     * Retrieves the current search bar activity.
     * @return {@code ImageButton } or a clickable {@code EditView} of the Search bar.
     */
    View getSearchButton();

    /**
     * Should return the click handler event. The method can simply return an anonymous method or a
     * reference to the locally implemented click listener method. (i.e if local class implements the click listener interface
     * then {@code return this; })
     * @return {@link android.view.View.OnClickListener} Click Listener method.
     */
    View.OnClickListener getSearchListener();
}
