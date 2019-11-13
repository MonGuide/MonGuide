package com.monguide.monguide.questionandanswer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.monguide.monguide.R;
import com.monguide.monguide.models.Answer;
import com.monguide.monguide.models.QuestionSummary;
import com.monguide.monguide.utils.Constants;
import com.monguide.monguide.utils.DatabaseHelper;

import java.util.HashMap;

public class FullQuestionActivity extends AppCompatActivity {
    private String mCurrQID;

    private Toolbar mToolbar;

    private EditText mWriteAnswerEditText;
    private Button mSubmitAnswerButton;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullquestion);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        } else {
            mCurrQID = extras.getString(Constants.UID);
        }

        mToolbar = (Toolbar) findViewById(R.id.activity_fullquestion_toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWriteAnswerEditText = findViewById(R.id.activity_fullquestion_writeansweredittext);
        mSubmitAnswerButton = findViewById(R.id.activity_fullquestion_submmitanswerbutton);

        mRecyclerView = findViewById(R.id.activity_fullquestion_recyclerview);
        mProgressBar = findViewById(R.id.activity_fullquestion_progressbar);

        mProgressBar = findViewById(R.id.activity_addquestion_progressbar);

        mSubmitAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mWriteAnswerEditText.getText())) {
                    String answerBody = mWriteAnswerEditText.getText().toString().trim();
                    addAnswerToDatabase(answerBody);
                }
            }
        });
    }

    private void addAnswerToDatabase(String answerBody){
        mSubmitAnswerButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        final String aid = DatabaseHelper.getReferenceToAnswersOfParticularQuestion(mCurrQID).push().getKey();

        Answer answerToAdd = new Answer(FirebaseAuth.getInstance().getCurrentUser().getUid(), answerBody);

        DatabaseHelper.getReferenceToAnswersOfParticularQuestion(mCurrQID).child(aid)
                .setValue(answerToAdd, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if(databaseError == null) {
                            // answer added successfully
                            // clear edittext and increment answer count
                            mWriteAnswerEditText.setText("");
                            DatabaseHelper.getReferenceToParticularQuestion(mCurrQID)
                                    .runTransaction(new Transaction.Handler() {
                                        @Override
                                        public Transaction.Result doTransaction(MutableData mutableData) {
                                            QuestionSummary questionSummary = mutableData.getValue(QuestionSummary.class);
                                            if (questionSummary == null) {
                                                return Transaction.success(mutableData);
                                            }
                                            questionSummary.setAnswerCount(questionSummary.getAnswerCount() + 1);
                                            // Set value and report transaction success
                                            mutableData.setValue(questionSummary);
                                            return Transaction.success(mutableData);
                                        }
                                        @Override
                                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {}
                                    });
                        } else {
                            // rare case
                            // error occured
                            Toast.makeText(FullQuestionActivity.this,
                                    getResources().getString(R.string.error),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                        mProgressBar.setVisibility(View.GONE);
                        mSubmitAnswerButton.setVisibility(View.VISIBLE);
                    }
                });
    }


}
