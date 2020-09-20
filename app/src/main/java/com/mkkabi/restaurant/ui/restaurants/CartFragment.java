package com.mkkabi.restaurant.ui.restaurants;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mkkabi.restaurant.DB.OrdersFirestoreManager;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.Cart;
import com.mkkabi.restaurant.model.Dish;
import com.mkkabi.restaurant.model.Order;
import com.mkkabi.restaurant.model.User;
import com.mkkabi.restaurant.model.UserFactory;
import com.mkkabi.restaurant.ui.account.FirebaseLoginActivity;
import com.mkkabi.restaurant.utils.LoggerToaster;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.mkkabi.restaurant.utils.Constants.DUBUG;
import static com.mkkabi.restaurant.utils.Constants.FIREBASE_SIGN_IN;

public class CartFragment extends Fragment {
    FloatingActionButton fab;
    CollapsingToolbarLayout collapsingToolbar;
    private Context context;
    private CartAdapter adapter;
    private RecyclerView dishesListRecyclerView;
    private Cart cart;
    private ConstraintLayout emptyCartConstraintLayout;
    private CoordinatorLayout dishesListIncludedLayout;
    private CartViewModel viewModel;
	private FirebaseUser firebaseUser;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        cart = Cart.getInstance();
//        setRetainInstance(true);//so that the fragment is NOT recreated


		firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);

        // Removes blinks - need for the additional ingredients fold/unfold
//        ((SimpleItemAnimator) dishesListRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        dishesListRecyclerView.setLayoutManager(layoutManager);
        LoggerToaster.printD(context, "items in cart: " + Cart.getInstance().getDishes());

        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        adapter = new CartAdapter(context, viewModel);
        Log.d(DUBUG, "dishes list from ViewModel = " + viewModel.getDishesList().getValue());

        viewModel.getDishesList().observe(getViewLifecycleOwner(), dishes -> {

            if (dishes != null && dishes.size() > 0) {
                showCartContents();
            } else {
                showEmptyCartImage();
            }

            adapter.setData(dishes);
            Log.d(DUBUG, "livedata changed =  " + dishes);
        });

        dishesListRecyclerView.setAdapter(adapter);
        dishesListRecyclerView.refreshDrawableState();

        setHasOptionsMenu(true);
//        Toolbar toolbarCart = view.findViewById(R.id.toolbarCart);
//        collapsingToolbar = view.findViewById(R.id.toolbar_layout);
//        collapsingToolbar.setTitle("Cart total: ");

//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbarCart);
//        activity.setTitle("Project Details");
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbarCart.setTitle("hhh");


//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarCart);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
//        toolbar.setTitle("Shopping cart total: "+ Cart.getInstance().getCartTotalPrice());
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_24dp);
//        toolbar.inflateMenu(R.menu.menu_account_fragment);
    }

    private void initViews(View root) {
        dishesListRecyclerView = root.findViewById(R.id.dishesListRecyclerView);
        fab = root.findViewById(R.id.fab);
        emptyCartConstraintLayout = root.findViewById(R.id.emptyCartConstraintLayout);
        dishesListIncludedLayout = root.findViewById(R.id.dishesListIncludedLayout);

        fab.setOnClickListener((view) -> {
            if (UserFactory.getInstance().getUser() != null) {
                LoggerToaster.printD(context, "User singed in. email = " + UserFactory.getInstance().getUser().getEmail());
                makeOrder(new Order(cart, UserFactory.getInstance().getUser().getFirebaseID()));

            } else {
                LoggerToaster.printD(context, "No user is signed in, launching Sign in Activity");
                signIn();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FIREBASE_SIGN_IN) {
            if (resultCode == RESULT_OK) {
//                FirebaseUser user = data.getParcelableExtra(USER_INSTANCE);

                LoggerToaster.printD(context, "onActivityResult() user ID = " + UserFactory.getInstance().getUser().getFirebaseID());
                makeOrder(new Order(cart, UserFactory.getInstance().getUser().getFirebaseID()));
            }
        }
    }

    private void signIn() {
        Intent intent = new Intent(context, FirebaseLoginActivity.class);
        startActivityForResult(intent, FIREBASE_SIGN_IN);
    }


    private void makeOrder(Order order) {
//		saveOrderToUser();
//		notifyRestaurants(); - add orders to table NewOrders in restaurant? with status new maybe

        OrdersFirestoreManager.newInstance().createOrder(order);
        viewModel.emptyCart();

    }

    private void showEmptyCartImage() {
        emptyCartConstraintLayout.setVisibility(View.VISIBLE);
        dishesListIncludedLayout.setVisibility(View.GONE);
    }

    private void showCartContents() {
        emptyCartConstraintLayout.setVisibility(View.GONE);
        dishesListIncludedLayout.setVisibility(View.VISIBLE);
    }

}