package com.mkkabi.restaurant.DB;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.mkkabi.restaurant.ContextProvider;
import com.mkkabi.restaurant.utils.LoggerToaster;
import com.mkkabi.restaurant.utils.MyFunction;

import java.util.ArrayList;
import java.util.List;

// parameterized generic Listener that implements functional interface MyFunction - this way you can pass any additional action
// used to get list of classes from Firebase and then this list can be used to pupulate UI by passing the corresponding action to the performAction() method
public abstract class GetItemsCollectionListener<T> implements OnCompleteListener<QuerySnapshot>, MyFunction {
    private List<T> itemsList = new ArrayList<>();
    private final Class<T> type;

    public GetItemsCollectionListener(Class<T> type){
        this.type = type;
    }

    protected List<T> getItemsList(){
        return itemsList;
    }
    private Class<T> getClazz(){
        return this.type;
    }

    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            // Get the query snapshot from the task result
            QuerySnapshot querySnapshot = task.getResult();
            if (querySnapshot != null) {
                // Get the Restaurants list from the query snapshot
                itemsList = querySnapshot.toObjects(this.getClazz());
                performAction();
            }
        } else {
            LoggerToaster.printV(ContextProvider.getAppContext(), "Error getting documents: ", task.getException());
        }
    }
}