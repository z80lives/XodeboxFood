package xodebox.food;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import com.google.gson.Gson;

/**
 * Created by Max on 24/7/2016.
 */
public class Restaurant {

    int id;
    int manager;
    String address,email,website,name;
    //contains all images of a restaurant
    ArrayList<Image> RestaurantImages;

    public Restaurant() {
        this.RestaurantImages=new ArrayList<Image>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public Restaurant(int id, int manager, String address, String email, String website, String name) {
        this.id = id;
        this.manager = manager;
        this.address = address;
        this.email = email;
        this.website = website;
        this.name = name;

    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void FetchRestaurantImages(final Context ctx){
        String URL=Configs.BackEndUrl+"?type=4&restaurant="+this.getId();
        HttpRequest request=new HttpRequest(ctx);
        request.SendGetrequest(URL, new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.v("Volley", "Response is: " + response);
                        Gson g = new Gson();
                        JSONArray response_jsonarray = null;
                        try {
                            RestaurantImages.clear();
                            response_jsonarray = new JSONArray(response);
                            for (int i = 0; i < response_jsonarray.length(); i++) {
                                JSONObject jsonobject = response_jsonarray.getJSONObject(i);
                                Image img= g.fromJson(jsonobject.toString(), Image.class);
                                RestaurantImages.add(img);
                            }
                            Log.v("Volley", "images fetched: " + RestaurantImages.size());
                            Image image5=RestaurantImages.get(5);
                            Log.v("Volley", "restaurant image 5:" +image5);
                        } catch (JSONException e) {
                            Log.v("Volley", "Response is: " + response + "(invalid json array)");

                        }

                    }
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Volley", "Error,Response is: " + error.getMessage());
                    }
                }
        );
    }

    public ArrayList<Image> getRestaurantImages() {
        return RestaurantImages;
    }

    public void setRestaurantImages(ArrayList<Image> restaurantImages) {
        RestaurantImages = restaurantImages;
    }



}
