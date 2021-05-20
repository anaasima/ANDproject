package com.example.sem4_andproject.ui.signIn;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sem4_andproject.data.users.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class SignInViewModel extends AndroidViewModel
{
    private final UserRepository repository;
    public SignInViewModel(Application application) {
        super(application);
        repository = UserRepository.getInstance(application);
    }
    public LiveData<FirebaseUser> getCurrentUser()
    {
        return repository.getCurrentUser();
    }
}
