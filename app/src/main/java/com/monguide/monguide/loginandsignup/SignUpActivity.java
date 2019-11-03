package com.monguide.monguide.loginandsignup;

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
import com.monguide.monguide.R;

public class SignUpActivity extends AppCompatActivity {

    private Button mSignupButton;
    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private TextView mLoginTextView;

    private Firebase mRefRoot;
    private Firebase mRefCurrentUser;
    private FirebaseAuth mFirebaseAuth;
    private String mUserUID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Firebase.setAndroidContext(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mRefRoot = new Firebase("https://monguide-9b111.firebaseio.com/users");

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

                mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mUserUID = mFirebaseAuth.getCurrentUser().getUid().toString();
                            mRefCurrentUser = new Firebase("https://monguide-9b111.firebaseio.com/users/"+mUserUID);

                            Firebase refCurrentChildName = mRefCurrentUser.child("Name");
                            refCurrentChildName.setValue(mUsernameEditText.getText().toString());

                            Firebase refCurrentChildEmail = mRefCurrentUser.child("Email");
                            refCurrentChildEmail.setValue(mEmailEditText.getText().toString());

                            Firebase refCurrentChildPassword = mRefCurrentUser.child("Password");
                            refCurrentChildPassword.setValue(mPasswordEditText.getText().toString());

                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "Registration Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }
}
