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
import com.mkkabi.restaurant.DB.GetItemsCollectionListener;
import com.mkkabi.restaurant.DB.RestaurantsFirestoreManager;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.MenuCategory;
import com.mkkabi.restaurant.model.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mkkabi.restaurant.utils.MyFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class RestaurantsListFragment extends Fragment {
    private Context context;
    private RecyclerView restaurantsRecyclerView;

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

        RestaurantsFirestoreManager.newInstance().getAllRestaurants(new GetItemsCollectionListener<Restaurant>(Restaurant.class) {
            @Override
            public void performAction() {
                populateRestaurantRecyclerView(this.getItemsList());
            }
        });
    }

    private void populateRestaurantRecyclerView(List<Restaurant> restaurantList) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        restaurantsRecyclerView.setLayoutManager(mLayoutManager);
        // Set the restaurantListMainRecyclerViewAdapter in the restaurantListRecyclerView
        RestaurantsListAdapter adapter = new RestaurantsListAdapter(restaurantList);
        restaurantsRecyclerView.setAdapter(adapter);
        restaurantsRecyclerView.refreshDrawableState();
    }

    private void initViews(View root) {
        restaurantsRecyclerView = root.findViewById(R.id.restaurantsRecyclerView);
    }


}


