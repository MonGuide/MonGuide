package com.monguide.monguide.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.monguide.monguide.R;
import com.monguide.monguide.models.question.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionSummaryAdapter extends RecyclerView.Adapter<QuestionSummaryHolder> {
    private static final String TAG = "QUESTION_SUMMARY_ADAP";

    private List<Question> questionsList = new ArrayList<>();

    public void addQuestionToQuestionsList(Question question) {
        questionsList.add(question);
        notifyItemInserted(questionsList.size() - 1);
    }

    @NonNull
    @Override
    public QuestionSummaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        questionsList.add(new Question("hello", "boys"));
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questionsummary_item, parent, false);
        return new QuestionSummaryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionSummaryHolder holder, int position) {
        Question question = questionsList.get(position);
        holder.getTitleTextView().setText(question.getTitle());
        holder.getBodyTextView().setText(question.getBody());
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }
}
