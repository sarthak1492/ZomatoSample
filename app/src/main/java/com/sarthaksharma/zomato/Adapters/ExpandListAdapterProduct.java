package com.sarthaksharma.zomato.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sarthaksharma.zomato.POJO.ExpandListChild1;
import com.sarthaksharma.zomato.POJO.ExpandListGroupProduct;
import com.sarthaksharma.zomato.R;

import java.util.ArrayList;

/**
 * Created by Sarthak.Sharma on 03-01-2018.
 */

public class ExpandListAdapterProduct extends BaseExpandableListAdapter {

    private Context context;

    private ArrayList<ExpandListGroupProduct> groups;

    public ExpandListAdapterProduct(Context context, ArrayList<ExpandListGroupProduct> groups) {

        this.context = context;

        this.groups = groups;

    }

    public Object getChild(int groupPosition, int childPosition) {

        // TODO Auto-generated method stub

        ArrayList<ExpandListChild1> chList = groups.get(groupPosition).getItems();

        return chList.get(childPosition);

    }
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        final ExpandListChild1 child = (ExpandListChild1) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.expandlist_child_item_product, null);
        }

        TextView lblExChildRestaurantName = (TextView) view.findViewById(R.id.lblExChildRestaurantName);
        TextView lblExChildAddress = (TextView) view.findViewById(R.id.lblExChildAddress);

        lblExChildRestaurantName.setText(child.getName());
        lblExChildAddress.setText(child.getAddress());

        lblExChildRestaurantName.setTag(child.getTag());
        lblExChildAddress.setTag(child.getTag());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (child.getAddress() != null && !child.equals("")){
                    Toast.makeText(context, child.getAddress(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "No address found", Toast.LENGTH_SHORT).show();
                }

            }
        });


        // TODO Auto-generated method stub

        return view;

    }

    public int getChildrenCount(int groupPosition) {

        // TODO Auto-generated method stub

        ArrayList<ExpandListChild1> chList = groups.get(groupPosition).getItems();


        return chList.size();

    }

    public Object getGroup(int groupPosition) {

        // TODO Auto-generated method stub

        return groups.get(groupPosition);

    }

    public int getGroupCount() {

        // TODO Auto-generated method stub

        return groups.size();

    }

    public long getGroupId(int groupPosition) {

        // TODO Auto-generated method stub

        return groupPosition;

    }

    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {

        ExpandListGroupProduct group = (ExpandListGroupProduct) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.expandlist_group_item_product, null);
        }

        TextView lblExListViewHeading = (TextView) view.findViewById(R.id.lblExListViewHeading);
        TextView lblNoOfPlaces = (TextView) view.findViewById(R.id.lblNoOfPlaces);

        lblExListViewHeading.setText(group.getName());
        lblNoOfPlaces.setText(group.getPlaces());
        ImageView imgArrowDown = (ImageView) view.findViewById(R.id.imgArrowDown);
        // TODO Auto-generated method stub
        if(isLastChild){
            imgArrowDown.setImageResource(R.drawable.ic_arrow_up);
        }else {
            imgArrowDown.setImageResource(R.drawable.ic_arrow_down);
        }
        return view;

    }

    public boolean hasStableIds() {

        // TODO Auto-generated method stub

        return true;

    }

    public boolean isChildSelectable(int arg0, int arg1) {

        // TODO Auto-generated method stub

        return true;

    }
}
