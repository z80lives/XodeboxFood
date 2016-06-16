package xodebox.food;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Max on 2/6/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.v("ServerMsg",remoteMessage.getData().toString());
        //Log.v("ServerMsg",remoteMessage.getNotification().getBody());
       // Log.v("ServerMsg",remoteMessage.getMessageType());


    }
}
