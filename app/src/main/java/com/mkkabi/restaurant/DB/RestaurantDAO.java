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
        List<Restaurant> result = new ArrayList<>();

        // Read from database
        db.collection("restoraunts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Restaurant restaurant = new Restaurant(document.getData().get("name").toString(), document.getData().get("id").toString());
                                restaurant.setshortDescription(document.getData().get("description").toString());
                                result.add(restaurant);
                            }
                        } else {
                            Log.w("myTag", "Error getting documents.", task.getException());
                        }
                    }
                });
        return result;
    }
}
