package com.monguide.monguide.utils;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.monguide.monguide.R;


public class QuestionSummaryHolder extends RecyclerView.ViewHolder {
    private String mUID;
    private String mQID;

    private ImageView mProfilePictureImageView;
    private TextView mUserNameTextView;
    private TextView mTimeStampTextView;
    private TextView mTitleTextView;
    private TextView mBodyTextView;
    private TextView mUpvoteCountTextView;
    private TextView mDownVoteCountTextView;
    private TextView mAnswerCountTextView;
    private Button mUpvoteButtom;
    private Button mDownVoteButton;
    private TextView mAddAnswerTextView;

    private LinearLayout mUserDetailsContainer;
    private LinearLayout mQuestionDetailsContainer;

    private final View.OnClickListener redirectToUserProfileInFocus
            = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // redirect to user profile
            Toast.makeText(v.getContext(), "User profile", Toast.LENGTH_LONG).show();
        }
    };

    private final View.OnClickListener redirectToFullQuestion
            = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // redirect to user profile
            // WORKING TILL HERE
            Toast.makeText(v.getContext(), "Full Question", Toast.LENGTH_LONG).show();
        }
    };

    public QuestionSummaryHolder(View view) {
        super(view);
        mProfilePictureImageView = view.findViewById(R.id.questionsummary_item_profilepictureimageview);
        mUserNameTextView = view.findViewById(R.id.questionsummary_item_usernametextview);
        mTitleTextView = view.findViewById(R.id.questionsummary_item_titletextview);
        mBodyTextView = view.findViewById(R.id.questionsummary_item_bodytextview);
        mAddAnswerTextView = view.findViewById(R.id.questionsummary_item_addanswertextview);

        mUserDetailsContainer = view.findViewById(R.id.questionsummary_item_userdetailscontainer);
        mQuestionDetailsContainer = view.findViewById(R.id.questionsummary_item_questiondetailscontainer);

        // for rounded corners of profile picture
        mProfilePictureImageView.setClipToOutline(true);

        mUserDetailsContainer.setOnClickListener(redirectToUserProfileInFocus);

        mQuestionDetailsContainer.setOnClickListener(redirectToFullQuestion);
        mAddAnswerTextView.setOnClickListener(redirectToFullQuestion);
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    public TextView getBodyTextView() {
        return mBodyTextView;
    }
}
