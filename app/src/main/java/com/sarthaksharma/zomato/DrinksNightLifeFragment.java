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
import android.widget.Toast;

import com.sarthaksharma.zomato.Adapters.DineOurRestaurantAdapter;
import com.sarthaksharma.zomato.Adapters.ExpandListAdapterProduct;
import com.sarthaksharma.zomato.Adapters.GoOutForMealAdapter;
import com.sarthaksharma.zomato.POJO.ExpandListChild1;
import com.sarthaksharma.zomato.POJO.ExpandListGroupProduct;
import com.sarthaksharma.zomato.POJO.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sarthak.Sharma on 19-12-2017.
 */

@SuppressLint("ValidFragment")
public class DrinksNightLifeFragment extends Fragment {

    Activity activity;
    GoOutForMealAdapter adapter;
    DineOurRestaurantAdapter dineOurRestaurantAdapter;
    private ExpandListAdapterProduct ExpAdapter;
    private ArrayList<ExpandListGroupProduct> ExpListItems;
    ExpandableListView exLInTheMoodForDrinksNightlife;
    SharedPreferences mPreferences;
    Button btnNearByDrinksnNightlife;

    ArrayList<String> images = new ArrayList<>(Arrays.asList("https://backgroundimages.withfloats.com/actual/58cb8094d9dabf0b8417f0ec.jpg",
            "https://www.goway.com/media/cache/8c/cb/8ccbea441df10e50ffea295eed02ba28.jpg",
            "http://2.bp.blogspot.com/--lt4H7Iop2I/VYoex8Gy21I/AAAAAAAAEaE/bfbMtfpsxic/s1600/sizzling-pork-sisig-2.jpg", "http://www.dinner4tonight.com/img/creamy-salmon-with-prawns.jpg"));
    ArrayList<String> strRestaurantNames = new ArrayList<>(Arrays.asList("Moti Mahal Delux", "Royal China", "Jom Jom Malay", "Zooby's Kitchen"));
    ArrayList<String> strRatings = new ArrayList<>(Arrays.asList("3.1", "3.7", "4.3", "3.6"));
    ArrayList<String> strAddresses = new ArrayList<>(Arrays.asList("OKHLA PHASE 2, NEW DELHI", "GREATER KAILASH 2, NEW DELHI", "LIVING STYLE MALL, JASOLA",
            "NEW FRIENDS COLONY, NEW DELHI"));

    ArrayList<String> arrDRImages = new ArrayList<>(Arrays.asList("http://www.churchofthemessiah.ca/wp-content/uploads/2014/10/22e.jpg",
            "http://cdn.home-designing.com/wp-content/uploads/2011/10/restaurant-wooden-theme.jpg",
            "https://tummydiary.files.wordpress.com/2010/12/dsc_0986.jpg?w=800",
            "https://s3-ap-southeast-2.amazonaws.com/sporteluxe-wp/wp-content/uploads/2015/08/Commissary4.jpg",
            "http://2dc81c5d4570ab86a55d-78cd203e5fa27bda3b714b8bffcc3432.r54.cf1.rackcdn.com/XLGallery/wes1004re-151715-LAPrime.jpg",
            "http://i.telegraph.co.uk/multimedia/archive/03312/ithaca_3312829k.jpg"));
    ArrayList<String> strDRNames = new ArrayList<>(Arrays.asList("The Cinnamon", "Moonlight", "House of Delicious", "Turnip the Beet",
            "Tequila Mockingbird", "Planet of the Crepes"));
    ArrayList<String> strDRAddresses = new ArrayList<>(Arrays.asList("JASOLA VIHAR, NEW DELHI", "LIVING STYLE MALL, JASOLA VIHAR",
            "ohkhla  phase 3, new delhi", "Tower B, Jasola Vihar", "RPS colony, New Delhi", "dilshad garden, new delhi"));

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.drinks_night_fragment, container, false);
        btnNearByDrinksnNightlife = (Button) convertView.findViewById(R.id.btnNearByDrinksnNightlife);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

        final ExpandableHeightGridView gridView=(ExpandableHeightGridView) convertView.findViewById(R.id.gridGoGrabaDrinkAt);
        gridView.setExpanded(true);
        adapter= new GoOutForMealAdapter(activity, images, strRestaurantNames, strRatings, strAddresses);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(activity, strRestaurantNames.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        final ExpandableHeightGridView gridEnjoyTheNightlife = (ExpandableHeightGridView) convertView.findViewById(R.id.gridEnjoyTheNightlife);
        gridEnjoyTheNightlife.setExpanded(true);
        dineOurRestaurantAdapter = new DineOurRestaurantAdapter(activity, arrDRImages, strDRNames, strDRAddresses);
        gridEnjoyTheNightlife.setAdapter(dineOurRestaurantAdapter);
        gridEnjoyTheNightlife.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(activity, strDRNames.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        exLInTheMoodForDrinksNightlife = (ExpandableListView) convertView.findViewById(R.id.exLInTheMoodForDrinksNightlife);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExpandListAdapterProduct(activity, ExpListItems);
        exLInTheMoodForDrinksNightlife.setAdapter(ExpAdapter);
        setListViewHeightOnCreate(exLInTheMoodForDrinksNightlife, 0);
        exLInTheMoodForDrinksNightlife.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                setListViewHeight(exLInTheMoodForDrinksNightlife, i);
                return false;
            }
        });

        String strCurrentLocation = mPreferences.getString("CurrentLocation", null);
        if (strCurrentLocation != null){
            try {
                btnNearByDrinksnNightlife.setText("Nearby - Drinks & Nightlife (" + strCurrentLocation + ")");
            }catch (Exception e){
                e.printStackTrace();
                btnNearByDrinksnNightlife.setText("Nearby - Drinks & Nightlife");
            }
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
        gru1.setName("Wi-Fi Cafes");
        gru1.setPlaces("177 Places");

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
        gru2.setName("Start your day right");
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
