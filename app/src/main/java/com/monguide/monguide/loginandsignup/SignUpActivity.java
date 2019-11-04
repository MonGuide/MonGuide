package com.monguide.monguide.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.monguide.monguide.R;
import com.monguide.monguide.home.HomeActivity;
import com.monguide.monguide.utils.EducationDetails;
import com.monguide.monguide.utils.MyUser;

public class SignUpActivity extends AppCompatActivity {

    private Button mSignupButton;
    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private TextView mLoginTextView;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mSignupButton = (Button) findViewById(R.id.activity_signup_signupButton);
        mUsernameEditText = (EditText) findViewById(R.id.activity_signup_nameEditText);
        mEmailEditText = (EditText) findViewById(R.id.activity_signup_emailEditText);
        mPasswordEditText = (EditText) findViewById(R.id.activity_signup_passwordEditText);
        mLoginTextView = (TextView) findViewById(R.id.activity_signup_loginTextView);

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String mUID = mAuth.getCurrentUser().getUid();
                            DatabaseReference currUserDatabaseReference = mDatabaseReference.child("users/" + mUID);
                            currUserDatabaseReference.setValue(new MyUser(mUsernameEditText.getText().toString(), new EducationDetails("abc", "def", 123)));
                            startHomeActivity();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Registration Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });


        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });
    }


    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void startHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
