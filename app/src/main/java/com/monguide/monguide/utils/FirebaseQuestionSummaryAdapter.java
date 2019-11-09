package com.monguide.monguide.utils;

import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        StorageHelper.getReferenceToProfilePictureOfParticularUser(questionSummary.getUid())
                .getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                String url = task.getResult().toString();
                Glide.with(view.getContext())
                        .load(url)
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                holder.getmProfilePictureImageView().setImageDrawable(resource);
                                holder.getmFullQuestionSummaryContainer().setVisibility(View.VISIBLE);
                                holder.getmPlaceholderForShimmerContainer().setVisibility(View.GONE);
                                holder.getmPlaceholderForShimmerContainer().stopShimmer();
                            }
                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {}
                        });
            }
        });
        DatabaseHelper.getReferenceToParticularUser(questionSummary.getUid())
                .child("name")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.getValue(String.class);
                        holder.getmUserNameTextView().setText(name);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
        // set rest of the details
        holder.getmTimeStampTextView().setText(questionSummary.getTimestamp());
        holder.getmTitleTextView().setText(questionSummary.getTitle());
        holder.getmBodyTextView().setText(questionSummary.getBody());
        // load them statically and then add listeners
        // for dynamic updation as well
        holder.getmUpvoteCountTextView().setText(String.valueOf(questionSummary.getUpvoteCount()));
        holder.getmDownVoteCountTextView().setText(String.valueOf(questionSummary.getDownvoteCount()));
        holder.getmAnswerCountTextView().setText(String.valueOf(questionSummary.getAnswerCount()));
        DatabaseHelper.getReferenceToParticularQuestion(qid)
                .child("upvoteCount")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.getmUpvoteCountTextView()
                                .setText(String.valueOf(dataSnapshot.getValue(Integer.class)));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
        DatabaseHelper.getReferenceToParticularQuestion(qid)
                .child("downvoteCount")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.getmDownVoteCountTextView()
                                .setText(String.valueOf(dataSnapshot.getValue(Integer.class)));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
        DatabaseHelper.getReferenceToParticularQuestion(qid)
                .child("answerCount")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.getmAnswerCountTextView()
                                .setText(String.valueOf(dataSnapshot.getValue(Integer.class)));
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