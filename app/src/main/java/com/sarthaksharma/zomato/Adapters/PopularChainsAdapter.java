package com.sarthaksharma.zomato.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sarthaksharma.zomato.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sarthak.Sharma on 04-01-2018.
 */

public class PopularChainsAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<String> intPCImages = new ArrayList<String>();
    LayoutInflater inflater;

    public PopularChainsAdapter(Activity activity, ArrayList<String> intPCImages) {
        this.activity = activity;
        this.intPCImages = intPCImages;
    }

    @Override
    public int getCount() {
        return intPCImages.size();
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

        inflater =  (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View convertView = inflater.inflate(R.layout.popular_chains, viewGroup, false);
        ImageView imgPopularChainsLogos = (ImageView) convertView.findViewById(R.id.imgPopularChainsLogos);
        Picasso.with(activity).load(intPCImages.get(i)).into(imgPopularChainsLogos);


        return convertView;
    }
}
