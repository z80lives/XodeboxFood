package xodebox.food.ui.interfaces;

import android.widget.ImageButton;

/**
 * Interface for an Activity containing a dice button and a
 * fragment that contains the event handler method for the dice button.
 * Implemented in {@code HomePageFragment}
 *  @see xodebox.food.ui.fragments.HomePageFragment
 *  @see xodebox.food.ui.adapters.ScreenPagerAdapter
 *  @author Ibrahim
 */
public interface RollDiceInterface {
    /**
     * Set the on click listener inside this method.
     * @param rollDiceImage Image button in navigation bar of our original design. (ref: Xodebox FoodApp, wireframe 2)
     */
    void setRollDiceImage(ImageButton rollDiceImage);

}
