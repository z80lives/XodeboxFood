package xodebox.food;

import java.util.ArrayList;

/**
 * Created by MaxAhirwe on 26/07/16.
 * @deprecated ...
 */
public class Services {

    ArrayList<Restaurant> NearByRestaurants;


/*
public  ArrayList<Restaurant> GetNearbyRestaurant(int useraddress,Context ctx){
    
    //// TODO: 27/07/16 :Generate random addresses nearby my location and assign them to the sql table restaurants -http://gis.stackexchange.com/questions/25877/how-to-generate-random-locations-nearby-my-location

    NearByRestaurants=null;
    //call  using http request that will return a set of restaurants
    String URL=Configs.BackEndUrl+"?type=5&address="+useraddress;
    HttpRequest request=new HttpRequest(ctx);
    request.SendGetrequest(URL, new VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    Log.v("Volley", "Response is: " + response);
                    Gson g = new Gson();
                    //http://stackoverflow.com/questions/2770273/pdostatement-to-json
                    JSONArray response_jsonarray = null;
                    try {
                        response_jsonarray = new JSONArray(response);
                        for (int i = 0; i < response_jsonarray.length(); i++) {
                            JSONObject jsonobject = response_jsonarray.getJSONObject(i);
                            Restaurant restaurant = g.fromJson(jsonobject.toString(), Restaurant.class);
                            NearByRestaurants.add(restaurant);
                        }
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

}*/

}
