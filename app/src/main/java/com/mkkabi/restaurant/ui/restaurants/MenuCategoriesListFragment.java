package com.mkkabi.restaurant.ui.restaurants;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkkabi.restaurant.DB.GetItemsCollectionListener;
import com.mkkabi.restaurant.DB.RestaurantsFirestoreManager;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.MenuCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.mkkabi.restaurant.utils.Constants.RESTAURANT_ID;

public class MenuCategoriesListFragment extends Fragment {

    private Context context;
    private RecyclerView menuCategoriesRecyclerView;
    private String restaurantId = "";

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

        RestaurantsFirestoreManager.newInstance().getAllMenus(restaurantId, new GetItemsCollectionListener<MenuCategory>(MenuCategory.class) {
            @Override
            public void performAction() {
                populateMenusRecyclerView(this.getItemsList());
            }
        });
    }

    private void populateMenusRecyclerView(List<MenuCategory> menuCategories) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        menuCategoriesRecyclerView.setLayoutManager(mLayoutManager);
        MenuCategoriesListAdapter adapter = new MenuCategoriesListAdapter(menuCategories);
        menuCategoriesRecyclerView.setAdapter(adapter);
        menuCategoriesRecyclerView.refreshDrawableState();
    }

    private void initViews(View root) {
        menuCategoriesRecyclerView = root.findViewById(R.id.menuCategoriesList);
    }

}
