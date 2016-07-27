package xodebox.food;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Max on 26/6/2016.
 *  helps easily access usefull android methods associated to widgets and components
 */
public class QuickMethods {

    //needs to be initialized at the launch of the app
    public static Context ctx;

    public static void Toast(String msg){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(ctx, msg, duration);
        toast.show();

    }
}
