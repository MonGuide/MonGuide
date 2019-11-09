package com.monguide.monguide.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
import com.firebase.ui.database.paging.LoadingState;
import com.monguide.monguide.R;
import com.monguide.monguide.models.question.Question;

public class FirebaseQuestionSummaryAdapter extends FirebaseRecyclerPagingAdapter<Question, QuestionSummaryHolder> {

    public FirebaseQuestionSummaryAdapter(@NonNull DatabasePagingOptions<Question> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuestionSummaryHolder viewHolder, int position, @NonNull Question question) {
        Log.e("QID", getRef(position).getKey());
        viewHolder.getTitleTextView().setText(question.getTitle());
        viewHolder.getBodyTextView().setText(question.getBody());
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
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questionsummary_item, parent, false);
        return new QuestionSummaryHolder(itemView);
    }
}
