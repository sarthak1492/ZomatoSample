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

public class DineOurRestaurantAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<String> images = new ArrayList<String>();
    ArrayList<String> strNames = new ArrayList<String>();
    ArrayList<String> strAddresses = new ArrayList<String>();

    LayoutInflater inflater;


    public DineOurRestaurantAdapter(Activity activity, ArrayList<String> images, ArrayList<String> strNames, ArrayList<String> strAddresses) {
        this.activity = activity;
        this.images = images;
        this.strNames = strNames;
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
        View itemview = inflater.inflate(R.layout.delivery_restaurants, viewGroup, false);
        ImageView imgDRImages = (ImageView) itemview.findViewById(R.id.imgDRImages);
        Picasso.with(activity).load(images.get(i)).into(imgDRImages);

        TextView lblDRNames = (TextView) itemview.findViewById(R.id.lblDRNames);
        lblDRNames.setText(strNames.get(i));

        TextView lblDRAddresses = (TextView) itemview.findViewById(R.id.lblDRAddresses);
        lblDRAddresses.setText(strAddresses.get(i));
        return itemview;
    }
}
