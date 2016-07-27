package xodebox.food;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MaxAhirwe on 26/07/16.
 */
public class Services {

    public ArrayList<Restaurant> getNearByRestaurants() {
        return NearByRestaurants;
    }

    static ArrayList<Restaurant> NearByRestaurants=new ArrayList<Restaurant>();


    /**
     * fetch the nearest restaurant according to the user current address co0rdinates
     * @param useraddress
     * @param ctx
     * @return
     */
    public static ArrayList<Restaurant> GetNearbyRestaurant(String useraddress, final Context ctx){
    
    //// TODO: 27/07/16 :Generate random addresses nearby my location and assign them to the sql table restaurants -http://gis.stackexchange.com/questions/25877/how-to-generate-random-locations-nearby-my-location


    //call  using http request that will return a set of restaurants
    String URL=Configs.BackEndUrl+"?type=5&useraddress="+useraddress;
    HttpRequest request=new HttpRequest(ctx);
    request.SendGetrequest(URL, new VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    Log.v("Volley", "Response is: " + response);
                    Gson g = new Gson();
                    JSONArray response_jsonarray = null;
                    try {
                        response_jsonarray = new JSONArray(response);
                        NearByRestaurants.clear();
                        for (int i = 0; i < response_jsonarray.length(); i++) {
                            JSONObject jsonobject = response_jsonarray.getJSONObject(i);
                            Restaurant restaurant = g.fromJson(jsonobject.toString(), Restaurant.class);
                            NearByRestaurants.add(restaurant);
                        }
                        Log.v("Volley", "restaurant arraylist size:" + NearByRestaurants.size());

                        Log.v("Volley", "restaurant 5:" + NearByRestaurants.get(5));

                        NearByRestaurants.get(5).FetchRestaurantImages(ctx);



                    } catch (JSONException e) {
                        Log.v("Volley", "Response is: " + response + "(invalid json array)");

                    }
                    //Picasso.with(ctx).setIndicatorsEnabled(true);
                    //Picasso.with(ctx).load(img.image_link).into(imgview);
                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("Volley", "Error,Response is: " + error.getMessage());
                }
            }
    );
    return null;

}

}
