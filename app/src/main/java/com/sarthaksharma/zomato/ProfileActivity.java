package com.sarthaksharma.zomato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.sarthaksharma.zomato.Adapters.ViewPagerAdapter;
import com.sarthaksharma.zomato.POJO.ProfileCircularNetworkImageView;
import com.sarthaksharma.zomato.app.AppController;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences preferences;
    ProfileCircularNetworkImageView imgUserImage;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    TextView lblProfileUsername;

    private TabLayout tabsProfileDetails;
    private ViewPager viewPagerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#CD0000"));
        }

        imgUserImage = (ProfileCircularNetworkImageView) findViewById(R.id.imgUserImage);
        lblProfileUsername = (TextView) findViewById(R.id.lblProfileUserName);

        viewPagerProfile = (ViewPager) findViewById(R.id.viewPagerProfile);
        setUpViewPager(viewPagerProfile);

        tabsProfileDetails = (TabLayout) findViewById(R.id.tabsProfileDetails);
        tabsProfileDetails.setupWithViewPager(viewPagerProfile);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String strUserName = preferences.getString("UserName", null);
        String strProfileImage = preferences.getString("UserImage", null);

        try {
            imgUserImage.setImageUrl(strProfileImage, imageLoader);
            lblProfileUsername.setText(strUserName);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.bookmark){
            Intent intent = new Intent(ProfileActivity.this, BookmarksActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void fncShare(View view){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void fncEditProfile(View view){
        Intent intent =  new Intent(ProfileActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void setUpViewPager(final ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DeliveryFragment(), "DINELINE");
        adapter.addFragment(new DiningFragment(), "REVIEWS");
        adapter.addFragment(new DesertsBakesFragment(), "PHOTOS");
        viewPager.setAdapter(adapter);
    }
}
