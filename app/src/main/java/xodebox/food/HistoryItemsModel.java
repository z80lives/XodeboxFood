package xodebox.food;

/**
 * Created by shath on 6/14/16.
 */

public class HistoryItemsModel {
    private String restaurant_name;
    private String restaurant_details;


    //Default constructor
    public HistoryItemsModel(){
        restaurant_name    = "Unkown Restaurant";
        restaurant_details = "Information not updated yet.";
    }

    public HistoryItemsModel(String name, String detail)
    {
        restaurant_name     = name;
        restaurant_details  = detail;
    }

    //Access Modifiers
    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_details() {
        return restaurant_details;
    }

    public void setRestaurant_details(String restaurant_details) {
        this.restaurant_details = restaurant_details;
    }
}
