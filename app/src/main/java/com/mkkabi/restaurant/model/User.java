package com.mkkabi.restaurant.model;

import android.util.Log;

import com.google.firebase.firestore.DocumentId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mkkabi.restaurant.utils.Constants.DUBUG;

public class User {
    @DocumentId
    protected String firebaseID;
    protected String email, name, phoneNumber, photoUrl, fcmToken;
    protected Map<String, Boolean> roles = new HashMap<>();

    protected List<String> orders;
	
    public User() {
    }

    public void setRole(String key, Boolean val){
        this.roles.put(key, val);
    }

    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setRoles(Map<String, Boolean> roles) {
        this.roles = roles;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

    public String getFirebaseID() {
        return firebaseID;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Map<String, Boolean> getRoles() {
        return roles;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    @Override
    public String toString(){
        // compiler will use StringBuilder automatically here
        return "User " + getName() + " with email " + getEmail();
    }
}