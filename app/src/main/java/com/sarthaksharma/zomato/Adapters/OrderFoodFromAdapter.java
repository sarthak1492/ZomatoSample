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


public class OrderFoodFromAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<String> images = new ArrayList<String>();
    ArrayList<String> strNames = new ArrayList<String>();
    ArrayList<String> strRatings = new ArrayList<String>();
    ArrayList<String> strAddresses = new ArrayList<String>();

    LayoutInflater inflater;


    public OrderFoodFromAdapter(Activity activity, ArrayList<String> images, ArrayList<String> strNames , ArrayList<String> strRatings,
                                ArrayList<String> strAddresses) {
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
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemview = inflater.inflate(R.layout.order_from_grid, parent, false);


        ImageView imgView = (ImageView) itemview.findViewById(R.id.imgImages);
        //imgView.setImageResource(images.get(position));
        Picasso.with(activity).load(images.get(position)).into(imgView);

        TextView lblRestaurantName = (TextView) itemview.findViewById(R.id.lblRestaurantName);
        lblRestaurantName.setText(strNames.get(position));

        TextView lblRatings = (TextView) itemview.findViewById(R.id.lblRatings);
        lblRatings.setText(strRatings.get(position));

        TextView lblRestaurantAddresses = (TextView) itemview.findViewById(R.id.lblRestaurantAddresses);
        lblRestaurantAddresses.setText(strAddresses.get(position));

        return itemview;
    }
}


