package com.mkkabi.restaurant.DB;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mkkabi.restaurant.ContextProvider;
import com.mkkabi.restaurant.utils.LoggerToaster;

import static com.mkkabi.restaurant.utils.Constants.DUBUG;


// parameterized generic Listener that implements functional interface MyFunction - this way you can pass any additional action
// used to get list of classes from Firebase and then this list can be used to pupulate UI by passing the corresponding action to the performAction() method
public abstract class GetSingleItemListener<T> implements OnCompleteListener<DocumentSnapshot>, BiAction {
    private final Class<T> type;
    T item;

    public GetSingleItemListener(Class<T> type) {
        this.type = type;
    }

    public T getItem() {
        return this.item;
    }

    private Class<T> getClazz() {
        return this.type;
    }

    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
            DocumentSnapshot documentSnapshot = task.getResult();
            if (documentSnapshot.exists()) {
                item = documentSnapshot.toObject(this.getClazz());
                Log.d(DUBUG, "Item exists in DB, parsed " + item.toString());
                onSuccess();
            } else {
                Log.d(DUBUG, "No such Document in DB");
                onFail();
            }

        } else {
            LoggerToaster.printV(ContextProvider.getAppContext(), "Error getting documents: ", task.getException());

        }

        onDefault();
    }
}