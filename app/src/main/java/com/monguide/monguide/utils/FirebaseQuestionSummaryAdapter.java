package com.monguide.monguide.utils;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
import com.firebase.ui.database.paging.LoadingState;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.monguide.monguide.R;
import com.monguide.monguide.models.question.QuestionSummary;

public class FirebaseQuestionSummaryAdapter extends FirebaseRecyclerPagingAdapter<QuestionSummary, QuestionSummaryHolder> {

    private View view;

    public FirebaseQuestionSummaryAdapter(@NonNull DatabasePagingOptions<QuestionSummary> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final QuestionSummaryHolder holder, int position, @NonNull QuestionSummary questionSummary) {
        // Activate shimmer
        holder.getmPlaceholderForShimmerContainer().setVisibility(View.VISIBLE);
        holder.getmFullQuestionSummaryContainer().setVisibility(View.GONE);
        holder.getmPlaceholderForShimmerContainer().startShimmer();

        String qid = getRef(position).getKey();

        // set profile picture
        // this will take time getting from server
        String url = StorageHelper.getReferenceToProfilePictureOfParticularUser(questionSummary.getUid())
                .getDownloadUrl().getResult().toString();
        Glide.with(view.getContext())
                .load(url)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.getmProfilePictureImageView().setImageDrawable(resource);
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {}
                });
        // set rest of the details
        holder.getmTimeStampTextView().setText(questionSummary.getTimestamp().toString());
        holder.getmTitleTextView().setText(questionSummary.getTitle());
        holder.getmBodyTextView().setText(questionSummary.getBody());
        // load them statically and then add listeners
        // for dynamic updation as well
        holder.getmUpvoteCountTextView().setText(questionSummary.getUpvoteCount());
        holder.getmDownVoteCountTextView().setText(questionSummary.getDownvoteCount());
        holder.getmAnswerCountTextView().setText(questionSummary.getAnswerCount());
        DatabaseHelper.getReferenceToParticularQuestion(qid)
                .child("upvoteCount")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.getmUpvoteCountTextView().setText(dataSnapshot.getValue(Integer.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
        DatabaseHelper.getReferenceToParticularQuestion(qid)
                .child("downvoteCount")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.getmDownVoteCountTextView().setText(dataSnapshot.getValue(Integer.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
        DatabaseHelper.getReferenceToParticularQuestion(qid)
                .child("answerCount")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.getmAnswerCountTextView().setText(dataSnapshot.getValue(Integer.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
        // set username, hide shimmer and show data
        DatabaseHelper.getReferenceToParticularUser(questionSummary.getUid())
                .child("name")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.getValue(String.class);
                        holder.getmTitleTextView().setText(name);
                        holder.getmFullQuestionSummaryContainer().setVisibility(View.VISIBLE);
                        holder.getmPlaceholderForShimmerContainer().setVisibility(View.GONE);
                        holder.getmPlaceholderForShimmerContainer().stopShimmer();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
        holder.setOnClickListenerToOpenUserProfileInFocus(questionSummary.getUid());
        holder.setOnClickListenerToOpenFullQuestion(qid);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {

        switch (state) {
            case LOADING_INITIAL:
                // The initial load has begun
                // ...
            case LOADING_MORE:
                // The adapter has started to load an additional page
                // ...
            case LOADED:
                // The previous load (either initial or additional) completed
                // ...
            case ERROR:
                // The previous load (either initial or additional) failed. Call
                // the retry() method in order to retry the load operation.
                // ...
        }
    }

    @NonNull
    @Override
    public QuestionSummaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questionsummary_item, parent, false);
        return new QuestionSummaryHolder(view);
    }
}