package com.monguide.monguide.loginandsignup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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

    private static int RESULT_LOAD_IMAGE = 1;
    private ImageView mimageView;
    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mCollegeNameEditText;
    private EditText mCourseNameEditText;
    private EditText mGraduationYearEditText;
    private EditText mCompanyNameEditText;
    private EditText mJobProfileEditText;
    private Button mSignupButton;
    private TextView mLoginTextView;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mSignupButton = (Button) findViewById (R.id.activity_signup_signupButton);
        mUsernameEditText = (EditText) findViewById (R.id.activity_signup_nameEditText);
        mEmailEditText = (EditText) findViewById (R.id.activity_signup_emailEditText);
        mPasswordEditText = (EditText) findViewById (R.id.activity_signup_passwordEditText);
        mLoginTextView = (TextView) findViewById (R.id.activity_signup_loginTextView);
        mimageView = (ImageView) findViewById (R.id.activity_signup_imageView);
        mCollegeNameEditText = (EditText) findViewById (R.id.activity_signup_collegeNameEditText);
        mCourseNameEditText = (EditText) findViewById (R.id.activity_signup_courseName_EditText);
        mGraduationYearEditText = (EditText) findViewById (R.id.activity_signup_graduationYearEditText);
        mCompanyNameEditText = (EditText) findViewById (R.id.activity_signup_companyNameEditText);
        mJobProfileEditText = (EditText) findViewById (R.id.activity_signup_jobProfile_EditText);


        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkProfileDetails() && checkEducationDetails() && checkWorkDetails())
                    sendToDatabase();
            }
        });

        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });

        mimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });
    }


    private void sendToDatabase(){
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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri imageAddress = data.getData();
            mimageView.setImageURI(imageAddress);
        }
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void startHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private boolean checkWorkDetails() {

        if(TextUtils.isEmpty(mCompanyNameEditText.getText()) && TextUtils.isEmpty(mJobProfileEditText.getText())){
            return true;
        }

        if(!TextUtils.isEmpty(mCompanyNameEditText.getText()) && !TextUtils.isEmpty(mJobProfileEditText.getText())){
            return true;
        }

        if(TextUtils.isEmpty(mCompanyNameEditText.getText()) && !TextUtils.isEmpty(mJobProfileEditText.getText())){
            mCompanyNameEditText.setError("Company name required");
            return false;
        } else{
            mJobProfileEditText.setError("Job profile required");
            return false;
        }
    }

    private boolean checkEducationDetails() {

        if(TextUtils.isEmpty(mCollegeNameEditText.getText())){
            mCollegeNameEditText.setError("College name required");
            return false;
        }

        if(TextUtils.isEmpty(mCourseNameEditText.getText())){
            mCourseNameEditText.setError("Course name required");
            return false;
        }

        if(TextUtils.isEmpty(mGraduationYearEditText.getText())){
            mGraduationYearEditText.setError("Graduation year required.");
            return false;
        }

        return true;
    }

    private boolean checkProfileDetails() {

        if(TextUtils.isEmpty(mUsernameEditText.getText())){
            mUsernameEditText.setError("Name required");
            return false;
        }

        if(TextUtils.isEmpty(mPasswordEditText.getText())){
            mPasswordEditText.setError("Password required");
            return false;
        }

        if(TextUtils.isEmpty(mEmailEditText.getText())){
            mEmailEditText.setError("Email required");
            return false;
        }

        return true;
    }
}
