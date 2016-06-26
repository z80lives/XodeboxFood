package xodebox.food;

import com.android.volley.VolleyError;

/**
 * Created by Max on 26/6/2016.
 */
public interface VolleyCallback {
    void onSuccess(String response);
    void onErrorResponse(VolleyError error);
}
