package com.mkkabi.restaurant.ui.restaurants;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mkkabi.restaurant.model.Cart;
import com.mkkabi.restaurant.model.Dish;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    //    private String TAG = MainActivityViewModel.class.getSimpleName();
    private MutableLiveData<List<Dish>> dishesList;
    private Cart cart = Cart.getInstance();
    public CartViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<List<Dish>> getDishesList() {
        if (dishesList == null) {
            dishesList = new MutableLiveData<>();
            loadDishesList();
        }
        return dishesList;
    }

    public void removeDish(Dish dish) {
        cart.removeDish(dish);
        loadDishesList();
    }

    public void emptyCart(){
        cart.emptyCart();
        loadDishesList();
    }


    private void loadDishesList() {
        Handler myHandler = new Handler();

//        myHandler.postDelayed(() -> {
//            dishesList.setValue(Cart.getInstance().getDishes());
//        }, 2000);

        myHandler.post(() -> dishesList.setValue(Cart.getInstance().getDishes()));
    }
}
