package com.monguide.monguide.home.notifications;

public class Notification {
    private String userId;
    private String text;
    private String questionId;
    private String timeStamp;

    public Notification() {

    }

    public Notification(String userId, String text, String questionId, String timeStamp) {
        this.questionId=questionId;
        this.text=text;
        this.timeStamp=timeStamp;
        this.userId=userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
