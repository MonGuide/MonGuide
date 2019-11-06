package com.monguide.monguide.utils;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.monguide.monguide.R;


public class QuestionSummaryHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private TextView bodyTextView;

    public QuestionSummaryHolder(View view) {
        super(view);
        titleTextView = view.findViewById(R.id.questionsummary_item_titletextview);
        bodyTextView = view.findViewById(R.id.questionsummary_item_bodytextview);
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public TextView getBodyTextView() {
        return bodyTextView;
    }
}
