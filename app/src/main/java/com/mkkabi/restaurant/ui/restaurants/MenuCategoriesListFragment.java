package com.mkkabi.restaurant.ui.restaurants;

import android.content.Context;
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
import android.widget.Toast;

import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.MenuCategory;
import com.mkkabi.restaurant.model.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MenuCategoriesListFragment extends Fragment {

    private Context context;
    private MenuCategoriesListAdapter adapter;
    private RecyclerView menuCategoriesRecyclerView;
    private static final String RESTAURANT_ID = "restaurantId";
    private String restaurantId;
    List<MenuCategory> menuCategories;
    FirebaseFirestore db;

    public MenuCategoriesListFragment() {
        // Required empty public constructor
    }

    public static MenuCategoriesListFragment newInstance(String restaurantId) {
        MenuCategoriesListFragment fragment = new MenuCategoriesListFragment();
        Bundle args = new Bundle();
        args.putString(RESTAURANT_ID, restaurantId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurantId = getArguments().getString(RESTAURANT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        initViews(view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        menuCategoriesRecyclerView.setLayoutManager(mLayoutManager);

        menuCategories = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        // Read from database
        db.collection("restaurants/"+restaurantId+"/menu_categories/")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MenuCategory menuCategory = new MenuCategory(document.getId(), restaurantId, document.getData().get("description_short").toString(),
                                        document.getData().get("name").toString(), document.getData().get("image_url").toString());

                                menuCategories.add(menuCategory);
                            }

                            adapter = new MenuCategoriesListAdapter(menuCategories);
                            menuCategoriesRecyclerView.setAdapter(adapter);
                            menuCategoriesRecyclerView.refreshDrawableState();

                        } else {
                            Log.w("myTag", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void initViews(View root) {
        menuCategoriesRecyclerView = root.findViewById(R.id.menuCategoriesList);
    }
}
