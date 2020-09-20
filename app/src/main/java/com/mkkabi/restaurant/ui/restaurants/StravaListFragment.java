package com.mkkabi.restaurant.ui.restaurants;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mkkabi.restaurant.DB.GetItemsCollectionListener;
import com.mkkabi.restaurant.DB.RestaurantsFirestoreManager;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.Ingredient;
import com.mkkabi.restaurant.model.Dish;
import com.mkkabi.restaurant.model.User;
import com.mkkabi.restaurant.ui.account.FirebaseLoginActivity;
import com.mkkabi.restaurant.utils.LoggerToaster;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.mkkabi.restaurant.utils.Constants.FIREBASE_SIGN_IN;
import static com.mkkabi.restaurant.utils.Constants.FIREBASE_USER;
import static com.mkkabi.restaurant.utils.Constants.MENUCATEGORY_ID;
import static com.mkkabi.restaurant.utils.Constants.RESTAURANT_ID;

public class StravaListFragment extends Fragment {

    private Context context;
    private StravaListAdapter adapter;
    private RecyclerView dishesListRecyclerView;
    private String menucategoryId, restaurantId;
    FloatingActionButton fab;


    public static StravaListFragment newInstance() {
        return new StravaListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menucategoryId = getArguments().getString(MENUCATEGORY_ID);
            restaurantId = getArguments().getString(RESTAURANT_ID);
        }
        context = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_dishes_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        RestaurantsFirestoreManager restaurantsFirestoreManager = RestaurantsFirestoreManager.newInstance();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        // Removes blinks - need for the additional ingredients fold/unfold
        ((SimpleItemAnimator) dishesListRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        dishesListRecyclerView.setLayoutManager(layoutManager);

        // Parse Dishes and additional ingredients from the DB
        restaurantsFirestoreManager.getDishesForMenu(menucategoryId, new GetItemsCollectionListener<Dish>(Dish.class) {
            @Override
            public void performAction() {
                List<Dish> dishList = this.getItemsList();
                for (Dish dish : dishList) {
                    restaurantsFirestoreManager.setupAdditionalIngredients(dish, new GetItemsCollectionListener<Ingredient>(Ingredient.class){
                        @Override
                        public void performAction() {
                            dish.setAdditionalIngredients(this.getItemsList());
                            LoggerToaster.printD(context, "additional Ingredients for "+ dish.getName()+" "+this.getItemsList());
                        }
                    });
                }

                adapter = new StravaListAdapter(context, dishList);
                dishesListRecyclerView.setAdapter(adapter);
                dishesListRecyclerView.refreshDrawableState();
            }
        });

    }


    private void initViews(View root) {
        dishesListRecyclerView = root.findViewById(R.id.dishesListRecyclerView);
		fab = root.findViewById(R.id.fab);
		
		
        fab.setOnClickListener((view)->{
            Navigation.findNavController(view).navigate(R.id.nav_cart, null);
        });

    }


}
