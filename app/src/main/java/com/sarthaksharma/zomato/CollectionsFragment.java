package com.sarthaksharma.zomato;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarthaksharma.zomato.Adapters.CollectionsAdapter;
import com.sarthaksharma.zomato.POJO.CollectionListItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarthak.Sharma on 19-12-2017.
 */

@SuppressLint("ValidFragment")
public class CollectionsFragment extends Fragment {

    private RecyclerView collectionsList;
    View view;
    Activity activity;
    CollectionsAdapter collectionsAdapter;
    List<CollectionListItems> collectionListItems = new ArrayList<>();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.collections_fragment, container, false);

        collectionsList = (RecyclerView) convertView.findViewById(R.id.collectionsList);

        collectionsAdapter = new CollectionsAdapter(activity,collectionListItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity.getApplicationContext());
        collectionsList.setLayoutManager(mLayoutManager);
        collectionsList.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));
        collectionsList.setAdapter(collectionsAdapter);

        prepareCollectionListItemsData();
        return convertView;
    }

    private void prepareCollectionListItemsData() {
        CollectionListItems items = new CollectionListItems("Trending this week", "30 Places", "2015");
        collectionListItems.add(items);

        items = new CollectionListItems("Great food, no bull", "71 Places", "2015");
        collectionListItems.add(items);

        items = new CollectionListItems("Newly Opened", "333 Places", "2015");
        collectionListItems.add(items);

        items = new CollectionListItems("Valentine's Dat Specials", "30 Places", "2015");
        collectionListItems.add(items);

        items = new CollectionListItems("Luxury Dining", "66 Places", "2015");
        collectionListItems.add(items);

        items = new CollectionListItems("Happy Hours", "45 Places", "2015");
        collectionListItems.add(items);

        items = new CollectionListItems("Popular Gold Restaurants", "30 Places", "2009");
        collectionListItems.add(items);

        items = new CollectionListItems("Beer in a bar", "30 Places", "2009");
        collectionListItems.add(items);

        items = new CollectionListItems("Ladies luncheon", "30 Places", "2014");
        collectionListItems.add(items);

        items = new CollectionListItems("Pocket friendly bars", "30 Places", "2008");
        collectionListItems.add(items);

        items = new CollectionListItems("Live Music", "30 Places", "1986");
        collectionListItems.add(items);

        items = new CollectionListItems("Brilliant Biryanis", "30 Places", "2000");
        collectionListItems.add(items);

        items = new CollectionListItems("Where's the party?", "30 Places", "1985");
        collectionListItems.add(items);

        items = new CollectionListItems("South Indian delights", "30 Places", "1981");
        collectionListItems.add(items);

        items = new CollectionListItems("Sunday Brunches", "30 Places", "1965");
        collectionListItems.add(items);

        items = new CollectionListItems("Romantic", "30 Places", "2014");
        collectionListItems.add(items);

        collectionsAdapter.notifyDataSetChanged();
    }
    
}
