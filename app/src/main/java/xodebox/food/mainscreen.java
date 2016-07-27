package xodebox.food;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Max on 16/5/2016.
 *sample to show library functionalities i implemented
 * @deprecated Sample code by Max. Thank you.
 * @author Max
 */
public class mainscreen extends AppCompatActivity {


    private static String TAG =mainscreen.class.getSimpleName();
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    Context ctx;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);
        ctx=this;
        mNavItems.add(new NavItem("Home", "Meetup destination", R.drawable.folder));
        mNavItems.add(new NavItem("Preferences", "Change your preferences", R.drawable.folder));
        mNavItems.add(new NavItem("About", "Get to know about us", R.drawable.folder));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigation Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });


        //listen to the drawer event
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.d(TAG, "onDraweropen: " + getTitle());

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d(TAG, "onDrawerClosed: " + getTitle());

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //call  using http request and show it in the imageview widget
        /*
        final ImageView imgview= (ImageView) findViewById(R.id.imageView);
        int restaurantid=2;
        String URL=Configs.BackEndUrl+"?type=4&restaurant="+restaurantid;
        HttpRequest request=new HttpRequest(this);
        request.SendGetrequest(URL, new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.v("Volley", "Response is: " + response);
                        Gson g = new Gson();

                        //fetch the images if they are many
                        //http://stackoverflow.com/questions/2770273/pdostatement-to-json
                        JSONArray jsonarray = null;
                        try {
                            jsonarray = new JSONArray(response);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);



                                // String name = jsonobject.getString("name");
                                //String url = jsonobject.getString("url");
                            }
                        } catch (JSONException e) {

                        }

                        Image img = g.fromJson(response, Image.class);
                        Picasso.with(ctx).setIndicatorsEnabled(true);
                        Picasso.with(ctx).load(img.image_link).into(imgview);
                       // System.out.println(g.toJson(person)); // {"name":"John"}
                    }
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Volley", "Error,Response is: " + error.getMessage());
                    }
                }
        );*/


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    /*
* Called when a particular item from the navigation drawer
* is selected.
* */
    private void selectItemFromDrawer(int position) {

        Log.v("Drawerclick","Drawer menu item clicked:"+position);
//        Fragment fragment = new PreferencesFragment();
//
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.mainContent, fragment)
//                .commit();
//
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mNavItems.get(position).mTitle);
//
//        // Close the drawer
//        mDrawerLayout.closeDrawer(mDrawerPane);
    }
}
