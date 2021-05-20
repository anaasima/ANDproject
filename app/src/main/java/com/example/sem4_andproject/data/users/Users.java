package com.example.sem4_andproject.data.users;

import androidx.lifecycle.LiveData;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Users extends LiveData<FirebaseUser>
{
    private final FirebaseAuth.AuthStateListener listener = firebaseAuth -> setValue(firebaseAuth.getCurrentUser());

    @Override
    protected void onActive() {
        super.onActive();
        FirebaseAuth.getInstance().addAuthStateListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        FirebaseAuth.getInstance().removeAuthStateListener(listener);
    }
}
