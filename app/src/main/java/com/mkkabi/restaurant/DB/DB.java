package com.mkkabi.restaurant.DB;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mkkabi.restaurant.ContextProvider;

public class DB {

    public static DB getInstance() {

        ConnectivityManager cm =
                (ConnectivityManager) ContextProvider.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            return Firestore.getInstance();
        }

        return null;
    }


}
