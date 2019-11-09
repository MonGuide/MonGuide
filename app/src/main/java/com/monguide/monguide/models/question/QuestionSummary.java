package com.monguide.monguide.models.question;

import com.google.firebase.Timestamp;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class QuestionSummary {
    private String uid;

    private Timestamp timestamp;
    private String title;
    private String body;
    private int upvoteCount;
    private int downvoteCount;
    private int answerCount;

    public QuestionSummary() {}

    public QuestionSummary(String uid, String title, String body) {
        this.uid = uid;
        this.title = title;
        this.body = body;
        this.upvoteCount = 0;
        this.downvoteCount = 0;
        this.answerCount = 0;
        this.timestamp = new Timestamp(new Date());
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getUpvoteCount() {
        return upvoteCount;
    }

    public void setUpvoteCount(int upvoteCount) {
        this.upvoteCount = upvoteCount;
    }

    public int getDownvoteCount() {
        return downvoteCount;
    }

    public void setDownvoteCount(int downvoteCount) {
        this.downvoteCount = downvoteCount;
    }

    public String getTitle() {
        return title;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
