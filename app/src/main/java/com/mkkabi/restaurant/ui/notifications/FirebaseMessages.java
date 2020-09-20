package com.mkkabi.restaurant.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.ui.notifications.ui.firebasemessages.FirebaseMessagesFragment;

public class FirebaseMessages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_messages_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FirebaseMessagesFragment.newInstance())
                    .commitNow();
        }
    }
}
