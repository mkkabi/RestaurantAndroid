package com.mkkabi.restaurant.model;

import com.google.firebase.firestore.DocumentId;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Dish implements Comparable<Dish>{
    @DocumentId
    private String documentId;
    private String name, description, restaurantId, imageUrl;
    private int price, calories;
    private List<String> tags;
    private List<String> menuCategories;
    // List of available ingredients for a particular dish
    private List<Ingredient> additionalIngredients;
    // map of Ingredients selected for order, @Integer in Map is the number of ingredients of every type
    private Map<Ingredient, Integer> selectedIngredients = new HashMap<>();

    public Dish(){}

    public Dish(String documentId, String name) {
        this.documentId = documentId;
        this.name = name;
    }

    public Dish(String name, String id, double price, String restaurantID){
        this(name, id);
        this.restaurantId = restaurantID;
    }

    @Override
    public int compareTo(Dish dish) {
        return this.name.compareTo(((Dish) dish).getName());
    }

    public void addAdditionalIngredientForOrder(Ingredient ingredient, int number){
        this.selectedIngredients.put(ingredient, number);
    }

    public int calculateOverallDishPrice(){
        int result = this.price;
        Iterator<Map.Entry<Ingredient, Integer>> iter =  selectedIngredients.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<Ingredient, Integer> entry = iter.next();
            result += entry.getValue() * entry.getKey().getPrice();
        }
        return result;
    }

    public void removeAdditionalIngredientIfContains(Ingredient ingredient){
        if(selectedIngredients.containsKey(ingredient)){
            selectedIngredients.remove(ingredient);
        }
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public int getCalories() {
        return calories;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getMenuCategories() {
        return menuCategories;
    }

    public List<Ingredient> getAdditionalIngredients() {
        return additionalIngredients;
    }

    public Map<Ingredient, Integer> getSelectedIngredients() {
        return selectedIngredients;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setMenuCategories(List<String> menuCategories) {
        this.menuCategories = menuCategories;
    }

    public void setAdditionalIngredients(List<Ingredient> additionalIngredients) {
        this.additionalIngredients = additionalIngredients;
    }
}
