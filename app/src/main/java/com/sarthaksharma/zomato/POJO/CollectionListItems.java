package com.sarthaksharma.zomato.POJO;

/**
 * Created by sarthaksharma on 28/01/18.
 */

public class CollectionListItems {

    private String title, genre, year;

    public CollectionListItems() {
    }

    public CollectionListItems(String title, String genre, String year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
