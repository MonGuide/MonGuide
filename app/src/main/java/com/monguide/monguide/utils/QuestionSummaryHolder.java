package com.monguide.monguide.utils;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.monguide.monguide.R;


public class QuestionSummaryHolder extends RecyclerView.ViewHolder {
    private String mUID;
    private String mQID;

    private ShimmerFrameLayout mPlaceholderForShimmerContainer;
    private LinearLayout mFullQuestionSummaryContainer;
    private LinearLayout mUserDetailsContainer;
    private LinearLayout mTitleBodyContainer;

    private ImageView mProfilePictureImageView;
    private TextView mUserNameTextView;
    private TextView mTimeStampTextView;
    private TextView mTitleTextView;
    private TextView mBodyTextView;
    private TextView mUpvoteCountTextView;
    private TextView mDownVoteCountTextView;
    private TextView mAnswerCountTextView;
    private ImageButton mUpvoteButtom;
    private ImageButton mDownVoteButton;
    private TextView mAddAnswerTextView;

    public QuestionSummaryHolder(View view) {
        super(view);

        mProfilePictureImageView = view.findViewById(R.id.questionsummary_item_profilepictureimageview);
        mUserNameTextView = view.findViewById(R.id.questionsummary_item_usernametextview);
        mTimeStampTextView = view.findViewById(R.id.questionsummary_item_timestamptextview);
        mTitleTextView = view.findViewById(R.id.questionsummary_item_titletextview);
        mBodyTextView = view.findViewById(R.id.questionsummary_item_bodytextview);
        mUpvoteCountTextView = view.findViewById(R.id.questionsummary_item_upvotecounttextview);
        mDownVoteCountTextView = view.findViewById(R.id.questionsummary_item_downvotecounttextview);
        mAnswerCountTextView = view.findViewById(R.id.questionsummary_item_answercounttextview);
        mUpvoteButtom = view.findViewById(R.id.questionsummary_item_upvotebutton);
        mDownVoteButton = view.findViewById(R.id.questionsummary_item_downvotebutton);
        mAddAnswerTextView = view.findViewById(R.id.questionsummary_item_addanswertextview);

        mPlaceholderForShimmerContainer = view.findViewById(R.id.questionsummary_item_shimmercontainer);
        mFullQuestionSummaryContainer = view.findViewById(R.id.questionsummary_item_fullquestionsummarycontainer);

        mUserDetailsContainer = view.findViewById(R.id.questionsummary_item_userdetailscontainer);
        mTitleBodyContainer = view.findViewById(R.id.questionsummary_item_titlebodycontainer);

        // for rounded corners of profile picture
        mProfilePictureImageView.setClipToOutline(true);
    }

    public void setOnClickListenerToOpenUserProfileInFocus(String uid) {
        View.OnClickListener redirectToUserProfileInFocus
                = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to user profile using uid
            }
        };
        mUserDetailsContainer.setOnClickListener(redirectToUserProfileInFocus);
    }

    public void setOnClickListenerToOpenFullQuestion(String qid) {
        View.OnClickListener redirectToFullQuestion
                = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to full question using qid
            }
        };
        mTitleBodyContainer.setOnClickListener(redirectToFullQuestion);
        mAddAnswerTextView.setOnClickListener(redirectToFullQuestion);
    }

    public ShimmerFrameLayout getmPlaceholderForShimmerContainer() {
        return mPlaceholderForShimmerContainer;
    }

    public LinearLayout getmFullQuestionSummaryContainer() {
        return mFullQuestionSummaryContainer;
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

    public TextView getmTitleTextView() {
        return mTitleTextView;
    }

    public TextView getmBodyTextView() {
        return mBodyTextView;
    }

    public TextView getmUpvoteCountTextView() {
        return mUpvoteCountTextView;
    }

    public TextView getmDownVoteCountTextView() {
        return mDownVoteCountTextView;
    }

    public TextView getmAnswerCountTextView() {
        return mAnswerCountTextView;
    }
}
