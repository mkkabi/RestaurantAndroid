package com.mkkabi.restaurant.DB;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mkkabi.restaurant.model.Restaurant;
import com.mkkabi.restaurant.model.Dish;

import static com.mkkabi.restaurant.DB.RestaurantFirestoreDbContract.ADDITIONAL_INGREDIENTS_COLLECTION_NAME;
import static com.mkkabi.restaurant.DB.RestaurantFirestoreDbContract.DISHES_COLLECTION_NAME;
import static com.mkkabi.restaurant.DB.RestaurantFirestoreDbContract.DISH_ARRAY_MENUCATEGORIES;
import static com.mkkabi.restaurant.DB.RestaurantFirestoreDbContract.MENUS_COLLECTION_NAME;
import static com.mkkabi.restaurant.DB.RestaurantFirestoreDbContract.ORDER_ARRAY_DISHESIDS;
import static com.mkkabi.restaurant.DB.RestaurantFirestoreDbContract.RESTAURANTS_COLLECTION_NAME;

public class RestaurantsFirestoreManager {
    private static RestaurantsFirestoreManager restaurantsFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference restaurantsCollectionReference;
    private CollectionReference dishesCollectionReference;


    private RestaurantsFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        restaurantsCollectionReference = firebaseFirestore.collection(RESTAURANTS_COLLECTION_NAME);
        dishesCollectionReference = firebaseFirestore.collection(DISHES_COLLECTION_NAME);
    }

    public static RestaurantsFirestoreManager newInstance() {
        if (restaurantsFirestoreManager == null) {
            restaurantsFirestoreManager = new RestaurantsFirestoreManager();
        }
        return restaurantsFirestoreManager;
    }

    // RESTAURANT only START
    public void createRestaurant(Restaurant restaurant) {
        restaurantsCollectionReference.add(restaurant);
    }

    public void getAllRestaurants(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        restaurantsCollectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateRestaurant(Restaurant restaurant) {
        String documentId = restaurant.getDocumentId();
        DocumentReference documentReference = restaurantsCollectionReference.document(documentId);
        documentReference.set(restaurant);
    }

    public void deleteRestaurant(String documentId) {
        DocumentReference documentReference = restaurantsCollectionReference.document(documentId);
        documentReference.delete();
    }

    // RESTAURANT only END


    // MENU START
    public void getAllMenus(String restaurantId, OnCompleteListener<QuerySnapshot> onCompleteListener){
        CollectionReference menus = restaurantsCollectionReference.document(restaurantId).collection(MENUS_COLLECTION_NAME);
        menus.get().addOnCompleteListener(onCompleteListener);
    }

    // MENU END

    // Strava and Additional ingredients START
    public void getDishesForMenu(String menuCategoryId, OnCompleteListener<QuerySnapshot> onCompleteListener){
        dishesCollectionReference.whereArrayContains(DISH_ARRAY_MENUCATEGORIES, menuCategoryId).get().addOnCompleteListener(onCompleteListener);
    }
	
	public void getDishesForOrder(String orderId, OnCompleteListener<QuerySnapshot> onCompleteListener){
        dishesCollectionReference.whereArrayContains(ORDER_ARRAY_DISHESIDS, orderId).get().addOnCompleteListener(onCompleteListener);
    }

    public void setupAdditionalIngredients(Dish dish, OnCompleteListener<QuerySnapshot> onCompleteListener){
        CollectionReference ingredientsReference = dishesCollectionReference.document(dish.getDocumentId()).collection(ADDITIONAL_INGREDIENTS_COLLECTION_NAME);
//            ingredientsReference.whereArrayContains(ADDITIONAL_INGREDIENTS_ARRAY_OF_DISHES_NAME, strava.getDocumentId()).get().addOnCompleteListener(onCompleteListener);
        ingredientsReference.get().addOnCompleteListener(onCompleteListener);
    }

    // Strava and Additional ingredients END




}
