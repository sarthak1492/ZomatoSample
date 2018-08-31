package com.sarthaksharma.zomato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.sarthaksharma.zomato.Adapters.ViewPagerAdapter;
import com.sarthaksharma.zomato.LocationServices.GPSTracker;
import com.sarthaksharma.zomato.POJO.ProfileCircularNetworkImageView;
import com.sarthaksharma.zomato.app.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabsCategories;
    private ViewPager viewPager;
    TextView lblLoadLocation;
    boolean doubleBackToExitPressedOnce = false;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    String strNewLocation;
    String strUserName;
    String strProfilePic;
    List<String> arrTitleList = new ArrayList<>();
    int count = 3;
    String strName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        MovableFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        setUpViewPager(viewPager);
        tabsCategories = (TabLayout) findViewById(R.id.tabsCategories);
        tabsCategories.setupWithViewPager(viewPager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#CD0000"));
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        strUserName = mPreferences.getString("UserName", null);
        strProfilePic = mPreferences.getString("ImageUrl", null);
        if (mPreferences.getString("CurrentLocation", null) != null) {
            strNewLocation = mPreferences.getString("CurrentLocation", null);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        RelativeLayout layoutUserName = (RelativeLayout) headerView.findViewById(R.id.layoutUserName);
        layoutUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, ProfileActivity.class);
                mEditor.putString("UserName", strUserName);
                mEditor.putString("UserImage", strProfilePic);
                mEditor.commit();
                startActivity(intent);
            }
        });
        TextView lblUserName = (TextView) headerView.findViewById(R.id.lblUserName);
        ProfileCircularNetworkImageView imgUserImage = (ProfileCircularNetworkImageView) headerView.findViewById(R.id.imgUserImage);
        if (strUserName != null){
            lblUserName.setText(strUserName);
        }
        if (strProfilePic != null){
            imgUserImage.setImageUrl(strProfilePic, imageLoader);
        }

        lblLoadLocation = (TextView) findViewById(R.id.lblLoadLocation);

        ActivityCompat.requestPermissions(HomePageActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 123);

        GPSTracker gpsTracker = new GPSTracker(this);
        Location l = gpsTracker.getLocation();
        if (l != null){

            double lat = l.getLatitude();
            double lon = l.getLongitude();

            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                final List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
                if (addresses.size() > 0){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(addresses.get(0).getSubLocality() != null){
                                lblLoadLocation.setText(addresses.get(0).getSubLocality());
                                mEditor.putString("CurrentLocation", addresses.get(0).getSubLocality());
                                mEditor.commit();
                            }else {
                                lblLoadLocation.setText("Location not found!");
                            }

                        }
                    }, 1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
        
        new GetHeadingsTask().execute();
    }

    public class GetHeadingsTask extends AsyncTask<String, Void, String>{

        JSONObject jsonObject;
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        @Override
        protected String doInBackground(String... strings) {

            Ion.getDefault(getApplicationContext()).configure().setLogging(getString(R.string.app_name), Log.DEBUG);
            Ion.with(getApplicationContext())
                    .load("https://developers.zomato.com/api/v2.1/categories")
                    .setHeader("user-key", "131579da26e301d3506565431a58d25e")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            Log.i("Result", result.toString());
                            try {
                                jsonObject = new JSONObject(result.toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("categories");
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject catObject = (JSONObject) jsonArray.get(i);
                                    JSONObject jsonCatItems = catObject.getJSONObject("categories");

                                    strName = jsonCatItems.getString("name");

                                    arrTitleList.add(strName);
                                    adapter.addFragment(new DeliveryFragment(), arrTitleList.get(i));
                                    viewPager.setAdapter(adapter);
                                }
                            }catch (Exception ex){
                                    e.printStackTrace();
                            }
                        }
                    });


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            try{
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }


        }
    }


//    private void setUpViewPager(ViewPager viewPager){
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
////        adapter.addFragment(new DeliveryFragment(), "Delivery");
////        adapter.addFragment(new DiningFragment(), "Dining Out");
////        adapter.addFragment(new DesertsBakesFragment(), "Deserts & Bakes");
////        adapter.addFragment(new CafesMoreFragment(), "Cafes & More");
////        adapter.addFragment(new DrinksNightLifeFragment(), "Drinks & Nightlife");
////        adapter.addFragment(new CollectionsFragment(), "Collections");
//
//        for (int i = 0; i < arrTitleList.size(); i++){
//            adapter.addFragment(new DeliveryFragment(), arrTitleList.get(i));
//            viewPager.setAdapter(adapter);
//        }
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
