package com.mkkabi.restaurant.ui.restaurants;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsListFragment extends Fragment {
    private Context context;
    private RestaurantsListAdapter adapter;
    private RecyclerView restaurantsRecyclerView;
    FirebaseFirestore db;
    List<Restaurant> restaurants;

    public static RestaurantsListFragment newInstance() {
        return new RestaurantsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_restaurants_list, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        initViews(view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        restaurantsRecyclerView.setLayoutManager(mLayoutManager);
        restaurants = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        // Read from database
        db.collection("restaurants")
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

                            adapter = new RestaurantsListAdapter(restaurants);
                            restaurantsRecyclerView.setAdapter(adapter);
                            restaurantsRecyclerView.refreshDrawableState();
                        } else {
                            Log.w("myTag", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void initViews(View root) {
        restaurantsRecyclerView = root.findViewById(R.id.restaurantsRecyclerView);
    }

}
