package com.mkkabi.restaurant.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentId;


public class Ingredient implements Comparable<Ingredient>{
    @DocumentId
    private String id;
    private String name, description, measureUnit;
    // @amount is ingredient's weight or volume per portion
    private int price, calories, amount;

    public Ingredient(){}

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public int getAmount() {
        return amount;
    }

    public int getCalories() {
        return calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // todo может проверяться с ингредиетом, у которого имя null, нужно ли в методе compareTo делать проверку на Null параметра name?
    @Override
    public int compareTo(@NonNull Ingredient ingredient) {
        return this.name.compareTo(((Ingredient)ingredient).getName());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Ingredient)) {
            return false;
        }

        Ingredient ingredient = (Ingredient) o;
        return ingredient.getName().equals(this.name);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode() + price;
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                '}';
    }
}
