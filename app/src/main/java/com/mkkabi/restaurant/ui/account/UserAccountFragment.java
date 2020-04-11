package com.mkkabi.restaurant.ui.account;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mkkabi.restaurant.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UserAccountFragment extends Fragment {

    private static final String LAST_SIGNED_IN_ACCOUNT = "lastSignedInAccount";
    private static final String GSO = "googleSignInOptions";
    private String USER_ID = "userId";
    private String userID = "userId";
    private TextView useraccountEmailText;
    private GoogleSignInAccount lastSignedInAccount;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient mGoogleSignInClient;

    public static UserAccountFragment newInstance() {
        return new UserAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            lastSignedInAccount = getArguments().getParcelable(LAST_SIGNED_IN_ACCOUNT);
            googleSignInOptions = getArguments().getParcelable(GSO);
            userID = getArguments().getString(USER_ID);
            mGoogleSignInClient = GoogleSignIn.getClient(this.getContext(), googleSignInOptions);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        useraccountEmailText = view.findViewById(R.id.textViewUserAccountEmail);
        useraccountEmailText.setText(userID);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_account_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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


    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this.getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // ...
            }
        });
    }

}
