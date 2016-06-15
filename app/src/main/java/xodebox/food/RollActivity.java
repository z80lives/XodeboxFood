package xodebox.food;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.widget.Button;

public class RollActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roll_screen);

        Button settingButton = (Button) findViewById(R.id.btn_settings);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu= new PopupMenu(getApplicationContext(), view);

                popupMenu.inflate(R.menu.roll_settings);
                popupMenu.show();
            }
        });

    }
}
