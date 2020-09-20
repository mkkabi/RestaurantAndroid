package com.mkkabi.restaurant.DB;

import com.mkkabi.restaurant.model.Restaurant;

public class RestaurantFirestoreDbContract {

    public static final String RESTAURANTS_COLLECTION_NAME = "restaurants";
	
	// Restaurant
    public static final String RESTAURANT_FIELD_DESCRIPTION = "shortDescription";
    public static final String RESTAURANT_FIELD_REGISTRATION_DATE = "registrationDate";
    public static final String RESTAURANT_FIELD_IMAGE = "imageUrl";
    public static final String RESTAURANT_FIELD_NAME = "name";
	

    // Menus
    public static final String MENUS_COLLECTION_NAME = "menu_categories";
    public static final String MENU_FIELD_DESCRIPTION = "descriptionShort";
    public static final String MENU_FIELD_IMAGE = "imageUrl";
    public static final String MENU_FIELD_NAME = "name";

    // Additional Ingredients
    public static final String ADDITIONAL_INGREDIENTS_COLLECTION_NAME = "additional_ingredients";
	public static final String ADDITIONAL_INGREDIENTS_FIELD_AMOUNT = "amount";
	public static final String ADDITIONAL_INGREDIENTS_FIELD_CALORIES = "calories";
	public static final String ADDITIONAL_INGREDIENTS_FIELD_DESCRIPTION = "description";
	public static final String ADDITIONAL_INGREDIENTS_FIELD_MEASURE_UNIT = "measureUnit";
	public static final String ADDITIONAL_INGREDIENTS_FIELD_NAME = "name";
	public static final String ADDITIONAL_INGREDIENTS_FIELD_PRICE = "price";
    

    // Dishes
    public static final String DISHES_COLLECTION_NAME = "dishes";
	public static final String DISH_FIELD_CALORIES = "calories";
	public static final String DISH_FIELD_DESCRIPTION = "description";
	public static final String DISH_FIELD_IMAGEURL = "imageUrl";
		public static final String DISH_ARRAY_MENUCATEGORIES = "menuCategories";
	public static final String DISH_FIELD_NAME = "name";
	public static final String DISH_FIELD_PRICE = "price";
	public static final String DISH_FIELD_RESTAURANTID = "restaurantId";
	public static final String DISH_ARRAY_TAGS = "tags";
	
	// Users
	public static final String USERS_COLLECTION_NAME = "users";
	public static final String USER_FIELD_USERID = "userId";
	public static final String USER_FIELD_EMAIL = "email";
	public static final String USER_FIELD_PHONENUMBER = "phoneNumber";
	public static final String USER_FIELD_PHOTOURL = "photoUrl";
	public static final String USER_FIELD_NAME = "name";
	public static final String USER_MAP_ROLES = "roles";
	public static final String USER_MAP_ROLES_KEY_CLIENT = "isClient";
	public static final String USER_MAP_ROLES_KEY_STAFF = "isStaff";
	
	// Orders
	public static final String ORDERS_COLLECTION_NAME = "orders";
	public static final String ORDER_FIELD_ORDERID = "orderId";
	public static final String ORDER_FIELD_DATE = "date";
	public static final String ORDER_FIELD_RESTAURANTID = "restaurantId";
	public static final String ORDER_FIELD_CLIENTID = "clientId";
	public static final String ORDER_FIELD_ORDERAMOUNT = "orderAmount";
	public static final String ORDER_ARRAY_DISHESIDS = "dishesIDs";
	
    private RestaurantFirestoreDbContract() {}
}
