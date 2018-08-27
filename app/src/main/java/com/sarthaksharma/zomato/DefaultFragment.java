package com.sarthaksharma.zomato;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.sarthaksharma.zomato.Adapters.DeliveryRestaurantAdapter;
import com.sarthaksharma.zomato.Adapters.OrderFoodFromAdapter;
import com.sarthaksharma.zomato.POJO.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sarthak.Sharma on 02-02-2018.
 */

public class DefaultFragment extends Fragment {

    View view;

    Activity activity;
    OrderFoodFromAdapter adapter;
    DeliveryRestaurantAdapter deliveryRestaurantAdapter;
    Button btnShowAllDelivery;
    SharedPreferences mPreferences;

    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> strRestaurantNames = new ArrayList<>();
    ArrayList<String> strRatings = new ArrayList<>();
    ArrayList<String> strAddresses = new ArrayList<>();

    ArrayList<String> arrDRImages = new ArrayList<>();
    ArrayList<String> strDRNames = new ArrayList<>();
    ArrayList<String> strDRAddresses = new ArrayList<>();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.delivery_fragment,container,false);

        btnShowAllDelivery = (Button) view.findViewById(R.id.btnShowAllDelivery);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

        new DefaultContentTask().execute();

        final ExpandableHeightGridView gridView=(ExpandableHeightGridView) view.findViewById(R.id.gridOrderFoodFrom);
        gridView.setExpanded(true);
        adapter= new OrderFoodFromAdapter(activity, images, strRestaurantNames, strRatings, strAddresses);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(activity, strRestaurantNames.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        final ExpandableHeightGridView gridDeliveryRestaurants = (ExpandableHeightGridView) view.findViewById(R.id.gridDeliveryRestaurants);
        gridDeliveryRestaurants.setExpanded(true);
        deliveryRestaurantAdapter = new DeliveryRestaurantAdapter(activity, arrDRImages, strDRNames, strDRAddresses);
        gridDeliveryRestaurants.setAdapter(deliveryRestaurantAdapter);
        gridDeliveryRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(activity, strDRNames.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        String strCurrentLocation = mPreferences.getString("CurrentLocation", null);
        if (strCurrentLocation != null){
            btnShowAllDelivery.setText("Show all - Delivery (" + strCurrentLocation + ")");
        }

        return view;

    }

    public class DefaultContentTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            images = new ArrayList<>(Arrays.asList("https://static.pexels.com/photos/46239/salmon-dish-food-meal-46239.jpeg",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1200px-Good_Food_Display_-_NCI_Visuals_Online.jpg",
                    "http://www.technobuffalo.com/wp-content/uploads/2014/04/fast-food.jpg", "https://static.pexels.com/photos/70497/pexels-photo-70497.jpeg"));
            strRestaurantNames = new ArrayList<>(Arrays.asList("Slice of Italy", "Whipped", "BTW", "Hwealthcafe"));
            strRatings = new ArrayList<>(Arrays.asList("4.3", "3.6", "2.9", "4.9"));
            strAddresses = new ArrayList<>(Arrays.asList("OKHLA PHASE 2, NEW DELHI", "GREATER KAILASH 2, NEW DELHI", "LIVING STYLE MALL, JASOLA",
                    "NEW FRIENDS COLONY, NEW DELHI"));


            arrDRImages = new ArrayList<>(Arrays.asList("https://static.pexels.com/photos/46239/salmon-dish-food-meal-46239.jpeg",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1200px-Good_Food_Display_-_NCI_Visuals_Online.jpg",
                    "http://www.technobuffalo.com/wp-content/uploads/2014/04/fast-food.jpg", "https://static.pexels.com/photos/70497/pexels-photo-70497.jpeg",
                    "https://bunge.s3.amazonaws.com/categories/images/000/000/006/content/Super-Cat-Food-Ingredients.jpg?1357968333",
                    "https://i.kinja-img.com/gawker-media/image/upload/s--XjPNJ3d7--/c_scale,fl_progressive,q_80,w_1600/be9vyovcn2gqwod7kd16.jpg"));
            strDRNames = new ArrayList<>(Arrays.asList("Work on Wheels", "Deli Belly", "House of Delicious", "Subway", "BTW", "Domino's Pizza"));
            strDRAddresses = new ArrayList<>(Arrays.asList("JASOLA DISTRICT CENTER, NEW DELHI", "LIVING STYLE MALL, JASOLA VIHAR",
                    "ohkhla  phase 3, new delhi", "Tower B, Jasola Vihar", "RPS colony, New Delhi", "dilshad garden, new delhi"));
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
