package xodebox.food.ui.interfaces;

/**
 * Interface for Activity with a 'Search View'.
 * Should be used by a fragment With Search bar and it's parent activity.
 */
public interface ActivityWithSearchView {
    /**
     * Performs the actions when the search bar is clicked. The default action is to
     * display the search view within the Activity.
     */
    void searchButtonClicked();
}
