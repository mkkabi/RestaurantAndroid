package com.mkkabi.restaurant.DB;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mkkabi.restaurant.model.User;
import com.mkkabi.restaurant.services.MyFirebaseMessagingService;

import static com.mkkabi.restaurant.DB.RestaurantFirestoreDbContract.USERS_COLLECTION_NAME;

public class UsersFirestoreManager {
    private static UsersFirestoreManager usersFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference usersCollectionReference;


    private UsersFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        usersCollectionReference = firebaseFirestore.collection(USERS_COLLECTION_NAME);
    }

    public static UsersFirestoreManager newInstance() {
        if (usersFirestoreManager == null) {
            usersFirestoreManager = new UsersFirestoreManager();
        }
        return usersFirestoreManager;
    }

    public Task<Void> createUser(User user) {
        return usersCollectionReference.document(user.getFirebaseID()).set(user);
//        return usersCollectionReference.add(user);


    }

    public void getAllUsers(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        usersCollectionReference.get().addOnCompleteListener(onCompleteListener);
    }
	
	public void getUserByID(String userID, OnCompleteListener<DocumentSnapshot> onCompleteListener){
		DocumentReference docRef = usersFirestoreManager.getUserDocumentReference(userID);
		docRef.get().addOnCompleteListener(onCompleteListener);
	}
	
	public DocumentReference getUserDocumentReference(String id){
	return usersCollectionReference.document(id);
	}

    public void updateUser(User user) {
        String documentId = user.getFirebaseID();
        DocumentReference documentReference = usersCollectionReference.document(documentId);
        documentReference.set(user);
    }

    public void deleteUser(String documentId) {
        DocumentReference documentReference = usersCollectionReference.document(documentId);
        documentReference.delete();
    }

    public Task<QuerySnapshot> getUsersByFirebaseID(String firebaseUserID, OnCompleteListener<QuerySnapshot> listener) {
        return usersCollectionReference.whereEqualTo("firebaseID", firebaseUserID).get().addOnCompleteListener(listener);
    }

    // returns true on task complete
    public Task<QuerySnapshot> addUserToDBIfNotExists(FirebaseUser firebaseUser) {
        return usersFirestoreManager.getUsersByFirebaseID(firebaseUser.getUid(), new GetItemsCollectionListener<User>(User.class) {
            @Override
            public void performAction() {
                if (getItemsList().isEmpty()) {
                    usersFirestoreManager.createUser(constructUserFromFirebase(firebaseUser));
                }
            }
        });
    }

    // check if user is already saved to DB
    // if user is in the DB - construct class instance with the info from DB
    // if user is not in the DB - create new User document in the DB and then construct class instance with the info from DB
	
	// DocumentSnapshot	A DocumentSnapshot contains data read from a document in your Cloud Firestore database. 
//	public User getUser(String firebaseID){
//        DocumentSnapshot dd = new DocumentSnapshot();
//		User user = DocumentSnapshot.get(firebaseID, User.class);
//	}


    public User createOrGetUser(FirebaseUser firebaseUser) {
        User[] user = new User[1];
        usersFirestoreManager.getUsersByFirebaseID(firebaseUser.getUid(), new GetItemsCollectionListener<User>(User.class) {
            @Override
            public void performAction() {
                if (getItemsList().isEmpty()) {
                     usersFirestoreManager.createUser(constructUserFromFirebase(firebaseUser));
                     createOrGetUser(firebaseUser);
                } else {
                    user[0] = getItemsList().get(0);
                }
            }
        });
        return user[0];
    }


//	public boolean firebaseUserIsInDB(FirebaseUser firebaseUser){
//		usersFirestoreManager.getUsersByFirebaseID(firebaseUser.getUid(), new GetItemsCollectionListener<User>(User.class){
//            @Override
//            public void performAction() {
//                return !getItemsList().isEmpty();
//            }
//        });
//	}



    public User constructUserFromFirebase(FirebaseUser firebaseUser) {
        User user = new User();
        user.setFirebaseID(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(firebaseUser.getDisplayName());
        user.setPhoneNumber(firebaseUser.getPhoneNumber());
        user.setPhotoUrl(firebaseUser.getPhotoUrl().toString());
        user.setRole("isClient", true);
        user.setFcmToken(MyFirebaseMessagingService.fcmToken);
        return user;
    }


}	