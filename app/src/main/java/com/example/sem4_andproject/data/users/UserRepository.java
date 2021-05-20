package com.example.sem4_andproject.data.users;

import android.app.Application;
import android.view.animation.AnimationUtils;

import androidx.lifecycle.LiveData;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserRepository
{
    private final Users currentUser;
    private final Application app;
    private static UserRepository instance;

    private UserRepository(Application app)
    {
        this.app=app;
        currentUser = new Users();
    }
    public static synchronized UserRepository getInstance(Application app)
    {
        if(instance == null)
            instance = new UserRepository(app);
       return instance;
    }
    public LiveData<FirebaseUser> getCurrentUser()
    {
        return currentUser;
    }
    public void signOut()
    {
        AuthUI.getInstance().signOut(app.getApplicationContext());
    }
}
