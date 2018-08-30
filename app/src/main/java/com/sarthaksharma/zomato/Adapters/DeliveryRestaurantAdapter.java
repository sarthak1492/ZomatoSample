package com.sarthaksharma.zomato.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.sarthaksharma.zomato.R;
import com.sarthaksharma.zomato.app.AppController;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sarthak.Sharma on 21-12-2017.
 */

public class DeliveryRestaurantAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<String> intDRimages = new ArrayList<String>();
    ArrayList<String> strDRNames = new ArrayList<String>();
    ArrayList<String> strDRAddresses = new ArrayList<String>();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    LayoutInflater inflater;


    public DeliveryRestaurantAdapter(Activity activity, ArrayList<String> intDRimages, ArrayList<String> strDRNames, ArrayList<String> strDRAddresses) {
        this.activity = activity;
        this.intDRimages = intDRimages;
        this.strDRNames = strDRNames;
        this.strDRAddresses = strDRAddresses;
    }

    @Override
    public int getCount() {
        return intDRimages.size();
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
        Picasso.with(activity).load(intDRimages.get(i)).into(imgDRImages);

        TextView lblDRNames = (TextView) itemview.findViewById(R.id.lblDRNames);
//        lblDRNames.setText(strDRNames.get(i));

        TextView lblDRAddresses = (TextView) itemview.findViewById(R.id.lblDRAddresses);
        //lblDRAddresses.setText(strDRAddresses.get(i));

        return itemview;
    }
}
