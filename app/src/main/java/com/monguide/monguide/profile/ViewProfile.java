package com.monguide.monguide.profile;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monguide.monguide.R;
import com.monguide.monguide.models.user.UserDetails;
import com.monguide.monguide.utils.Constants;
import com.monguide.monguide.utils.DatabaseHelper;

public class ViewProfile extends AppCompatActivity {
    private static final String TAG = "ViewProfile";

    private String mCurrUID;

    private Toolbar mToolBar;

    private ImageView mProfilePictureImageView;
    private TextView mUsernameTextView;

    private TextView mJobProfileTextView;
    private TextView mCompanyNameTextView;

    private TextView mCourseNameTextView;
    private TextView mCollegeNameTextView;
    private TextView mGraduationYearTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle extras = getIntent().getExtras();
        // Close the activity if no UID is given
        if (extras == null) {
            Log.e(TAG, "Empty User ID...");
            finish();
        }
        mCurrUID = extras.getString(Constants.UID);
        setContentView(R.layout.activity_viewprofile);

        mProfilePictureImageView = (ImageView) findViewById(R.id.activity_viewprofile_imageview_profilepicture);
        mUsernameTextView = (TextView) findViewById(R.id.activity_viewprofile_textview_username);
        mJobProfileTextView = (TextView) findViewById(R.id.activity_viewprofile_textview_jobprofile);
        mCompanyNameTextView = (TextView) findViewById(R.id.activity_viewprofile_textview_companyname);
        mCourseNameTextView = (TextView) findViewById(R.id.activity_viewprofile_textview_coursename);
        mCollegeNameTextView = (TextView) findViewById(R.id.activity_viewprofile_textview_collegename);
        mGraduationYearTextView = (TextView) findViewById(R.id.activity_viewprofile_textview_graduationyear);
        mProfilePictureImageView.setClipToOutline(true);

        populateUserDetails();
        populateQuestionsAsked();


    }

    private void populateUserDetails() {
        DatabaseHelper.getReferenceToParticularUser(mCurrUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "Updating user details..........");
                UserDetails userDetails = dataSnapshot.getValue(UserDetails.class);
                mUsernameTextView.setText(userDetails.getName());
                mJobProfileTextView.setText(userDetails.getWorkDetails().getJobProfile());
                mCompanyNameTextView.setText(userDetails.getWorkDetails().getCompanyName());
                mCourseNameTextView.setText(userDetails.getEducationDetails().getCourseName());
                mCollegeNameTextView.setText(userDetails.getEducationDetails().getCollegeName());
                mGraduationYearTextView.setText(String.valueOf(userDetails.getEducationDetails().getGraduationYear()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void populateQuestionsAsked() {

    }

}
