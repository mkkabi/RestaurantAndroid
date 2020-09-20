package com.mkkabi.restaurant.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    @DocumentId
    private String orderId;
    private Timestamp date;
    private String clientId;
    private List<String> dishesIDs = new ArrayList<>();
    private int orderAmount;

    public Order() {

    }
	
	public Order(Cart cart, String userId){
        this.date = Timestamp.now();
		this.clientId = userId;
		this.dishesIDs = cart.getDishesIds();
		this.orderAmount = cart.getCartTotalPrice();
	}

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<String> getDishesIDs() {
        return dishesIDs;
    }

    public void setDishesIDs(List<String> dishesIDs) {
        this.dishesIDs = dishesIDs;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public String toString(){
        return "Order " + getOrderId() + " amount = " + getOrderAmount();
    }
}
