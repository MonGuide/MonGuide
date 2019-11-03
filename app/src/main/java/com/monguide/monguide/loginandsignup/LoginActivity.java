package com.monguide.monguide.loginandsignup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.monguide.monguide.R;
import com.monguide.monguide.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    private Button mLogInButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mEmailEditText = (AppCompatEditText) findViewById(R.id.activity_login_emailEditText);
        mPasswordEditText = (AppCompatEditText) findViewById(R.id.activity_login_passwordEditText);
        mLogInButton = (AppCompatButton) findViewById(R.id.activity_login_loginButton);

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAuthentication();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // if user is already logged in, go to home page
        if(mAuth.getCurrentUser() != null) {
            startHomeActivity();
        }
    }

    private void startAuthentication() {
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    startHomeActivity();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.illegal_login, Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void startHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
