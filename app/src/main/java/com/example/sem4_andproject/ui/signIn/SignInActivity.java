package com.example.sem4_andproject.ui.signIn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.sem4_andproject.MainActivity;
import com.example.sem4_andproject.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private SignInViewModel viewModel;

    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);


        checkIfSignedIn();
        setContentView(R.layout.activity_sign_in);
        signInButton = findViewById(R.id.signIn);
        signInButton.setOnClickListener(this::choosingAuthenticationProviders);

    }
    private void checkIfSignedIn()
    {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null)
                goToMainActivity();
        });
    }
    public void choosingAuthenticationProviders(View view)
    {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                .createSignInIntentBuilder()
                        .setTheme(R.style.Theme_SEM4ANDProject)
                        .setLogo(R.drawable.logo_size)
                .setAvailableProviders(providers)
                .build(), RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN)
        {
            handleSignIn(resultCode);
        }

    }
    private void handleSignIn(int resultCode)
    {
        if(resultCode == RESULT_OK)
        {
            goToMainActivity();
        }
        else
        {
            Toast.makeText(this, "SIGN IN CANCELLED", Toast.LENGTH_SHORT).show();
        }
    }
    public void goToMainActivity()
    {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}