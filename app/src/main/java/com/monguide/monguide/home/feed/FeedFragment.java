package com.monguide.monguide.home.feed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.google.firebase.database.Query;
import com.monguide.monguide.R;
import com.monguide.monguide.models.question.QuestionSummary;
import com.monguide.monguide.utils.DatabaseHelper;
import com.monguide.monguide.utils.FirebaseQuestionSummaryAdapter;

public class FeedFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FirebaseQuestionSummaryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_feed, container, false);
        mRecyclerView = (RecyclerView) inflatedView.findViewById(R.id.fragment_feed_recyclerview);
        setupRecyclerViewWithAdapter();
        return inflatedView;
    }

    private void setupRecyclerViewWithAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(50);
        Query baseQuery = DatabaseHelper.getReferenceToAllQuestions();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(10)
                .setPageSize(10)
                .build();
        DatabasePagingOptions<QuestionSummary> options = new DatabasePagingOptions.Builder<QuestionSummary>()
                .setLifecycleOwner(this)
                .setQuery(baseQuery, config, QuestionSummary.class)
                .build();
        mAdapter = new FirebaseQuestionSummaryAdapter(options);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}
