package com.sarthaksharma.zomato.POJO;

import java.util.ArrayList;

/**
 * Created by Sarthak.Sharma on 03-01-2018.
 */

public class ExpandListGroupProduct {

    private String Name;
    private String Places;

    private ArrayList<ExpandListChild1> Items;

    public String getName() {

        return Name;

    }

    public String getPlaces() {
        return Places;
    }

    public void setPlaces(String places) {
        Places = places;
    }

    public void setName(String name) {

        this.Name = name;
    }

    public ArrayList<ExpandListChild1> getItems() {

        return Items;

    }

    public void setItems(ArrayList<ExpandListChild1> Items) {

        this.Items = Items;

    }
}
