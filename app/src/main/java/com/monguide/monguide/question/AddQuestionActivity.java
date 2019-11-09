package com.monguide.monguide.question;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.monguide.monguide.R;
import com.monguide.monguide.models.question.QuestionSummary;
import com.monguide.monguide.utils.DatabaseHelper;

public class AddQuestionActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private EditText mTitleEditText;
    private EditText mBodyEditText;
    private Button mSubmitButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);
        mToolbar = (Toolbar) findViewById(R.id.activity_addquestion_toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleEditText = findViewById(R.id.activity_addquestion_titleedittext);
        mBodyEditText = findViewById(R.id.activity_addquestion_bodyedittext);
        mSubmitButton = findViewById(R.id.activity_addquestion_submitbutton);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkQuestionDetails())
                    sendToDatabase();
            }
        });
    }

    private void sendToDatabase(){
        String mQID = DatabaseHelper.getReferenceToAllQuestions().push().getKey();
        DatabaseHelper.getReferenceToParticularQuestion(mQID)
                .setValue(
                        new QuestionSummary(
                                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                mTitleEditText.getText().toString(),
                                mBodyEditText.getText().toString()
                        )
                );
        Toast.makeText(AddQuestionActivity.this,"Question added successfully.",Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean checkQuestionDetails() {
        if(TextUtils.isEmpty(mTitleEditText.getText())) {
            mTitleEditText.setError("Title Required");
            return false;
        } else if(TextUtils.isEmpty(mBodyEditText.getText())) {
            mBodyEditText.setError("Description required");
            return false;
        }
        return true;
    }
}
