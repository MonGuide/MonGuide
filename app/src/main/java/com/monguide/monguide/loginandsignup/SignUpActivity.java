package com.monguide.monguide.loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monguide.monguide.R;
import com.monguide.monguide.home.HomeActivity;

import java.util.Map;

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
                DatabaseReference temp = mDatabaseReference.child("users/XNdIoSHChJWhliVOTlEgQ8VfGol1");
                temp.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        Log.e("XXX", map.toString());
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}

                });

                /*
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String mUID = mAuth.getCurrentUser().getUid();
                            DatabaseReference currUserDatabaseReference = mDatabaseReference.child("users/" + mUID);
                            String name = mUsernameEditText.getText().toString();
                            UserDetails.EducationDetails educationDetails = new UserDetails.EducationDetails("a", "b", 1);
                            UserDetails.WorkDetails workDetails = new UserDetails.WorkDetails("aa", "bb");
                            UserDetails user = new UserDetails(name, educationDetails, workDetails);
                            currUserDatabaseReference.setValue(user);
                            Toast.makeText(SignUpActivity.this, "Success!", Toast.LENGTH_LONG).show();
                            startHomeActivity();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Registration Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                */
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
