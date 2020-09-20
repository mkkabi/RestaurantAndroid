package com.mkkabi.restaurant.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Dish> dishes = new ArrayList<>();
    private int cartTotalPrice;

    private Cart() {
        if (this.dishes == null){
            dishes = new ArrayList<>();
        }
    }

    public static Cart getInstance() {
        return CartHelpter.instance;
    }

    private static class CartHelpter {
        private final static Cart instance = new Cart();
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
		updateCartTotalPrice();
    }

    public void emptyCart(){
        dishes.clear();
		cartTotalPrice = 0;
    }

    public void removeDish(Dish dish) {
        dishes.remove(dish);
		updateCartTotalPrice();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public int getCartTotalPrice() {
        return cartTotalPrice;
    }

	public void updateCartTotalPrice(){
		cartTotalPrice = recalculatePrice();
	}

    public int recalculatePrice() {
        int result = 0;
        if (dishes.size() > 0) {
            for (Dish dish : dishes) {
                result += dish.calculateOverallDishPrice();
            }
        }
        return result;
    }


	
	public List<String> getDishesIds(){
		List<String> result = new ArrayList();
		for(Dish d : dishes){
			result.add(d.getDocumentId());
		}
		return result;
	}
}