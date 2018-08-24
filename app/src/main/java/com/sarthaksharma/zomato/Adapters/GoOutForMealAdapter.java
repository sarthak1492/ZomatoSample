package com.sarthaksharma.zomato.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sarthaksharma.zomato.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sarthak.Sharma on 23-01-2018.
 */

public class GoOutForMealAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<String> images = new ArrayList<String>();
    ArrayList<String> strNames = new ArrayList<String>();
    ArrayList<String> strRatings = new ArrayList<String>();
    ArrayList<String> strAddresses = new ArrayList<String>();

    LayoutInflater inflater;

    public GoOutForMealAdapter(Activity activity, ArrayList<String> images, ArrayList<String> strNames,
                               ArrayList<String> strRatings, ArrayList<String> strAddresses) {
        this.activity = activity;
        this.images = images;
        this.strNames = strNames;
        this.strRatings = strRatings;
        this.strAddresses = strAddresses;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.order_from_grid, viewGroup, false);
        ImageView imgView = (ImageView) itemview.findViewById(R.id.imgImages);
        //imgView.setImageResource(images.get(position));
        Picasso.with(activity).load(images.get(i)).into(imgView);

        TextView lblRestaurantName = (TextView) itemview.findViewById(R.id.lblRestaurantName);
        lblRestaurantName.setText(strNames.get(i));

        TextView lblRatings = (TextView) itemview.findViewById(R.id.lblRatings);
        lblRatings.setText(strRatings.get(i));

        TextView lblRestaurantAddresses = (TextView) itemview.findViewById(R.id.lblRestaurantAddresses);
        lblRestaurantAddresses.setText(strAddresses.get(i));

        return itemview;
    }
}
