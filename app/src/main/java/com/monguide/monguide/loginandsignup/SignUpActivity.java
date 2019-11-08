package com.monguide.monguide.loginandsignup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.UploadTask;
import com.monguide.monguide.R;
import com.monguide.monguide.home.HomeActivity;
import com.monguide.monguide.models.user.*;
import com.monguide.monguide.utils.DatabaseHelper;
import com.monguide.monguide.utils.StorageHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;

    private Toolbar mToolbar;
    private ImageView mProfileImageView;
    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mCollegeNameEditText;
    private EditText mCourseNameEditText;
    private EditText mGraduationYearEditText;
    private EditText mCompanyNameEditText;
    private EditText mJobProfileEditText;
    private ProgressBar mProgressBar;
    private Button mSignupButton;
    private Uri mImageAddress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mToolbar = (Toolbar) findViewById(R.id.activity_signup_toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mUsernameEditText = (EditText) findViewById (R.id.activity_signup_nameedittext);
        mEmailEditText = (EditText) findViewById (R.id.activity_signup_emailedittext);
        mPasswordEditText = (EditText) findViewById (R.id.activity_signup_passwordedittext);
        mProfileImageView = (ImageView) findViewById (R.id.activity_signup_profilepictureimageview);
        mCollegeNameEditText = (EditText) findViewById (R.id.activity_signup_collegeNameEditText);
        mCourseNameEditText = (EditText) findViewById (R.id.activity_signup_courseName_EditText);
        mGraduationYearEditText = (EditText) findViewById(R.id.activity_signup_graduationyearedittext);
        mCompanyNameEditText = (EditText) findViewById (R.id.activity_signup_companyNameEditText);
        mJobProfileEditText = (EditText) findViewById (R.id.activity_signup_jobProfile_EditText);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_signup_progressbar);
        mSignupButton = (Button) findViewById (R.id.activity_signup_signupbutton);

        mImageAddress = Uri.fromFile(new File("C:\\Users\\piyus\\AndroidStudioProjects\\MonGuide\\app\\src\\main\\res\\drawable-v24\\default_profile_photo.png"));

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkProfileDetails() && checkEducationDetails() && checkWorkDetails()) {
                    sendToDatabase();
                }
            }
        });

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

    }

    private void sendToDatabase(){
        mSignupButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String mUID = mAuth.getCurrentUser().getUid();
                    if(TextUtils.isEmpty(mCompanyNameEditText.getText()) &&
                            TextUtils.isEmpty(mJobProfileEditText.getText())) {
                        DatabaseHelper.getReferenceToParticularUser(mUID).setValue(
                                new UserDetails(
                                        mUsernameEditText.getText().toString(),
                                        new UserDetails.EducationDetails(
                                                mCollegeNameEditText.getText().toString(),
                                                mCourseNameEditText.getText().toString(),
                                                Integer.parseInt(mGraduationYearEditText.getText().toString())
                                        ),
                                        new UserDetails.WorkDetails()
                                )
                        );
                    } else {
                        DatabaseHelper.getReferenceToParticularUser(mUID).setValue(
                                new UserDetails(
                                        mUsernameEditText.getText().toString(),
                                        new UserDetails.EducationDetails(
                                                mCollegeNameEditText.getText().toString(),
                                                mCourseNameEditText.getText().toString(),
                                                Integer.parseInt(mGraduationYearEditText.getText().toString())
                                        ),
                                        new UserDetails.WorkDetails(
                                                mCompanyNameEditText.getText().toString(),
                                                mJobProfileEditText.getText().toString()
                                        )
                                )
                        );
                    }
                    //uploadProfilePictureAToDatabase(mUID);
                    startHomeActivity();
                } else {
                    mProgressBar.setVisibility(View.GONE);
                    mSignupButton.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // for image
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            mImageAddress = data.getData();
            mProfileImageView.setImageURI(mImageAddress);
        }
    }

    private void uploadProfilePictureAToDatabase(String uid) {
        StorageHelper.getRefrenceToParticularProfilePicture(uid).putFile(mImageAddress).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    private boolean checkProfileDetails() {
        if(TextUtils.isEmpty(mUsernameEditText.getText())){
            mUsernameEditText.setError(getResources().getString(R.string.required));
            return false;
        } else if(TextUtils.isEmpty(mPasswordEditText.getText())){
            mPasswordEditText.setError(getResources().getString(R.string.required));
            return false;
        } else if(TextUtils.isEmpty(mEmailEditText.getText())){
            mEmailEditText.setError(getResources().getString(R.string.required));
            return false;
        } else {
            return true;
        }
    }

    private boolean checkEducationDetails() {
        if(TextUtils.isEmpty(mCollegeNameEditText.getText())) {
            mCollegeNameEditText.setError(getResources().getString(R.string.required));
            return false;
        } else if(TextUtils.isEmpty(mCourseNameEditText.getText())) {
            mCourseNameEditText.setError(getResources().getString(R.string.required));
            return false;
        } else {
            return true;
        }
    }

    private boolean checkWorkDetails() {
        // Return true if both are filled or both are empty
        if((TextUtils.isEmpty(mCompanyNameEditText.getText()) && TextUtils.isEmpty(mJobProfileEditText.getText())) ||
                (!TextUtils.isEmpty(mCompanyNameEditText.getText()) && !TextUtils.isEmpty(mJobProfileEditText.getText()))) {
            return true;
        } else {
            if(TextUtils.isEmpty(mCompanyNameEditText.getText())) {
                mCompanyNameEditText.setError(getResources().getString(R.string.required));
                return false;
            } else {
                mJobProfileEditText.setError(getResources().getString(R.string.required));
                return false;
            }
        }
    }
}
