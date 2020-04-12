package com.mkkabi.restaurant.DB;

import android.util.Log;

import androidx.annotation.NonNull;

import com.mkkabi.restaurant.model.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();


        db = FirebaseFirestore.getInstance();
        // Read from database
        Task<QuerySnapshot> task = db.collection("restaurants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Restaurant restaurant = new Restaurant(document.getData().get("name").toString(), document.getId());
                                restaurant.setshortDescription(document.getData().get("short_description").toString());
                                restaurant.setImageUrl(document.getData().get("image_url").toString());
                                restaurants.add(restaurant);
                            }
                        } else {
                            Log.w("myTag", "Error getting documents.", task.getException());
                        }
                    }
                });

        return restaurants;
    }
}
