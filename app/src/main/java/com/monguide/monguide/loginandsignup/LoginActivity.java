package com.monguide.monguide.loginandsignup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.monguide.monguide.R;
import com.monguide.monguide.home.HomeActivity;
import com.monguide.monguide.profile.ViewProfile;
import com.monguide.monguide.utils.Constants;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private TextView mSignupTextView;

    private Button mLogInButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        testConnection();

        mAuth = FirebaseAuth.getInstance();

        mEmailEditText = (AppCompatEditText) findViewById(R.id.activity_login_emailedittext);
        mPasswordEditText = (AppCompatEditText) findViewById(R.id.activity_login_passwordedittext);
        mLogInButton = (AppCompatButton) findViewById(R.id.activity_login_loginbutton);
        mSignupTextView = (AppCompatTextView) findViewById(R.id.activity_login_signuptextview);

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MONGUIDE", "Starting authentication...");
                startAuthentication();
            }
        });

        mSignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUpActivity();
            }
        });
    }

    private void testConnection() {
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            Toast.makeText(LoginActivity.this, "Connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // if user is already logged in, go to home page
        if(mAuth.getCurrentUser() != null) {
            // ****************
            //startHomeActivity();
        }
    }

    private void startAuthentication() {
        Log.e("MONGUIDE", "logging in...");

        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent i = new Intent(LoginActivity.this, ViewProfile.class);
                    i.putExtra(Constants.UID, mAuth.getCurrentUser().getUid());
                    startActivity(i);
                    //startHomeActivity();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.illegal_login, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startSignUpActivity() {
        startActivity(new Intent(this,SignUpActivity.class));
        finish();
    }

    private void startHomeActivity() {
        Log.e("MONGUIDE", "Successfully logged in...");

        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
