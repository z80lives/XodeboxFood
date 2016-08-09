package xodebox.food.common.interfaces;

import android.view.View;
import android.widget.ImageView;

import java.util.Map;

/**
 * Should be something like this:
 * @startuml
 * [*]    --> Create
 * Create --> Load
 * Display   --> Update
 * Display --> [*]
 * Load --> Display
 * Update --> Display
 * Display --> Display
 *
 * Create : UI is created for first time
 * Load : Load Images, View is ready to display
 * Update : Refresh is called
 * Display : Views are rendered
 *
 * @enduml
 */
public interface ViewWithStates {
    Map<ImageView, String> getImageMap();

    View getActiveView();
    void onCreate();
    void onLoad();
    void update();
    //void onDraw();
}
