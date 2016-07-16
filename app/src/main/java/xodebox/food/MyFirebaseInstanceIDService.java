package xodebox.food;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max on 2/6/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("GCM-Token", "Refreshed Token:" + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        Map<String, String> params = new HashMap<String, String>();
        params.put("token",refreshedToken);
        HttpRequest request = new HttpRequest(this);
        request.SendPostrequest("http://foodapp.xodebox.com",params);

    }
}
