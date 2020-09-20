package com.mkkabi.restaurant.ui.notifications.ui.firebasemessages;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.Cart;
import com.mkkabi.restaurant.model.Dish;

import java.util.ArrayList;
import java.util.List;

public class FirebaseMessagesFragment extends Fragment {

    private FirebaseMessagesViewModel mViewModel;

    public static FirebaseMessagesFragment newInstance() {
        return new FirebaseMessagesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.firebase_messages_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FirebaseMessagesViewModel.class);
        // TODO: Use the ViewModel




     }

}
