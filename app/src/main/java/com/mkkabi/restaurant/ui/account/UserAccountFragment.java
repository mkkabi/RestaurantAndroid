package com.mkkabi.restaurant.ui.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mkkabi.restaurant.DB.GetItemsCollectionListener;
import com.mkkabi.restaurant.DB.GetSingleItemListener;
import com.mkkabi.restaurant.DB.OrdersFirestoreManager;
import com.mkkabi.restaurant.DB.UsersFirestoreManager;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.Order;
import com.mkkabi.restaurant.model.User;
import com.mkkabi.restaurant.model.UserFactory;
import com.mkkabi.restaurant.utils.LoggerToaster;

import java.util.List;
import java.util.Objects;

import static com.mkkabi.restaurant.utils.Constants.DUBUG;
import static com.mkkabi.restaurant.utils.Constants.FIREBASE_SIGN_IN;


public class UserAccountFragment extends Fragment {

    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions gso;
    private FirebaseAuth firebaseAuth;

    private Context context;
    private Activity activity;
    private NavController navController;
    //    private String firebaseUserID = "";
    private TextView useraccountEmailText, textViewName, textViewPhoneNumber;
    private LinearLayout loginLayout;
    private ScrollView accountDetailsLayout;
    private ImageButton buttonSignin;
    private ImageView imageViewUserImage;
	private ConstraintLayout progressBarLayout;
	private RecyclerView ordersRecyclerView;

    private UsersFirestoreManager usersFirestoreManager;
    private FirebaseUser firebaseUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        usersFirestoreManager = UsersFirestoreManager.newInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestId()
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_user_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initViews(view);
        navController = Navigation.findNavController(view);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        firebaseUser = firebaseAuth.getCurrentUser();
        updateUI(firebaseUser);
    }

    public void initViews(View view) {
        useraccountEmailText = view.findViewById(R.id.textViewEmailAddress);
        textViewName = view.findViewById(R.id.textViewName);
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber);
        imageViewUserImage = view.findViewById(R.id.imageViewUserImage);

        loginLayout = view.findViewById(R.id.loginLayout);
        accountDetailsLayout = view.findViewById(R.id.accountDetailsLayout);
		progressBarLayout = view.findViewById(R.id.progressBarLayout);
		
        buttonSignin = view.findViewById(R.id.buttonSignin);
        buttonSignin.setOnClickListener((v) -> signIn());

        ordersRecyclerView = view.findViewById(R.id.ordersRecyclerView);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = this.getContext();
        activity = this.getActivity();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_account_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_logout:
                signOut();
                return true;
            default:
                break;
        }
        return false;
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, FIREBASE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == FIREBASE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(DUBUG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(DUBUG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
		progressBarLayout.setVisibility(ConstraintLayout.VISIBLE);
		
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
            firebaseUser = firebaseAuth.getCurrentUser();
            if (task.isSuccessful() && firebaseUser != null) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(DUBUG, "signInWithCredential: success");
                usersFirestoreManager.getUserByID(firebaseUser.getUid(), new GetSingleItemListener<User>(User.class) {
                    @Override
                    public void onSuccess() {
                        UserFactory.getInstance().setUser(getItem());
                    }

                    @Override
                    public void onFail() {
                        User user = usersFirestoreManager.constructUserFromFirebase(firebaseUser);
                        UserFactory.getInstance().setUser(user);
                        Log.d(DUBUG, "Adding new User document to Firebase DB");
                        usersFirestoreManager.createUser(user);
                    }

                    @Override
                    public void onDefault() {
                        updateUI(firebaseUser);
                    }
                });
            } else {
                // If sign in fails, display a message to the user.
                Log.w(DUBUG, "signInWithCredential:failure", task.getException());
                LoggerToaster.printD(context, "Authentication Failed. Please try again");
                updateUI(null);
            }
        });
    }

    private void updateUI(FirebaseUser FBUser) {
        if (FBUser != null) {
            // Signed in
			User user = UserFactory.getInstance().getUser();
            if (user != null && user.getFirebaseID().equals(firebaseUser.getUid())) {
                initViewsWithUserData(user);
                Log.d(DUBUG, "User is already set in UserFactory, using the cached instance "+user);
            } else {
				Log.d(DUBUG, "in updateUI(), no cached User instance, parsing new User ");
				progressBarLayout.setVisibility(ConstraintLayout.VISIBLE);
                usersFirestoreManager.getUserByID(firebaseUser.getUid(), new GetSingleItemListener<User>(User.class) {
                    @Override
                    public void onSuccess() {
                        UserFactory.getInstance().setUser(getItem());
                        initViewsWithUserData(getItem());
                    }

                    @Override
                    public void onFail() {
                        // action in cases when there's no such document in the Firestore DB
                    }

                    @Override
                    public void onDefault() {}
                });
            }
        } else {
            // Signed out
            destroyViews(); //  -  set all account info fields to null
        }
    }

    private void initViewsWithUserData(User user) {
		loginLayout.setVisibility(View.GONE);
        accountDetailsLayout.setVisibility(View.VISIBLE);
        useraccountEmailText.setText(user.getEmail());
        textViewName.setText(user.getName());
        textViewPhoneNumber.setText(user.getPhoneNumber());
        Glide.with(accountDetailsLayout).load(user.getPhotoUrl()).into(imageViewUserImage);
        populateOrdersRecyclerView(user);
		progressBarLayout.setVisibility(ConstraintLayout.GONE);
    }

    private void populateOrdersRecyclerView(User user){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        ordersRecyclerView.setLayoutManager(mLayoutManager);
        OrderAdapter adapter = new OrderAdapter(context);
        ordersRecyclerView.setAdapter(adapter);
        ordersRecyclerView.refreshDrawableState();
		loadUserOrders(user, adapter);
    }

    private void loadUserOrders(User user, OrderAdapter adapter){
        OrdersFirestoreManager.newInstance().getOrdersByUserID(user.getFirebaseID(), new GetItemsCollectionListener<Order>(Order.class) {
            @Override
            public void performAction() {
                List<Order> orders = getItemsList();
                Log.d(DUBUG, "parsed orders = " + orders);
                adapter.setData(orders);
            }
        });
    }

    private void destroyViews() {
        useraccountEmailText.setText(null);
        textViewName.setText(null);
        textViewPhoneNumber.setText(null);
//        imageViewUserImage.setImageDrawable(R.drawable.ic_account_box_black_24dp);
		accountDetailsLayout.setVisibility(View.GONE);
        loginLayout.setVisibility(View.VISIBLE);
        UserFactory.getInstance().destroyUser();
		progressBarLayout.setVisibility(ConstraintLayout.GONE);
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        googleSignInClient.signOut();
        updateUI(null);
        Toast.makeText(context, R.string.signed_out, Toast.LENGTH_SHORT).show();
    }

}
