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

    private static int RESULT_LOAD_IMAGE = 1;
    private int mGraduationYearInt;
    private ImageView mProfileImageView;
    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mCollegeNameEditText;
    private EditText mCourseNameEditText;
    private Spinner mGraduationYearSpinner;
    private EditText mCompanyNameEditText;
    private EditText mJobProfileEditText;
    private Button mSignupButton;
    private Uri mImageAddress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1950; i <= thisYear+10; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> graduationYearspinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);


        mAuth = FirebaseAuth.getInstance();

        mSignupButton = (Button) findViewById (R.id.activity_signup_signupbutton);
        mUsernameEditText = (EditText) findViewById (R.id.activity_signup_nameedittext);
        mEmailEditText = (EditText) findViewById (R.id.activity_signup_emailedittext);
        mPasswordEditText = (EditText) findViewById (R.id.activity_signup_passwordedittext);
        mProfileImageView = (ImageView) findViewById (R.id.activity_signup_profilepictureimageview);
        mCollegeNameEditText = (EditText) findViewById (R.id.activity_signup_collegeNameEditText);
        mCourseNameEditText = (EditText) findViewById (R.id.activity_signup_courseName_EditText);
        mGraduationYearSpinner = (Spinner) findViewById (R.id.activity_signup_graduationYearSpinner);
        mCompanyNameEditText = (EditText) findViewById (R.id.activity_signup_companyNameEditText);
        mJobProfileEditText = (EditText) findViewById (R.id.activity_signup_jobProfile_EditText);

        mGraduationYearSpinner.setAdapter(graduationYearspinnerAdapter);
        mImageAddress = Uri.fromFile(new File("C:\\Users\\piyus\\AndroidStudioProjects\\MonGuide\\app\\src\\main\\res\\drawable-v24\\default_profile_photo.png"));


        mGraduationYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGraduationYearInt = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkProfileDetails() && checkEducationDetails() && checkWorkDetails())
                    sendToDatabase();
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
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String mUID = mAuth.getCurrentUser().getUid();

                    if(TextUtils.isEmpty(mCompanyNameEditText.getText()) && TextUtils.isEmpty(mJobProfileEditText.getText()))
                        DatabaseHelper.getReferenceToParticularUser(mUID).setValue(new UserDetails(mUsernameEditText.getText().toString(), new UserDetails.EducationDetails(mCollegeNameEditText.getText().toString(), mCourseNameEditText.getText().toString(), mGraduationYearInt), new UserDetails.WorkDetails()));
                    else
                        DatabaseHelper.getReferenceToParticularUser(mUID).setValue(new UserDetails(mUsernameEditText.getText().toString(), new UserDetails.EducationDetails(mCollegeNameEditText.getText().toString(), mCourseNameEditText.getText().toString(), mGraduationYearInt), new UserDetails.WorkDetails(mCompanyNameEditText.getText().toString(), mJobProfileEditText.getText().toString())));

                    uploadProfilePictureAToDatabase(mUID);
                    startHomeActivity();
                } else {
                    Toast.makeText(SignUpActivity.this, "Enter Valid Email-id or Password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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

        if(TextUtils.isEmpty(mCollegeNameEditText.getText())) {
            mCollegeNameEditText.setError("College name required");
            return false;
        }

        if(TextUtils.isEmpty(mCourseNameEditText.getText())) {
            mCourseNameEditText.setError("Course name required");
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
