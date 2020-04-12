package com.mkkabi.restaurant.model;

import com.google.firebase.firestore.DocumentId;


public class Ingredient implements Comparable<Ingredient>{
    private String name, id, description, measureUnit;
    // @amount is ingredient's weight or volume per portion
    private int price, calories, amount;

    public static class Builder{
        private String name, id, description, measureUnit;
        private int price, calories, amount;

        public Builder() {
        }

        public Ingredient build() {
            return new Ingredient(this);
        }

        public Builder measureUnit(String measureUnit) {
            this.measureUnit = measureUnit;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(int price){
            this.price = price;
            return this;
        }

        public Builder amount(int amount){
            this.amount = amount;
            return this;
        }

        public Builder calories(int calories){
            this.calories = calories;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }
    }

    private Ingredient(Builder builder){
        this.name = builder.name;
        this.price = builder.price;
    }

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
    public int compareTo(Ingredient ingredient) {
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

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                '}';
    }
}
