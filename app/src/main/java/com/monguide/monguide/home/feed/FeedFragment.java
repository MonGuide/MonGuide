package com.monguide.monguide.home.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.monguide.monguide.R;
import com.monguide.monguide.models.question.Question;
import com.monguide.monguide.utils.QuestionSummaryAdapter;
import com.monguide.monguide.utils.QuestionSummaryHolder;
import com.monguide.monguide.utils.DatabaseHelper;

public class FeedFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private QuestionSummaryAdapter mQuestionSummaryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_feed, container, false);

        mRecyclerView = (RecyclerView) inflatedView.findViewById(R.id.fragment_feed_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mQuestionSummaryAdapter = new QuestionSummaryAdapter();
        mRecyclerView.setAdapter(mQuestionSummaryAdapter);

        for (int i = 0; i < 30; i++) {
            Question question = new Question("This is title " + i, "This is body " + i);
            mQuestionSummaryAdapter.addQuestionToQuestionsList(question);
        }

        populateFeed();
        return inflatedView;
    }

    private void populateFeed() {
        DatabaseHelper.getReferenceToAllQuestions().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
