package com.monguide.monguide.home.notifications;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.monguide.monguide.R;
import com.monguide.monguide.home.HomeActivity;

public class NotificationsFragment extends Fragment {
    @Nullable

    private TextView mcomment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_notification, container, false);

        Notification notification = new Notification("Harry Potter upvoted your answer to: What is a black hole?");

        SpannableString ss = new SpannableString(notification.getText());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Log.e("uttam", "clicked");
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 37, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mcomment = inflatedView.findViewById(R.id.notification_item_commenttextview);
        mcomment.setText(ss);
        mcomment.setMovementMethod(LinkMovementMethod.getInstance());
        mcomment.setHighlightColor(Color.TRANSPARENT);

        return inflatedView;
    }
}

