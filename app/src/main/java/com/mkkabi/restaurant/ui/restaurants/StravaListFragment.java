package com.mkkabi.restaurant.ui.restaurants;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.mkkabi.restaurant.DB.Firestore;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.Ingredient;
import com.mkkabi.restaurant.model.Restaurant;
import com.mkkabi.restaurant.model.Strava;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class StravaListFragment extends Fragment {

    private Context context;
    private StravaListAdapter adapter;
    private RecyclerView dishesListRecyclerView;
    private static final String MENUCATEGORY_ID = "menucategoryid";
    private static final String RESTAURANT_ID = "restaurantid";
    private String menucategoryId, restaurantId;
    List<Strava> stravaList;
    FirebaseFirestore db;
    private FirebaseStorage storage;


    public static StravaListFragment newInstance() {
        return new StravaListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = FirebaseStorage.getInstance();
        if (getArguments() != null) {
            menucategoryId = getArguments().getString(MENUCATEGORY_ID);
            restaurantId = getArguments().getString(RESTAURANT_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Toast.makeText(this.getContext(), "StravaList Fragment, menuCategory = " + menucategoryId, Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_dishes_list, container, false);
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
        // Removes blinks
        ((SimpleItemAnimator) dishesListRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        dishesListRecyclerView.setLayoutManager(mLayoutManager);

        stravaList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        // Read from database

        CollectionReference ingredientsReference = db.collection("restaurants/" + restaurantId + "/additional_ingredients/");
        CollectionReference dishes = db.collection("dishes");
        Query queryForDishes = dishes.whereArrayContains("menu_categories", menucategoryId);
        queryForDishes.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Strava strava = new Strava(document.getData().get("name").toString(), document.getId());
                    strava.setPrice(Integer.parseInt(document.getData().get("price").toString()));
                    strava.setDescription(document.getData().get("description").toString());
                    strava.setImageurl(document.getData().get("image_url").toString());

                    Query queryForAdditionalIngredients = ingredientsReference.whereArrayContains("in_dishes", strava.getId());
                    List<Ingredient> additionalIngredients = new ArrayList<>();
                    queryForAdditionalIngredients.get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            for (QueryDocumentSnapshot ingredientDocument : task1.getResult()) {
                                Ingredient ingredient = new Ingredient.Builder()
                                        .name(ingredientDocument.get("name").toString())
                                        .price(Integer.parseInt(ingredientDocument.get("price").toString()))
                                        .calories(Integer.parseInt(ingredientDocument.get("calories").toString()))
                                        .amount(Integer.parseInt(ingredientDocument.get("amount").toString()))
                                        .measureUnit(ingredientDocument.get("measure_unit").toString())
                                        .build();
                                additionalIngredients.add(ingredient);
                            }
                        }
                    });
                    strava.setIngredientList(additionalIngredients);
                    stravaList.add(strava);
                }
                adapter = new StravaListAdapter(this.getContext(), stravaList);
                dishesListRecyclerView.setAdapter(adapter);
                dishesListRecyclerView.refreshDrawableState();
            } else {
                Log.w("myTag", "Error getting documents.", task.getException());
            }
        });

    }

    private void initViews(View root) {
        dishesListRecyclerView = root.findViewById(R.id.dishesListRecyclerView);
    }

}
