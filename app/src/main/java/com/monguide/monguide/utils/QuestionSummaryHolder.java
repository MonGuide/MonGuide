package com.monguide.monguide.utils;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.monguide.monguide.R;
import com.monguide.monguide.models.question.QuestionSummary;


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
    private ImageView mUpvoteButtom;
    private ImageView mDownVoteButton;
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
        mUpvoteButtom = view.findViewById(R.id.questionsummary_item_upvoteimageview);
        mDownVoteButton = view.findViewById(R.id.questionsummary_item_downvoteimageview);
        mAddAnswerTextView = view.findViewById(R.id.questionsummary_item_addanswertextview);

        mPlaceholderForShimmerContainer = view.findViewById(R.id.questionsummary_item_shimmercontainer);
        mFullQuestionSummaryContainer = view.findViewById(R.id.questionsummary_item_fullquestionsummarycontainer);

        mUserDetailsContainer = view.findViewById(R.id.questionsummary_item_userdetailscontainer);
        mTitleBodyContainer = view.findViewById(R.id.questionsummary_item_titlebodycontainer);

        // for rounded corners of profile picture
        mProfilePictureImageView.setClipToOutline(true);
    }

    public void setOnClickListenerToOpenUserProfileInFocus() {
        View.OnClickListener redirectToUserProfileInFocus
                = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to user profile using uid
            }
        };
        mUserDetailsContainer.setOnClickListener(redirectToUserProfileInFocus);
    }

    public void setOnClickListenerToOpenFullQuestion() {
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

    public void setOnClickListenerToUpvoteDownvoteButtons() {
        mUpvoteButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.getReferenceToParticularQuestion(mQID)
                        .runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        QuestionSummary questionSummary = mutableData.getValue(QuestionSummary.class);
                        questionSummary.setUpvoteCount(questionSummary.getUpvoteCount() + 1);
                        // Set value and report transaction success
                        mutableData.setValue(questionSummary);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {}
                });
            }
        });
        mDownVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.getReferenceToParticularQuestion(mQID)
                        .runTransaction(new Transaction.Handler() {
                            @Override
                            public Transaction.Result doTransaction(MutableData mutableData) {
                                QuestionSummary questionSummary = mutableData.getValue(QuestionSummary.class);
                                questionSummary.setDownvoteCount(questionSummary.getDownvoteCount() + 1);
                                // Set value and report transaction success
                                mutableData.setValue(questionSummary);
                                return Transaction.success(mutableData);
                            }

                            @Override
                            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {}
                        });
            }
        });
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
