package com.monguide.monguide.utils;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.monguide.monguide.R;
import com.monguide.monguide.models.QuestionSummary;
import com.monguide.monguide.profile.ViewProfile;
import com.monguide.monguide.questionandanswer.FullQuestionActivity;

import java.util.HashMap;


public class AnswerHolder extends RecyclerView.ViewHolder {
    private View view;

    private String mUID;
    private String mQID;

    private ShimmerFrameLayout mPlaceholderForShimmerContainer;

    private LinearLayout mFullAnswerContainer;

    private LinearLayout mUserDetailsContainer;

    private ImageView mProfilePictureImageView;
    private TextView mUserNameTextView;
    private TextView mTimeStampTextView;

    private TextView mBodyTextView;

    public AnswerHolder(View view) {
        super(view);
        this.view = view;

        mProfilePictureImageView = view.findViewById(R.id.questionsummary_item_profilepictureimageview);
        mUserNameTextView = view.findViewById(R.id.questionsummary_item_usernametextview);
        mTimeStampTextView = view.findViewById(R.id.questionsummary_item_timestamptextview);
        mBodyTextView = view.findViewById(R.id.questionsummary_item_bodytextview);

        mPlaceholderForShimmerContainer = view.findViewById(R.id.questionsummary_item_shimmercontainer);

        mUserDetailsContainer = view.findViewById(R.id.questionsummary_item_userdetailscontainer);

        // for rounded corners of profile picture
        mProfilePictureImageView.setClipToOutline(true);
    }

    public void setOnClickListenerToOpenUserProfileInFocus() {
        View.OnClickListener redirectToUserProfileInFocus
                = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ViewProfile.class);
                intent.putExtra(Constants.UID, mUID);
                view.getContext().startActivity(intent);
            }
        };
        mUserDetailsContainer.setOnClickListener(redirectToUserProfileInFocus);
    }

    public ShimmerFrameLayout getmPlaceholderForShimmerContainer() {
        return mPlaceholderForShimmerContainer;
    }

    public String getmUID() {
        return mUID;
    }

    public void setmUID(String mUID) {
        this.mUID = mUID;
    }

    public String getmQID() {
        return mQID;
    }

    public void setmQID(String mQID) {
        this.mQID = mQID;
    }

    public ImageView getmProfilePictureImageView() {
        return mProfilePictureImageView;
    }

    public TextView getmUserNameTextView() {
        return mUserNameTextView;
    }

    public TextView getmTimeStampTextView() {
        return mTimeStampTextView;
    }

    public TextView getmBodyTextView() {
        return mBodyTextView;
    }
}
