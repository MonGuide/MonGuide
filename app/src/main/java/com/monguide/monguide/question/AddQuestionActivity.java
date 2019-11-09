package com.monguide.monguide.question;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.monguide.monguide.R;
import com.monguide.monguide.models.question.Question;
import com.monguide.monguide.utils.DatabaseHelper;

public class AddQuestionActivity extends AppCompatActivity {
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private Button mSubmitButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);
        mTitleEditText = findViewById(R.id.activity_addquestion_titleedittext);
        mDescriptionEditText = findViewById(R.id.activity_addquestion_descriptionedittext);
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
        //DatabaseHelper.getReferenceToParticularQuestion(mQID).setValue(new Question(mTitleEditText.getText().toString(), mDescriptionEditText.getText().toString()));
        Toast.makeText(AddQuestionActivity.this,"Question added",Toast.LENGTH_SHORT).show();
    }

    private boolean checkQuestionDetails() {
        if(TextUtils.isEmpty(mTitleEditText.getText())) {
            mTitleEditText.setError("Title Required");
            return false;
        }
        if(TextUtils.isEmpty(mDescriptionEditText.getText())) {
            mDescriptionEditText.setError("Description required");
            return false;
        }
        return true;
    }
}
