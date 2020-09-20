package com.mkkabi.restaurant.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.mkkabi.restaurant.DB.GetSingleItemListener;
import com.mkkabi.restaurant.DB.UsersFirestoreManager;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.User;
import com.mkkabi.restaurant.model.UserFactory;
import com.mkkabi.restaurant.utils.LoggerToaster;

import static com.mkkabi.restaurant.utils.Constants.DUBUG;
import static com.mkkabi.restaurant.utils.Constants.FIREBASE_SIGN_IN;
import static com.mkkabi.restaurant.utils.Constants.FIREBASE_USER;
import static com.mkkabi.restaurant.utils.Constants.USER_INSTANCE;

public class FirebaseLoginActivity extends AppCompatActivity {
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions gso;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;
	private User user;
    private UsersFirestoreManager usersFirestoreManager;
    private ImageButton singInButton;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_account_login);

        singInButton = findViewById(R.id.buttonSignin);
        progressBar = findViewById(R.id.progressBarMedium);

        singInButton.setOnClickListener(v -> signIn());

        this.context = getApplicationContext();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestId()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(context, gso);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        usersFirestoreManager = UsersFirestoreManager.newInstance();
		user = UserFactory.getInstance().getUser();
		
        if (user != null) {
            sendLoginResult(user, RESULT_OK);
        }


    }

    public void signIn() {
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
                GoogleSignInAccount acc = task.getResult(ApiException.class);
                Log.d(DUBUG, "firebaseAuthWithGoogle:" + acc.getId());
                firebaseAuthWithGoogle(acc.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(DUBUG, "Google sign in failed", e);
                // ...
            }
        }
    }

//    private void firebaseAuthWithGoogle(String idToken) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(DUBUG, "signInWithCredential:success");
//                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//						UsersFirestoreManager.newInstance().addUserToDBIfNotExists(firebaseUser);
//                        sendLoginResult(firebaseUser, RESULT_OK);
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(DUBUG, "signInWithCredential:failure", task.getException());
//                        LoggerToaster.printD(context, "Authentication Failed.");
//                        sendLoginResult(null, RESULT_CANCELED);
//                    }
//                });
//    }
	
	
	private void firebaseAuthWithGoogle(String idToken) {
        // todo add progress bar
		progressBar.setVisibility(ProgressBar.VISIBLE);
		
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            
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
                        sendLoginResult(UserFactory.getInstance().getUser(), RESULT_OK);
                    }
                });
            } else {
                // If sign in fails, display a message to the user.
                Log.w(DUBUG, "signInWithCredential:failure", task.getException());
                LoggerToaster.printD(context, "Authentication Failed. Please try again");
                sendLoginResult(null, RESULT_CANCELED);
            }
        });
    }

    public void sendLoginResult(User user, int resultCode) {
        Intent data = new Intent();
        data.putExtra(USER_INSTANCE, "user");
        setResult(resultCode, data);
        finish();
    }

}
