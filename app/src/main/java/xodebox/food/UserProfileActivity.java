package xodebox.food;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfileActivity extends Activity {
    final private XodeboxBase appBase = XodeboxBase.getInstance();
    private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings_screen);

        //Update user name
        currentUser = appBase.getCurrentUser();
        TextView userName = (TextView) findViewById(R.id.tv_user_name);
        userName.setText(currentUser.getName());

        updateProfilePicture();

        //Temporarily refresh the picture when update button is pressed.
        Button update_btn = (Button) findViewById(R.id.btn_upload_profile_pic);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    void updateProfilePicture(){
        //Update profile picture
        ImageView profilePic = (ImageView) findViewById(R.id.iv_profile_pic);
        Drawable drawablePic = currentUser.getDrawable();
        if(drawablePic!=null)
            profilePic.setImageDrawable(drawablePic);
        else
            profilePic.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
    }
}
