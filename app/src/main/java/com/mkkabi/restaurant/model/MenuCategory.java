package com.mkkabi.restaurant.model;

import java.util.ArrayList;
import java.util.List;

public class MenuCategory {
    private String id, restaurantId, descriptionShort, name, imageurl;
    List<Strava> stravaList = new ArrayList<>();

    public MenuCategory(String id, String restaurantId, String descriptionShort, String name, String imageurl) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.descriptionShort = descriptionShort;
        this.name = name;
        this.imageurl = imageurl;
    }

    public void addStravaToMenuCategory(Strava strava){
        this.stravaList.add(strava);
    }

    public List<Strava> getStravaList() {
        return stravaList;
    }

    public void setStravaList(List<Strava> stravaList) {
        this.stravaList = stravaList;
    }

    public String getId() {
        return id;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public String getName() {
        return name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getRestaurantId() {
        return restaurantId;
    }
}
