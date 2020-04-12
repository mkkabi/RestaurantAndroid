package com.mkkabi.restaurant.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Strava implements Comparable<Strava>{
    private String name, description, id, restaurantID, imageurl;
    private int price;
//    private Map<Ingredient, Integer> ingredienti = new HashMap<>();
    private Map<Ingredient, Integer> additionalIngredients = new HashMap<>();
    private List<Ingredient> ingredientList = new ArrayList<>();

    public Strava(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Strava(String name, String id, double price, String restaurantID){
        this(name, id);
        this.restaurantID = restaurantID;
    }

//    public Strava izmenitIngredientIliDobavitNovij(Ingredient ingredient, int kolichestvo) {
//        if(ingredient != null && kolichestvo > 0) {
//            ingredienti.put(ingredient, kolichestvo);
//            return this;
//        }else{
//            throw new IllegalArgumentException("Ingredient cannot be null or kolichastvo < 0");
//        }
//    }

//    private int calculatePrice(Map<Ingredient, Integer> ingredients, Function<Ingredient, Integer> function) {
//        int[] result = new int[1];
//        ingredients.entrySet().forEach((map) -> {
//            Ingredient ingredient = map.getKey();
//            int kolichestvo = map.getValue();
//            int ingredientPrice = function.apply(ingredient);
//            result[0] += kolichestvo * ingredientPrice;
//        });
//        return result[0];
//    }

//    public int poschitatStoimostDliaKlienta() {
//        if(additionalIngredients.size() > 0){
//            return calculatePrice(additionalIngredients, Ingredient::getPriceRoznica) + price;
//        }
//        return price;
//    }

    public Map<Ingredient, Integer> getadditionalIngredients() {
        return additionalIngredients;
    }

    public void setadditionalIngredients(Map<Ingredient, Integer> additionalIngredients) {
        this.additionalIngredients = additionalIngredients;
    }

    // for initial set up, adds ingredients to Map and sets values to 0
    public void setadditionalIngredients(List<Ingredient> ingredients){
        for(Ingredient ingredient : ingredients){
            this.additionalIngredients.put(ingredient, 0);
        }
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public Map<Ingredient, Integer> getAdditionalIngredients() {
        return additionalIngredients;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAdditionalIngredients(Map<Ingredient, Integer> additionalIngredients) {
        this.additionalIngredients = additionalIngredients;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public int compareTo(Strava strava) {
        return this.name.compareTo(((Strava) strava).getName());
    }

//    @Override
//    public String toString() {
//        return "Strava{" + name + '\'' +
//                ", цена с доп ингр = " + this.poschitatStoimostDliaKlienta() + "} \n";
//    }
}
