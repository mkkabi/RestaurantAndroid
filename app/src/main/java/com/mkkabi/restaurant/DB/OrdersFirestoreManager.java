package com.mkkabi.restaurant.DB;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseOrder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mkkabi.restaurant.model.Order;
import com.mkkabi.restaurant.services.MyFirebaseMessagingService;

import static com.mkkabi.restaurant.DB.RestaurantFirestoreDbContract.ORDERS_COLLECTION_NAME;
import static com.mkkabi.restaurant.DB.RestaurantFirestoreDbContract.ORDER_FIELD_CLIENTID;

public class OrdersFirestoreManager {
    private static OrdersFirestoreManager ordersFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference ordersCollectionReference;


    private OrdersFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        ordersCollectionReference = firebaseFirestore.collection(ORDERS_COLLECTION_NAME);
    }

    public static OrdersFirestoreManager newInstance() {
        if (ordersFirestoreManager == null) {
            ordersFirestoreManager = new OrdersFirestoreManager();
        }
        return ordersFirestoreManager;
    }

    public Task<DocumentReference> createOrder(Order order) {
        return ordersCollectionReference.add(order);
    }

    public void getAllOrders(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        ordersCollectionReference.get().addOnCompleteListener(onCompleteListener);
    }
	
	public void getOrdersByUserID(String userID, OnCompleteListener<QuerySnapshot> onCompleteListener){
        ordersCollectionReference.whereEqualTo("clientId", userID).get().addOnCompleteListener(onCompleteListener);
	}
	
	public DocumentReference getOrderDocumentReference(String id){
	return ordersCollectionReference.document(id);
	}

    public void updateOrder(Order order) {
        String documentId = order.getOrderId();
        DocumentReference documentReference = ordersCollectionReference.document(documentId);
        documentReference.set(order);
    }

    public void deleteOrder(String documentId) {
        DocumentReference documentReference = ordersCollectionReference.document(documentId);
        documentReference.delete();
    }
}	