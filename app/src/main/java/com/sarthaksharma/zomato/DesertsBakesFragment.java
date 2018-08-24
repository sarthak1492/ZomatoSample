package com.sarthaksharma.zomato;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.sarthaksharma.zomato.Adapters.OrderFoodFromAdapter;
import com.sarthaksharma.zomato.Adapters.PopularChainsAdapter;
import com.sarthaksharma.zomato.POJO.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sarthak.Sharma on 19-12-2017.
 */

@SuppressLint("ValidFragment")
public class DesertsBakesFragment extends Fragment {
    Activity activity;
    PopularChainsAdapter popularChainsAdapter;
    SharedPreferences mPreferences;
    Button btnNearByDesserts;
    ExpandableHeightGridView gridPopularChains;
    OrderFoodFromAdapter adapter;
    ExpandableHeightGridView gridCafesDeserts;


    ArrayList<String> images = new ArrayList<>(Arrays.asList("http://r2.vendingmarketwatch.com/files/base/image/AUTM/2014/01/16x9/640x360/starbucks-logo_11301825.jpg",
            "http://im.magp.in/terms/53528.png", "http://www.internationalnewsandviews.com/wp-content/uploads/2016/02/CCD-New-Logo.jpg",
            "https://www.kashmate.com/image/cache/barista%20logoj_1468589944-350x350.png"));
    ArrayList<String> strRestaurantNames = new ArrayList<>(Arrays.asList("Slice of Italy", "Whipped", "BTW", "Hwealthcafe"));
    ArrayList<String> strRatings = new ArrayList<>(Arrays.asList("4.3", "3.6", "2.9", "4.9"));
    ArrayList<String> strAddresses = new ArrayList<>(Arrays.asList("OKHLA PHASE 2, NEW DELHI", "GREATER KAILASH 2, NEW DELHI", "LIVING STYLE MALL, JASOLA",
            "NEW FRIENDS COLONY, NEW DELHI"));

    ArrayList<String> strCafeImages = new ArrayList<>(Arrays.asList("https://static.pexels.com/photos/46239/salmon-dish-food-meal-46239.jpeg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1200px-Good_Food_Display_-_NCI_Visuals_Online.jpg",
            "http://www.technobuffalo.com/wp-content/uploads/2014/04/fast-food.jpg"));
    ArrayList<String> strCafeRestaurantNames = new ArrayList<>(Arrays.asList("Slice of Italy", "Whipped", "Hwealthcafe"));
    ArrayList<String> strCafeRatings = new ArrayList<>(Arrays.asList("4.3", "3.6", "2.9"));
    ArrayList<String> strCafeAddresses = new ArrayList<>(Arrays.asList( "GREATER KAILASH 2, NEW DELHI", "LIVING STYLE MALL, JASOLA",
            "NEW FRIENDS COLONY, NEW DELHI"));

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.deserts_bakes_fragment, container, false);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        gridPopularChains = (ExpandableHeightGridView) convertView.findViewById(R.id.gridPopularChainsDeserts);
        gridPopularChains.setExpanded(true);

        popularChainsAdapter = new PopularChainsAdapter(activity, images);
        gridPopularChains.setAdapter(popularChainsAdapter);
        gridPopularChains.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(activity, strRestaurantNames.get(i), Toast.LENGTH_SHORT).show();
            }
        });


        gridCafesDeserts = (ExpandableHeightGridView) convertView.findViewById(R.id.gridCafesDeserts);
        gridCafesDeserts.setExpanded(true);
        adapter = new OrderFoodFromAdapter(activity, strCafeImages, strCafeRestaurantNames, strCafeRatings, strCafeAddresses);
        gridCafesDeserts.setAdapter(adapter);


        return convertView;
    }
}
