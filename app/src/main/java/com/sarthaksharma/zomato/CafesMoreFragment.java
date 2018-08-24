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
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.sarthaksharma.zomato.Adapters.ExpandListAdapterProduct;
import com.sarthaksharma.zomato.Adapters.OrderFoodFromAdapter;
import com.sarthaksharma.zomato.Adapters.PopularChainsAdapter;
import com.sarthaksharma.zomato.POJO.ExpandListChild1;
import com.sarthaksharma.zomato.POJO.ExpandListGroupProduct;
import com.sarthaksharma.zomato.POJO.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sarthak.Sharma on 19-12-2017.
 */

@SuppressLint("ValidFragment")
public class CafesMoreFragment extends Fragment {

    Activity activity;
    private ExpandListAdapterProduct ExpAdapter;
    private ArrayList<ExpandListGroupProduct> ExpListItems;
    ExpandableListView exLInTheMoodFor;
    PopularChainsAdapter popularChainsAdapter;
    OrderFoodFromAdapter adapter;
    ExpandableHeightGridView gridPopularChains;
    Button btnNearByCafesnMore;
    SharedPreferences mPreferences;


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
        View convertView = inflater.inflate(R.layout.cafes_more_fragment, container, false);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

        final ExpandListChild1 items = new ExpandListChild1();
        final ExpandableHeightGridView gridCafes = (ExpandableHeightGridView) convertView.findViewById(R.id.gridCafes);
        gridCafes.setExpanded(true);
        adapter= new OrderFoodFromAdapter(activity, strCafeImages, strCafeRestaurantNames, strCafeRatings, strCafeAddresses);
        gridCafes.setAdapter(adapter);
        gridCafes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(activity, strRestaurantNames.get(i), Toast.LENGTH_SHORT).show();
            }
        });


        exLInTheMoodFor = (ExpandableListView) convertView.findViewById(R.id.exLInTheMoodFor);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandListAdapterProduct(activity, ExpListItems);
        exLInTheMoodFor.setAdapter(ExpAdapter);
        setListViewHeightOnCreate(exLInTheMoodFor, 0);
        exLInTheMoodFor.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                setListViewHeight(exLInTheMoodFor, i);
                return false;
            }
        });

        gridPopularChains = (ExpandableHeightGridView) convertView.findViewById(R.id.gridPopularChains);
        gridPopularChains.setExpanded(true);
        popularChainsAdapter = new PopularChainsAdapter(activity, images);
        gridPopularChains.setAdapter(popularChainsAdapter);

        btnNearByCafesnMore = (Button) convertView.findViewById(R.id.btnNearByCafesnMore);
        String strCurrentLocation = mPreferences.getString("CurrentLocation", null);
        if (strCurrentLocation != null){
            btnNearByCafesnMore.setText("Nearby - Cafes and more (" + strCurrentLocation + ")");
        }

        return convertView;
    }

    //for set height show method in expnadable list view
    private void setListViewHeight(ExpandableListView listView, int group) {
        android.widget.ExpandableListAdapter listAdapter = (android.widget.ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 0;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setListViewHeightOnCreate(ExpandableListView listView, int group) {
        android.widget.ExpandableListAdapter listAdapter = (android.widget.ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            //totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = 575;
        if (height < 10)
            height = 0;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    private ArrayList<ExpandListGroupProduct> SetStandardGroups() {

        ArrayList<ExpandListGroupProduct> list = new ArrayList<ExpandListGroupProduct>();
        ArrayList<ExpandListChild1> list2 = new ArrayList<ExpandListChild1>();

        ExpandListGroupProduct gru1 = new ExpandListGroupProduct();
        gru1.setName("Fine dining");
        gru1.setPlaces("114 Places");

        ExpandListChild1 ch1_1 = new ExpandListChild1();
        ch1_1.setName("Crack Pot Cafe");
        ch1_1.setAddress("East of Kailash");
        ch1_1.setTag(null);
        list2.add(ch1_1);

        ExpandListChild1 ch1_2 = new ExpandListChild1();
        ch1_2.setName("Greenr Cafe");
        ch1_2.setAddress("Green Park");
        ch1_2.setTag(null);
        list2.add(ch1_2);

        ExpandListChild1 ch1_3 = new ExpandListChild1();
        ch1_3.setName("City Socialite");
        ch1_2.setAddress("Hauz Khas");
        ch1_3.setTag(null);
        list2.add(ch1_3);

        ExpandListChild1 ch1_4 = new ExpandListChild1();
        ch1_4.setName("Mi Amor");
        ch1_2.setAddress("Jasola");
        ch1_4.setTag(null);
        list2.add(ch1_4);

        ExpandListChild1 ch1_5 = new ExpandListChild1();
        ch1_5.setName("Tiny Tuscan");
        ch1_2.setAddress("Nehru Place");
        ch1_5.setTag(null);
        list2.add(ch1_5);

        gru1.setItems(list2);
        list.add(gru1);


        list2 = new ArrayList<ExpandListChild1>();


        ExpandListGroupProduct gru2 = new ExpandListGroupProduct();
        gru2.setName("Brilliant buffets");
        gru2.setPlaces("96 Places");

        ExpandListChild1 ch2_1 = new ExpandListChild1();
        ch2_1.setName("The Coffee Shop");
        ch2_1.setAddress("Saket");
        ch2_1.setTag(null);
        list2.add(ch2_1);

        ExpandListChild1 ch2_2 = new ExpandListChild1();
        ch2_2.setName("The Brew Room");
        ch2_2.setAddress("SDA");
        ch2_2.setTag(null);
        list2.add(ch2_2);

        ExpandListChild1 ch2_3 = new ExpandListChild1();
        ch2_3.setName("Cafe Pluck");
        ch2_2.setAddress("Aerocity");
        ch2_3.setTag(null);
        list2.add(ch2_3);

        gru2.setItems(list2);
        list.add(gru2);


        return list;

    }
}
